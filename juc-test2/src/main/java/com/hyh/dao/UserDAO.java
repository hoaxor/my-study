package com.hyh.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static final List<Map<String, Object>> userList = new ArrayList<>();

    static {
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "hyh");
        user.put("age", 28);

        userList.add(user);
    }

    public Map<String, Object> getUserInfo(int id) {
        return userList.get(0);
    }
}
