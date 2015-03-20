package com.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import com.hibernate.model.Search;

public class SearchDao {
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

	public void save(Search search) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(search);
		tx.commit();
		session.close();
	}

	public Boolean isExits(Search search) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Search.class);
		criteria.add(Restrictions.eq("http", search.getHttp()));
		int count = criteria.list().size();
		session.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
