package com.dao;

import com.entity.Order;
import com.entity.OrderDeatil;
import com.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author guosx
 * @date 2021/2/3
 */
public class OrderDao {

    /*
     *
     *功能描述  查询订单
     * @author Guosx
     * @date 2021/2/3
     * @param [user]
     * @return java.util.ArrayList<com.entity.User>
     */
    public ArrayList<Order> listOrder() {
        ArrayList<Order> list = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        ResultSet rs = null;
        PreparedStatement prep = null;
        String sql = "select o.id id, " +
                "o.order_no orderNo, " +
                "CASE status " +
                "  WHEN 1 THEN '已完成' " +
                "  WHEN 2 THEN '已退货' " +
                "  ELSE '' " +
                "END statusName, " +
                "create_time createTime, " +
                "u.`name` createName " +
                "from `order` o left JOIN user u on o.create_by=u.id ORDER BY create_time DESC";
        try {
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                Order Order0 = new Order();
                Order0.setId(rs.getInt("id"));
                Order0.setOrderNo(rs.getString("orderNo"));
                Order0.setStatusName(rs.getString("statusName"));
                Order0.setCreateTime(rs.getString("createTime"));
                Order0.setCreateName(rs.getString("createName"));
                list.add(Order0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep, rs);
        }
        return list;
    }


    /*
     *
     *功能描述 添加订单
     * @author Guosx
     * @date 2021/2/4
     * @param [Order]
     * @return int
     */
    public Integer addOrder(Order order) {
        Integer id = null;

        Connection conn = JdbcUtils.getConn();
        String sql = "insert into `order` (order_no,status,create_time,create_by) values (?,?,?,?)";
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, order.getOrderNo());
            prep.setString(2, order.getStatus());
            prep.setString(3, order.getCreateTime());
            prep.setInt(4, order.getCreateBy());
            prep.executeUpdate();
            //检索由于执行Statement对象而创建的所以自动生成的键
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep);
        }
        return id;
    }

    /*
     *
     *功能描述 修改退货状态
     * @author Guosx
     * @date 2021/2/4
     * @param [orderNo]
     * @return java.lang.Integer
     */
    public Integer updateStatus(String orderNo) {
        Integer i = null;
        Connection conn = JdbcUtils.getConn();
        String sql = "update `order`  set status=2 where order_no=?";
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, orderNo);
            i = prep.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep);
        }
        return i;
    }

    public  ArrayList<OrderDeatil> getGoodsIdAndNumber(String orderNo) {
        ArrayList<OrderDeatil> list = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        ResultSet rs = null;
        PreparedStatement prep = null;
        String sql = "select od.number number,od.goods_id goodsId from `order` o left JOIN order_detail od on o.id = od.order_id where o.order_no=?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1,orderNo);
            rs = prep.executeQuery();
            while (rs.next()) {
                OrderDeatil orderDeatil = new OrderDeatil();
                orderDeatil.setNumber(rs.getInt("number"));
                orderDeatil.setGoodsId(rs.getInt("goodsId"));
                list.add(orderDeatil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep, rs);
        }
        return list;
    }

}
