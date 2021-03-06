package ru.skillbox.socialnetwork.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.api.responses.FileType;
import ru.skillbox.socialnetwork.api.responses.FileUploadResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.Person;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUploadServiceTest {

    @Autowired
    private FileUploadService fileUploadService = new FileUploadService();

    @Test
    public void fileUploadTest() throws IOException {
        Person person = new Person();
        person.setId(0);
        File file = new File("d://test.jpg"); // path to test file
        FileInputStream input = new FileInputStream(file);
        String fileName = file.getName();
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, new MimetypesFileTypeMap().getContentType(fileName), input);
        Response<FileUploadResponse> response = fileUploadService.fileUpload(multipartFile, person);
        Assert.assertEquals("jpg", response.getData().getFileFormat());
        Assert.assertNotNull(response.getData().getId());
        Assert.assertTrue(response.getData().getRawFileURL().contains(response.getData().getId()));
        Assert.assertEquals((Long) file.length(), response.getData().getBytes());
        Assert.assertEquals(FileType.IMAGE, response.getData().getFileType());
        Assert.assertNotNull(response.getData().getCreatedAt());
        Assert.assertEquals(person.getId(), response.getData().getOwnerId());
    }
}
