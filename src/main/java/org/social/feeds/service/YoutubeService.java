/**
 * 
 */
package org.social.feeds.service;

import java.util.List;

import org.social.feeds.model.Youtube;

/**
 * @author devang.desai
 *
 */
public interface YoutubeService {
	public void addYoutubeFeed(Youtube feed);
	public List<Youtube> listFeeds();
	public List<Youtube> getFeedsByPage(int page, int size);
	public Long getYoutubeFeedSinceId(String text, boolean isLast); 
}
