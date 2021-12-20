package bm.crud.security.spring.model;

import java.util.List;

public class UserWrapper {
    private List<User> users;

    public UserWrapper() {

    }

    public UserWrapper(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByNickname(String nickname) {
        User user = null;
        for (User userWithThisNickname: users) {
            if (userWithThisNickname.getNickname() != null && userWithThisNickname.getNickname().equals(nickname)) {
                user = userWithThisNickname;
            }
        }

        return user;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
