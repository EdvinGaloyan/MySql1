package am.gitc.core.repo;

import am.gitc.core.module.User;

import java.util.List;

public interface InMemoryUserRepo {

    void insert(User user);

    void update(User user);

    void delete(int id);

    void delete(User user);

    User getById(int id);

    List<User> getByName(String name);

    List<User> getByNameAndSurname(String name, String surname);
}
