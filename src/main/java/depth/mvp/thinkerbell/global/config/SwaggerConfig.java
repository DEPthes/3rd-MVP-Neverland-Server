package depth.mvp.thinkerbell.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI getOpenApi() {
                return new OpenAPI().components(new Components())
                        .info(getInfo());
        }
        private Info getInfo() {
                return new Info()
                        .title("Thinkerbell API")
                        .description("Thinkerbell REST API DOC")
                        .version("1.0.0");
        }
}
