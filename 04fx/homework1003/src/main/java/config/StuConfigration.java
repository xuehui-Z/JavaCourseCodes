package config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pojo.Student;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(Student.class)
public class StuConfigration {
    @Bean
    public Map getBean(Student student){
        Map result = new HashMap<String, String>();
        result.put("ID",student.getId());
        result.put("name",student.getName());
        return result;
    }

}
