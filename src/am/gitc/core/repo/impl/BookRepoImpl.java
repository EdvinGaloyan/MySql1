package am.gitc.core.repo.impl;

import am.gitc.core.module.Books;
import am.gitc.core.repo.BookRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BookRepoImpl implements BookRepo {
    private static final String url = "jdbc:mysql://localhost:3306/test?user=root&password=rootroot";
    private static Connection connection;

    public BookRepoImpl() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url);
    }

    @Override
    public void read(String path) throws SQLException {

        List<Books> books = new LinkedList<>();

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
            String[] arr = line.get(i).strip().split(",");
            Books book = new Books();
            book.setId(Integer.parseInt(arr[0]));
            book.setAfter(arr[1]);
            book.setName(arr[2]);
            books.add(book);
        }

        insert(books);


    }

    @Override
    public void insert(List<Books> book) throws SQLException {
        String query = "insert into books(after, name) values(?,?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < book.size(); i++) {
                statement.setString(1, book.get(i).getAfter());
                statement.setString(2, book.get(i).getName());
                statement.execute();
            }

        }
    }

}
