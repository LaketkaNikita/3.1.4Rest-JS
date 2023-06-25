package web.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileConverterServiceImpl implements FileConverterService {

    public File convert(MultipartFile multipartFile) throws IOException, InterruptedException {
        // Создаем временный файл
        Path tempDir = Files.createTempDirectory("");
        File input = new File(tempDir.toFile(), multipartFile.getOriginalFilename());
        multipartFile.transferTo(input);

        String outputFilename = "converted_" + input.getName();
        File output = new File(tempDir.toFile(), outputFilename);
        String fileExtension = FilenameUtils.getExtension(input.getName()).toLowerCase();

        ProcessBuilder pb;
        if (fileExtension.equals("mp3")) {
            pb = new ProcessBuilder("lame", "--preset", "standard", input.getAbsolutePath(), output.getAbsolutePath());
        } else if (fileExtension.equals("mp4")) {
            pb = new ProcessBuilder("ffmpeg", "-i", input.getAbsolutePath(), "-vcodec", "libx264", "-crf", "23", output.getAbsolutePath());
        } else if(fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")){
            pb = new ProcessBuilder("cwebp", "-q", "80", input.getAbsolutePath(), "-o", output.getAbsolutePath());
        }else if (fileExtension.equals("exe")|| fileExtension.equals("dll")) {
            pb = new ProcessBuilder("upx", "--best", input.getAbsolutePath(), "-o", output.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        Process process = pb.start();

        // Чтение вывода ошибок
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorOutput = errorReader.lines().collect(Collectors.joining("\n"));

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IllegalStateException("Process failed with exit code: " + exitCode + ", error output: " + errorOutput);
        }
        return output;
    }
    public File compressExecutable(File input) throws IOException, InterruptedException {
        String nameWithoutExtension = FilenameUtils.getBaseName(input.getName());
        String extension = FilenameUtils.getExtension(input.getName());
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File output = new File(tempDirectory + File.separator + nameWithoutExtension + "_" + UUID.randomUUID().toString() + "." + extension);

        ProcessBuilder pb = new ProcessBuilder("upx", "--best", input.getAbsolutePath(), "-o", output.getAbsolutePath());
        Process p = pb.start();

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }
    public File compressVideo(File input) throws IOException {
        String nameWithoutExtension = FilenameUtils.getBaseName(input.getName());
        String extension = FilenameUtils.getExtension(input.getName());
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File output = new File(tempDirectory + File.separator + nameWithoutExtension + "_" + UUID.randomUUID().toString() + "." + extension);

        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", input.getAbsolutePath(), "-vcodec", "libaom-av1", "-b:v", "800k", "-strict", "experimental", "-acodec", "opus", output.getAbsolutePath());
        Process p = pb.start();

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }

    public File compressPicture(File inputFile) throws IOException, InterruptedException {
        String nameWithoutExtension = FilenameUtils.getBaseName(inputFile.getName());
        String extension = FilenameUtils.getExtension(inputFile.getName());
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File output = new File(tempDirectory + File.separator + nameWithoutExtension + "_" + UUID.randomUUID().toString() + "." + extension);

        ProcessBuilder pb = new ProcessBuilder("cwebp", "-q", "80", inputFile.getAbsolutePath(), "-o", output.getAbsolutePath());
        Process p = pb.start();
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }
    public File compressAudio(File input) throws IOException {
        String nameWithoutExtension = FilenameUtils.getBaseName(input.getName());
        String extension = FilenameUtils.getExtension(input.getName());
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File output = new File(tempDirectory + File.separator + nameWithoutExtension + "_" + UUID.randomUUID().toString() + "." + extension);

        ProcessBuilder pb = new ProcessBuilder("lame", "--preset", "standard", input.getAbsolutePath(), output.getAbsolutePath());
        Process p = pb.start();

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }
}




