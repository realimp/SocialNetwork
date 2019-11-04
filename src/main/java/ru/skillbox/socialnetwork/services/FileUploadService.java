package ru.skillbox.socialnetwork.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.api.responses.FileType;
import ru.skillbox.socialnetwork.api.responses.FileUploadResponse;
import ru.skillbox.socialnetwork.api.responses.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class FileUploadService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${com.cloudinary.cloud_name}")
    private String cloudName;
    @Value("${com.cloudinari.url}")
    private String cloudUri;
    @Value("${com.cloudinary.api_key}")
    private String apiKey;
    @Value("${com.cloudinary.api_secret}")
    private String apiSecret;

    private final DateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public Response<FileUploadResponse> fileUpload(MultipartFile file) {
        logger.info("Загрузка файла {}", file.getName());
        if (file.isEmpty()) {
            logger.error("Не удалось загрузить файл, потому что файл пустой");
            return new Response<>("Не удалось загрузить файл, потому что файл пустой", null);
        }
        if (file.getContentType() == null || !file.getContentType().contains("image")) {
            logger.error("Не удалось загрузить файл, тип файлов {} не поддерживается.", file.getContentType());
            return new Response<>("Не удалось загрузить файл, данный тип файлов не поддерживается", null);
        }
        try {
            Cloudinary cloudinary = new Cloudinary("cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName);
            Map response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            FileUploadResponse fileUploadResponse = new FileUploadResponse();
            fileUploadResponse.setId(response.get("public_id").toString());
            fileUploadResponse.setFileName(file.getName());
            fileUploadResponse.setRelativeFilePath(cloudUri + cloudName + "/" + response.get("resource_type").toString()
                    + "/" + response.get("type").toString() + "/c_thumb,w_100,h_100/v" + response.get("version").toString() + "/"
                    + response.get("public_id").toString() + "." + response.get("format").toString());
            fileUploadResponse.setRawFileURL(response.get("url").toString());
            fileUploadResponse.setFileFormat(response.get("format").toString());
            fileUploadResponse.setBytes(Long.valueOf(response.get("bytes").toString()));
            fileUploadResponse.setFileType(FileType.IMAGE);
            fileUploadResponse.setCreatedAt(formatter.parse(response.get("created_at").toString()).getTime());
            //TODO: установить идентификатор текущего пользователя
            fileUploadResponse.setOwnerId(0);
            logger.info("Файл {} успешно загружен в {}", file.getName(), fileUploadResponse.getRawFileURL());
            return new Response<>(fileUploadResponse);
        } catch (IOException | ParseException e) {
            logger.error("При загрузке файла {} произошла ошибка {}", file.getName(), e.getMessage());
            new Response<FileUploadResponse>("Не удалось загрузить файл. " + e.getMessage(), null);
        }
        return new Response<>(null);
    }
}
