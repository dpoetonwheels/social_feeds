package org.social.feeds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 */
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * @author devang.desai
 *
 */
@Entity
@Table(name="twitter_feeds")
public class Twitter {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;

	@Column(name="tweet")
	private String tweet;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="email")
	private String emailAddress;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	@Index(name="twitter_since_id")
	@Column(name="since_id")
	private Long since_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getSince_id() {
		return since_id;
	}

	public void setSince_id(Long since_id) {
		this.since_id = since_id;
	}
	
}
