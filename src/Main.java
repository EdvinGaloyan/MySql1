import am.gitc.core.module.Books;
import am.gitc.core.module.User;
import am.gitc.core.repo.BookRepo;
import am.gitc.core.repo.UserBooksRepo;
import am.gitc.core.repo.UserRepo;
import am.gitc.core.repo.impl.BookRepoImpl;
import am.gitc.core.repo.impl.UserBooksImpl;
import am.gitc.core.repo.impl.UserRepoImpl;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        BookRepo bookRepo = new BookRepoImpl();
//        bookRepo.read("book.csv");

        UserRepo userRepo = new UserRepoImpl();
//        userRepo.read("user.csv");

        UserBooksRepo userBooksRepo = new UserBooksImpl();
//        userBooksRepo.read("userbooks.csv");
        LinkedHashMap<User, Books> res = userBooksRepo.getUserBooks();
        for (User user : res.keySet()) {
            System.out.println(user.toString() + res.get(user).toString());
        }

/*
        LinkedHashMap<User, Books> result = userRepo.peek();
        for (User user : result.keySet()) {
            System.out.println(user.toString() + result.get(user).toString());
        }
*/


    }
}
