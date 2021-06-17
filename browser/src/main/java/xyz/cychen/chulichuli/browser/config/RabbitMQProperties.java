package xyz.cychen.chulichuli.browser.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;

//@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {
    private String host, qname;
}
