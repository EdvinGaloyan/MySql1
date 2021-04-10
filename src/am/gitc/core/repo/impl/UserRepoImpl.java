package am.gitc.core.repo.impl;

import am.gitc.core.module.Books;
import am.gitc.core.module.User;
import am.gitc.core.repo.UserRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private String url = "jdbc:mysql://localhost:3306/test?user=root&password=rootroot";
    private Connection connection;

    public UserRepoImpl() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url);
    }


    @Override
    public void read(String path) throws SQLException {
        List<User> users = new LinkedList<>();

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
            String[] arr = line.get(i).split(",");
            User user = new User();
            user.setId(Integer.parseInt(arr[0]));
            user.setName(arr[1]);
            user.setSurname(arr[2]);
            user.setAge(Integer.parseInt(arr[3]));
            user.setIq(Double.parseDouble(arr[4]));
            user.setBookId(Integer.parseInt(arr[5]));
            users.add(user);

        }

        insert(users);
    }

    @Override
    public void insert(List<User> user) throws SQLException {
        String query = "insert into user(name, surName, age, iq, bookId) values(?,?,?,?,?);";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < user.size(); i++) {

                statement.setString(1, user.get(i).getName());
                statement.setString(2, user.get(i).getSurname());
                statement.setInt(3, user.get(i).getAge());
                statement.setDouble(4, user.get(i).getIq());
                statement.setInt(5, user.get(i).getBookId());
                statement.execute();
            }

        }

    }

    @Override
    public LinkedHashMap<User, Books> peek() throws SQLException {

        LinkedHashMap<User, Books> hm = new LinkedHashMap<>();
        String query = "select * from user left join books on bookId = books.id;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.getResultSet();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    Books books = new Books();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setAge(resultSet.getInt("age"));
                    user.setIq(resultSet.getDouble("iq"));
                    user.setBookId(resultSet.getInt("bookId"));
                    books.setId(resultSet.getInt("books.id"));
                    books.setAfter(resultSet.getString("after"));
                    books.setName(resultSet.getString("books.name"));
                    hm.put(user, books);
                }
            }
        }
        return hm;
    }


    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public List<User> getByName(String name) {
        return null;
    }
}
