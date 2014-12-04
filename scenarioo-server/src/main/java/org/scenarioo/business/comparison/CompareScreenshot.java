package org.scenarioo.business.comparison;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class CompareScreenshot {

	int threshold = 10;

	public double compare(String fileA, String fileB) {
		checkPreconditions(fileA, fileB);
		Image imageA = loadImage(fileA);
		Image imageB = loadImage(fileB);
		int scale = checkScale(imageA, imageB);
		double diffvalue = compareImages(imageA, imageB, scale);
		return diffvalue;
	}

	private double compareImages(Image imageA, Image imageB, int scale) {

		BufferedImage img1 = imageToBufferedImage(imageA);
		BufferedImage img2 = imageToBufferedImage(imageB);
		switch (scale) {
		case 0:
			return compareBufferedImages(img1, img2);
		case 1:
			return img1HeigtIsBigger(img1, img2);
		case 2:
			return 0.0;
		case 3:
			return 0.0;
		case 4:
			return 0.0;
		case 5:
			return 0.0;
		case 6:
			return 0.0;
		default:
			return 0.0;
		}
	}

	private double img1HeigtIsBigger(BufferedImage img2, BufferedImage img1) {
		int area_img1 = img1.getHeight() * img1.getWidth();
		int area_img2 = img2.getHeight() * img2.getWidth();
		int area_diff = Math.min(img1.getHeight(), img2.getHeight()) * Math.min(img1.getWidth(), img2.getWidth());
		double percentageAreaDiff = area_diff / Math.max(area_img1, area_img2);
		BufferedImage subimage = img2.getSubimage(img1.getMinX(), img1.getMinY(), img1.getWidth(), img1.getHeight());
		double difference = compareBufferedImages(subimage, img1);
		return (percentageAreaDiff / 100) * (difference / 100);
	}

	private double compareBufferedImages(BufferedImage img1, BufferedImage img2) {
		if ((img1.getHeight() == img2.getHeight()) && (img1.getWidth() == img2.getWidth())) {
			int PixelDiff = 0;
			int totalPixel = img1.getHeight()*img1.getWidth();
			for (int x = img1.getMinX(); x < img1.getWidth(); ++x) {
				for (int y = img1.getMinY(); y < img1.getHeight(); ++y) {
					int diff=Math.abs(img1.getRGB(x, y)-img2.getRGB(x, y));
					if (diff > threshold) {
						++PixelDiff;
					}
				}
			}
			return calculatePercentage(PixelDiff, totalPixel);
		} else {
			throw new IllegalArgumentException("Images not the Same Size");
		}

	}

	private double calculatePercentage(int quotient, int divisor) {
		if (divisor == 0) {
			throw new IllegalArgumentException("'divisor' is 0");
		}
		float percent = (quotient * 100.0f) / divisor;
		return percent;
	}

	// buffered images are just better.
	protected static BufferedImage imageToBufferedImage(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
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

	private void checkPreconditions(String fileA, String fileB) {
		if (fileA == null) {
			throw new IllegalArgumentException("First file argument must not be null.");
		}
		if (fileB == null) {
			throw new IllegalArgumentException("Second file argument must not be null.");
		}
	}

	// 0=same size
	// 1=img1 height bigger
	// 2=img1 width bigger
	// 3=img1 height and width are bigger
	// 4=img2 height bigger
	// 5=img2 width bigger
	// 6=img2 height and width are bigger
	private int checkScale(Image img1, Image img2) {
		int scale = 0;
		BufferedImage bimg1 = imageToBufferedImage(img1);
		BufferedImage bimg2 = imageToBufferedImage(img2);
		if ((bimg1.getHeight() == bimg2.getHeight()) & (bimg1.getWidth() == bimg2.getWidth())) {
			scale = 0;
		} else if ((bimg1.getHeight() > bimg2.getHeight()) & (bimg1.getWidth() == bimg2.getWidth())) {
			scale = 1;
		} else if ((bimg1.getHeight() == bimg2.getHeight()) & (bimg1.getWidth() > bimg2.getWidth())) {
			scale = 2;
		} else if ((bimg1.getHeight() > bimg2.getHeight()) & (bimg1.getWidth() > bimg2.getWidth())) {
			scale = 3;
		} else if ((bimg1.getHeight() < bimg2.getHeight()) & (bimg1.getWidth() == bimg2.getWidth())) {
			scale = 4;
		} else if ((bimg1.getHeight() == bimg2.getHeight()) & (bimg1.getWidth() < bimg2.getWidth())) {
			scale = 5;
		} else if ((bimg1.getHeight() < bimg2.getHeight()) & (bimg1.getWidth() < bimg2.getWidth())) {
			scale = 6;
		}
		return scale;
	}
}
