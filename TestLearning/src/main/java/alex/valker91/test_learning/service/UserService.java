package alex.valker91.test_learning.service;

import alex.valker91.test_learning.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long id);
    User getUserByEmail(String email);
    List<User> getUsersByName(String name, int pageSize, int pageNum);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(long userId);
}
