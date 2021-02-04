package com.service;

import com.dao.GoodsDao;
import com.dao.OrderDao;
import com.dao.OrderDetailDao;
import com.entity.Goods;
import com.entity.Order;
import com.entity.OrderDeatil;
import com.enums.StatusEnum;
import com.utils.DateUtil;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author guosx
 * @date 2021/2/3
 */
public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private GoodsDao goodsDao = new GoodsDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();

    /*
     *
     *功能描述  查询所有订单信息
     * @author Guosx
     * @date 2021/2/3
     * @param [goods]
     * @return java.util.ArrayList<com.entity.Goods>
     */
    public ArrayList<Order> listOrder() {
        ArrayList<Order> list = orderDao.listOrder();
        return list;
    }

    /*
     *
     *功能描述 添加订单
     * @author Guosx
     * @date 2021/2/4
     * @param [orderDeatils]
     * @return java.lang.Boolean
     */
    public Boolean addOrder(ArrayList<OrderDeatil> orderDeatils) {
        //判断库存是否够
        for (OrderDeatil orderDeatil : orderDeatils) {
            Goods goods = goodsDao.getGoodsById(orderDeatil.getGoodsId());
            if (orderDeatil.getNumber() > goods.getStock()) {
                //库存不够
                return false;
            }
        }

        //order添加数据
        Order order = new Order();
        order.setOrderNo(DateUtil.getOrderNo());
        order.setStatus(StatusEnum.FINISH.getName());
        order.setCreateTime(DateUtil.getDateStr());
        order.setCreateBy(1);
        Integer orderId = orderDao.addOrder(order);

        //orderDeatil 添加数据
        orderDeatils.stream().forEach(n -> {
            OrderDeatil orderDeatil = new OrderDeatil();
            orderDeatil.setOrderId(orderId);
            orderDeatil.setGoodsId(n.getGoodsId());
            orderDeatil.setNumber(n.getNumber());
            Goods goods = goodsDao.getGoodsById(orderDeatil.getGoodsId());
            orderDeatil.setPrice(goods.getPrice());
            BigDecimal total = goods.getPrice().multiply(new BigDecimal(n.getNumber()));
            orderDeatil.setTotal(total);
            orderDetailDao.addOrderDeatil(orderDeatil);
        });

        //减库存
        orderDeatils.stream().forEach(n -> {
            goodsDao.updateGoods(n);
        });

        return true;
    }



    /*
    *
     *功能描述 退货
     * @author Guosx
     * @date 2021/2/4
     * @param [orderNo]
     * @return java.lang.Boolean
     */
    public Boolean updateStatus(String orderNo){
        // 修改订单状态
         if (orderDao.updateStatus(orderNo)==null){
             return false;
         }
        //修改库存
        ArrayList<OrderDeatil> list = orderDao.getGoodsIdAndNumber(orderNo);
         list.stream().forEach(n->{
             OrderDeatil orderDeatil = new OrderDeatil();
             orderDeatil.setNumber(n.getNumber());
             orderDeatil.setGoodsId(n.getGoodsId());
             goodsDao.updateStock(orderDeatil);
         });
        return true;
    }
}
