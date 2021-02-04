package com.controller;

import com.dao.OrderDao;
import com.entity.Goods;
import com.entity.Order;
import com.entity.OrderDeatil;
import com.entity.User;
import com.service.GoodsService;
import com.service.OrderService;
import com.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author guosx
 * @date 2021/2/3
 */
public class MainController {
    private UserService userService = new UserService();
    private GoodsService goodsService = new GoodsService();
    private OrderService orderService = new OrderService();

    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.login();
    }

    /*
     *
     *功能描述 系统登录页面
     * @author Guosx
     * @date 2021/2/3
     * @param []
     * @return void
     */
    public void login() {
        System.out.println("欢迎来到商城大厦购物中心");
        System.out.println("--------------------");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入账号:");
            String nameMsg = scanner.nextLine();
            System.out.println("请输入密码:");
            String passwordMsg = scanner.nextLine();
            User user = new User();
            user.setName(nameMsg);
            user.setPassword(passwordMsg);
            //判断账号密码
            if (userService.checkNameAndPassword(user)) {
                System.out.println("登录成功！");
                //登录成功跳转主页面
                manager();
                break;
            } else {
                System.out.println("登录失败，账号密码错误！");
            }
        }
    }

    //选择业务
    public void manager() {
        System.out.println("--------------------");
        System.out.println("欢迎进入主页");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择您的操作:");
            System.out.println("--1 ： 商品管理--");
            System.out.println("--2 ： 订单管理--");
            System.out.println("--3 ： 结束使用--");
            String msg = scanner.nextLine();
            if ("1".equals(msg)) {
                //进入商品管理页面
                goodsManager();
            } else if ("2".equals(msg)) {
                //今天订单管理页面
                orderManager();
            } else if ("3".equals(msg)) {
                //结束本次使用
                System.out.println("欢迎再次使用本系统！");
                return;
            } else {
                System.out.println("请选择正确的业务！");
            }
        }
    }

    //商品管理页面
    public void goodsManager() {
        System.out.println("--------------------");
        System.out.println("欢迎进入商品管理");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择您的操作：");
            System.out.println("--1 ： 商品查询--");
            System.out.println("--2 ： 商品添加--");
            System.out.println("--3 ： 商品删除--");
            System.out.println("--4 ： 返回主页--");
            String msg = scanner.nextLine();
            if ("1".equals(msg)) {
                //​	1 查询
                ArrayList<Goods> list = goodsService.listGoods();
                System.out.println("id" + " 商品名称" + " 商品单价" + " 商品单位" + " 商品库存" + " 商品备注");
                list.stream().forEach(n -> {
                    System.out.println(n.getId() + "  " + n.getName() + "  " + n.getPrice() + "  " + n.getUnit() + "  "+ n.getStock() + "    " + n.getRemark());
                });
            } else if ("2".equals(msg)) {
                //​    2 添加（名称（唯一），价格，单位，图片路径，备注，用逗号隔开）
                System.out.println("添加商品格式：名称，价格，单位，图片路径(D:\\\\1.jpg)，库存数量，备注，用逗号隔开");
                System.out.println("请输入商品信息：");
                String msg1 = scanner.nextLine();
                String[] split = msg1.split(",");
                Goods goods = new Goods();
                goods.setName(split[0]);
                goods.setPrice((BigDecimal.valueOf(Double.parseDouble(split[1]))));
                goods.setUnit(split[2]);
                goods.setImg(split[3]);
                goods.setStock(Integer.valueOf(split[4]));
                goods.setRemark(split[5]);
                if (goodsService.oneGoods(split[0])){
                    if (goodsService.addGoods(goods)) {
                        System.out.println("添加成功！");
                        //​	添加完成后 查询商品信息
                        ArrayList<Goods> list = goodsService.listGoods();
                        System.out.println("id" + " 商品名称" + " 商品单价" + " 商品单位" + " 商品库存" + " 商品备注");
                        list.stream().forEach(n -> {
                            System.out.println(n.getId() + "  " + n.getName() + "  " + n.getPrice() + "  " + n.getUnit() + "  "+ n.getStock() + "    " + n.getRemark());
                        });
                    } else {
                        System.out.println("添加失败！");
                    }
                }else {
                    System.out.println("已经存在该商品，添加失败！");
                }

            } else if ("3".equals(msg)) {
                //​    3 删除
                System.out.println("请输入需要删除商品的名称：");
                String nameMsg = scanner.nextLine();
                if (goodsService.deleteGoods(nameMsg)) {
                    System.out.println("删除成功！");
                } else {
                    System.out.println("删除失败！");
                }
            } else if ("4".equals(msg)) {
                //​    4 返回上一层
                break;
            } else {
                System.out.println("请选择正确的业务！");
            }
        }
    }

    //2 订单管理
    public void orderManager() {
        System.out.println("--------------------");
        System.out.println("欢迎进入订单管理");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择您的操作：");
            System.out.println("--1 ： 查询历史订单--");
            System.out.println("--2 ： 添加订单--");
            System.out.println("--3 ： 退货--");
            System.out.println("--4 ： 返回主页--");
            String msg = scanner.nextLine();
            if ("1".equals(msg)) {
                //​   1 查询历史订单
                ArrayList<Order> list = orderService.listOrder();
                System.out.println("id" + "   订单编号" + "   订单状态" + "         创建时间" + "         创建人");
                list.stream().forEach(n -> {
                    System.out.println(n.getId() + " " + n.getOrderNo() + "  " + n.getStatusName() + "  "+ n.getCreateTime() + "  " + n.getCreateName());
                });
            } else if ("2".equals(msg)) {
                //​   2 添加订单（商品名称，数量，判断账户余额是否足够）
                ArrayList<OrderDeatil> deatilArrayList = addOrder(scanner);
                if(orderService.addOrder(deatilArrayList)){
                    System.out.println("添加成功！");
                }else {
                    System.out.println("添加失败！");
                }
            } else if ("3".equals(msg)) {
                //​3 退货 （根据订单编号退回，库存增加
                System.out.println("请输入退货订单编号：");
                String orderNo = scanner.nextLine();
                if (orderService.updateStatus(orderNo)){
                    System.out.println("订单退货成功！");
                }else {
                    System.out.println("该订单不存在！");
                }
                //把order表里面的status 改为2（已退回）


            } else if ("4".equals(msg)) {
                //​   4 返回上一层
                break;
            } else {
                System.out.println("请选择正确的业务！");
            }
        }
    }

    public ArrayList<OrderDeatil>  addOrder(Scanner scanner){
        ArrayList<OrderDeatil> orderDeatilList = new ArrayList<>();
        while (true){
            System.out.println("请输入要添加的商品id,商品数量");
            String s = scanner.nextLine();
            String[] split = s.split(",");
            OrderDeatil orderDeatil = new OrderDeatil();
            orderDeatil.setGoodsId(Integer.valueOf(split[0]));
            orderDeatil.setNumber(Integer.valueOf(split[1]));
            orderDeatilList.add(orderDeatil);
            System.out.println("是否继续添加 y/n");
            String msg = scanner.nextLine();
            if (!"y".equals(msg)){
                break;
            }
        }
        return orderDeatilList;
    }
}
