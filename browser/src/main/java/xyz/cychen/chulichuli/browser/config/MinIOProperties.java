package xyz.cychen.chulichuli.browser.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinIOProperties {
    private String url, accessKey, secretKey;
    private String bkt360, bkt720, bktOrig;
}
