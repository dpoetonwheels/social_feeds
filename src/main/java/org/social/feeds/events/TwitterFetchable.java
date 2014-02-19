/**
 * 
 */
package org.social.feeds.events;

import java.util.ArrayList;
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
public class TwitterFetchable implements Runnable {

	// Twitter properties
	@Autowired
	private TwitterConfigurationTemplate twitterTemplate;

	@Autowired
	private TwitterService twitterService;
	
	@Override
	public void run() {
		// first fetch the last since id tweet from the db.
		// use that to make a forward search on twitter to fetch next data.
		// if data found, store it in db, and sleep for 15 minutes.

		Query query = new Query("from:" + "davidstarsoccer" + " #" + "share2");
		List<Status> tweets = new ArrayList<Status>();

		while (true) {
			Long sinceID = fetchTweetSinceId("", true);
			query.setSinceId(sinceID);
			System.out.println("since ID == " + sinceID);

			try {
				tweets = twitterTemplate.twitterFactoryBean().search(query)
						.getTweets();
				if (canSaveTweets(tweets) || sinceID == 0) {
					System.out.println("saving -- ");
					saveTweets(tweets);
				}
				System.out.println("sleeping current thread-----");
				Thread.sleep(50000);
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				
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
	
}
