/**
 * 
 */
package org.social.feeds.service;

import java.util.List;

import org.social.feeds.dao.TwitterDAO;
import org.social.feeds.dao.YoutubeDAO;
import org.social.feeds.model.Youtube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author devang.desai
 *
 */
@Service
public class YoutubeServiceImpl implements YoutubeService {

	@Autowired
	private YoutubeDAO youtubeDAO;
	
	@Transactional
	public List<Youtube> listFeeds() {
		return youtubeDAO.listFeeds();
	}

	@Transactional
	public List<Youtube> getFeedsByPage(int page, int size) {
		return youtubeDAO.getFeedsByPage(page, size);
	}

	@Transactional
	public Long getYoutubeFeedSinceId(String text, boolean isLast) {
		return youtubeDAO.getYoutubeFeedSinceId(text, isLast);
	}

	@Transactional
	public void addYoutubeFeed(Youtube feed) {
		youtubeDAO.addYoutubeFeed(feed);
	}

}
