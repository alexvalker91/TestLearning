package alex.valker91.test_learning.facade;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.model.impl.UserImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingFacadeIT {

    private static ClassPathXmlApplicationContext context;
    private static BookingFacade facade;

    @BeforeAll
    static void initContext() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        facade = context.getBean("bookingFacade", BookingFacade.class);
    }

    @AfterAll
    static void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    void userEventTicketLifecycle() {
        // Create user
        UserImpl user = new UserImpl();
        user.setName("Test User");
        user.setEmail("test.user@example.com");
        User createdUser = facade.createUser(user);
        assertTrue(createdUser.getId() > 0);

        // Create event
        EventImpl event = new EventImpl();
        event.setId(999); // Event ids are managed by caller in DAO
        event.setTitle("Test Event");
        event.setDate(new Date());
        Event createdEvent = facade.createEvent(event);
        assertEquals(999, createdEvent.getId());

        // Book ticket
        Ticket booked = facade.bookTicket(createdUser.getId(), createdEvent.getId(), 7, Ticket.Category.STANDARD);
        assertNotNull(booked);
        assertEquals(createdUser.getId(), booked.getUserId());
        assertEquals(createdEvent.getId(), booked.getEventId());

        // Verify queries
        List<Ticket> byUser = facade.getBookedTickets(createdUser, 10, 1);
        assertFalse(byUser.isEmpty());

        List<Ticket> byEvent = facade.getBookedTickets(createdEvent, 10, 1);
        assertFalse(byEvent.isEmpty());

        // Cancel ticket
        boolean canceled = facade.cancelTicket(booked.getId());
        assertTrue(canceled);
        assertTrue(facade.getBookedTickets(createdUser, 10, 1).stream().noneMatch(t -> t.getId() == booked.getId()));
    }
}

