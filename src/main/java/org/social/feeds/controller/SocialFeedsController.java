package org.social.feeds.controller;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.feeds.config.InstagramConfigurationTemplate;
import org.social.feeds.model.DataTablesTO;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


/**
 * Handles requests for the application home page.
 */
@Controller
public class SocialFeedsController {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialFeedsController.class);
		
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
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		
		// get tweets from Twitter
		try {			
			model.addAttribute("tweets", getAllTweets());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		// get posts from facebook.
		// model.addAttribute("fbposts", getFacebookFeeds());

		// get user from instagram
		try {
			model.addAttribute("instauser", getInstagramFeeds().getFullName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "home";
	}
	
	private List<Twitter> getAllTweets() {
		return twitterService.listTweets();
	}
		
	private com.sola.instagram.model.User getInstagramFeeds() throws Exception {
		com.sola.instagram.model.User me = instagramTemplate.instagramBean()
				.searchUsersByName("devangvdesai").get(0);
		
		return me;
	}
	
	
	private void getYoutubeVideos() {
		
	}
	
}
