/**
 * 
 */
package org.social.feeds.controller;

import java.util.List;
import java.util.Locale;

import org.social.feeds.config.YoutubeConfigurationTemplate;
import org.social.feeds.helper.JSONHelper;
import org.social.feeds.model.DataTablesTO;
import org.social.feeds.model.Twitter;
import org.social.feeds.model.Youtube;
import org.social.feeds.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * @author devang.desai
 *
 */
@Controller
@RequestMapping("/youtube")
public class YoutubeFeedsController {
	// Youtube properties
	@Autowired
	private YoutubeConfigurationTemplate youtubeTemplate;

	@Autowired
	private YoutubeService youtubeService;
	
	/**
	 * Get paginated json for twitter feeds
	 * 
	 * @return twitter json, given the fetch number
	 */
	@RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getYoutubeJson(@PathVariable("number") String number) {
		List<Youtube> feeds = youtubeService.getVideosForJSON(Integer.parseInt(number), 5);
		Gson gson = new Gson();
		String json = gson.toJson(feeds);
		return json;
	}
	
	/**
	 * Gets all the twitter feeds json
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody String getAllVideosJSON(Locale locale, Model model) {
		Gson gson = new Gson();
		return gson.toJson(youtubeService.listFeeds());
	}

	@RequestMapping(value = "/fetch", produces = "application/json")
	 public @ResponseBody
	 String showVideoFeeds(@RequestParam int iDisplayStart,
	            @RequestParam int iDisplayLength, @RequestParam int sEcho) {
	 
	  DataTablesTO<Youtube> dt = new DataTablesTO<Youtube>();
	  
	  List<Youtube> paginatedFeeds = youtubeService.getFeedsByPage(iDisplayStart,iDisplayLength);
	  
	  List<Youtube> allFeeds = youtubeService.listFeeds();
	  dt.setAaData(paginatedFeeds);  // this is the dataset reponse to client
	  dt.setiTotalDisplayRecords(allFeeds.size());  // // the total data in db for datatables to calculate page no. and position
	  dt.setiTotalRecords(allFeeds.size());   // the total data in db for datatables to calculate page no.
	  dt.setsEcho(sEcho);
	  
	  return JSONHelper.toJson(dt);
	 }
	
}
