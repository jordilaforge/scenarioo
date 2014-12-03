package org.scenarioo.business.comparison;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class CompareScreenshot {

	// Attributes
	protected int comparex = 10;
	protected int comparey = 10;
	protected int factorD = 10;
	protected int tolerance = 50;

	// returns difference in percentage
	// 0% = same image 100% = totally different image
	public int compare(String fileA, String fileB){
		checkPreconditions(fileA, fileB);
		Image imageA=loadImage(fileA);
		Image imageB=loadImage(fileB);
		int diffvalue = compareImages(imageA,imageB);
		int maxdiff = calculateMaxDiff(imageA,imageB);
		double percentage = calculatePercentage(diffvalue, maxdiff);
		return (int) Math.round(percentage);
	}
	
	
	private double calculatePercentage(int diffvalue, int maxdiff) {
		float percent = (diffvalue * 100.0f) / maxdiff;
		return percent;
	}


	public int compareImages(Image imageA, Image imageB) {
		// convert to gray images.
		BufferedImage img1 = imageToBufferedImage(GrayFilter
				.createDisabledImage(imageA));
		BufferedImage img2 = imageToBufferedImage(GrayFilter
				.createDisabledImage(imageB));
		checkScale(img1, img2);
		int totaldiff = 0;
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
		return totaldiff;
	}

	private void checkScale(BufferedImage img1, BufferedImage img2) {
		if ((img1.getHeight() != img2.getHeight())
				&& (img1.getWidth() != img2.getWidth())) {
			throw new IllegalArgumentException("Image scale is not the same");
		}

	}


	public int calculateMaxDiff(Image bimg1,Image bimg2) {
		BufferedImage orig1 = imageToBufferedImage(bimg1);
		BufferedImage orig2 = imageToBufferedImage(bimg2);
		BufferedImage im = new BufferedImage(orig1.getWidth(),orig1.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
		 // We need its raster to set the pixels' values.
		     WritableRaster raster = im.getRaster();
		     // Put the pixels on the raster. Note that only values 0 and 1 are used for the pixels.
		     // You could even use other values: in this type of image, even values are black and odd
		     // values are white.
		     for(int h=0;h<orig1.getHeight();h++)
		       for(int w=0;w<orig1.getWidth();w++)
		         raster.setSample(w,h,0,0);
		BufferedImage im2 = new BufferedImage(orig1.getWidth(),orig1.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
			 // We need its raster to set the pixels' values.
			     WritableRaster raster2 = im2.getRaster();
			     // Put the pixels on the raster. Note that only values 0 and 1 are used for the pixels.
			     // You could even use other values: in this type of image, even values are black and odd
			     // values are white.
			     for(int h=0;h<orig1.getHeight();h++)
			       for(int w=0;w<orig1.getWidth();w++)
			         raster2.setSample(w,h,0,1);
		return compareImages(im,im2);
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
	protected static Image loadImage(String filename) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (java.io.FileNotFoundException io) {
			io.printStackTrace();
		}
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(in);
			in.close();
		} catch (java.io.IOException io) {
			io.printStackTrace();
		}
		return bi;
	}
}
