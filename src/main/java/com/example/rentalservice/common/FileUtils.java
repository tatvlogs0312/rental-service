package com.example.rentalservice.common;

import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.exception.ExceptionEnums;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileUtils {

    /**
     * Convert MultipartFile to File
     *
     * @param file
     * @return
     */
    public static File convertFile(MultipartFile file) {
        log.info("FileUtil convertFile start ... ");
        try {
            File f = new File(System.getProperty("java.io.tmpdir") +
                                "tmp_" + UUID.randomUUID() +
                                "_" +
                                file.getName() +
                                ".jpg");
            file.transferTo(f);
            return f;
        } catch (IOException ex) {
            log.error("FileUtil convertFile ex: " + ex);
            throw new ApplicationException(ExceptionEnums.DATA_NOT_VALID.getMessage());
        }
    }

    public static File convertFileV2(MultipartFile multipartFile) {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            file.createNewFile(); // Create the file if it doesn't exist
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes()); // Write the bytes of the multipart file to the file
            }
            return file;
        } catch (Exception e) {
            throw new ApplicationException(ExceptionEnums.EX_INTERNAL_SERVER.getMessage());
        }
    }

    public static File convertFileV2(byte[] bytes) {
        try {
            File file = new File("file_temp");
            file.createNewFile(); // Create the file if it doesn't exist
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes); // Write the bytes of the multipart file to the file
            }
            return file;
        } catch (Exception e) {
            throw new ApplicationException(ExceptionEnums.EX_INTERNAL_SERVER.getMessage());
        }
    }


    /**
     * Xóa File tạm đã tạo
     *
     * @param file
     */
    public static void cleanFileNotNull(File file) {
        if (file != null && file.isFile()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                throw new ApplicationException(ExceptionEnums.EX_INTERNAL_SERVER.getMessage());
            }

        }
    }
}
