package com.service;

import com.dao.UserDao;
import com.entity.User;

import java.util.ArrayList;

/**
 * @author guosx
 * @date 2021/2/3
 */
public class UserService {

    private UserDao userDao = new UserDao();
    /*
    *
     *功能描述 验证账号密码
     * @author Guosx
     * @date 2021/2/3
     * @param
     * @return
     */

    public boolean checkNameAndPassword(User user){
        ArrayList<User> list = userDao.checkNameAndPassword(user);
        if (list.size()==1){
            return true;
        }
        //系统存在两个相同的账号密码
        //账号密码错误
        return false;
    }
}
