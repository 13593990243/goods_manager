package com.dao;

import com.entity.Order;
import com.entity.OrderDeatil;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 * @author guosx
 * @date 2021/2/4
 */
public class OrderDetailDao {
    public void addOrderDeatil(OrderDeatil orderDeatil) {
        Connection conn = JdbcUtils.getConn();
        String sql = "insert into order_detail (order_id,goods_id,number,price,total) values (?,?,?,?,?)";
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1,orderDeatil.getOrderId());
            prep.setInt(2,orderDeatil.getGoodsId());
            prep.setInt(3,orderDeatil.getNumber());
            prep.setBigDecimal(4,orderDeatil.getPrice());
            prep.setBigDecimal(5,orderDeatil.getTotal());
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep);
        }
    }
}
