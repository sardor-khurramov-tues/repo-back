package repo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE");

        registry.addMapping("/**") // This applies the CORS policy to all endpoints
                .allowedOrigins("http://localhost:3001") // Specify the exact origin of your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow all necessary methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Crucially, allow credentials (cookies) to be sent
    }
}
