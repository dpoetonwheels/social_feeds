package org.social.feeds.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

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
	public String home(Locale locale, final Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		// get tweets from Twitter
		try {
			TwitterEvents twitterEvents = new TwitterEvents(twitterService,
					twitterTemplate);
			twitterEvents.updateTwitterFeeds();
			model.addAttribute("tweets", getAllTweets());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		/*return new Callable<String>() {
			@Override
			public String call() throws Exception {
				for(int i=0; i < 10; i++) {
					model.addAttribute("foo", "Adding new --- " + i);
				}
				Thread.sleep(2000);
				return "home";
			}
		};*/
		
		// get posts from facebook.
		// model.addAttribute("fbposts", getFacebookFeeds());

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
		
	private com.sola.instagram.model.User getInstagramFeeds() throws Exception {
		com.sola.instagram.model.User me = instagramTemplate.instagramBean()
				.searchUsersByName("devangvdesai").get(0);
		System.out.println("user = " + me.getFullName());
		return me;
	}
	
}
