/**
 * 
 */
package org.social.feeds.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * @author devang.desai
 *
 */
public class TwitterEvents {

	private TwitterConfigurationTemplate twitterTemplate;
	private TwitterService twitterService;
	
	public TwitterEvents(TwitterService twitterService, TwitterConfigurationTemplate twitterTemplate) {
		this.twitterService = twitterService;
		this.twitterTemplate = twitterTemplate;
	}
	
	public Long fetchTweetSinceId(String tweetText, boolean isLast) {
		return twitterService.getTwitterSinceId(tweetText, isLast);
	}
	
	public boolean canSaveTweets(List<Status> tweets) {
		return tweets.size() > 0 ? true : false;
	}
	
	public void saveTweets(List<Status> tweets) {
		Long id;
		for(Status status: tweets) {
			id = status.getId();
			System.out.println("status --- " + status.getText());
			System.out.println("id --- " + id);
			Twitter twitter = new Twitter();
			twitter.setSince_id(id);
			twitter.setTweet(status.getText());
			twitterService.addTwitter(twitter);
		}
	}
	
	/**
	 * first fetch the last since id tweet from the db.
	 * use that to make a forward search on twitter to fetch next data.
	 * if data found, store it in db, and sleep for 15 minutes 
	 * (the thread sleep should take place in the view - AJAX)
	 * @return
	 */
	public List<Status> updateTwitterFeeds() {		

		Query query = new Query("from:" + "davidstarsoccer" + " #" + "share2");
		// query.setSinceId(433750539751280642L);
		List<Status> tweets = new ArrayList<Status>();

		Long sinceID = fetchTweetSinceId("", true);
		query.setSinceId(sinceID);
		
		try {
			tweets = twitterTemplate.twitterFactoryBean().search(query)
					.getTweets();
			// The default sorting order will rely on the sinceid
			Collections.sort(tweets, null);
						
			if (canSaveTweets(tweets)) {
				saveTweets(tweets);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return tweets;
			
	}
		
	
}
