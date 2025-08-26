package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.TicketDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTicketDao implements TicketDao {

    private static final String NAME_SPACE = "ticket";
    private final Map<String, Ticket> storage = new HashMap<>();

    public InMemoryTicketDao() {
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return null;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return List.of();
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return List.of();
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return false;
    }
}
