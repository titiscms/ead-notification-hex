package com.ead.notificationhex.adapters.configs;

import com.ead.notificationhex.NotificationHexApplication;
import com.ead.notificationhex.core.ports.NotificationPersistencePort;
import com.ead.notificationhex.core.services.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationHexApplication.class)
public class BeanConfiguration {

    @Bean
    public NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        return new NotificationServicePortImpl(notificationPersistencePort);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
