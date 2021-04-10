package am.gitc.core.repo.impl;

import am.gitc.core.module.Books;
import am.gitc.core.module.User;
import am.gitc.core.module.UserBooks;
import am.gitc.core.repo.UserBooksRepo;
import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class UserBooksImpl implements UserBooksRepo {

    private String url = "jdbc:mysql://localhost:3306/test?user=root&password=rootroot";
    private Connection connection;

    public UserBooksImpl() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url);

    }

    @Override
    public void read(String path) throws SQLException {

        List<UserBooks> userBooks = new LinkedList<>();


        List<String> line = new LinkedList<>();
        try (FileReader fileReader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                line.add(str.strip());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < line.size(); i++) {
            String[] strip = line.get(i).split(",");
            UserBooks userBook = new UserBooks();
            userBook.setUserId(Integer.parseInt(strip[0]));
            userBook.setBookId(Integer.parseInt(strip[1]));
            userBooks.add(userBook);
        }
        insert(userBooks);

    }


    @Override
    public void insert(List<UserBooks> userBooks) throws SQLException {
        String query = "insert into userbook(userId, bookId) values(?,?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < userBooks.size(); i++) {
                statement.setInt(1, userBooks.get(i).getUserId());
                statement.setInt(2, userBooks.get(i).getBookId());
                statement.execute();
            }
        }
    }

    @Override
    public LinkedHashMap<User, Books> getUserBooks() throws SQLException {
        LinkedHashMap<User, Books> hm = new LinkedHashMap<>();
        String query = "select * from user inner join userbook on " +
                "userbook.userId = user.id left join books on userbook.bookId = books.id;";
        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    User user = new User();
                    Books books = new Books();

                    user.setName(resultSet.getString("user.name"));
                    user.setSurname(resultSet.getString("surName"));
                    user.setAge(resultSet.getInt("age"));
                    user.setIq(resultSet.getDouble("iq"));
                    user.setBookId(resultSet.getInt("bookId"));

                    books.setAfter(resultSet.getString("after"));
                    books.setName(resultSet.getString("books.name"));
                    hm.put(user, books);
                }
            }
            return hm;
        }
    }
}