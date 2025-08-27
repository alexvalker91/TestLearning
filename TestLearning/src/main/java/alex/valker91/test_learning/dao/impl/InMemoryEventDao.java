package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.EventDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.storage.InMemoryStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryEventDao implements EventDao {

    private static final String NAME_SPACE = "event";
    private final Map<String, Event> storage;
    private static final Logger log = LoggerFactory.getLogger(InMemoryEventDao.class);

    public InMemoryEventDao(InMemoryStorage storage) {
        this.storage = storage.getEventStorage();
    }

    @Override
    public Event getEventById(long eventId) {
        String key = NAME_SPACE + ":" + eventId;
        Event event = storage.get(key);
        log.debug("getEventById: id={}, found={}", eventId, event != null);
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> eventsByTitle = storage.values().stream()
                .filter(event -> event.getTitle().contains(title))
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, eventsByTitle.size());
        List<Event> page = eventsByTitle.subList(fromIndex, toIndex);
        log.debug("getEventsByTitle: title='{}', pageSize={}, pageNum={}, returned={}", title, pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startOfDayStr = new SimpleDateFormat("yyyy-MM-dd").format(day) + " 00:00:00";
        String endOfDayStr = new SimpleDateFormat("yyyy-MM-dd").format(day) + " 23:59:59";

        Date startOfDay = null;
        Date endOfDay = null;
        try {
            startOfDay = dateFormat.parse(startOfDayStr);
            endOfDay = dateFormat.parse(endOfDayStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Date finalStartOfDay = startOfDay;
        Date finalEndOfDay = endOfDay;
        List<Event> eventsForDay = storage.values().stream()
                .filter(event -> {
                    Date eventDate = event.getDate();
                    return eventDate != null && !eventDate.before(finalStartOfDay) && !eventDate.after(finalEndOfDay);
                })
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, eventsForDay.size());
        List<Event> page = eventsForDay.subList(fromIndex, toIndex);
        log.debug("getEventsForDay: day={}, pageSize={}, pageNum={}, returned={}", day, pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public Event createEvent(Event event) {
        long eventId = event.getId();
        String key = NAME_SPACE + ":" + eventId;
        storage.put(key, event);
        log.info("createEvent: id={}, title={}", eventId, event.getTitle());
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        long eventId = event.getId();
        String key = NAME_SPACE + ":" + eventId;
        storage.put(key, event);
        log.info("updateEvent: id={}, title={}", eventId, event.getTitle());
        return event;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        String key = NAME_SPACE + ":" + eventId;
        if (storage.containsKey(key)) {
            storage.remove(key);
            log.info("deleteEvent: id={}, result=deleted", eventId);
            return true;
        } else {
            log.info("deleteEvent: id={}, result=not_found", eventId);
            return false;
        }
    }
}
