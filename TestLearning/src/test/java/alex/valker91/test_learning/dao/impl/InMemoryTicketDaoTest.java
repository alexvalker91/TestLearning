package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.model.impl.UserImpl;
import alex.valker91.test_learning.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTicketDaoTest {

    private InMemoryTicketDao ticketDao;

    @BeforeEach
    void setUp() {
        ticketDao = new InMemoryTicketDao(new InMemoryStorage());
    }

    @Test
    void bookAndCancelTicket() {
        Ticket t = ticketDao.bookTicket(1, 1, 50, Ticket.Category.PREMIUM);
        assertTrue(t.getId() > 0);

        assertTrue(ticketDao.cancelTicket(t.getId()));
        assertFalse(ticketDao.cancelTicket(t.getId()));
    }

    @Test
    void getBookedTicketsByUserAndEventPagination() {
        // Create tickets for user 1 and event 10
        for (int i = 0; i < 5; i++) {
            ticketDao.bookTicket(1, 10, i + 1, Ticket.Category.STANDARD);
        }
        for (int i = 0; i < 2; i++) {
            ticketDao.bookTicket(2, 10, 100 + i, Ticket.Category.BAR);
        }

        User u = new UserImpl();
        u.setId(1);
        u.setEmail("u1@example.com");
        u.setName("U1");

        Event e = new EventImpl();
        e.setId(10);

        List<Ticket> userPage1 = ticketDao.getBookedTickets(u, 3, 1);
        List<Ticket> userPage2 = ticketDao.getBookedTickets(u, 3, 2);

        assertEquals(3, userPage1.size());
        assertEquals(2, userPage2.size());

        List<Ticket> eventPage1 = ticketDao.getBookedTickets(e, 4, 1);
        assertEquals(4, eventPage1.size());
    }
}

