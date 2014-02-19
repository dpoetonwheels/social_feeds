/**
 * 
 */
package org.social.feeds.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.social.feeds.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * @author devang.desai
 *
 */
@Controller
@RequestMapping("/twitter")
public class TwitterFeedsController {

	// Twitter properties
	@Autowired
	private TwitterConfigurationTemplate twitterTemplate;

	@Autowired
	private TwitterService twitterService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/1")
	@ResponseBody
	public JSONObject feeds(Locale locale, Model model) {
		
		//Twitter twitter = new Twitter();
		//twitter.setName("hello");
		
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject("{'test1':'test', 'test2':'test two'}");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return json;
	}
	
	public JSONObject testJSON() throws JSONException {
		JSONObject json = new JSONObject("{'test1':'test', 'test2':'test two'}");
		return json;
	}
	
	public List<Status> getTwitterFeeds() {

		// first fetch the last since id tweet from the db.
		// use that to make a forward search on twitter to fetch next data.
		// if data found, store it in db, else sleep for 15 minutes.
		// use do, while.

		Query query = new Query("from:" + "davidstarsoccer" + " #" + "share2");
		// query.setSinceId(433750539751280642L);

		query.setSinceId(0);

		List<Status> tweets = new ArrayList<Status>();
		try {
			tweets = twitterTemplate.twitterFactoryBean().search(query).getTweets();
			Long id;
			for (Status status : tweets) {
				id = status.getId();
				System.out.println("status --- " + status.getText());
				System.out.println("id --- " + id);
				Twitter twitter = new Twitter();
				twitter.setSince_id(id);
				twitter.setTweet(status.getText());
				twitterService.addTwitter(twitter);
			}
			// query.setSinceId(sinceId);
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return tweets;
	}
	
}
