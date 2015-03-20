package com.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import com.hibernate.model.News;

public class NewsDao {
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

	public void save(News news) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(news);
		tx.commit();
		session.close();
	}

	public Boolean getExist(News news) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(News.class);
		criteria.add(Restrictions.eq("http", news.getHttp()));
		int count = criteria.list().size();
		session.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
