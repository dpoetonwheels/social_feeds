/**
 * 
 */
package org.social.feeds.helper;

import org.social.feeds.model.DataTablesTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author devang.desai
 *
 */
public class JSONHelper {

	public static String toJson(DataTablesTO<?> dt) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(dt);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
