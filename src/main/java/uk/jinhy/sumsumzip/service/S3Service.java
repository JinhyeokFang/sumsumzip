package uk.jinhy.sumsumzip.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloudfront.url}")
    private String cloudFront;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        var filename = multipartFile.getOriginalFilename();
        var extension = filename.substring(filename.lastIndexOf(".") + 1);
        var newFilename = Timestamp.valueOf(LocalDateTime.now()) + "." + extension;

        var metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(
                bucket,
                newFilename,
                multipartFile.getInputStream(),
                metadata
        );
        return cloudFront + newFilename;
    }
}