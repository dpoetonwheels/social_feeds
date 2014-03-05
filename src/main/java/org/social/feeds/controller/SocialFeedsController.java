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
	
	/**
	 * Gets all the twitter feeds json
	 */
	@RequestMapping(value = "/twitter", method = RequestMethod.GET)
	public @ResponseBody String getAllTwitterJSON(Locale locale, Model model) {
		
		Gson gson = new Gson();		
		return gson.toJson(getAllTweets());
	}
	
	/**
	 * Get paginated json for twitter feeds
	 * 
	 * @return
	 */
	@RequestMapping(value = "/twitter/{number}", method = RequestMethod.GET)
	public @ResponseBody String getTwitterJson(@PathVariable("number") String number) {
		List<Twitter> tweets = twitterService.getTweetsByPage(Integer.parseInt(number), 0);
		Gson gson = new Gson();		
		return gson.toJson(tweets);
	}
	
	/**
	    * Fetch a the user list from the service, and package up into a Map that is 
	    * compatible with datatables.net
	    */
	     
	    @RequestMapping(method={RequestMethod.POST,RequestMethod.GET} ,value="/twitter_feeds")
	    public @ResponseBody Map<String, Object[]> findAllForTableView(){
	       
	       Collection<Twitter> tweets = twitterService.listTweets();
	       Map<String, Object[]> twitterJSON = new HashMap<String, Object[]>();
	       
	       twitterJSON.put("iTotalRecords", new Object[] {tweets.size()});
	       twitterJSON.put("iTotalDisplayRecords", new Object[] {10});
	       twitterJSON.put("aaData", getJSONForTwitter(tweets));
	       
	       return twitterJSON;
	    }
	
	    /**
	     * I only want certain user info..
	     */
	     public Object[] getJSONForTwitter(Collection<Twitter> tweets){
	         Object[] rdArray = new Object[tweets.size()];
	         int i = 0;
	         for(Twitter u:tweets){
	             Object[] us = new String[]{u.getId().toString(), u.getUserName(), u.getTweet()}; 
	             rdArray[i] = us;
	             i++;            
	         }
	         return rdArray;
	     }   
	
	     @RequestMapping(value = "/sample", method = RequestMethod.GET)
	     public @ResponseBody String doAjax( @RequestParam int iDisplayStart,
	                                 @RequestParam int iDisplayLength,
	                                 @RequestParam int iColumns,
	                                 @RequestParam String sEcho) {      
	              
	             
	    	 	// sample json to be filled
	             return
	              
	             "{  \"sEcho\": 2," +
	             "   \"iTotalRecords\": 2," +
	             "   \"iTotalDisplayRecords\": 2," +
	             "   \"aaData\": [" +
	             "       [" +
	             "           \"Gecko\"," +
	             "           \"Firefox 1.0\"," +
	             "           \"Win 98+ / OSX.2+\"," +
	             "           \"1.7\"," +
	             "           \"A\"" +
	             "       ]," +
	             "       [" +
	             "           \"Gecko\"," +
	             "           \"Firefox 1.5\"," +
	             "           \"Win 98+ / OSX.2+\"," +
	             "           \"1.8\"," +
	             "           \"A\"" +
	             "       ]" +
	             "   ]" +
	             "}";
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
