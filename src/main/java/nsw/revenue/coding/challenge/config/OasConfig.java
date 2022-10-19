package nsw.revenue.coding.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import javax.servlet.http.HttpServletRequest;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class OasConfig {

    @Bean
    private OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info().title("Nsw Revenue Challenge Code")
                    .description(
                        "This API that allows clients to retrieve and add Vehicle."
                    )
                    .version("1.0")
            );
    }

    @Bean
    private GroupedOpenApi userEndpoints() {
        String paths = "/users/**";
        return GroupedOpenApi.builder()
            .group("Vehicle-Api")
            .pathsToMatch(paths)
            .build();
    }

    @Controller
    class RedirectToDocs {

        @GetMapping("/")
        private String greeting(HttpServletRequest httpServletRequest) {
            String prefix = httpServletRequest.getHeader("x-forwarded-prefix");
            return "redirect:" + prefix + "/swagger-ui";
        }
    }


}
