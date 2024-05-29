package com.example.ebookstorebackend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FileAndImage {
    private static String getAbsolutePath(String relativePath) {
        return System.getProperty("user.dir") + "/src/main/resources/static" + relativePath;
    }

    static public void saveFile(MultipartFile file, String relativePath) throws IOException {
        String path = getAbsolutePath(relativePath);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File newFile = new File(path + "/" + file.getOriginalFilename());
        file.transferTo(newFile); // MultipartFile提供的存储文件的方法
    }

    static public void saveImage(String base64String, String relativePath) throws IOException {
        String path = getAbsolutePath(relativePath);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        byte[] bytes = Base64.getDecoder().decode(base64String);
        File newFile = new File(path);
        Files.write(newFile.toPath(), bytes);
    }

    public static String encodeImageToBase64(File imageFile) throws IOException {
        // 读取图片文件的字节
        byte[] bytes = Files.readAllBytes(imageFile.toPath());

        // 使用Base64编码字节
        String base64String = Base64.getEncoder().encodeToString(bytes);

        return base64String;
    }

    static public void deleteFile(String relativePath) {
        String path = getAbsolutePath(relativePath);
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    static public String readImage(String relativePath) throws IOException {
        // 返回base64编码的图片
        if (!relativePath.endsWith(".jpg") && !relativePath.endsWith(".png")) {
            System.out.println("不支持的图片格式");
            return null;
        }
        String path = getAbsolutePath(relativePath);
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("图片不存在");
            return null;
        }
        return encodeImageToBase64(file);
    }
}
