package alex.valker91.test_learning.dao.Impl;

import alex.valker91.test_learning.dao.impl.InMemoryTicketDao;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryTicketDaoTest {

    private InMemoryTicketDao ticketDao;

    @BeforeEach
    void setUp() {
        ticketDao = new InMemoryTicketDao(new InMemoryStorage());
    }

    @Test
    void bookAndCancelTicket() {
        Ticket t = ticketDao.bookTicket(1, 1, 50, Ticket.Category.PREMIUM);
        assertTrue(t.getId() == 1);
        assertTrue(ticketDao.cancelTicket(t.getId()));
        assertFalse(ticketDao.cancelTicket(t.getId()));
    }
}
