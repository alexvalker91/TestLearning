package alex.valker91.test_learning.storage;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage {
    public InMemoryStorage() {}

    private final Map<String, User> userStorage = new HashMap<>();
    private final Map<String, Event> eventStorage = new HashMap<>();
    private final Map<String, Ticket> ticketStorage = new HashMap<>();

    

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
