package alex.valker91.test_learning.storage;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.model.impl.TicketImpl;
import alex.valker91.test_learning.model.impl.UserImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
        try (InputStream inputStream = dataFile.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            Map<String, User> userStorage = storage.getUserStorage();
            Map<String, Event> eventStorage = storage.getEventStorage();
            Map<String, Ticket> ticketStorage = storage.getTicketStorage();

            userStorage.clear();
            eventStorage.clear();
            ticketStorage.clear();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                String[] parts = trimmed.split(",");
                String type = parts[0].toLowerCase();

                switch (type) {
                    case "user": {
                        if (parts.length < 4) {
                            throw new IllegalArgumentException("Invalid user line: " + line);
                        }
                        long id = Long.parseLong(parts[1]);
                        String name = parts[2];
                        String email = parts[3];
                        UserImpl user = new UserImpl();
                        user.setId(id);
                        user.setName(name);
                        user.setEmail(email);
                        userStorage.put("user:" + id, user);
                        break;
                    }
                    case "event": {
                        if (parts.length < 4) {
                            throw new IllegalArgumentException("Invalid event line: " + line);
                        }
                        long id = Long.parseLong(parts[1]);
                        String title = parts[2];
                        long dateEpochMillis = Long.parseLong(parts[3]);
                        EventImpl event = new EventImpl();
                        event.setId(id);
                        event.setTitle(title);
                        event.setDate(new java.util.Date(dateEpochMillis));
                        eventStorage.put("event:" + id, event);
                        break;
                    }
                    case "ticket": {
                        if (parts.length < 6) {
                            throw new IllegalArgumentException("Invalid ticket line: " + line);
                        }
                        long id = Long.parseLong(parts[1]);
                        long eventId = Long.parseLong(parts[2]);
                        long userId = Long.parseLong(parts[3]);
                        Ticket.Category category = Ticket.Category.valueOf(parts[4]);
                        int place = Integer.parseInt(parts[5]);
                        TicketImpl ticket = new TicketImpl();
                        ticket.setId(id);
                        ticket.setEventId(eventId);
                        ticket.setUserId(userId);
                        ticket.setCategory(category);
                        ticket.setPlace(place);
                        ticketStorage.put("ticket:" + id, ticket);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unknown record type: " + type);
                }
            }

            System.out.println("StorageInitializerPostProcessor: initialized storage from " + dataFile);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to initialize storage from resource: " + dataFile, ex);
        }
    }
}

