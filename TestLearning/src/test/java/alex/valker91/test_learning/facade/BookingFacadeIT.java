package alex.valker91.test_learning.facade;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.model.impl.UserImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFacadeIT {

    private static ClassPathXmlApplicationContext context;
    private static BookingFacade facade;

    @BeforeAll
    static void initContext() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        facade = context.getBean("bookingFacade", BookingFacade.class);
    }

    @Test
    void userEventTicketLifecycle() {
        // Create user
        User user = new UserImpl();
        user.setName("Test_User");
        user.setEmail("test_user@example.com");
        User createdUser = facade.createUser(user);
        assertTrue(createdUser.getId() == 1);

        EventImpl event = new EventImpl();
        event.setId(2);
        event.setTitle("Test_Event");
        event.setDate(new Date());
        Event createdEvent = facade.createEvent(event);
        assertEquals(2, createdEvent.getId());

        Ticket booked = facade.bookTicket(createdUser.getId(), createdEvent.getId(), 7, Ticket.Category.STANDARD);
        assertNotNull(booked);
        assertEquals(createdUser.getId(), booked.getUserId());
        assertEquals(createdEvent.getId(), booked.getEventId());

        List<Ticket> byUser = facade.getBookedTickets(createdUser, 10, 1);
        assertFalse(byUser.isEmpty());

        List<Ticket> byEvent = facade.getBookedTickets(createdEvent, 10, 1);
        assertFalse(byEvent.isEmpty());

        assertTrue(facade.cancelTicket(booked.getId()));
        assertFalse(facade.cancelTicket(booked.getId()));
    }

    @AfterAll
    static void closeContext() {
        if (context != null) {
            context.close();
        }
    }
}
