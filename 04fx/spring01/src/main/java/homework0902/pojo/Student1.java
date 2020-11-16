package homework0902.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student1 implements Student {
    
    private int id;
    private String name;

    @Override
    public void dingdo() {
        System.out.println("Student1");
    }
}
