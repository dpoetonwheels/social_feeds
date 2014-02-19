/**
 * 
 */
package org.social.feeds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author devang.desai
 *
 */
@Entity
@Table(name="instagram_feeds")
public class Instagram {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;
}
