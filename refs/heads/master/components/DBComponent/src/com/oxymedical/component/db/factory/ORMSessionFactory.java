package com.oxymedical.component.db.factory;

import java.util.HashMap;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * SessionFactory that stores ORM's SessionFactory object so that not every time
 * we have to buildSessionFactory from config.
 * <p />
 * Since there can be multiple configs, creating a new sessionFactory every time
 * using buildSessionFactory can be costly. Hence maintaining a HashMap of
 * sessionFactories pertaining to each ORM Configuration and fetching from that
 * HashMap.
 * 
 * @author hs
 * 
 */
public class ORMSessionFactory
{
	static HashMap<String, SessionFactory> sessionFactories = null;

	/**
	 * Returns ORM's SessionFactory based on Configuration and serverURL
	 * provided. The dbServerURL acts as key for HashMap
	 * 
	 * @param config
	 * @param dbServerURL
	 * @return
	 */
	public SessionFactory getSessionFactory(Configuration config)
	{
		String dbConnUrl = config.getProperty("hibernate.connection.url");

		if (sessionFactories == null)
		{
			sessionFactories = new HashMap<String, SessionFactory>();
		}
		
		SessionFactory sessionFac = sessionFactories.get(dbConnUrl);
		if (sessionFac == null)
		{
			synchronized (config)
			{
				sessionFac = config.buildSessionFactory();
				sessionFactories.put(dbConnUrl, sessionFac);
			}
		}
		return sessionFac;
	}
}
