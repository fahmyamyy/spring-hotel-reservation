package com.amyganz.notificationservice.configs;

import java.util.HashMap;
import java.util.Map;

import com.amyganz.notificationservice.dtos.ReservationDetails;
import com.amyganz.notificationservice.entities.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;


@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    @Value("${port.kafka}")
    private String PORT_KAFKA;
    @Bean
    public ConsumerFactory<String, ReservationDetails> consumerFactoryReservation() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, PORT_KAFKA);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "email-doc");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(ReservationDetails.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationDetails> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationDetails> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryReservation());
        return factory;
    }

}
