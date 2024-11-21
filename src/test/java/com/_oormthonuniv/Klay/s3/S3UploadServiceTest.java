package com._oormthonuniv.Klay.s3;

import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Import(S3MockConfig.class)
@SpringBootTest
@ActiveProfiles("dev")
class S3UploadServiceTest {
    @Autowired
    private S3Mock s3Mock;
    @Autowired
    private S3UploadService s3UploadService;

    @AfterEach
    public void tearDown() {
        s3Mock.stop();
    }

    @Test
    void 업로드_성공() {
        // given
        String path = "test.png";
        String contentType = "image/png";
        String dirName = "test";

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        // when
        String urlPath = s3UploadService.uploadFile(file);

        // then
        assertThat(urlPath).contains(path);
        assertThat(urlPath).contains(dirName);
    }
}