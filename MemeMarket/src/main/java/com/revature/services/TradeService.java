package com.revature.services;

import java.util.Set;

import com.revature.beans.Trade;

public interface TradeService {
	public int addTrade(Trade tr);
	public Trade getTrade(int id);
	public boolean updateTrade(Trade tr);
	public boolean deleteTrade(Trade tr);
	public Set<Trade> getTrades(Integer id);
	public void acceptTrade(Trade tr);
}
