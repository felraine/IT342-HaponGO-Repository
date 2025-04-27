package edu.cit.hapongo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://hapongo-backend-819908927275.asia-southeast1.run.app");
        server.setDescription("Hapongo backend server");

        return new OpenAPI()
                .info(new Info().title("Hapongo API").version("v0"))
                .servers(List.of(server));
    }
}
