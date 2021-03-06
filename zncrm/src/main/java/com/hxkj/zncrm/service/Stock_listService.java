package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.Stock_listEntity;

public interface Stock_listService {
	
	public List<Stock_listEntity> getStockList(Map<String, String> input);

    public String getStockCount(Map<String, String> input);

    public long addStock(Map<String, String> input);

    public int delStock(Map<String, String> input);

    public int updateStock(Map<String, String> input);

}
