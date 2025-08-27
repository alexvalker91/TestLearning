package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.model.impl.UserImpl;
import alex.valker91.test_learning.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserDaoTest {

    private InMemoryUserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new InMemoryUserDao(new InMemoryStorage());
    }

    @Test
    void createAndGetUser() {
        UserImpl u = new UserImpl();
        u.setName("Alice");
        u.setEmail("alice@example.com");

        User created = userDao.createUser(u);
        assertTrue(created.getId() > 0);

        User byId = userDao.getUserById(created.getId());
        assertEquals("Alice", byId.getName());
        assertEquals("alice@example.com", byId.getEmail());

        User byEmail = userDao.getUserByEmail("alice@example.com");
        assertEquals(created.getId(), byEmail.getId());
    }

    @Test
    void updateUser() {
        UserImpl u = new UserImpl();
        u.setName("Bob");
        u.setEmail("bob@example.com");
        User created = userDao.createUser(u);

        created.setName("Bobby");
        User updated = userDao.updateUser(created);
        assertEquals("Bobby", updated.getName());
        assertEquals("Bobby", userDao.getUserById(created.getId()).getName());
    }

    @Test
    void deleteUser() {
        UserImpl u = new UserImpl();
        u.setName("Carol");
        u.setEmail("carol@example.com");
        User created = userDao.createUser(u);

        assertTrue(userDao.deleteUser(created.getId()));
        assertFalse(userDao.deleteUser(created.getId()));
        assertNull(userDao.getUserById(created.getId()));
    }

    @Test
    void getUsersByNamePagination() {
        for (int i = 0; i < 7; i++) {
            UserImpl u = new UserImpl();
            u.setName("User-Name");
            u.setEmail("u" + i + "@example.com");
            userDao.createUser(u);
        }

        List<User> page1 = userDao.getUsersByName("User", 3, 1);
        List<User> page2 = userDao.getUsersByName("User", 3, 2);
        List<User> page3 = userDao.getUsersByName("User", 3, 3);

        assertEquals(3, page1.size());
        assertEquals(3, page2.size());
        assertEquals(1, page3.size());
    }
}

