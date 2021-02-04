package com.service;

import com.dao.GoodsDao;
import com.entity.Goods;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author guosx
 * @date 2021/2/3
 */
public class GoodsService {
    private GoodsDao goodsDao = new GoodsDao();

    /*
     *
     *功能描述  查询所以商品信息
     * @author Guosx
     * @date 2021/2/3
     * @param [goods]
     * @return java.util.ArrayList<com.entity.Goods>
     */
    public ArrayList<Goods> listGoods() {
        ArrayList<Goods> list = goodsDao.listGoods();
        return list;
    }

    /*
     *
     *功能描述 根据商品姓名删除商品
     * @author Guosx
     * @date 2021/2/3
     * @param [name]
     * @return boolean
     */
    public boolean deleteGoods(String name) {
        int i = goodsDao.deleteGoods(name);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /*
     *
     *功能描述 添加商品
     * @author Guosx
     * @date 2021/2/3
     * @param [goods]
     * @return boolean
     */
    public boolean addGoods(Goods goods) {
        //上传图片
        try {
            FileInputStream fis  = new FileInputStream(goods.getImg());
            FileOutputStream fos  = new FileOutputStream(getPath());
            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] bytes = new byte[1024];
            int len;
            while (true){
                len = bis.read(bytes);
                if (len==-1){
                    break;
                }
                bos.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = goodsDao.addGoods(goods);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /*
     *
     *功能描述 查询数据库是否有该商品名
     * @author Guosx
     * @date 2021/2/3
     * @param [name]
     * @return boolean
     */
    public boolean oneGoods(String name) {
        ArrayList<Goods> list = goodsDao.oneGoods(name);
        if (list.size() > 0) {
            return false;
        }
        return true;
    }

    public String getPath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String format = sdf.format(date);
        return "D:\\img\\"+format+".jpg";
    }

}
