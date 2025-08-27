package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.UserDao;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.storage.InMemoryStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryUserDao implements UserDao {

    private static final String NAME_SPACE = "user";
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserDao.class);
    private final Map<String, User> storage;
    private long userIdCounter = 0;

    public InMemoryUserDao(InMemoryStorage storage) {
        this.storage = storage.getUserStorage();
    }

    @Override
    public User getUserById(long userId) {
        String key = NAME_SPACE + ":" + userId;
        User user = storage.get(key);
        log.debug("getUserById: id={}, found={}", userId, user != null);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User found = storage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        log.debug("getUserByEmail: email={}, found={}", email, found != null);
        return found;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> matchingUsers = storage.values().stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, matchingUsers.size());

        List<User> page = fromIndex >= matchingUsers.size() ? List.of() : matchingUsers.subList(fromIndex, toIndex);
        log.debug("getUsersByName: name='{}', pageSize={}, pageNum={}, returned={}", name, pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public User createUser(User user) {
        user.setId(++userIdCounter);

        String key = NAME_SPACE + ":" + user.getId();
        storage.put(key, user);
        log.info("createUser: id={}, name={}, email={}", user.getId(), user.getName(), user.getEmail());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String key = NAME_SPACE + ":" + user.getId();

        storage.put(key, user);
        log.info("updateUser: id={}, name={}, email={}", user.getId(), user.getName(), user.getEmail());
        return user;
    }

    @Override
    public boolean deleteUser(long userId) {
        String key = NAME_SPACE + ":" + userId;
        if (storage.containsKey(key)) {
            storage.remove(key);
            log.info("deleteUser: id={}, result=deleted", userId);
            return true;
        } else {
            log.info("deleteUser: id={}, result=not_found", userId);
            return false;
        }
    }
}
