package config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MagicConfig {
    @Bean
    public BeanPostProcessor beanPostProcessor(){
        return new MagicBeanPostProcessor();
    }
}
