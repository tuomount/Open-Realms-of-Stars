package org.openRealmOfStars.utilities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TextUtilitiestTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOne() {
    String value = TextUtilities.getOrderNumberAsText(1);
    assertEquals("first", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwo() {
    String value = TextUtilities.getOrderNumberAsText(2);
    assertEquals("second", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testThree() {
    String value = TextUtilities.getOrderNumberAsText(3);
    assertEquals("third", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFour() {
    String value = TextUtilities.getOrderNumberAsText(4);
    assertEquals("fourth", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFive() {
    String value = TextUtilities.getOrderNumberAsText(5);
    assertEquals("5th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEleven() {
    String value = TextUtilities.getOrderNumberAsText(11);
    assertEquals("11th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwelve() {
    String value = TextUtilities.getOrderNumberAsText(12);
    assertEquals("12th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testThirteen() {
    String value = TextUtilities.getOrderNumberAsText(13);
    assertEquals("13th", value);
  }
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHundredEleven() {
    String value = TextUtilities.getOrderNumberAsText(111);
    assertEquals("111th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHundredTwelve() {
    String value = TextUtilities.getOrderNumberAsText(112);
    assertEquals("112th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHundredThirteen() {
    String value = TextUtilities.getOrderNumberAsText(113);
    assertEquals("113th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFourteen() {
    String value = TextUtilities.getOrderNumberAsText(14);
    assertEquals("14th", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwentyOne() {
    String value = TextUtilities.getOrderNumberAsText(21);
    assertEquals("21st", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwentyTwo() {
    String value = TextUtilities.getOrderNumberAsText(22);
    assertEquals("22nd", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwentyThree() {
    String value = TextUtilities.getOrderNumberAsText(23);
    assertEquals("23rd", value);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwentyFour() {
    String value = TextUtilities.getOrderNumberAsText(24);
    assertEquals("24th", value);
  }
  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testZero() {
    String value = TextUtilities.getOrderNumberAsText(0);
    assertEquals("0", value);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEscaping() {
    String value = TextUtilities.handleEscapes("Example text.\\n\\nContinue");
    assertEquals("Example text.\n\nContinue", value);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEscaping2() {
    String value = TextUtilities.handleEscapes("Example text. Continue");
    assertEquals("Example text. Continue", value);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEscaping3() {
    String value = TextUtilities.handleEscapes("Example text.\\Continue");
    assertEquals("Example text.\\Continue", value);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testConcanate() {
    String[] value1 = {"text1", "text2"};
    String[] value2 = {"text3", "text4", "text5"};
    String[] result = TextUtilities.concanateStringArrays(value1, value2);
    assertEquals(5, result.length);
    assertEquals(value2[1], result[3]);
    assertEquals(value1[0], result[0]);
  }

}
