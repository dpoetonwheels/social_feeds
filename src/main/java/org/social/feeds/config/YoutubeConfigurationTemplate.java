/**
 * 
 */
package org.social.feeds.config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.common.io.Files;

/**
 * @author devang.desai
 *
 */
@Configuration
@PropertySource("classpath:youtube.properties")
public class YoutubeConfigurationTemplate {
	
	// Youtube properties
	@Value("${youtube.application_name}")
	private String youtubeApplicationName;
		
	@Value("${youtube.service_account_email}")
	private String youtubeServiceAccountEmail;
	
	@Value("${youtube.channelId}")
	private String youtubeChannelId;
	
	@Bean
	public YouTube youtubeFactoryBean() {
		YouTube youtube = null;
		try {
			HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			
			File file = getKeyResource();
			if(!isAccountValid() && !isResourceValid(file)) {
				System.exit(1);
			}
			
			JsonFactory JSON_FACTORY = JacksonFactory
					.getDefaultInstance();

			// service account credential (uncomment setServiceAccountUser
			// for domain-wide delegation)
			GoogleCredential credential = new GoogleCredential.Builder()
					.setTransport(httpTransport)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(youtubeServiceAccountEmail)
					.setServiceAccountScopes(
							Collections.singleton(YouTubeScopes.YOUTUBE))
					.setServiceAccountPrivateKeyFromP12File(file).build();

			// set up Youtube instance
			youtube = new YouTube.Builder(httpTransport, JSON_FACTORY,
					credential).setApplicationName(youtubeApplicationName)
					.build();
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return youtube;
	}
	
	
	private boolean isAccountValid() {
		if (youtubeServiceAccountEmail.startsWith("Enter ")) {
			System.err.println(youtubeServiceAccountEmail);
			return false;
		}
		return true;
	}

	private boolean isResourceValid(File file) throws IOException {
		String p12Content = Files.readFirstLine(file,
				Charset.defaultCharset());
		if (p12Content.startsWith("Please")) {
			System.err.println(p12Content);
			return false;
		}
		return true;
	}

	private File getKeyResource() throws URISyntaxException {
		URL resource = YoutubeConfigurationTemplate.class.getClassLoader()
				.getResource("key.p12");
		return Paths.get(resource.toURI()).toFile();
	}
	
}
