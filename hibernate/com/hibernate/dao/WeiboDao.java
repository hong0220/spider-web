package com.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import com.hibernate.model.Weibo;

public class WeiboDao {
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

	public void save(Weibo weibo) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(weibo);
		tx.commit();
		session.close();
	}

	// public Boolean isExits(Weibo weibo) {
	// session = sessionFactory.openSession();
	// List mates = session.find("from Weixin weixin where weixin.http = ?",
	// weixin.getHttp(), Hibernate.STRING);
	// int count = mates.size();
	// session.close();
	// if (count == 0) {
	// return false;
	// } else {
	// return true;
	// }
	// }
}
