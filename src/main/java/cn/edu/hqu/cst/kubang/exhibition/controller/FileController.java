package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.dao.UserInformationDao;
import cn.edu.hqu.cst.kubang.exhibition.dao.UserSessionDao;
import cn.edu.hqu.cst.kubang.exhibition.entity.UserInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

//与文件上传下载有关的控制器

@Controller
@RequestMapping("/file")
@Api(tags = "文件上传/下载")
public class FileController {

    @Autowired
    private UserInformationDao userDao;

    @Autowired
    private UserSessionDao sessionDao;

    //接口 上传用户头像
    @ApiOperation(value = "上传用户头像", notes = "接口待修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query")
    })
    @PostMapping("/upload/UserPic")
    public ModelAndView UploadUserPictures(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        JsonBuilder json = new JsonBuilder();

        //这里还是根据会话ID 判断登录的用户的ID
        String sessionId = request.getRequestedSessionId();
        if (sessionId == null) {
            json.add("success", "false");
            json.add("errCode", "1001");
            json.add("errMsg", "上传文件失败，还没有登陆!");
            return json.getJsonResult();
        }

        if (file == null) {
            json.add("success", "false");
            json.add("errCode", "1002");
            json.add("errMsg", "上传文件失败，文件为空!");
            return json.getJsonResult();
        }
        int userId = sessionDao.queryBySessionId(sessionId);
        UserInformation user = userDao.GetUserInfoFromId(Integer.valueOf(userId));

        //这里 存储的用户头像的文件名是 就是用户上传的文件名
        //存储路径是/UserPictures
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        return Upload(file, request.getServletContext().getRealPath("/UserPictures"), file.getName());
    }

    private ModelAndView Upload(MultipartFile file, String path, String fileName)
            throws IllegalStateException, IOException {
        JsonBuilder json = new JsonBuilder();

        // 创建文件实例
        File filePath = new File(path, fileName);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
        json.add("success", "true");
        return json.getJsonResult();
    }

    //接口 download
    //功能:提供要下载的文件名称，并下载
    @ApiOperation(value = "提供要下载的文件名称，并下载", notes = "无参，接口待修改")
    @PostMapping(value = "/download/UserPic", produces = "application/json;charset=UTF-8")
    public ModelAndView DownloadUserPictures(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        JsonBuilder json = new JsonBuilder();

        //这里还是根据会话ID 判断登录的用户的ID
        String sessionId = request.getRequestedSessionId();
        if (sessionId == null) {
            json.add("success", "false");
            json.add("errCode", "1101");
            json.add("errMsg", "下载文件失败，还没有登陆!");
            return json.getJsonResult();
        }
        int userId = sessionDao.queryBySessionId(sessionId);
        UserInformation user = userDao.GetUserInfoFromId(Integer.valueOf(userId));
        String fileName = user.getUserPicture();//文件名就是用户信息中的UserPictrue字段

        return Download(request.getServletContext().getRealPath("/UserPictures"), fileName, response);
    }

    private ModelAndView Download(String path, String fileName, HttpServletResponse response) {

        JsonBuilder json = new JsonBuilder();

        File file = new File(path, fileName);
        if (file.exists()) {
            //设置响应类型，这里是下载pdf文件
            response.setContentType("image");
            //设置Content-Disposition，设置attachment，浏览器会激活文件下载框；filename指定下载后默认保存的文件名
            //不设置Content-Disposition的话，文件会在浏览器内打卡，比如txt、img文件
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            // if using Java 7, use try-with-resources
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException ex) {
                // do something,
                // probably forward to an Error page
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        json.add("success", "true");
        return json.getJsonResult();
    }
}
