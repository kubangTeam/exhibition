package cn.edu.hqu.cst.kubang.exhibition.Utilities;

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
        // 创建文件实例
        File dest = new File(localPath + "/" + fileName);
        // 如果文件目录不存在，创建目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            System.out.println("创建目录" + dest);
        }
        // 存储文件
        file.transferTo(dest);
        //Web访问路径
        String url = webPath + fileName;
        return url;
    }
}
