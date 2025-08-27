package alex.valker91.test_learning.service.impl;

import alex.valker91.test_learning.dao.UserDao;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.service.UserService;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        log.debug("getUserById: id={}, found={}", id, user != null);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        log.debug("getUserByEmail: email={}, found={}", email, user != null);
        return user;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> page = userDao.getUsersByName(name, pageSize, pageNum);
        log.debug("getUsersByName: name='{}', pageSize={}, pageNum={}, returned={}", name, pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public User createUser(User user) {
        User created = userDao.createUser(user);
        log.info("createUser: id={}, name={}, email={}", created.getId(), created.getName(), created.getEmail());
        return created;
    }

    @Override
    public User updateUser(User user) {
        User updated = userDao.updateUser(user);
        log.info("updateUser: id={}, name={}, email={}", updated.getId(), updated.getName(), updated.getEmail());
        return updated;
    }

    @Override
    public boolean deleteUser(long userId) {
        boolean result = userDao.deleteUser(userId);
        log.info("deleteUser: id={}, result={}", userId, result);
        return result;
    }
}
