package xyz.cychen.chulichuli.browser.model;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xyz.cychen.chulichuli.browser.config.MinIOProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Configuration
@EnableConfigurationProperties(MinIOProperties.class)
public class MinIO {

    public enum Bucket {
        BOrig,
        B360,
        B720
    }

    private MinIOProperties minIOProperties;

    @Autowired
    private MinioClient minioClient;

    public MinIO(MinIOProperties minIOProperties) {
        this.minIOProperties = minIOProperties;
    }

    @Bean
    public MinioClient minIOClientBean() {
        return MinioClient
                .builder()
                .endpoint(minIOProperties.getUrl())
                .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                .build();
    }


    public void saveMultipartFile(MultipartFile file, String fileName, Bucket b) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bkt = "";
        switch (b) {
            case BOrig:
                bkt = minIOProperties.getBktOrig();
                break;
            case B360:
                bkt = minIOProperties.getBkt360();
                break;
            case B720:
                bkt = minIOProperties.getBkt720();
                break;
            default:
                throw new IOException("No such bucket");
        }
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bkt).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bkt).build());
        }
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bkt)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    public void saveFile(File file, String fileName, Bucket b) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bkt = "";
        switch (b) {
            case BOrig:
                bkt = minIOProperties.getBktOrig();
                break;
            case B360:
                bkt = minIOProperties.getBkt360();
                break;
            case B720:
                bkt = minIOProperties.getBkt720();
                break;
            default:
                throw new IOException("No such bucket");
        }
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bkt).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bkt).build());
        }
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bkt)
                        .object(fileName)
                        .stream(new FileInputStream(file), file.length(), -1)
                        .build()
        );
    }

    public InputStream get(String fileName, Bucket b) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bkt = "";
        switch (b) {
            case BOrig:
                bkt = minIOProperties.getBktOrig();
                break;
            case B360:
                bkt = minIOProperties.getBkt360();
                break;
            case B720:
                bkt = minIOProperties.getBkt720();
                break;
            default:
                throw new IOException("No such bucket");
        }

        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bkt)
                        .object(fileName)
                        .build()
        );
    }
//    public boolean fileExists(String fileName, Bucket bkt) {
//        String bktName = null;
//        switch (bkt) {
//            case BOrig:
//                bktName = "vorig";
//                break;
//            case B360:
//                bktName = "v360";
//                break;
//            case B720:
//                bktName = "v720";
//                break;
//        }
//        try {
//            minioClient.statObject(StatObjectArgs.builder().bucket(bktName).object(fileName).build());
//            return true;
//        }
//        catch (ErrorResponseException e) {
//            if (e.errorResponse().code() == )
//        }
//    }
}
