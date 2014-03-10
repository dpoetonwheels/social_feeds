/**
 * 
 */
package org.social.feeds.dao;

import java.util.List;

import org.hibernate.Query;
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
	public List<org.social.feeds.model.Twitter> listTweets() {
		return sessionFactory.getCurrentSession().createQuery("from Twitter").list();
	}

	@Override
	public Long getTwitterSinceId(String text, boolean isLast) {
		Long id = 0L;
		if (isLast) {
			List<org.social.feeds.model.Twitter> tweet = sessionFactory.getCurrentSession().createQuery("from Twitter order by id desc").setMaxResults(1).list();
			if(tweet.size() > 0) {
				id = tweet.get(0).getSince_id();
			}
		}
		
		return id;
	}

	@Override
	public List<org.social.feeds.model.Twitter> getTweetsByPage(int page, int size) {
		// fetch data using scrollable results (hibernate)
		String strQry = "from Twitter c order by c.id asc";
        Query query = this.sessionFactory.getCurrentSession().createQuery(strQry);
        query.setFirstResult(page);
        query.setMaxResults(size);
 
        return query.list();
	}
	
	@Override
	public List<org.social.feeds.model.Twitter> getTweetsForJSON(int startPage, int limit) {
		// fetch data using scrollable results (hibernate)
		String strQry = "from Twitter c order by c.id asc";
        Query query = this.sessionFactory.getCurrentSession().createQuery(strQry);
        int firstResult = (startPage * limit) - (limit);
        query.setFirstResult(firstResult);
        query.setMaxResults(limit);
 
        return query.list();
	}
	
}
