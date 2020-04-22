package cn.edu.hqu.cst.kubang.exhibition.Utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class upload {
    /**
     *
     * @param path 指定上传的位置
     * @param file 需要上传的文件
     * @return
     * @throws IOException
     */
    public static String uploadFile(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        // 创建文件实例
        File filePath = new File(path, fileName);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
        return filePath.toString();
    }
    //add annotation
}
