package am.gitc.core.repo;

import am.gitc.core.module.Books;
import am.gitc.core.module.User;
import am.gitc.core.module.UserBooks;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface UserBooksRepo {

    void read(String path) throws SQLException;

    void insert(List<UserBooks> userBooks) throws SQLException;

    LinkedHashMap<User, Books> getUserBooks() throws SQLException;

}
