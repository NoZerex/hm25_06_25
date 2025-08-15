

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean("restTemplateStandard")
    public RestTemplate restTemplateStandard() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 секунд
        factory.setReadTimeout(5000);    // 5 секунд
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
}
