package org.social.feeds.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.feeds.config.InstagramConfigurationTemplate;
import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.events.TwitterEvents;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SocialFeedsController {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialFeedsController.class);
	
	// Twitter properties
	@Autowired
	private TwitterConfigurationTemplate twitterTemplate;

	@Autowired
	private TwitterService twitterService;
	
	// Instagram properties
	@Autowired
	private InstagramConfigurationTemplate instagramTemplate;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
						
		// get tweets from Twitter
    	try {    		
    		
    		TwitterEvents twitterEvents = new TwitterEvents(twitterService, twitterTemplate);
    		twitterEvents.updateTwitterFeeds();
    		model.addAttribute("tweets",  getAllTweets());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
    	// get posts from facebook.
    	//model.addAttribute("fbposts",  getFacebookFeeds());
    	
    	// get user from instagram
    	try {
			model.addAttribute("instauser", getInstagramFeeds());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home";
	}
	
	public List<Twitter> getAllTweets() {
		return twitterService.listTweets();
	}
	
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
	public List<Status> processTwitterFeeds() {		

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
		
	private com.sola.instagram.model.User getInstagramFeeds() throws Exception {
		com.sola.instagram.model.User me = instagramTemplate.instagramBean()
				.searchUsersByName("devangvdesai").get(0);
		System.out.println("user = " + me.getFullName());
		return me;
	}
	
}
