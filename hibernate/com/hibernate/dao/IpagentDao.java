package com.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import com.hibernate.model.Ipagent;

public class IpagentDao {
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

	public void save(Ipagent ipagent) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		System.out.println(ipagent);
		session.save(ipagent);
		tx.commit();
		session.close();
	}

	public Boolean isExits(Ipagent ipagent) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Ipagent.class);
		criteria.add(Restrictions.eq("ip", ipagent.getIp()));
		criteria.add(Restrictions.eq("port", ipagent.getPort()));
		int count = criteria.list().size();
		session.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
