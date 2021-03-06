/**
 * 
 */
package org.social.feeds.service;

import java.util.List;

import org.social.feeds.model.Twitter;

/**
 * @author devang.desai
 *
 */
public interface TwitterService {
	public void addTwitter(Twitter twitter);
	public Long getTwitterSinceId(String text, boolean isLast);
	public List<Twitter> getTweetsByPage(int page, int size);
	public List<Twitter> getTweetsForJSON(int startPage, int limit);
	public List<Twitter> listTweets();
}
