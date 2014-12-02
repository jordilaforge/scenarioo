package org.scenarioo.business.comparison;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class CompareScreenshot {

	// Atributes
	protected BufferedImage img1 = null;
	protected BufferedImage img2 = null;
	protected BufferedImage imgc = null;
	protected int comparex = 10;
	protected int comparey = 10;
	protected int factorD = 10;
	final static double maxdiff = 3400;

	// returns similarity in percentage
	// 100% = same image
	public int compare(String fileA, String fileB) {
		checkPreconditions(fileA, fileB);
		// convert to gray images.
		img1 = imageToBufferedImage(GrayFilter
				.createDisabledImage(loadJPG(fileA)));
		img2 = imageToBufferedImage(GrayFilter
				.createDisabledImage(loadJPG(fileB)));
		return compareBufferedImage(img1,img2);
		}
	
	public int compareBufferedImage(BufferedImage img1, BufferedImage img2){
		double totaldiff = 0;
		// how big are each section
		int blocksx = (int) (img1.getWidth() / comparex);
		int blocksy = (int) (img1.getHeight() / comparey);
		// loop through whole image and compare individual blocks of images
		for (int y = 0; y < comparey; y++) {
			for (int x = 0; x < comparex; x++) {
				int b1 = getAverageBrightness(img1.getSubimage(x * blocksx, y
						* blocksy, blocksx - 1, blocksy - 1));
				int b2 = getAverageBrightness(img2.getSubimage(x * blocksx, y
						* blocksy, blocksx - 1, blocksy - 1));
				int diff = Math.abs(b1 - b2);
				totaldiff += diff;
			}
		}
		if(totaldiff==0){
			return 100;
		}
		if(totaldiff==maxdiff){
			return 0;
		}
		return (int) Math.round((totaldiff/maxdiff)*100);
	}

	private void checkPreconditions(String fileA, String fileB) {
		if (fileA == null) {
			throw new IllegalArgumentException(
					"First file argument must not be null.");
		}
		if (fileB == null) {
			throw new IllegalArgumentException(
					"Second file argument must not be null.");
		}
	}
	



	// returns a value specifying some kind of average brightness in the image.
	protected int getAverageBrightness(BufferedImage img) {
		Raster r = img.getData();
		int total = 0;
		for (int y = 0; y < r.getHeight(); y++) {
			for (int x = 0; x < r.getWidth(); x++) {
				total += r.getSample(r.getMinX() + x, r.getMinY() + y, 0);
			}
		}
		return (int) (total / ((r.getWidth() / factorD) * (r.getHeight() / factorD)));
	}

	// buffered images are just better.
	protected static BufferedImage imageToBufferedImage(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, null, null);
		return bi;
	}

	// read a jpeg/png file into a buffered image
	protected static Image loadJPG(String filename) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (java.io.FileNotFoundException io) {
			System.out.println("File Not Found");
		}
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(in);
			in.close();
		} catch (java.io.IOException io) {
			System.out.println("IOException");
		}
		return bi;
	}
}
