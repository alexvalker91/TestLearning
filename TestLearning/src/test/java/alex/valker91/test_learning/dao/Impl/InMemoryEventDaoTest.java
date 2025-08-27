package alex.valker91.test_learning.dao.Impl;

import alex.valker91.test_learning.dao.impl.InMemoryEventDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.impl.EventImpl;
import alex.valker91.test_learning.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryEventDaoTest {

    private InMemoryEventDao eventDao;

    @BeforeEach
    void setUp() {
        eventDao = new InMemoryEventDao(new InMemoryStorage());
    }

    @Test
    void createAndGetEvent() {
        Event e = new EventImpl();
        e.setId(100);
        e.setTitle("Concert");
        e.setDate(new Date());
        Event created = eventDao.createEvent(e);
        assertEquals(100, created.getId());
        assertEquals("Concert", eventDao.getEventById(100).getTitle());
    }
}
