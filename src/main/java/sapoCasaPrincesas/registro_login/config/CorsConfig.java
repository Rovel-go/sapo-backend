package sapoCasaPrincesas.registro_login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // Habilito CORS para permitir que el frontend (Vite/React) pueda consumir la API
                registry.addMapping("/**") // Aplica a todos los endpoints del backend

                        // Origen permitido: mi frontend corriendo en localhost:5173 y en Netlify
                        .allowedOrigins("http://localhost:5173")
                        "https://sapocasaprincesas.netlify.app"

                        // Métodos HTTP que permito usar desde el frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // Permito cualquier header enviado desde el cliente
                        .allowedHeaders("*")

                        // Permito el envío de cookies o tokens si fuera necesario
                        .allowCredentials(true);
            }
        };
    }

}
