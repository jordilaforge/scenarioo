package org.scenarioo.business.comparison;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class CompareScreenshotTest {

	private static final String FILE_1 = new String("/home/scenarioo/scenarioo-ip5/scenarioo-server/src/test/resources/image_1.png");
	private static final String FILE_2 = new String("/home/scenarioo/scenarioo-ip5/scenarioo-server/src/test/resources/image_2.png");
	private static final String FILE_50_1 = new String("/home/scenarioo/scenarioo-ip5/scenarioo-server/src/test/resources/image_50_1.png");
	private static final String FILE_50_2 = new String("/home/scenarioo/scenarioo-ip5/scenarioo-server/src/test/resources/image_50_2.png");
	private static final String FILE_TOTAL_BLACK = new String("/home/scenarioo/scenarioo-ip5/scenarioo-server/src/test/resources/image_total_black.png");
	private static final String FILE_TOTAL_WHITE = new String("/home/scenarioo/scenarioo-ip5/scenarioo-server/src/test/resources/image_total_white.png");
	
	
	private String fileA;
	private String fileB;
	private int similarityInPrecent;
	
	@Test
	public void ifFirstParameterIsNull_expectException() {
		givenFirstParameterIsNullAndSecondIsSet();
		
		try {
			whenComparingScreenshots();
			Assert.fail();
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("First file argument must not be null.", e.getMessage());
		}
	}
	
	@Test
	public void ifSecondParameterIsNull_expectException() {
		givenSecondParameterIsNullAndFirstIsSet();
		
		try {
			whenComparingScreenshots();
			Assert.fail();
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Second file argument must not be null.", e.getMessage());
		}
	}
	
	private void givenFirstParameterIsNullAndSecondIsSet() {
		fileA = null;
		fileB = FILE_1;
	}
	
	private void givenSecondParameterIsNullAndFirstIsSet() {
		fileA = FILE_1;
		fileB = null;
	}

	@Test
	public void ifBothImagesAreEqual_returns100() {
		givenBothFilesAreEqual();
		
		whenComparingScreenshots();
		
		expectReturns100();
	}
	
	
	@Test
	public void ifImagesAreHalfTheSame_returns50() {
		givenBothFilesAreHalfEqual();
		
		whenComparingScreenshots();
		
		expectReturns50();
	}

	private void expectReturns50() {
		Assert.assertEquals(50, similarityInPrecent);
	}

	private void givenBothFilesAreHalfEqual() {
		fileA = FILE_50_1;
		fileB = FILE_TOTAL_WHITE;
	}

	private void givenBothFilesAreEqual() {
		fileA = FILE_1;
		fileB = FILE_1;
	}

	private void givenBothFilesAreTotalDifferent() {
		fileA = FILE_TOTAL_BLACK;
		fileB = FILE_TOTAL_WHITE;
	}
	private void whenComparingScreenshots() {
		CompareScreenshot test = new CompareScreenshot();
		similarityInPrecent = test.compare(fileA,fileB);
	}

	private void expectReturns100() {
		Assert.assertEquals(100, similarityInPrecent);
	}
	
	@Test
	public void ifImagesAreTotalDifferent_returns0() {
		givenBothFilesAreTotalDifferent();
		
		whenComparingScreenshots();
		
		expectReturns0();
	}
	
	@Test
	public void ifImageColorAndBigger() {
		givenImageColorAndBigger();
		
		whenComparingScreenshots();
		
		expectReturnsLessThan50();
	}
	
	private void expectReturns0() {
		Assert.assertEquals(0, similarityInPrecent);
	}
	
	private void expectReturnsLessThan50() {
		Assert.assertTrue(similarityInPrecent<=50);
	}
	
	private void givenImageColorAndBigger() {
		fileA = FILE_1;
		fileB = FILE_2;
	}
	
}
