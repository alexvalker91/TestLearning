package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.EventDao;
import alex.valker91.test_learning.model.Event;

import java.util.Date;
import java.util.List;

public class InMemoryEventDao implements EventDao {

    @Override
    public Event getEventById(long eventId) {
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return List.of();
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return List.of();
    }

    @Override
    public Event createEvent(Event event) {
        return null;
    }

    @Override
    public Event updateEvent(Event event) {
        return null;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return false;
    }
}
