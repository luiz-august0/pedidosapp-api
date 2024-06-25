package com.pedidosapp.api.utils;

import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.model.beans.MultipartBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {

    public static File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new ApplicationGenericsException(e.getMessage());
        }

        return convertedFile;
    }

    public static File convertMultipartBeanToFile(MultipartBean multipartBean) {
        File convertedFile = new File(Objects.requireNonNull(multipartBean.getFilename()));

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartBean.getBytes());
        } catch (IOException e) {
            throw new ApplicationGenericsException(e.getMessage());
        }

        return convertedFile;
    }

}
