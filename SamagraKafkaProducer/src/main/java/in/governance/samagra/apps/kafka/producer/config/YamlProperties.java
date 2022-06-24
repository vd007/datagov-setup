package in.governance.samagra.apps.kafka.producer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
@Configuration
@EnableConfigurationProperties
@Getter
@Setter
public class YamlProperties {
    @Value("${app.kafka.producer.topic}")
    private String topicName;
    @Value("${app.kafka.producer.client-id-config}")
    private String clientIdConfig;
    @Value("${app.kafka.producer.data-file-location}")
    private String dataFileLocation;
    @Value("${app.kafka.producer.data-file-name}")
    private String dataFileName;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;
    @Value("${spring.kafka.properties.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.properties.producer.value-serializer}")
    private String valueSerializer;
}
