/**
 * 
 */
package org.social.feeds.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.social.feeds.model.Twitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author devang.desai
 *
 */
@Repository
public class TwitterDAOImpl implements TwitterDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addTweet(Twitter tweet) {
		sessionFactory.getCurrentSession().save(tweet);
	}

	@Override
	public List<Twitter> listTweet() {
		
		return null;
	}

	@Override
	public Long getTwitterSinceId(String text, boolean isLast) {
		Long id = 0L;
		if (isLast) {
			List<Twitter> tweet = sessionFactory.getCurrentSession().createQuery("from Twitter order by id desc").setMaxResults(1).list();
			if(tweet.size() > 0) {
				id = tweet.get(0).getSince_id();
			}
		}
		
		return id;
	}
	
}
