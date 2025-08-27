package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.UserDao;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.storage.InMemoryStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryUserDao implements UserDao {

    private static final String NAME_SPACE = "user";
    private Map<String, User> storage;
    private long userIdCounter = 0;

    public InMemoryUserDao() {}

    public void setInMemoryStorage(InMemoryStorage storage) {
        this.storage = storage.getUserStorage();
    }

    @Override
    public User getUserById(long userId) {
        String key = NAME_SPACE + ":" + userId;
        return storage.get(key);
    }

    @Override
    public User getUserByEmail(String email) {
        return storage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> matchingUsers = storage.values().stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, matchingUsers.size());

        return fromIndex >= matchingUsers.size() ? List.of() : matchingUsers.subList(fromIndex, toIndex);
    }

    @Override
    public User createUser(User user) {
        user.setId(++userIdCounter);

        String key = NAME_SPACE + ":" + user.getId();
        storage.put(key, user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        String key = NAME_SPACE + ":" + user.getId();

        storage.put(key, user);
        return user;
    }

    @Override
    public boolean deleteUser(long userId) {
        String key = NAME_SPACE + ":" + userId;
        if (storage.containsKey(key)) {
            storage.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
