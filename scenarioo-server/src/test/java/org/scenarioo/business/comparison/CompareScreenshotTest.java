package org.scenarioo.business.comparison;


import org.junit.Assert;
import org.junit.Test;

public class CompareScreenshotTest {

	private static final String FILE_1 = new String("src/test/resources/image_1.png");
	private static final String FILE_2 = new String("src/test/resources/image_2.png");
	private static final String FILE_50_1 = new String("src/test/resources/image_50_1.png");
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
	private static final String FILE_WIKI_BUILD0_NOCHANGE = new String("src/test/resources/wiki_build0_nochange.png");
	private static final String FILE_WIKI_BUILD1_NOCHANGE = new String("src/test/resources/wiki_build1_nochange.png");
	private static final String FILE_WIKI_BUILD0_CHANGE = new String("src/test/resources/wiki_build0_change.png");
	private static final String FILE_WIKI_BUILD1_CHANGE = new String("src/test/resources/wiki_build1_change.png");
	private static final String FILE_ERROR1 = new String("src/test/resources/image_error1.png");
	private static final String FILE_ERROR2 = new String("src/test/resources/image_error2.png");


	private String imageA;
	private String imageB;
	private double similarityInPercent;
	private double accuracy = 1;

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
		Assert.assertEquals("Similarity was: " + similarityInPercent, 50.00, similarityInPercent, accuracy);
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
		similarityInPercent = test.compare(imageA, imageB);
	}

	private void expectReturns100() {
		Assert.assertEquals("Similarity was: " + similarityInPercent, 100.00, similarityInPercent, accuracy);
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

		expectReturnsLessThan10();
	}

	private void expectReturnsLessThan10() {
		Assert.assertTrue("Similarity was: " + similarityInPercent, similarityInPercent <= 50);
	}

	private void expectReturns0() {
		Assert.assertEquals("Similarity was: " + similarityInPercent, 0.00, similarityInPercent, accuracy);
	}


	private void givenImageColorAndBigger() {
		imageA = FILE_1;
		imageB = FILE_2;
	}

	@Test
	public void ifImagesAreTotalDifferentBig_returns0() {
		givenBothFilesAreTotalDifferentBig();

		whenComparingScreenshots();

		expectReturns0();
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

		expectReturns38();
	}

	private void expectReturns38() {
		Assert.assertEquals("Similarity was: " + similarityInPercent, 38.00, similarityInPercent, accuracy);
	}

	private void givenifImg1HeightBigger() {
		imageA = FILE_50_300_400;
		imageB = FILE_TOTAL_WHITE_300;
	}

	@Test
	public void ifImg1WidthBigger() {
		givenifImg1WidthBigger();

		whenComparingScreenshots();

		expectReturns38();
	}

	private void givenifImg1WidthBigger() {
		imageA = FILE_50_400_300;
		imageB = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImg2HeightBigger() {
		givenifImg2HeightBigger();

		whenComparingScreenshots();

		expectReturns38();
	}

	private void givenifImg2HeightBigger() {
		imageB = FILE_50_300_400;
		imageA = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImg2WidthBigger() {
		givenifImg2WidthBigger();

		whenComparingScreenshots();

		expectReturns38();
	}

	private void givenifImg2WidthBigger() {

		imageB = FILE_50_400_300;
		imageA = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImagesAreOneQuaterTheSameBig_returns75() {
		givenBothFilesAreOneQuaterEqualBig();

		whenComparingScreenshots();

		expectReturns25();
	}


	private void givenBothFilesAreOneQuaterEqualBig() {
		imageA = FILE_75_300;
		imageB = FILE_TOTAL_WHITE_300;

	}

	@Test
	public void ifImagesAreOneQuaterTheSameMassiv_returns25() {
		givenBothFilesAreOneQuaterEqualMassiv();

		whenComparingScreenshots();

		expectReturns25();
	}

	private void expectReturns25() {
		Assert.assertEquals("Similarity was: " + similarityInPercent, 25.00, similarityInPercent, accuracy);
		
	}

	private void givenBothFilesAreOneQuaterEqualMassiv() {
		imageA = FILE_75_2000;
		imageB = FILE_TOTAL_WHITE_2000;
	}
	
	@Test
	public void ifImg1WidthAndHeightBigger() {
		givenImg1WidthAndHeigtBigger();

		whenComparingScreenshots();

		expectReturns6();
	}

	private void expectReturns6() {
		Assert.assertEquals("Similarity was: " + similarityInPercent, 6.00, similarityInPercent, accuracy);
		
	}

	private void givenImg1WidthAndHeigtBigger() {
		imageA = FILE_TOTAL_WHITE_300;
		imageB = FILE_50_1;
	}
	
	@Test
	public void ifImg2WidthAndHeightBigger() {
		givenImg2WidthAndHeigtBigger();

		whenComparingScreenshots();

		expectReturns6();
	}



	private void givenImg2WidthAndHeigtBigger() {
		imageA = FILE_50_1;
		imageB = FILE_TOTAL_WHITE_300;
	}
	
	@Test
	public void ifRealDataWikiChange() {
		givenRealDataWikiChange();

		whenComparingScreenshots();

		expectReturnsLessThan100();
	}
	
	private void expectReturnsLessThan100() {
		Assert.assertTrue("Similarity was: " + similarityInPercent, similarityInPercent < 100);
	}

	private void givenRealDataWikiChange() {
		imageA = FILE_WIKI_BUILD0_CHANGE;
		imageB = FILE_WIKI_BUILD1_CHANGE;
		
	}

	@Test
	public void ifRealDataWikiNoChange_expect100() {
		givenRealDataWikiNoChange();

		whenComparingScreenshots();

		expectReturns100();
	}

	private void givenRealDataWikiNoChange() {
		imageA = FILE_WIKI_BUILD0_NOCHANGE;
		imageB = FILE_WIKI_BUILD1_NOCHANGE;
	}

	
	@Test
	public void ifImageWrongSize() {
		givenRealRandomExample();

		whenComparingScreenshots();

		expectLessThanTwenty();
	}

	private void expectLessThanTwenty() {
		Assert.assertTrue("Similarity was: " + similarityInPercent, similarityInPercent < 20);
	}

	private void givenRealRandomExample() {
		imageA = FILE_ERROR1;
		imageB = FILE_ERROR2;
	}
}
