package jp.co.cosmicb.reception.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.cosmicb.reception.config.TesseractConfig;

import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;

@Service
public class HandWritingDemoService {

	@Autowired
	TesseractConfig config;

	/**
	 * @param dataURL data URI
	 * @return OCR結果を単に文字列で返す
	 * @throws TesseractException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String doOCR(String dataURL) throws TesseractException, MalformedURLException, IOException {

		BufferedImage image = this.getBufferedImage(dataURL);

		if (image == null) {
			return "";
		}

		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath(config.getTraineddata().getPath());
		tesseract.setLanguage(config.getTraineddata().getLang());
		tesseract.setTessVariable("user_defined_dpi", "72"); // 警告を抑止するために必要

		return tesseract.doOCR(image);
	}

	/**
	 * @param dataURL data URI
	 * @return OCR結果の詳細を返す
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public List<Word> getWordList(String dataURL) throws MalformedURLException, IOException {

		BufferedImage image = this.getBufferedImage(dataURL);

		if (image == null) {
			return new ArrayList<Word>();
		}

		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath(config.getTraineddata().getPath());
		tesseract.setLanguage(config.getTraineddata().getLang());
		tesseract.setTessVariable("user_defined_dpi", "72"); // 警告を抑止するために必要

		return tesseract.getWords(image, TessPageIteratorLevel.RIL_BLOCK);
	}

	/**
	 * OCR結果を元画像に重ねて出力する<br />
	 * Bounding BoxとconfidenceをWord単位に表示する
	 *
	 * @param dataURL image data URL
	 * @return image data URL (png / base64 encoding)
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String getOCRResultImageDataURL(String dataURL) throws MalformedURLException, IOException {

		BufferedImage image = this.getBufferedImage(dataURL);
		List<Word> wordList = this.getWordList(dataURL);

		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.BLUE);

		for (Word word : wordList) {
			// Bounding Boxの描画
			Rectangle rect = word.getBoundingBox();
			graphics.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());

			// WordとConfidenceの描画（文字）
			graphics.drawString(word.getText() + " : confidence " + String.valueOf(word.getConfidence()),
					(int) rect.getX(), (int) rect.getY());
		}

		graphics.dispose();

		// BufferedImage -> data URIへ変換
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);

		String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
		String imageString = "data:image/png;base64," + data;

		return imageString;

	}

	/**
	 * Data URIを画像に変換して返す
	 * @param dataURL data URI
	 * @return data URIの表す画像のBufferedImageを返す
	 */
	private BufferedImage getBufferedImage(String dataURL) {

		// tokenize the data
		String[] parts = dataURL.split(",");
		String imageString;

		try {
			imageString = parts[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}

		// create a buffered image
		BufferedImage image = null;
		byte[] imageByte;

		imageByte = DatatypeConverter.parseBase64Binary(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);

		try {
			image = ImageIO.read(bis);
		} catch (IOException e) {
			image = null;
			e.printStackTrace();
		}

		try {
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;

	}
}
