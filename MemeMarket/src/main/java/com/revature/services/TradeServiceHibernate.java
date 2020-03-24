package com.revature.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Trade;
import com.revature.data.TradeDao;

@Service
public class TradeServiceHibernate implements TradeService {
	@Autowired
	private TradeDao td;
	
	@Override
	public int addTrade(Trade tr) {
		return td.addTrade(tr);
	}

	@Override
	public Trade getTrade(int id) {
		return td.getTrade(id);
	}

	@Override
	public boolean updateTrade(Trade tr) {
		return td.updateTrade(tr);
	}

	@Override
	public boolean deleteTrade(Trade tr) {
		return td.deleteTrade(tr);
	}

	@Override
	public Set<Trade> getTrades(Integer id) {
		return td.getTradesByPatron(id);
	}
	
	@Override
	public void acceptTrade(Trade tr) {
		td.acceptTrade(tr);
	}

}
