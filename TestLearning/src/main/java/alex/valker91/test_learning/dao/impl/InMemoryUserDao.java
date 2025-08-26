package alex.valker91.test_learning.dao.impl;

import alex.valker91.test_learning.dao.UserDao;
import alex.valker91.test_learning.model.User;

import java.util.List;

public class InMemoryUserDao implements UserDao {

    @Override
    public User getUserById(long userId) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return List.of();
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        return false;
    }
}
