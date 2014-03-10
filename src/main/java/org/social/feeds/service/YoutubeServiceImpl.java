/**
 * 
 */
package org.social.feeds.service;

import java.sql.Timestamp;
import java.util.List;

import org.social.feeds.dao.TwitterDAO;
import org.social.feeds.dao.YoutubeDAO;
import org.social.feeds.model.Youtube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.util.DateTime;

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
	public void addYoutubeFeed(Youtube feed) {
		youtubeDAO.addYoutubeFeed(feed);
	}

	@Transactional
	public String getYoutubeFeedSincePublished(boolean isLast) {
		return youtubeDAO.getYoutubeFeedSincePublished(isLast);
	}

}
