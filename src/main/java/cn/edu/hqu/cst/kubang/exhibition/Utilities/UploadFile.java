package cn.edu.hqu.cst.kubang.exhibition.Utilities;

import cn.edu.hqu.cst.kubang.exhibition.util.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadFile {
    public static String uploadFile (String localPath, String webPath, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();  // 获取上传图像的原始文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 获取后缀名
        //为了避免用户传递的图像文件名称相同，需要重新给上传的图像文件命名
        fileName = UUID.randomUUID() + suffixName;
        String path = localPath + "/" + fileName;
        // 创建文件实例
        File dest = new File(path);
        // 如果文件目录不存在，创建目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        // 存储文件
        file.transferTo(dest);
        //图片大于100k 压缩图片
        if(dest.length() > 102400)
           fileName = ImageUtil.generateSingleThumbnail2Directory(localPath,fileName);//fileName加了后缀
        //Web访问路径
        String url = webPath + fileName;
        return url;
    }
}
