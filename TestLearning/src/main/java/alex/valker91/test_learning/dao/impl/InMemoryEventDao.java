package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.EventDao;
import alex.valker91.test_learning.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryEventDao implements EventDao {

    private static final String NAME_SPACE = "user";
    private final Map<String, Event> storage = new HashMap<>();

    public InMemoryEventDao() {
    }

    @Override
    public Event getEventById(long eventId) {
        String key = NAME_SPACE + ":" + eventId;
        return storage.get(key);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> eventsByTitle = storage.values().stream()
                .filter(event -> event.getTitle().contains(title))
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, eventsByTitle.size());
        return eventsByTitle.subList(fromIndex, toIndex);
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
        return eventsForDay.subList(fromIndex, toIndex);
    }

    @Override
    public Event createEvent(Event event) {
        long eventId = event.getId();
        String key = NAME_SPACE + ":" + eventId;
        storage.put(key, event);
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        long eventId = event.getId();
        String key = NAME_SPACE + ":" + eventId;
        storage.put(key, event);
        return event;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        String key = NAME_SPACE + ":" + eventId;
        if (storage.containsKey(key)) {
            storage.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
