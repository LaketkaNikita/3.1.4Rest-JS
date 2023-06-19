package web.service;



import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileConverterService {
    public File convert(MultipartFile multipartFile) throws IOException, InterruptedException;
    public File compressExecutable(File input) throws IOException, InterruptedException;
    public File compressVideo(File input) throws IOException;
    public File compressPicture(File inputFile) throws IOException, InterruptedException;
    public File compressAudio(File input) throws IOException;
}
