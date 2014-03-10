/**
 * 
 */
package org.social.feeds.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.social.feeds.config.TwitterConfigurationTemplate;
import org.social.feeds.model.DataTablesTO;
import org.social.feeds.model.Twitter;
import org.social.feeds.service.TwitterService;
import org.social.feeds.service.TwitterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
	 * Get paginated json for twitter feeds
	 * 
	 * @return twitter json, given the fetch number
	 */
	@RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTwitterJson(@PathVariable("number") String number) {
		List<Twitter> tweets = twitterService.getTweetsForJSON(Integer.parseInt(number), 5);
		Gson gson = new Gson();
		String json = gson.toJson(tweets);
		return json;
	}
	
	/**
	 * Gets all the twitter feeds json
	 */
	@RequestMapping(value = "/twitter", method = RequestMethod.GET)
	public @ResponseBody String getAllTwitterJSON(Locale locale, Model model) {
		Gson gson = new Gson();
		return gson.toJson(twitterService.listTweets());
	}
	
	@RequestMapping(value = "/fetch", produces = "application/json")
	 public @ResponseBody
	 String showUser(@RequestParam int iDisplayStart,
	            @RequestParam int iDisplayLength, @RequestParam int sEcho) {
	 
	  DataTablesTO<Twitter> dt = new DataTablesTO<Twitter>();
	  
	  List<Twitter> paginatedTweets = twitterService.getTweetsByPage(iDisplayStart,iDisplayLength);
	  
	  List<Twitter> allTweets = twitterService.listTweets();
	  dt.setAaData(paginatedTweets);  // this is the dataset reponse to client
	  dt.setiTotalDisplayRecords(allTweets.size());  // // the total data in db for datatables to calculate page no. and position
	  dt.setiTotalRecords(allTweets.size());   // the total data in db for datatables to calculate page no.
	  dt.setsEcho(sEcho);
	  
	  return toJson(dt);
	 }
	
	private String toJson(DataTablesTO<?> dt) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(dt);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
