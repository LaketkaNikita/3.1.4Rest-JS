package web.controller;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.service.FileConverterService;
import web.service.RoleService;
import web.service.UserService;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
@RequestMapping("/file")
public class FileController {
    private final UserService userService;
    private final RoleService roleService;
    private final FileConverterService fileConverterService;
    @Autowired
    public FileController(UserService userService, RoleService roleService, FileConverterService fileConverterService) {
        this.userService = userService;
        this.roleService = roleService;
        this.fileConverterService = fileConverterService;
    }
    @PostMapping("/upload")
    public ResponseEntity<Resource> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        String name = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = FilenameUtils.getExtension(name).toLowerCase();

        File convertedFile = null;
        File compressedFile = null;
        try {
            if (extension.equals("mp3")) {
                compressedFile = fileConverterService.compressAudio(convertedFile);
                response.setContentType("audio/mpeg");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
            } else if (extension.equals("mp4")) {
                compressedFile = fileConverterService.compressVideo(convertedFile);
                response.setContentType("video/mp4");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
            } else if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                compressedFile = fileConverterService.compressPicture(convertedFile);
                response.setContentType("image/" + extension);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
            }else if(extension.equals("exe") || extension.equals("dll")){
                compressedFile = fileConverterService.compressExecutable(convertedFile);
                response.setContentType("application/octet-stream");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
            } else {
                throw new IllegalArgumentException("Invalid file type");
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(compressedFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (convertedFile != null) {
                convertedFile.deleteOnExit();
            }
            if (compressedFile != null) {
                compressedFile.deleteOnExit();
            }
        }
    }
    @PostMapping("/convert")
    public ResponseEntity<Resource> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            File convertedFile = fileConverterService.convert(file);
            Path path = Paths.get(convertedFile.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + originalFilename)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
