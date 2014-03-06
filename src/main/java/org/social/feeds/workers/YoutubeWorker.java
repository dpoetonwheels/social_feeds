/**
 * 
 */
package org.social.feeds.workers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

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

		// To increase efficiency, only retrieve the fields that the
		// application uses.
		//search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url,snippet/publishedAt)");
		search.setMaxResults(25L);

		// Call the API and print results.
		SearchListResponse searchResponse = search.execute();
		List<SearchResult> searchResultList = searchResponse.getItems();
		
		saveFeeds(searchResultList);
		
		System.out.println("searchResultList == " + searchResultList);
		// Prints information about the results.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveFeeds(List<SearchResult> searchResultList) {
		
		for(SearchResult s: searchResultList) {
			Youtube media = new Youtube();
			media.setVideoId(s.getId().getVideoId());
			media.setTitle(s.getSnippet().getTitle());
			java.sql.Timestamp timestamp = new Timestamp(s.getSnippet().getPublishedAt().getValue());
			media.setPublishedAt(timestamp);
			youtubeService.addYoutubeFeed(media);
		}
		
	}	
}
