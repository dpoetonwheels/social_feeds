/**
 * 
 */
package org.social.feeds.service;

import org.social.feeds.model.Twitter;

/**
 * @author devang.desai
 *
 */
public interface TwitterService {
	public void addTwitter(Twitter twitter);
	public Long getTwitterSinceId(String text, boolean isLast);
}
