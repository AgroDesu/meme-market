package com.revature.data;



import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.beans.User;
import com.revature.utils.HibernateUtil;
import com.revature.utils.LogUtil;

@Component
public class UserHibernate implements UserDao {
	private HibernateUtil hu = HibernateUtil.getInstance();
	@Autowired
	private PatronDao pd;
	
	@Override
	public int addUser(User u) {
		Session s = hu.getSession();
		Transaction t = null;
		Integer i = 0;
		try {
			t = s.beginTransaction();
			i = (Integer) s.save(u);
			t.commit();
		} catch(HibernateException e) {
			t.rollback();
			LogUtil.logException(e, UserHibernate.class);
		} finally {
			s.close();
		}
		return i;
	}
	
	@Override
	public User getUser(String username, String password) {
		Session s = hu.getSession();
		String query = "from User u where u.username=:username and u.password=:password";
		Query<User> q = s.createQuery(query, User.class);
		q.setParameter("username", username);
		q.setParameter("password", password);
		User u = q.uniqueResult();
		s.close();
		return u;
	}
	
	@Override
	public List<User> getAll(){
		Session s = hu.getSession();
		String query = "from User";
		Query<User> q = s.createQuery(query, User.class);
		List<User> uList = q.getResultList();
		return uList;
	}
	
	@Override
	public User getUser(String username) {
		Session s = hu.getSession();
		String query = "from User u where u.username=:username";
		Query<User> q = s.createQuery(query, User.class);
		q.setParameter("username", username);
		User u = q.uniqueResult();
		s.close();
		return u;
	}
	
	@Override
	public User getUser(User u) {
		Session s = hu.getSession();
		User ret = s.get(User.class, u.getId());
		if(ret==null) {
			String query = "from User u where u.username=:username and u.password=:password";
			Query<User> q = s.createQuery(query, User.class);
			q.setParameter("username", u.getUsername());
			q.setParameter("password", u.getPassword());
			ret = q.uniqueResult();
		}
		s.close();
		return ret;
	}
	
	@Override
	public User getUserByPatron(Integer id) {
		Session s = hu.getSession();
		String query = "from User where patron=:patron";
		Query<User> q = s.createQuery(query, User.class);
		q.setParameter("patron", pd.getPatron(id));
		User u = q.uniqueResult();
		s.close();
		return u;
	}
	
	@Override
	public User getUserById(Integer id) {
		Session s = hu.getSession();
		User ret = s.get(User.class, id);
		s.close();
		return ret;
	}
	
	@Override
	public boolean deleteUser(User user) {
		Session s = hu.getSession();
		Transaction t = null;
		boolean b = false;
		try{
			t = s.beginTransaction();
			s.delete(user);
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
	public boolean updateUser(User user) {
		Session s = hu.getSession();
		Transaction t = null;
		boolean b = false;
		try{
			t = s.beginTransaction();
			s.update(user);
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
}
