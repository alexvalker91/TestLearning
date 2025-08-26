package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.TicketDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;

import java.util.List;

public class InMemoryTicketDao implements TicketDao {
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
