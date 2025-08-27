package alex.valker91.test_learning.dao.Impl;

import alex.valker91.test_learning.dao.impl.InMemoryUserDao;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.UserImpl;
import alex.valker91.test_learning.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryUserDaoTest {

    private InMemoryUserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new InMemoryUserDao(new InMemoryStorage());
    }

    @Test
    void createAndGetUser() {
        User u = new UserImpl();
        u.setName("Alex");
        u.setEmail("alex@example.com");

        User created = userDao.createUser(u);
        assertTrue(created.getId() == 1);

        User byId = userDao.getUserById(created.getId());
        assertEquals("Alex", byId.getName());
        assertEquals("alex@example.com", byId.getEmail());

        User byEmail = userDao.getUserByEmail("alex@example.com");
        assertEquals(created.getId(), byEmail.getId());
    }
}
