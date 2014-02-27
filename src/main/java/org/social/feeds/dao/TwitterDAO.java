package org.social.feeds.dao;

import java.util.List;

import org.social.feeds.model.Twitter;

public interface TwitterDAO {
	public void addTweet(Twitter tweet);
	public List<Twitter> listTweets();
	public List<Twitter> getTweetsByPage(int page, int size);
	public Long getTwitterSinceId(String text, boolean isLast); 
}
