package in.governance.samagra.apps.kafka.producer;

import in.governance.samagra.apps.kafka.producer.model.YellowTrip;
import in.governance.samagra.apps.kafka.producer.utils.DataReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import in.governance.samagra.apps.kafka.producer.config.YamlProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@SpringBootApplication
@Component
public class SamagraKafkaProducerApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private YamlProperties yamlProperties;

    public static void main(String[] args) {SpringApplication.run(SamagraKafkaProducerApplication.class, args);}

       @Override
       public void run(String... args) throws Exception {
           Properties props = new Properties();
           props.put(ProducerConfig.CLIENT_ID_CONFIG,yamlProperties.getClientIdConfig());
           props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,yamlProperties.getBootStrapServers());
           props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, yamlProperties.getKeySerializer());
           props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, yamlProperties.getValueSerializer());


           logger.info("Producer has been created...Start sending Student Record ");
           KafkaProducer<String, YellowTrip> producer = new KafkaProducer<String,YellowTrip>(props);
           DataReader dataReader = new DataReader();
           List<YellowTrip> yellowTripList = dataReader.ReadYellowTripCSVFile(yamlProperties.getDataFileLocation()+"/"+yamlProperties.getDataFileName());
           for (Object yellowTripItem : yellowTripList) {
               YellowTrip yellowTrip = (YellowTrip) yellowTripItem;
               producer.send(new ProducerRecord<>(yamlProperties.getTopicName(),((YellowTrip) yellowTripItem).getTpepPickupDatetime(),yellowTrip));
           }
           logger.info("Producer has sent all Trip records successfully...");
           producer.close();
       }
    }


