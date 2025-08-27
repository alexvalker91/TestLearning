package alex.valker91.test_learning.service.impl;

import alex.valker91.test_learning.dao.UserDao;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl() {}

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(long id) {
        User user = userDao.getUserById(id);
        log.debug("getUserById: id={}", id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDao.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userDao.deleteUser(userId);
    }
}
