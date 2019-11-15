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
import ru.skillbox.socialnetwork.entities.Person;

import java.io.IOException;
import java.sql.Timestamp;
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

    public Response<FileUploadResponse> fileUpload(MultipartFile file, Person currentUser) {
        logger.info("Загрузка файла {}", file.getName());
        try {
            Cloudinary cloudinary = new Cloudinary("cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName);
            Map uploadResponse = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            FileUploadResponse fileUploadResponse = new FileUploadResponse();
            String photoId = uploadResponse.get("public_id").toString();
            fileUploadResponse.setId(photoId);
            fileUploadResponse.setOwnerId(currentUser.getId());
            fileUploadResponse.setFileName(photoId + "." + uploadResponse.get("format").toString());
            fileUploadResponse.setRelativeFilePath(cloudUri + cloudName + "/image/upload/");
            fileUploadResponse.setRawFileURL(uploadResponse.get("url").toString());
            fileUploadResponse.setFileFormat(uploadResponse.get("format").toString());
            fileUploadResponse.setBytes(Long.valueOf(uploadResponse.get("bytes").toString()));
            fileUploadResponse.setFileType(FileType.IMAGE);
            fileUploadResponse.setCreatedAt(formatter.parse(uploadResponse.get("created_at").toString()).getTime());
            logger.info("Файл {} успешно загружен в {}", file.getName(), fileUploadResponse.getRawFileURL());
            Response response = new Response(fileUploadResponse);
            response.setError("");
            response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
            return response;
        } catch (IOException | ParseException e) {
            logger.error("При загрузке файла {} произошла ошибка {}", file.getName(), e.getMessage());
            new Response<FileUploadResponse>("Не удалось загрузить файл. " + e.getMessage(), null);
        }
        return new Response<>(null);
    }
}
