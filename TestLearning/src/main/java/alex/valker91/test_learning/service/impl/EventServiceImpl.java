package alex.valker91.test_learning.service.impl;

import alex.valker91.test_learning.dao.EventDao;
import alex.valker91.test_learning.dao.TicketDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.service.EventService;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventServiceImpl implements EventService {

    private EventDao eventDao;
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    public EventServiceImpl() {}

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event getEventById(long id) {
        Event event = eventDao.getEventById(id);
        log.debug("getEventById: id={}, found={}", id, event != null);
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> page = eventDao.getEventsByTitle(title, pageSize, pageNum);
        log.debug("getEventsByTitle: title='{}', pageSize={}, pageNum={}, returned={}", title, pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> page = eventDao.getEventsForDay(day, pageSize, pageNum);
        log.debug("getEventsForDay: day={}, pageSize={}, pageNum={}, returned={}", day, pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public Event createEvent(Event event) {
        Event created = eventDao.createEvent(event);
        log.info("createEvent: id={}, title={}", created.getId(), created.getTitle());
        return created;
    }

    @Override
    public Event updateEvent(Event event) {
        Event updated = eventDao.updateEvent(event);
        log.info("updateEvent: id={}, title={}", updated.getId(), updated.getTitle());
        return updated;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        boolean result = eventDao.deleteEvent(eventId);
        log.info("deleteEvent: id={}, result={}", eventId, result);
        return result;
    }
}
