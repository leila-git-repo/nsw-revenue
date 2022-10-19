package nsw.revenue.coding.challenge.config;

import static springfox.documentation.builders.PathSelectors.regex;

import com.fasterxml.classmate.TypeResolver;
import java.util.Collections;
import java.util.List;
import nsw.revenue.coding.challenge.model.Credential;
import nsw.revenue.coding.challenge.model.Insurer;
import nsw.revenue.coding.challenge.model.Registration;
import nsw.revenue.coding.challenge.model.Vehicle;
import nsw.revenue.coding.challenge.model.VehicleRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class ApiConfiguration {

    private static final String API_KEY_NAME = "JWT";

    private final TypeResolver typeResolver;

    @Autowired
    public ApiConfiguration(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("user-vehicle-Api")
            .useDefaultResponseMessages(false)
            .select()
            .paths(regex("/users"))
            .build()
            .apiInfo(createApiInfo())
            .securitySchemes(Collections.singletonList(apiKey()))
            .securityContexts(Collections.singletonList(securityContext()))
            .additionalModels(typeResolver.resolve(Insurer.class))
            .additionalModels(typeResolver.resolve(Credential.class))
            .additionalModels(typeResolver.resolve(Registration.class))
            .additionalModels(typeResolver.resolve(Vehicle.class))
            .additionalModels(typeResolver.resolve(VehicleRegistration.class));

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(API_KEY_NAME, authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey(API_KEY_NAME, HttpHeaders.AUTHORIZATION, ApiKeyVehicle.HEADER.getValue());
    }

    private ApiInfo createApiInfo() {
        return new ApiInfoBuilder().title("Nsw Revenue API")
            .description("Some Api for Vehicle")
            .version("1.0")
            .build();
    }
}
