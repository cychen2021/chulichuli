package xyz.cychen.chulichuli.browser.model;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import xyz.cychen.chulichuli.browser.config.RabbitMQProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;


@Component
@Configuration
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQ {
    @Autowired
    private Channel channel;

    private RabbitMQProperties properties;

    public RabbitMQ(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Channel channelBean() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getHost());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//        channel.exchangeDeclare(properties.getExchange(), BuiltinExchangeType.DIRECT);
        channel.queueDeclare(properties.getQname(), false, false, false, null);
        return channel;
    }

    public String cosume(DeliverCallback callback) throws IOException {
        return channel.basicConsume(properties.getQname(), true, callback, consumerTag -> {});
    }

    public GetResponse get() throws IOException {
        GetResponse response = channel.basicGet(properties.getQname(), true);
        return response;
    }

    public void send(String message) throws IOException {
//        String ts = new SimpleDateFormat("yyyy-MM-dd,HH-mm-ss").format(new Date());
//        String withTs = ts + " " + message;
//        Mess
        AMQP.BasicProperties mp = MessageProperties.TEXT_PLAIN.builder().timestamp(new Date()).build();
        channel.basicPublish("", properties.getQname(), mp, message.getBytes(StandardCharsets.UTF_8));
    }
}
