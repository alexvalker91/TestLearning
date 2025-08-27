package alex.valker91.test_learning;


import alex.valker91.test_learning.facade.BookingFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookingFacade bookingFacade = context.getBean("bookingFacade", BookingFacade.class);
        System.out.println("Hello World");
        System.out.println(bookingFacade.getUserById(1).getEmail());
        System.out.println(bookingFacade.getEventById(1).getDate());
    }
}
