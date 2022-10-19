package nsw.revenue.coding.challenge.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.SyntheticModelProviderPlugin;
import springfox.documentation.spi.schema.TypeNameProviderPlugin;

@Configuration
@ComponentScan(basePackages = {
    "springfox.documentation.schema"
})
@EnablePluginRegistries({
    ModelBuilderPlugin.class,
    ModelPropertyBuilderPlugin.class,
    TypeNameProviderPlugin.class,
    SyntheticModelProviderPlugin.class
})
public class ModelConfiguration {

    @Bean
    public TypeResolver typeResolver() {
        return new TypeResolver();
    }
}
