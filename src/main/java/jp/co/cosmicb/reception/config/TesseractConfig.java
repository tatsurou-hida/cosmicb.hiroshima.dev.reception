package jp.co.cosmicb.reception.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tesseract")
public class TesseractConfig {

	public static class Traineddata {
		private String path;
		private String lang;

		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getLang() {
			return lang;
		}
		public void setLang(String lang) {
			this.lang = lang;
		}
	}

	private Traineddata traineddata;

	public Traineddata getTraineddata() {
		return traineddata;
	}

	public void setTraineddata(Traineddata traineddata) {
		this.traineddata = traineddata;
	}

}
