package com.hyh.transaction;

import java.util.List;

public interface GoodsService {
    int getGoods(int id);
    
    int setGoods(int id);
    
    List<Object> queryGoods();
    
}
