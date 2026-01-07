package br.org.creapi.sigec.api.configs.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public ClientErrorDecoderConfig errorDecoder() {
        return new ClientErrorDecoderConfig();
    }
}
