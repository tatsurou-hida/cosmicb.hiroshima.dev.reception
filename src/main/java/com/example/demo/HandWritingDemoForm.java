package com.example.demo;

public class HandWritingDemoForm {

	private String canvasDataURL;

	private String ocrResult;

	public String getCanvasDataURL() {
		return canvasDataURL;
	}

	public void setCanvasDataURL(String canvasDataURL) {
		this.canvasDataURL = canvasDataURL;
	}

	public String getOcrResult() {
		return ocrResult;
	}

	public void setOcrResult(String ocrResult) {
		this.ocrResult = ocrResult;
	}

	@Override
	public String toString() {
		return "HandWritingDemoForm [canvasDataURL=" + canvasDataURL + ", ocrResult=" + ocrResult + "]";
	}

}
