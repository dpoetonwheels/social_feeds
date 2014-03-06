/**
 * 
 */
package org.social.feeds.model;

import java.sql.Timestamp;

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
@Table(name="youtube_feeds")
public class Youtube {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public Timestamp getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Timestamp publishedAt) {
		this.publishedAt = publishedAt;
	}

	@Column(name="title")
	private String title;
	
	@Column(name="video_id")
	private String videoId;
	
	@Column(name="published_at")
	private Timestamp publishedAt;
	
}
