package homework0902.pojo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StuConfig{
    @Bean(name = "stu1")
    public Student getStudent() {
        return new Student1(3,"xiyuan3");
    }

}
