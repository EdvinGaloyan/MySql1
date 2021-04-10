package am.gitc.core.module;

import java.util.LinkedList;
import java.util.List;

public class Books {
    private int id;
    private String after;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Books{" +
                "after='" + after + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
