package com.dao;

import com.entity.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author guosx
 * @date 2021/2/3
 */
public class UserDao {
    public ArrayList<User> checkNameAndPassword(User user) {
        ArrayList<User> list = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        ResultSet rs = null;
        PreparedStatement prep = null;
        String sql = "select id,name,password from user where name=? and password=?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, user.getName());
            prep.setString(2, user.getPassword());
            rs = prep.executeQuery();
            while (rs.next()) {
                User user0 = new User();
                user0.setId(rs.getInt("id"));
                user0.setName(rs.getString("name"));
                user0.setPassword(rs.getString("password"));
                list.add(user0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep,rs);
        }
        return list;
    }
}
