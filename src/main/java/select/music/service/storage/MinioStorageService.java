package select.music.service.storage;

import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import select.music.infra.properties.MinioProperties;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
    private final MinioProperties properties;

    public String generatePresignedUrl(String objectKey) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(properties.getBucket())
                            .object(objectKey)
                            .expiry(30 * 60) // 30 minutos
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar URL temporária", e);
        }
    }


    @PostConstruct
    void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(properties.getBucket())
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(properties.getBucket())
                                .build()
                );

                log.info("Bucket '{}' criado com sucesso", properties.getBucket());
            }
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erro ao verificar/criar bucket MinIO", e
            );
        }
    }

    @Override
    public String upload(MultipartFile file, String folder) {

        try {
            var filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            var objectName = folder + "/" + filename;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(objectName)
                            .stream(
                                    file.getInputStream(),
                                    file.getSize(),
                                    -1
                            )
                            .contentType(file.getContentType())
                            .build()
            );

            return properties.getUrl()
                    + "/" + properties.getBucket()
                    + "/" + objectName;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem", e);
        }
    }
    private String buildPublicUrl(String objectName) {
        return properties.getUrl()
                + "/" + properties.getBucket()
                + "/" + objectName;
    }
}
