/**
 * 
 */
package org.social.feeds.workers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * @author devang.desai
 *
 */
@Component("twitterWorker")
public class TwitterWorker implements Worker {

	protected static Logger logger = Logger.getLogger("service");
	
	@Autowired
	private TwitterConfigurationTemplate twitterTemplate;

	@Autowired
	private TwitterService twitterService;
	
	/*private TwitterConfigurationTemplate twitterTemplate;
	private TwitterService twitterService;
	
	public TwitterWorker(TwitterService twitterService, TwitterConfigurationTemplate twitterTemplate) {
		this.twitterService = twitterService;
		this.twitterTemplate = twitterTemplate;
	}
	*/
	public Long fetchTweetSinceId(String tweetText, boolean isLast) {
		return twitterService.getTwitterSinceId(tweetText, isLast);
	}
	
	public boolean canSaveTweets(List<Status> tweets) {
		return tweets.size() == 0 ? false : true;
	}
	
	public void saveTweets(List<Status> tweets) {
		Long id;
		for(Status status: tweets) {
			id = status.getId();
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
	private void updateTwitterFeeds() {		

		Query query = new Query("#share2 from:davidstarsoccer");
		// query.setSinceId(433750539751280642L);
		List<Status> tweets = new ArrayList<Status>();

		Long sinceID = fetchTweetSinceId("", true);
		query.setSinceId(sinceID);
		
		try {
			tweets = twitterTemplate.twitterFactoryBean().search(query)
					.getTweets();
			logger.debug("fetching tweets from Twitter..." + tweets);
			// The default sorting order will rely on the sinceid
			Collections.sort(tweets, null);
						
			if (canSaveTweets(tweets)) {
				saveTweets(tweets);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
			
	}

	@Override
	public void updateDB() {
		String threadName = Thread.currentThread().getName();
		logger.debug("   " + threadName + " has began working.");
        try {
        	logger.debug("working...");
        	updateTwitterFeeds();
            Thread.sleep(10000); // simulates work
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.debug("   " + threadName + " has completed work.");
		
	}
		
	
}
