package com.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import com.hibernate.model.Bbs;

public class BbsDao {
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

	public void save(Bbs bbs) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(bbs);
		tx.commit();
		session.close();
	}

	public Boolean isExits(Bbs bbs) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Bbs.class);
		criteria.add(Restrictions.eq("http", bbs.getHttp()));
		int count = criteria.list().size();
		session.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
