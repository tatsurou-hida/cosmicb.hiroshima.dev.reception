package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "retention")
public class RetentionConfig {

	public static class Persontovisit {
		private int period;
		private String logfilepath;

		public int getPeriod() {
			return period;
		}

		public void setPeriod(int period) {
			this.period = period;
		}

		public String getLogfilepath() {
			return logfilepath;
		}

		public void setLogfilepath(String logfilepath) {
			this.logfilepath = logfilepath;
		}

	}

	private Persontovisit persontovisit;

	public Persontovisit getPersontovisit() {
		return persontovisit;
	}

	public void setPersontovisit(Persontovisit persontovisit) {
		this.persontovisit = persontovisit;
	}

}
