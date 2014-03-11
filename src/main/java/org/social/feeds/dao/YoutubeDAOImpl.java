/**
 * 
 */
package org.social.feeds.dao;

import java.util.List;

import org.hibernate.Query;
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
		// fetch data using scrollable results (hibernate)
		Query query = generateQuery();
		query.setFirstResult(page);
		query.setMaxResults(size);

		return query.list();
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

	private org.hibernate.Query generateQuery() {
		String strQry = "from Youtube c order by c.id asc";
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				strQry);
		return query;
	}
	
	@Override
	public List<Youtube> getVideosForJSON(int startPage, int limit) {
		// fetch data using scrollable results (hibernate)
		Query query = generateQuery();
		int firstResult = (startPage * limit) - (limit);
		query.setFirstResult(firstResult);
		query.setMaxResults(limit);

		return query.list();
	}
	
}
