package org.social.feeds.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.feeds.config.InstagramConfigurationTemplate;
import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    		
    		model.addAttribute("tweets",  getTwitterFeeds());
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
	
	public List<Status> getTwitterFeeds() {
		
		// first fetch the last since id tweet from the db.
		// use that to make a forward search on twitter to fetch next data.
		// if data found, store it in db, else sleep for 15 minutes.
		// use do, while.
		
		
		
		Query query = new Query("from:" + "davidstarsoccer" + " #" + "share2");
		//query.setSinceId(433750539751280642L);
		
		query.setSinceId(0);
		
		
		List<Status> tweets = new ArrayList<Status>();
		try {
			tweets = twitterTemplate.twitterBean().search(query).getTweets();
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
			//query.setSinceId(sinceId);
			
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
