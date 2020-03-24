
package com.revature.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.revature.beans.Card;
import com.revature.beans.Rarity;
import com.revature.beans.User;
import com.revature.utils.HibernateUtil;
import com.revature.utils.LogUtil;

@Component
public class CardHibernate implements CardDao{
	private HibernateUtil hu = HibernateUtil.getInstance();
	
	@Override
	public int addCard(Card c) {
		Session s = hu.getSession();
		Transaction t = null;
		Integer i = 0;
		try {
			t = s.beginTransaction();
			i = (Integer) s.save(c);
			t.commit();
		} catch(HibernateException e) {
			t.rollback();
			LogUtil.logException(e, UserHibernate.class);
		} finally {
			s.close();
		}
		return i;
	};
	
	@Override
	public Card getCard(int id) {
		Session s = hu.getSession();
		Card c = s.get(Card.class, id);
		s.close();
		return c;
	};
	
	@Override
	public List<Card> getCardsByRarity(Rarity r){
		Session s = hu.getSession();
		String query = "from Card where rarityId=:rarity";
		Query<Card> q = s.createQuery(query, Card.class);
		q.setParameter("rarity", r.getId());
		List<Card> cardList = q.getResultList();
		List<Card> notDeletedCards = new ArrayList<>();
		for(Card c : cardList) {
			if(!"Deleted Card".equals(c.getCardText())) {
				notDeletedCards.add(c);
			}
		}
		return notDeletedCards;		
	}
	
	public boolean updateCard(Card c) {
		Session s = hu.getSession();
		Transaction t = null;
		boolean b = false;
		try{
			t = s.beginTransaction();
			s.update(c);
			t.commit();
			b = true;
		} catch(Exception e) {
			if(t != null)
				t.rollback();
			LogUtil.logException(e, UserHibernate.class);
		} finally {
			s.close();
		}
		return b;
	};
	public boolean deleteCard(Card c) {
		Session s = hu.getSession();
		Transaction t = null;
		boolean b = false;
		c.setCardText("Deleted Card");
		c.setMemeText("Deleted Meme");
		c.setImage("https://i.imgur.com/JqFuYc1.png");
		try{
			t = s.beginTransaction();
			s.update(c);
			t.commit();
			b = true;
		} catch(Exception e) {
			if(t != null)
				t.rollback();
			LogUtil.logException(e, UserHibernate.class);
		} finally {
			s.close();
		}
		return b;
	}

	@Override
	public Set<Card> getCards() {
		Session s = hu.getSession();
		String query = "FROM Card";
		Query<Card> q = s.createQuery(query, Card.class);
		List<Card> cardList = q.getResultList();
		List<Card> notDeletedCards = new ArrayList<>();
		for(Card c : cardList) {
			if(!"Deleted Card".equals(c.getCardText())) {
				notDeletedCards.add(c);
			}
		}
		Set<Card> cardSet = new HashSet<Card>(notDeletedCards);
		s.close();
		return cardSet;	
	};

}
