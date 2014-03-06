/**
 * 
 */
package org.social.feeds.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.social.feeds.model.Youtube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author devang.desai
 *
 */
@Repository
public class YoutubeDAOImpl implements YoutubeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Youtube> listFeeds() {
		return sessionFactory.getCurrentSession().createQuery("from Youtube").list();
	}

	@Override
	public List<Youtube> getFeedsByPage(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getYoutubeFeedSinceId(String text, boolean isLast) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addYoutubeFeed(Youtube feed) {
		sessionFactory.getCurrentSession().save(feed);
	}

}
