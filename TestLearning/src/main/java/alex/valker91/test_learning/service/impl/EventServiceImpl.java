package alex.valker91.test_learning.service.impl;

import alex.valker91.test_learning.dao.EventDao;
import alex.valker91.test_learning.dao.TicketDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.service.EventService;

import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    public EventServiceImpl() {}

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event getEventById(long id) {
        return eventDao.getEventById(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventDao.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventDao.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventDao.deleteEvent(eventId);
    }
}
