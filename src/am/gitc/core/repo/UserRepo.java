package am.gitc.core.repo;

import am.gitc.core.module.Books;
import am.gitc.core.module.User;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public interface UserRepo {
    void read(String path) throws SQLException;

    void insert(List<User> users) throws SQLException;

    LinkedHashMap<User, Books> peek() throws SQLException;

    User getById(int id);

    List<User> getByName(String name);
}
