package alex.valker91.test_learning.dao;

import alex.valker91.test_learning.model.Event;

import java.util.Date;
import java.util.List;

public interface EventDao {

    Event getEventById(long eventId);
    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);
    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);
    Event createEvent(Event event);
    Event updateEvent(Event event);
    boolean deleteEvent(long eventId);
}
