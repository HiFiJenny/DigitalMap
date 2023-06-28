package com.comac.common.minio.configure;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Descripiton minio 配置文件
 * @Author huangcheng
 * @Date 2023/1/4 13:43
 * @Version 1.0.0
 */
@Configuration
@EnableCaching
public class MinioConfigure {

    @Value("${spring.minio.endPoint}")
    private String endPoint;

    @Value("${spring.minio.port}")
    private int port;

    @Value("${spring.minio.secure}")
    private Boolean secure;

    @Value("${spring.minio.accessKey}")
    private String accessKey;

    @Value("${spring.minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endPoint,port,secure)
                .credentials(accessKey, secretKey)
                .build();
    }
}
