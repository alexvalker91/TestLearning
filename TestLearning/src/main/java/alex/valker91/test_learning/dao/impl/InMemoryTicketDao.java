package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.TicketDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.TicketImpl;
import alex.valker91.test_learning.storage.InMemoryStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTicketDao implements TicketDao {

    private static final String NAME_SPACE = "ticket";
    private Map<String, Ticket> storage;
    private long ticketIdCounter = 0;

    public InMemoryTicketDao() {}

    public void setInMemoryStorage(InMemoryStorage storage) {
        this.storage = storage.getTicketStorage();
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new TicketImpl();
        ticket.setId(++ticketIdCounter);
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);

        String key = NAME_SPACE + ":" + ticket.getId();
        storage.put(key, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> bookedTickets = storage.values().stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, bookedTickets.size());

        return bookedTickets.subList(fromIndex, toIndex);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {

        List<Ticket> bookedTickets = storage.values().stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, bookedTickets.size());

        return bookedTickets.subList(fromIndex, toIndex);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        String key = NAME_SPACE + ":" + ticketId;
        if (storage.containsKey(key)) {
            storage.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
