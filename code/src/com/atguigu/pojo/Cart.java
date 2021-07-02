package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    /**
     * key 是商品编号，
     * value，是商品信息
     */
    private Map<Integer,CartItem> items = new LinkedHashMap<Integer, CartItem>();

    public void addItem(CartItem cartItem){
        //看map中是否已經存在
        CartItem item = items.get(cartItem.getId());
        if(item == null){
            items.put(cartItem.getId(),cartItem);
        }else {
            item.setCount(item.getCount()+1);
//            item.se
        }
    }
    public void  deleteItem(Integer id){
        items.remove(id);
    }
    //清空購物車
    public  void clear(){
        items.clear();
    }
    public void updateCount(Integer id,Integer count){
        CartItem cartItem = items.get(id);
        if(cartItem!=null){
            cartItem.setCount(count);//修改商品数
        }

    }
    public Integer getTotalCount(){
        Integer totalCount = 0;
        for(Map.Entry<Integer,CartItem> entry: items.entrySet()){
            totalCount+=entry.getValue().getCount();
        }
        return totalCount;
    }
    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = new BigDecimal(0);
        for(Map.Entry<Integer,CartItem> entry:items.entrySet()){
//            System.out.println(entry.getValue().getTotalPrice());
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return  totalPrice;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
