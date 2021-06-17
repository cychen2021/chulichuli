package xyz.cychen.chulichuli.browser.controller;

import io.minio.errors.*;
import lombok.Data;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.cychen.chulichuli.browser.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Configuration
@ComponentScan(basePackageClasses = {xyz.cychen.chulichuli.browser.model.VideoRepository.class})
public class MainController {
    private MinIO minIO;
    private final VideoRepository videoRepository;
    private RabbitMQ rabbitMQ;

    public MainController(MinIO minIO, RabbitMQ rabbitMQ, VideoRepository videoRepository) {
        this.minIO = minIO;
        this.videoRepository = videoRepository;
        this.rabbitMQ = rabbitMQ;
    }

    private class DuplicateVideoException extends Exception { }

    private final String[] allowedFormats = {"mp4"};

    private void addVideoEntry(String fileName) throws DuplicateVideoException {
        if (videoRepository.existsByFileName(fileName)) {
            throw new DuplicateVideoException();
        }
        videoRepository.save(new VideoEntry(null, fileName));
    }

    @PutMapping("/video")
    ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean unsupportedFmt = true;
        for (String f: allowedFormats) {
            Pattern p = Pattern.compile(".+\\." + f);
            Matcher m = p.matcher(fileName);
            if (m.matches()) {
                unsupportedFmt = false;
                break;
            }
        }
        if (unsupportedFmt) {
            return ResponseEntity.status(415).build();
        }

        try {
            addVideoEntry(fileName);
            minIO.saveMultipartFile(file, fileName, MinIO.Bucket.BOrig);
            rabbitMQ.send(
                    String.format("{%s}{%s}{%s}", fileName, fileName, fileName)
            );
            return ResponseEntity.ok().build();
        }
        catch (DuplicateVideoException e) {
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getList() {
            Iterable<VideoEntry> list = videoRepository.findAll();
            List<String> result = new ArrayList<>();
            for (VideoEntry l: list) {
                result.add(l.getFileName());
            }

            return ResponseEntity.ok(result);
    }

    private class NotFoundException extends Exception {}
    private class BadResolutionException extends Exception {}

    public InputStream getVideoFile(String fileName, String resolution)
            throws BadResolutionException, NotFoundException, ServerException,
            InsufficientDataException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException,
            InternalException, ErrorResponseException {
        MinIO.Bucket bkt = null;
        switch (resolution) {
            case "orig":
                bkt = MinIO.Bucket.BOrig;
                break;
            case "720p":
                bkt = MinIO.Bucket.B720;
                break;
            case "360p":
                bkt = MinIO.Bucket.B360;
                break;
            default:
                throw new BadResolutionException();
        }
        try {
            return minIO.get(fileName, bkt);
        }
        catch (ErrorResponseException e) {
            if(e.errorResponse().code().equals("NoSuchKey")) {
                throw new NotFoundException();
            }
            throw e;
        }
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<?> download(@PathVariable("id") String id,
                                      @RequestParam("resolution") String resolution,
                                      @RequestParam("inline") Boolean inline)
            throws ServerException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ErrorResponseException {
        try{
            InputStream f = getVideoFile(id, resolution);
            ByteArrayResource r = new ByteArrayResource(f.readAllBytes());
            Pattern p = Pattern.compile(".+\\.(.+)");
            Matcher m = p.matcher(id);
            if (!m.matches()) {
                throw new InvalidFileNameException(id, "Wrong format");
            }
            String suffix = m.group(1);

            HttpHeaders headers = new HttpHeaders();
            if (inline) {
                headers.setContentDisposition(ContentDisposition.inline().build());
            }
            else {
                headers.setContentDisposition(ContentDisposition.attachment().filename(id).build());
            }
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(r.contentLength())
                    .contentType(MediaType.parseMediaType("video/"+suffix))
                    .body(r);
        } catch (BadResolutionException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
