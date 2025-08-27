package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEventDaoTest {

    private InMemoryEventDao eventDao;

    @BeforeEach
    void setUp() {
        eventDao = new InMemoryEventDao(new InMemoryStorage());
    }

    @Test
    void createAndGetEvent() {
        EventImpl e = new EventImpl();
        e.setId(100);
        e.setTitle("Concert");
        e.setDate(new Date());
        Event created = eventDao.createEvent(e);
        assertEquals(100, created.getId());
        assertEquals("Concert", eventDao.getEventById(100).getTitle());
    }

    @Test
    void updateEvent() {
        EventImpl e = new EventImpl();
        e.setId(101);
        e.setTitle("Meetup");
        e.setDate(new Date());
        eventDao.createEvent(e);

        e.setTitle("Tech Meetup");
        Event updated = eventDao.updateEvent(e);
        assertEquals("Tech Meetup", updated.getTitle());
        assertEquals("Tech Meetup", eventDao.getEventById(101).getTitle());
    }

    @Test
    void deleteEvent() {
        EventImpl e = new EventImpl();
        e.setId(200);
        e.setTitle("DeleteMe");
        eventDao.createEvent(e);

        assertTrue(eventDao.deleteEvent(200));
        assertFalse(eventDao.deleteEvent(200));
        assertNull(eventDao.getEventById(200));
    }

    @Test
    void getEventsByTitleAndForDayWithPagination() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        Date day = cal.getTime();

        for (int i = 0; i < 5; i++) {
            EventImpl e = new EventImpl();
            e.setId(300 + i);
            e.setTitle("Event-ABC" + i);
            e.setDate(day);
            eventDao.createEvent(e);
        }
        for (int i = 0; i < 2; i++) {
            EventImpl e = new EventImpl();
            e.setId(400 + i);
            e.setTitle("Other");
            e.setDate(day);
            eventDao.createEvent(e);
        }

        List<Event> byTitle = eventDao.getEventsByTitle("ABC", 3, 1);
        assertEquals(3, byTitle.size());

        List<Event> byDay = eventDao.getEventsForDay(day, 4, 2);
        assertEquals(3, byDay.size());
    }
}

