package com.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import com.hibernate.model.WeiboComment;

public class WeiboCommentDao {
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

	public void save(WeiboComment weiboComment) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.save(weiboComment);
		tx.commit();
		session.close();
	}

	public Boolean isExits(WeiboComment weiboComment) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(WeiboComment.class);
		criteria.add(Restrictions.eq("cid", weiboComment.getCid()));
		criteria.add(Restrictions.eq("weiboid", weiboComment.getWeiboid()));
		int count = criteria.list().size();
		session.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
