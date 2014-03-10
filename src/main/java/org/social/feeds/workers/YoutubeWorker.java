/**
 * 
 */
package org.social.feeds.workers;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.apache.http.impl.cookie.DateUtils;
import org.apache.log4j.Logger;
import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.config.YoutubeConfigurationTemplate;
import org.social.feeds.model.Twitter;
import org.social.feeds.model.Youtube;
import org.social.feeds.service.TwitterService;
import org.social.feeds.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.Status;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

/**
 * @author devang.desai
 *
 */
@Component("youtubeWorker")
public class YoutubeWorker implements Worker {

	protected static Logger logger = Logger.getLogger("service");
	
	@Autowired
	private YoutubeConfigurationTemplate youtubeTemplate;

	@Autowired
	private YoutubeService youtubeService;
	
	@Override
	public void updateDB() {
		// Define the API request for retrieving search results.
		
		YouTube.Search.List search;
		try {
			search = youtubeTemplate.youtubeFactoryBean().search()
					.list("id,snippet");
		
		search.setChannelId("UCC0_KPV4oMrvPYyJhYkyPvw");
		search.setType("video");
		search.setOrder("date");
				
		// Check db if an entry exists
		String publishedAt = fetchFeedPublishedAt(true);
		//String publishedAt = "2014-03-02T21:51:11.000Z";
		//String publishedAt = "2014-03-06T21:51:11.000Z";
		if(publishedAt != null) {
			DateTime dt = new DateTime(DateTime.parseRfc3339(publishedAt).getValue() + 1000);
			search.setPublishedAfter(dt);
		}
				// To increase efficiency, only retrieve the fields that the
		// application uses.
		search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url,snippet/publishedAt)");
		search.setMaxResults(25L);

		// Limit searching based on what is available in the database.
		// We will use publishedAt as the check condition.
		
		
		// Call the API and print results.
		SearchListResponse searchResponse = search.execute();
		List<SearchResult> searchResultList = searchResponse.getItems();
		
		if(canSaveFeeds(searchResultList, publishedAt)) {
			saveFeeds(searchResultList);
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean canSaveFeeds(List<SearchResult> searchResultList, String publishedAt) {
					
		if(searchResultList.isEmpty()) {
			return false;
		} else if(publishedAt == null) {
			return true;
		} else {
			SearchResult s = searchResultList.get(0);

			Date date1 = new Date(s.getSnippet().getPublishedAt().getValue());
			Date date2 = new Date(new DateTime(publishedAt).getValue());
			
			if (date1.after(date2)) {
				return true;
			}
		}
		return false;
	}

	public String fetchFeedPublishedAt(boolean isLast) {
		return youtubeService.getYoutubeFeedSincePublished(isLast);
	}
	
	/**
	 * Save the results in the reverse order as we rely on 
	 * publishedAt date for the next search.
	 * @param searchResultList
	 */
	private void saveFeeds(List<SearchResult> searchResultList) {
		
		for(int i = searchResultList.size() - 1; i >= 0; i--) {
			Youtube media = new Youtube();
			media.setVideoId(searchResultList.get(i).getId().getVideoId());
			media.setTitle(searchResultList.get(i).getSnippet().getTitle());
			media.setPublishedAt(searchResultList.get(i).getSnippet().getPublishedAt().toString());
			youtubeService.addYoutubeFeed(media);
		}
		
	}	
}
