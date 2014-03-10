/**
 * 
 */
package org.social.feeds.dao;

import java.sql.Timestamp;
import java.util.List;

import org.social.feeds.model.Youtube;

import com.google.api.client.util.DateTime;

/**
 * @author devang.desai
 *
 */
public interface YoutubeDAO {
	public void addYoutubeFeed(Youtube feed);
	public List<Youtube> listFeeds();
	public List<Youtube> getFeedsByPage(int page, int size);
	public String getYoutubeFeedSincePublished(boolean isLast); 
}
