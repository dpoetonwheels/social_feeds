/**
 * 
 */
package org.social.feeds.model;

import com.google.api.client.util.DateTime;

/**
 * @author devang.desai
 *
 */
public class TestDateTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateTime dt = DateTime.parseRfc3339("2014-03-04T00:16:29.000Z");
		System.out.println("shift -- " + dt.getTimeZoneShift());
		DateTime dt1 = new DateTime(dt.getValue() + 1000);
		System.out.println("dt1 -- " + dt1);
		System.out.println("long value -- " + dt.getValue());
		
	}

}
