package com.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import com.hibernate.model.WeiboSearch;

public class WeiboSearchDao {
	private static SessionFactory sessionFactory = null;
	private Session session = null;
	private Transaction tx = null;
	static {
		try {
			Configuration config = new Configuration().configure();
			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void save(WeiboSearch weiboSearch) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(weiboSearch);
		tx.commit();
		session.close();
	}

	// public Boolean isExits(WeiboSearch weiboSearch) {
	// session = sessionFactory.openSession();
	// Criteria criteria = session.createCriteria(WeiboSearch.class);
	// criteria.add(Restrictions.eq("http", weiboSearch.getHttp()));
	// int count = criteria.list().size();
	// session.close();
	// if (count == 0) {
	// return false;
	// } else {
	// return true;
	// }
	// }
}
