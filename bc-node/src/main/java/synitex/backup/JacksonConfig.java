package synitex.backup;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper getObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("dd.MM.yyyy mm:hh:ss"))
                .serializationInclusion(Include.NON_NULL)
                .build();
    }

}
