package bm.crud.dao;

import bm.crud.model.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    void update(User user, int id);
    void delete(int id);
    User show(int id);
    List<User> index();
}
