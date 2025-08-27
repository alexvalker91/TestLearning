package alex.valker91.test_learning.storage;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.model.impl.TicketImpl;
import alex.valker91.test_learning.model.impl.UserImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage {
    public InMemoryStorage() {}

    private final Map<String, User> userStorage = new HashMap<>();
    private final Map<String, Event> eventStorage = new HashMap<>();
    private final Map<String, Ticket> ticketStorage = new HashMap<>();

    {
        User user = new UserImpl();
        user.setId(1);
        user.setName("Sanya");
        user.setEmail("sanya@example.com");
        userStorage.put("user:"+user.getId(), user);

        Event event = new EventImpl();
        event.setId(1);
        event.setTitle("Concert of the Year");
        event.setDate(new Date());
        eventStorage.put("event:"+event.getId(), event);

        Ticket ticket = new TicketImpl();
        ticket.setId(1);
        ticket.setEventId(event.getId());
        ticket.setUserId(user.getId());
        ticket.setCategory(Ticket.Category.PREMIUM);
        ticket.setPlace(42);
        ticketStorage.put("ticket:"+ticket.getId(), ticket);
    }

    public Map<String, User> getUserStorage() {
        return userStorage;
    }

    public Map<String, Event> getEventStorage() {
        return eventStorage;
    }

    public Map<String, Ticket> getTicketStorage() {
        return ticketStorage;
    }
}
