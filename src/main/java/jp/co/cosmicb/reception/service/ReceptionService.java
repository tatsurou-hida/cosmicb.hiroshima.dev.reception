package jp.co.cosmicb.reception.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.cosmicb.reception.config.SlackConfig;
import jp.co.cosmicb.reception.config.TesseractConfig;
import jp.co.cosmicb.reception.entity.OfficeVisit;
import jp.co.cosmicb.reception.repository.ReceptionRepository;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class ReceptionService {

	@Autowired
	ReceptionRepository repository;

	@Autowired
	TesseractConfig tessConfig;

	@Autowired
	SlackConfig slackConfig;

	public void insertVisitor(String org, String name, Integer number) {

		LocalDateTime date = LocalDateTime.now();
		LocalDateTime epoch = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

		OfficeVisit officeVisit = new OfficeVisit();

		try {
			officeVisit.setVisitorName(this.doOCR(name));
		} catch (TesseractException | IOException e) {
			e.printStackTrace();
		}

		officeVisit.setVisitorNameImageUrl(name);

		try {
			officeVisit.setVisitorOrg(this.doOCR(org));
		} catch (TesseractException | IOException e) {
			e.printStackTrace();
		}

		officeVisit.setVisitorOrgImageUrl(org);

		officeVisit.setVisitorCount(number);
		officeVisit.setVisitedAt(date);
		officeVisit.setPersonToVisit("");
		officeVisit.setLeftAt(epoch);

		repository.save(officeVisit);

	}

	/**
	 * @param dataURL data URI
	 * @return OCR結果を単に文字列で返す
	 * @throws TesseractException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String doOCR(String dataURL) throws TesseractException, MalformedURLException, IOException {

		BufferedImage image = this.getBufferedImage(dataURL);

		if (image == null) {
			return "";
		}

		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath(tessConfig.getTraineddata().getPath());
		tesseract.setLanguage(tessConfig.getTraineddata().getLang());
		tesseract.setTessVariable("user_defined_dpi", "72"); // 警告を抑止するために必要

		return tesseract.doOCR(image);
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

	/**
	 * @return
	 */
	public SlackConfig getSlackConfig() {
		return slackConfig;
	}

}