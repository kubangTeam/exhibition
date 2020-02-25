package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.Utilities.JsonBuilder;
import org.springframework.stereotype.Controller;
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
public class FileController {

    //接口 upload
    //功能:提供文件的路径，并上传到服务器端
    @RequestMapping("/upload")
    public ModelAndView upload(@RequestParam("file") MultipartFile file, HttpServletRequest req)
            throws IllegalStateException, IOException {
        System.out.println("upload called!");
        JsonBuilder json = new JsonBuilder();

        // 判断文件是否为空，空则返回失败页面
        if (file.isEmpty()) {
            json.add("result","FAILED");
            return json.getJsonResult();
        }
        // 获取文件存储路径（绝对路径）
        String path = req.getServletContext().getRealPath("/UserPictures");
        // 获取原文件名
        String fileName = file.getOriginalFilename();
        // 创建文件实例
        File filePath = new File(path, fileName);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
        json.add("result","SUCCESS");
        return json.getJsonResult();
    }

    //接口 download
    //功能:提供要下载的文件名称，并下载
    @RequestMapping(value = "/download",produces = "application/json;charset=UTF-8")
    public ModelAndView downloadResource(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("downloadResource Called");
        JsonBuilder json = new JsonBuilder();
        //这里是资源隐藏
        //如果当前session 没有登陆，不能访问
        /*if (session == null || session.getAttribute("loginned") == null) {
            json.add("result","您还没有登录不能访问");
            return json.getJsonResult();
        }*/
        String filename = request.getParameter("fileName");
        String dataDirectory = request.getServletContext().getRealPath("/UserPictures/");

        File file = new File(dataDirectory, filename);
        if (file.exists())
        {
            //设置响应类型，这里是下载pdf文件
            response.setContentType("image");
            //设置Content-Disposition，设置attachment，浏览器会激活文件下载框；filename指定下载后默认保存的文件名
            //不设置Content-Disposition的话，文件会在浏览器内打卡，比如txt、img文件
            response.addHeader("Content-Disposition",
                    "attachment; filename="+filename);
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
        json.add("result","下载成功");
        return json.getJsonResult();
    }
}
