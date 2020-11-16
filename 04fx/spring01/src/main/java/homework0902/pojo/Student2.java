package homework0902.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component(value = "student2")
public class Student2 implements Student {
    @Value("002")
    private int id;
    @Value("西元2")
    private String name;

    @Override
    public void dingdo() {
        System.out.println("Student2");
    }
}
