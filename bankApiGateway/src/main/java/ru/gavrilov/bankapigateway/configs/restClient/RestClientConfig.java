package ru.gavrilov.bankapigateway.configs.restClient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${bank.service.url}")
    private String bankServiceUrl;

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .baseUrl(bankServiceUrl)
                .build();
    }
}
