package sapoCasaPrincesas.registro_login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Desactivo CSRF porque la API se consume desde un frontend separado (Vite/React)
                .csrf(csrf -> csrf.disable())

                // Por ahora permito todas las rutas sin autenticación
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // Habilito autenticación básica (útil para pruebas)
                .httpBasic(Customizer.withDefaults())

                // Habilito el formulario de login por defecto de Spring (aunque no lo use)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt para cifrar contraseñas antes de guardarlas en la base de datos
        return new BCryptPasswordEncoder();
    }
}

















