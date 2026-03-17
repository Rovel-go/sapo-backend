package sapoCasaPrincesas;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication   // Punto de entrada principal de Spring Boot
@ServletComponentScan    // Activa el escaneo automático de clases anotadas con @WebServlet
public class RegistroLoginApplication {

    public static void main(String[] args) {
        // Arranca toda la aplicación Spring Boot
        SpringApplication.run(RegistroLoginApplication.class, args);
    }
}
