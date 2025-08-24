package com.employee.skill_tracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @author piyumi_navodani
 */

@Configuration
public class SwaggerConfig {
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Skill API")
                        .version("1.0")
                        .description("ngBoot API for skill management"));
    }
}
