package alex.valker91.test_learning.storage;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StorageInitializerPostProcessor implements BeanPostProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Resource usersDataFile;
    private Resource eventsDataFile;
    private Resource ticketsDataFile;

    public void setUsersDataFile(Resource usersDataFile) {
        this.usersDataFile = usersDataFile;
    }

    public void setEventsDataFile(Resource eventsDataFile) {
        this.eventsDataFile = eventsDataFile;
    }

    public void setTicketsDataFile(Resource ticketsDataFile) {
        this.ticketsDataFile = ticketsDataFile;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof InMemoryStorage storage) {
            initializeInMemoryStorage(storage);
        }
        return bean;
    }

    private void initializeInMemoryStorage(InMemoryStorage storage) {
        initUsers(storage);
        initEvents(storage);
        initTickets(storage);
    }

    private void initUsers(InMemoryStorage storage) {
        try (InputStream is = usersDataFile.getInputStream()) {
            JsonNode node = objectMapper.readTree(is);
            JsonNode usersNode = node.get("users");
            List<User> users = objectMapper.convertValue(usersNode, new TypeReference<List<User>>() {});
            storage.setUsers(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initEvents(InMemoryStorage storage) {
        try (InputStream is = eventsDataFile.getInputStream()) {
            JsonNode node = objectMapper.readTree(is);
            JsonNode eventsNode = node.get("events");
            List<Event> events = objectMapper.convertValue(eventsNode, new TypeReference<List<Event>>() {});
            storage.setEvents(events);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initTickets(InMemoryStorage storage) {
        try (InputStream is = ticketsDataFile.getInputStream()) {
            JsonNode node = objectMapper.readTree(is);
            JsonNode ticketsNode = node.get("tickets");
            List<Ticket> tickets = objectMapper.convertValue(ticketsNode, new TypeReference<List<Ticket>>() {});
            storage.setTickets(tickets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
