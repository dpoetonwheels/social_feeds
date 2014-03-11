/**
 * 
 */
package org.social.feeds.service;

import java.sql.Timestamp;
import java.util.List;

import org.social.feeds.model.Twitter;
import org.social.feeds.model.Youtube;


/**
 * @author devang.desai
 *
 */
public interface YoutubeService {
	public void addYoutubeFeed(Youtube feed);
	public List<Youtube> listFeeds();
	public List<Youtube> getFeedsByPage(int page, int size);
	public String getYoutubeFeedSincePublished(boolean isLast);
	public List<Youtube> getVideosForJSON(int startPage, int limit); 
}
