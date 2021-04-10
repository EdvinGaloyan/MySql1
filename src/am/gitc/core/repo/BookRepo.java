package am.gitc.core.repo;

import am.gitc.core.module.Books;

import java.sql.SQLException;
import java.util.List;


public interface BookRepo {

    void read(String path) throws SQLException;

    void insert(List<Books> book) throws SQLException;


}
