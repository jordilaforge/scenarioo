package org.scenarioo.business.comparison;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class CompareScreenshotTest {

	private static final String FILE_1 = new String("src/test/resources/image_1.png");
	private static final String FILE_2 = new String("src/test/resources/image_2.png");
	private static final String FILE_50_1 = new String("src/test/resources/image_50_1.png");
	private static final String FILE_50_2 = new String("src/test/resources/image_50_2.png");
	private static final String FILE_TOTAL_BLACK = new String("src/test/resources/image_total_black.png");
	private static final String FILE_TOTAL_WHITE = new String("src/test/resources/image_total_white.png");
	private static final String FILE_50_300 = new String("src/test/resources/image_50_300.png");
	private static final String FILE_75_2000 = new String("src/test/resources/image_75_2000.png");
	private static final String FILE_75_300 = new String("src/test/resources/image_75_300.png");
	private static final String FILE_50_300_400 = new String("src/test/resources/image_50_300_400.png");
	private static final String FILE_50_400_300 = new String("src/test/resources/image_50_400_300.png");
	private static final String FILE_TOTAL_WHITE_300 = new String("src/test/resources/image_total_white_300.png");
	private static final String FILE_TOTAL_BLACK_300 = new String("src/test/resources/image_total_black_300.png");
	private static final String FILE_TOTAL_WHITE_2000 = new String("src/test/resources/image_total_white_2000.png");

	private String imageA;
	private String imageB;
	private double differenceInPercent;
	private double accuracy = 1.00;

	@Test
	public void ifFirstParameterIsNull_expectException() {
		givenFirstParameterIsNullAndSecondIsSet();

		try {
			whenComparingScreenshots();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("First file argument must not be null.", e.getMessage());
		}
	}

	@Test
	public void ifSecondParameterIsNull_expectException() {
		givenSecondParameterIsNullAndFirstIsSet();

		try {
			whenComparingScreenshots();
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("Second file argument must not be null.", e.getMessage());
		}
	}

	private void givenFirstParameterIsNullAndSecondIsSet() {
		imageA = null;
		imageB = FILE_1;
	}

	private void givenSecondParameterIsNullAndFirstIsSet() {
		imageA = FILE_1;
		imageB = null;
	}

	@Test
	public void ifBothImagesAreEqual_returns0() {
		givenBothFilesAreEqual();

		whenComparingScreenshots();

		expectReturns0();
	}

	@Test
	public void ifImagesAreHalfTheSame_returns50() {
		givenBothFilesAreHalfEqual();

		whenComparingScreenshots();

		expectReturns50();
	}

	private void expectReturns50() {
		Assert.assertEquals("Value was:" + differenceInPercent, 50.00, differenceInPercent, accuracy);
	}

	private void givenBothFilesAreHalfEqual() {
		imageA = FILE_50_1;
		imageB = FILE_TOTAL_WHITE;
	}

	private void givenBothFilesAreEqual() {
		imageA = FILE_1;
		imageB = FILE_1;
	}

	private void givenBothFilesAreTotalDifferent() {
		imageA = FILE_TOTAL_BLACK;
		imageB = FILE_TOTAL_WHITE;
	}

	private void whenComparingScreenshots() {
		CompareScreenshot test = new CompareScreenshot();
		differenceInPercent = test.compare(imageA, imageB);
	}

	private void expectReturns100() {
		Assert.assertEquals("Value was: " + differenceInPercent, 100.00, differenceInPercent, accuracy);
	}

	@Test
	public void ifImagesAreTotalDifferent_returns100() {
		givenBothFilesAreTotalDifferent();

		whenComparingScreenshots();

		expectReturns100();
	}

	@Test
	public void ifImageColorAndBigger() {
		givenImageColorAndBigger();

		whenComparingScreenshots();

		expectReturnsMoreThan50();
	}

	private void expectReturns0() {
		Assert.assertEquals("Value was" + differenceInPercent, 0.00, differenceInPercent, accuracy);
	}

	private void expectReturnsMoreThan50() {
		Assert.assertTrue("Difference was: " + differenceInPercent, differenceInPercent >= 50);
	}

	private void givenImageColorAndBigger() {
		imageA = FILE_1;
		imageB = FILE_2;
	}

	@Test
	public void ifImagesAreTotalDifferentBig_returns100() {
		givenBothFilesAreTotalDifferentBig();

		whenComparingScreenshots();

		expectReturns100();
	}

	private void givenBothFilesAreTotalDifferentBig() {
		imageA = FILE_TOTAL_WHITE_300;
		imageB = FILE_TOTAL_BLACK_300;

	}

	@Test
	public void ifImagesAreHalfTheSameBig_returns50() {
		givenBothFilesAreHalfEqualBig();

		whenComparingScreenshots();

		expectReturns50();
	}

	private void givenBothFilesAreHalfEqualBig() {
		imageA = FILE_50_300;
		imageB = FILE_TOTAL_WHITE_300;
	}

	@Test
	public void ifImg1HeightBigger() {
		givenifImg1HeightBigger();

		whenComparingScreenshots();

		expectReturnsMoreThan50();
	}

	private void givenifImg1HeightBigger() {
		imageA = FILE_50_400_300;
		imageB = FILE_TOTAL_WHITE_300;
	}

	@Test
	public void ifImg1WidthBigger() {
		givenifImg1WidthBigger();

		whenComparingScreenshots();

		expectReturnsMoreThan50();
	}

	private void givenifImg1WidthBigger() {
		imageA = FILE_50_300_400;
		imageB = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImg2HeightBigger() {
		givenifImg2HeightBigger();

		whenComparingScreenshots();

		expectReturnsMoreThan50();
	}

	private void givenifImg2HeightBigger() {
		imageB = FILE_50_400_300;
		imageA = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImg2WidthBigger() {
		givenifImg2WidthBigger();

		whenComparingScreenshots();

		expectReturnsMoreThan50();
	}

	private void givenifImg2WidthBigger() {

		imageB = FILE_50_300_400;
		imageA = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImagesAreOneQuaterTheSameBig_returns75() {
		givenBothFilesAreOneQuaterEqualBig();

		whenComparingScreenshots();

		expectReturns75();
	}

	private void expectReturns75() {
		Assert.assertEquals("Value was: " + differenceInPercent, 75.00, differenceInPercent, accuracy);

	}

	private void givenBothFilesAreOneQuaterEqualBig() {
		imageA = FILE_75_300;
		imageB = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImagesAreOneQuaterTheSameMassiv_returns75() {
		givenBothFilesAreOneQuaterEqualMassiv();

		whenComparingScreenshots();

		expectReturns75();
	}

	private void givenBothFilesAreOneQuaterEqualMassiv() {
		imageA = FILE_75_2000;
		imageB = FILE_TOTAL_WHITE_2000;
	}

}
