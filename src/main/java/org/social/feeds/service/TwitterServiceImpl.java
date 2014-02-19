/**
 * 
 */
package org.social.feeds.service;

import java.util.List;

import org.social.feeds.dao.TwitterDAO;
import org.social.feeds.model.Twitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author devang.desai
 *
 */
@Service
public class TwitterServiceImpl implements TwitterService {

	@Autowired
	private TwitterDAO twitterDAO;
	
	@Transactional
	public void addTwitter(Twitter twitter) {
		twitterDAO.addTweet(twitter);
	}

	@Transactional
	public Long getTwitterSinceId(String text, boolean isLast) {
		return twitterDAO.getTwitterSinceId(text, isLast);
	}

	@Transactional
	public List<Twitter> listTweets() {
		return twitterDAO.listTweets();
	}

}
