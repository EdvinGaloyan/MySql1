package am.gitc.core.repo.impl;

import am.gitc.core.module.User;
import am.gitc.core.repo.InMemoryUserRepo;

import java.util.*;

public class InMemoryUserRepoImpl implements InMemoryUserRepo {

    private HashMap<Integer, User> userMap = new HashMap<>();

    private HashMap<String, List<Integer>> nameIndexMap = new HashMap<>();
    private int latestId = 0;

    @Override
    public void insert(User user) {
        int id = user.getId();
        if (userMap.containsKey(id)) {
            throw new RuntimeException("User with this id already exist");
        }
        if (id <= 0) {
            id = ++latestId;
        } else {
            latestId = Math.max(id, latestId);
        }
        user.setId(id);
        userMap.put(id, user);
        List<Integer> ids = nameIndexMap.get(user.getName());
        if (ids == null) {
            ids = new LinkedList<>();
            nameIndexMap.put(user.getName(), ids);
        }
        ids.add(user.getId());


    }

    @Override
    public void update(User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new RuntimeException("User not found");
        } else {
            userMap.put(user.getId(), user);
        }
    }

    @Override
    public void delete(int id) {
        userMap.remove(id);
    }

    @Override
    public void delete(User user) {
        this.delete(user.getId());
    }

    @Override
    public User getById(int id) {
        return this.userMap.get(id);
    }

    @Override
    public List<User> getByName(String name) {
        List<User> users = new LinkedList<>();
        for (Integer id : nameIndexMap.get(name)) {
            users.add(userMap.get(id));

        }

        return users;
    }

    @Override
    public List<User> getByNameAndSurname(String name, String surname) {
        List<User> users = new LinkedList<>();
        for (User user : userMap.values()) {
            if (Objects.equals(user.getName(), name) && Objects.equals(user.getSurname(), surname)) {
                users.add(user);
            }

        }
        return users;
    }
}
