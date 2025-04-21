package sistema.academico.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder plainTextPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString(); // No codifica
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword); // Compara en texto plano
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(plainTextPasswordEncoder()); // Usar el codificador de texto plano
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF por ahora (considerar habilitarlo en producción)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll() // Permitir acceso a v3 API docs
                        .requestMatchers("/swagger-ui/**").permitAll() // Permitir acceso a Swagger UI
                        .requestMatchers("/api/estudiantes/**").hasRole("ESTUDIANTE") // Solo los estudiantes pueden
                                                                                                    // acceder a /api/estudiantes/**
                        .requestMatchers("/api/docentes/**").hasRole("DOCENTE") // Solo los docentes pueden acceder a
                                                                                                  // /api/docentes/**
                        .requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR") // Solo los administradores pueden
                                                                                                    // acceder a /api/admin/**
                        .requestMatchers(HttpMethod.GET, "/api/cursos/**").permitAll() // Permitimos acceso de lectura a
                                                                                                      // todos los cursos
                        .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasRole("DOCENTE") // Solo los docentes
                                                                                                       // pueden crear cursos
                        .anyRequest().authenticated() // Todas las demás peticiones requieren autenticación
                )
                .formLogin(form -> form.disable()) // Deshabilitamos el formulario de login por defecto
                .httpBasic(basic -> basic.disable()) // Deshabilitamos la autenticación básica HTTP por defecto
                .logout(logout -> logout.permitAll()) // Permitimos acceso al logout sin autenticación
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}