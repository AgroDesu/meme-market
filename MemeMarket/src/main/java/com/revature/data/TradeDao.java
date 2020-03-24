package com.revature.data;

import java.util.Set;

import com.revature.beans.Trade;

public interface TradeDao {
	
	public int addTrade(Trade tr);
	public Trade getTrade(int id);
	public boolean updateTrade(Trade tr);
	public boolean deleteTrade(Trade tr);
	public Set<Trade> getTradesByPatron(Integer id);
	public void acceptTrade(Trade t);
	public boolean rejectTrade(Trade t);
}
