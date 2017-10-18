package app;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by gleb on 05.10.17.
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class ConfigFileConfigurer {
}
