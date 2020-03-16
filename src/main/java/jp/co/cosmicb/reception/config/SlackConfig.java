package jp.co.cosmicb.reception.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "slack")
public class SlackConfig {

	private String token;
	private String channel;
	private String url;

	/**
	 * @return token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token セットする token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel セットする channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url セットする url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
