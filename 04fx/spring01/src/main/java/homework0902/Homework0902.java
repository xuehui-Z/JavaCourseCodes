package homework0902;

import homework0902.pojo.Student;
import homework0902.pojo.Student1;
import homework0902.pojo.Student2;
import homework0902.pojo.StuConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Homework0902 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("hwApplicationContext.xml");
        ApplicationContext configContext = new AnnotationConfigApplicationContext(StuConfig.class);

        Student student1 = (Student1) applicationContext.getBean("student1");
        System.out.println("方法1：" + student1.toString());

        Student student2 = (Student2) applicationContext.getBean("student2");
        System.out.println("方法2：" + student2.toString());

        Student student3 = applicationContext.getBean(Student2.class);
        System.out.println("方法3：" + student3.toString());

        Student student4 = (Student) configContext.getBean("stu1");
        System.out.println("方法4：" + student4.toString());

    }


}
