package com.dao;

import com.entity.Goods;
import com.entity.OrderDeatil;
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
public class GoodsDao {

/*
*
 *功能描述  根据name查询
 * @author Guosx
 * @date 2021/2/3
 * @param
 * @return
 */
    public ArrayList<Goods> oneGoods(String name) {
        ArrayList<Goods> list = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        ResultSet rs = null;
        PreparedStatement prep = null;
        String sql = "select id,name,price,unit,img,stock,remark from goods where name = ?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1,name);
            rs = prep.executeQuery();
            while (rs.next()) {
                Goods goods0 = new Goods();
                goods0.setId(rs.getInt("id"));
                goods0.setName(rs.getString("name"));
                goods0.setPrice(rs.getBigDecimal("price"));
                goods0.setUnit(rs.getString("unit"));
                goods0.setRemark(rs.getString("remark"));
                list.add(goods0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep, rs);
        }
        return list;
    }


    public Goods getGoodsById(Integer id) {
        Goods goods0 = new Goods();
        Connection conn = JdbcUtils.getConn();
        ResultSet rs = null;
        PreparedStatement prep = null;
        String sql = "select id,name,price,unit,img,stock,remark from goods where id = ?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1,id);
            rs = prep.executeQuery();
            while (rs.next()) {
                goods0.setName(rs.getString("name"));
                goods0.setPrice(rs.getBigDecimal("price"));
                goods0.setUnit(rs.getString("unit"));
                goods0.setStock(rs.getInt("stock"));
                goods0.setRemark(rs.getString("remark"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep, rs);
        }
        return goods0;
    }
    /*
     *
     *功能描述  商品查询
     * @author Guosx
     * @date 2021/2/3
     * @param [user]
     * @return java.util.ArrayList<com.entity.User>
     */
    public ArrayList<Goods> listGoods() {
        ArrayList<Goods> list = new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        ResultSet rs = null;
        PreparedStatement prep = null;
        String sql = "select id,name,price,unit,img,stock,remark from goods";
        try {
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                Goods goods0 = new Goods();
                goods0.setId(rs.getInt("id"));
                goods0.setName(rs.getString("name"));
                goods0.setPrice(rs.getBigDecimal("price"));
                goods0.setUnit(rs.getString("unit"));
                goods0.setStock(rs.getInt("stock"));
                goods0.setRemark(rs.getString("remark"));
                list.add(goods0);
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
     *功能描述  商品删除
     * @author Guosx
     * @date 2021/2/3
     * @param [empno]
     * @return void
     */

    public int deleteGoods(String name) {
        Connection conn = JdbcUtils.getConn();
        String sql = "delete from goods where name=?";
        PreparedStatement prep = null;
        int i = 0;
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, name);
            i = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, prep);
        }
        return i;
    }

    /*
     *
     *功能描述 添加商品
     * @author Guosx
     * @date 2021/2/3
     * @param [goods]
     * @return int
     */
    public int addGoods(Goods goods) {
        Connection conn = JdbcUtils.getConn();
        String sql = "insert into goods (name,price,unit,img,stock,remark) values (?,?,?,?,?,?)";
        PreparedStatement prep = null;
        int i = 0;
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, goods.getName());
            prep.setBigDecimal(2, goods.getPrice());
            prep.setString(3, goods.getUnit());
            prep.setString(4, goods.getImg());
            prep.setInt(5,goods.getStock());
            prep.setString(6, goods.getRemark());
            i = prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JdbcUtils.close(conn, prep);
        }
        return i;
    }

    public int updateGoods(OrderDeatil orderDeatil) {
        Connection conn = JdbcUtils.getConn();
        String sql = "update goods  set stock=stock-? where id=?";
        PreparedStatement prep = null;
        int i = 0;
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1,orderDeatil.getNumber());
            prep.setInt(2,orderDeatil.getGoodsId());
            i = prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JdbcUtils.close(conn, prep);
        }
        return i;
    }


    public void updateStock(OrderDeatil orderDeatil) {
        Connection conn = JdbcUtils.getConn();
        String sql = "update goods  set stock=stock+? where id=?";
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1,orderDeatil.getNumber());
            prep.setInt(2,orderDeatil.getGoodsId());
            prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JdbcUtils.close(conn, prep);
        }
    }
}
