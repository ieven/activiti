package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hxkj.zncrm.dao.domain.Stock_listEntity;
import com.hxkj.zncrm.dao.mapper.Stock_listMapper;
import com.hxkj.zncrm.service.Stock_listService;
@Service
public class Stock_listServiceImpl implements Stock_listService {

    @Autowired
    private Stock_listMapper mapper;

    @Override
	public List<Stock_listEntity> getStockList(Map<String, String> input) {
		// TODO Auto-generated method stub
		return mapper.getStockList(input);
	}

	@Override
	public String getStockCount(Map<String, String> input) {
		// TODO Auto-generated method stub
		return mapper.getStockCount(input);
	}

	@Override
	public long addStock(Map<String, String> input) {
		// TODO Auto-generated method stub
		return mapper.addStock(input);
	}

	@Override
	public int delStock(Map<String, String> input) {
		// TODO Auto-generated method stub
		return mapper.delStock(input);
	}

	@Override
	public int updateStock(Map<String, String> input) {
		// TODO Auto-generated method stub
		return mapper.updateStock(input);
	}


}
