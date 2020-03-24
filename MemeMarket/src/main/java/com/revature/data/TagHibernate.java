package com.revature.data;

import java.util.List;

import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.revature.beans.Card;
import com.revature.beans.Tag;
import com.revature.utils.HibernateUtil;
import com.revature.utils.LogUtil;

@Component
public class TagHibernate implements TagDao{
	private HibernateUtil hu = HibernateUtil.getInstance();
	
	@Override
	public int addTag(Tag t) {
		Session s = hu.getSession();
		Transaction ts = null;
		Integer i = 0;
		try {
			ts = s.beginTransaction();
			i = (Integer) s.save(t);
			ts.commit();
		} catch(HibernateException e) {
			ts.rollback();
			LogUtil.logException(e, UserHibernate.class);
		} finally {
			s.close();
		}
		return i;	
	};
	
	@Override
	public Tag getTag(int id) {
		Session s = hu.getSession();
		Tag t = s.get(Tag.class, id);
		s.close();
		return t;
	};
	
	@Override
	public Tag getTagByName(String name) {
		Session s = hu.getSession();
		String query = "from Tag where name=:name";
		Query<Tag> q = s.createQuery(query, Tag.class);
		q.setParameter("name", name);
		Tag tagList = q.uniqueResult();
		s.close();
		return tagList;
	};
	
	@Override
	public boolean updateTag(Tag tg) {
		Session s = hu.getSession();
		Transaction t = null;
		boolean b = false;
		try{
			t = s.beginTransaction();
			s.update(tg.getId());
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
	
	@Override
	public boolean deleteTag(Tag tg) {
		Session s = hu.getSession();
		Transaction t = null;
		boolean b = false;
		try{
			t = s.beginTransaction();
			s.delete(tg);
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

}
