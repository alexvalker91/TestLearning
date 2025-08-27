package alex.valker91.test_learning.storage;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.model.impl.TicketImpl;
import alex.valker91.test_learning.model.impl.UserImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class StorageInitializerPostProcessor implements BeanPostProcessor {

    private Resource dataFile;

    public void setDataFile(Resource dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof InMemoryStorage) {
            initializeFromResource((InMemoryStorage) bean);
        }
        return bean;
    }

    private void initializeFromResource(InMemoryStorage storage) {
        if (dataFile == null) {
            System.out.println("StorageInitializerPostProcessor: no data file configured; skipping initialization");
            return;
        }
        try (InputStream inputStream = dataFile.getInputStream()) {
            Map<String, User> userStorage = storage.getUserStorage();
            Map<String, Event> eventStorage = storage.getEventStorage();
            Map<String, Ticket> ticketStorage = storage.getTicketStorage();

            userStorage.clear();
            eventStorage.clear();
            ticketStorage.clear();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            JsonNode users = root.path("users");
            if (users.isArray()) {
                Iterator<JsonNode> it = users.elements();
                while (it.hasNext()) {
                    JsonNode n = it.next();
                    long id = n.path("id").asLong();
                    String name = n.path("name").asText();
                    String email = n.path("email").asText();
                    UserImpl user = new UserImpl();
                    user.setId(id);
                    user.setName(name);
                    user.setEmail(email);
                    userStorage.put("user:" + id, user);
                }
            }

            JsonNode events = root.path("events");
            if (events.isArray()) {
                Iterator<JsonNode> it = events.elements();
                while (it.hasNext()) {
                    JsonNode n = it.next();
                    long id = n.path("id").asLong();
                    String title = n.path("title").asText();
                    long dateEpochMillis = n.path("date").asLong();
                    EventImpl event = new EventImpl();
                    event.setId(id);
                    event.setTitle(title);
                    event.setDate(new java.util.Date(dateEpochMillis));
                    eventStorage.put("event:" + id, event);
                }
            }

            JsonNode tickets = root.path("tickets");
            if (tickets.isArray()) {
                Iterator<JsonNode> it = tickets.elements();
                while (it.hasNext()) {
                    JsonNode n = it.next();
                    long id = n.path("id").asLong();
                    long eventId = n.path("eventId").asLong();
                    long userId = n.path("userId").asLong();
                    String categoryText = n.path("category").asText();
                    int place = n.path("place").asInt();
                    TicketImpl ticket = new TicketImpl();
                    ticket.setId(id);
                    ticket.setEventId(eventId);
                    ticket.setUserId(userId);
                    ticket.setCategory(Ticket.Category.valueOf(categoryText));
                    ticket.setPlace(place);
                    ticketStorage.put("ticket:" + id, ticket);
                }
            }

            System.out.println("StorageInitializerPostProcessor: initialized storage from JSON " + dataFile);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to initialize storage from resource: " + dataFile, ex);
        }
    }
}

