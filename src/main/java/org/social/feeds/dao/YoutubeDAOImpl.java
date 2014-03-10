/**
 * 
 */
package org.social.feeds.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.SessionFactory;
import org.social.feeds.model.Youtube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.client.util.DateTime;

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
	public void addYoutubeFeed(Youtube feed) {
		sessionFactory.getCurrentSession().save(feed);
	}

	/**
	 * Get the last video published at to update the database.
	 * Ideally this should also check for duplicates, but we don't care
	 * as youtube does not allow duplicate videos.
	 */
	@Override
	public String getYoutubeFeedSincePublished(boolean isLast) {
		String publishedAt = null;
		if (isLast) {
			List<org.social.feeds.model.Youtube> feed = sessionFactory.getCurrentSession().createQuery("from Youtube order by id desc").setMaxResults(1).list();
			if(feed.size() > 0) {
				publishedAt = feed.get(0).getPublishedAt();
			}
		}
		
		return publishedAt;
	}
	
}
