/**
 * 
 */
package org.social.feeds.dao;

import java.util.List;

import org.social.feeds.model.Twitter;
import org.social.feeds.model.Youtube;

/**
 * @author devang.desai
 *
 */
public interface YoutubeDAO {
	public void addYoutubeFeed(Youtube feed);
	public List<Youtube> listFeeds();
	public List<Youtube> getFeedsByPage(int page, int size);
	public Long getYoutubeFeedSinceId(String text, boolean isLast); 
}
