import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.ebooks.EBook;
import cs3500.ebooks.EBookChunk;
import cs3500.ebooks.EBookFlow;
import cs3500.ebooks.Hyperlink;
import cs3500.ebooks.Paragraph;
import cs3500.ebooks.Section;
import cs3500.ebooks.TextFlow;
import cs3500.ebooks.Utils;

/**
 * Tests for the EBook class and affiliated classes and methods.
 */

public class EBookTests {

  private EBookFlow mttf1;
  private EBookChunk mtp1;
  private EBook b1;
  private EBook b2;
  private EBook mtb1;
  private EBookFlow h1;
  private EBookFlow mth1;
  private EBookFlow sph1;
  private EBookChunk p1;
  private EBookChunk p2;
  private EBookChunk p3;
  private EBookChunk s1;
  private EBookChunk s2;
  private EBookChunk s3;
  private EBookChunk mts1;
  private EBookChunk spts1;
  private EBookFlow tf1;
  private EBookFlow tf2;
  private EBookFlow sptf1;

  private void init() {
    this.tf1 = new TextFlow("This is text flow #1.");
    this.tf2 = new TextFlow("This is text flow #2.");
    EBookFlow tf3 = new TextFlow("This is text flow #3.");
    EBookFlow tf4 = new TextFlow("This is text flow #4.");
    EBookFlow tf5 = new TextFlow("This is text flow #5.");
    this.mttf1 = new TextFlow("");
    this.sptf1 = new TextFlow("   Multiple    spaces here.   ");
    this.p1 = new Paragraph(List.of(tf1, tf2));
    this.p2 = new Paragraph(List.of(tf3, tf4));
    this.p3 = new Paragraph(List.of(tf5));
    this.mtp1 = new Paragraph(List.of(mttf1));
    this.s1 = new Section("Section One", List.of(p1, p2));
    this.s2 = new Section("Section Two", List.of(p3));
    this.s3 = new Section("Section Three", List.of(p1, p2, p3));
    this.mts1 = new Section("", List.of(mtp1));
    this.spts1 = new Section("   Spaced  Section    Title Is Here ", List.of(p1, p2));
    this.b1 = new EBook(List.of(s1, s2));
    this.b2 = new EBook(List.of(s1, s2, s3));
    this.mtb1 = new EBook(List.of(mts1));
    EBook mtb2 = new EBook(List.of(spts1));
    this.h1 = new Hyperlink(new TextFlow("Google Search URL"), "https://www.google.com");
    this.mth1 = new Hyperlink(new TextFlow(""), "https://www.google.com");
  }

  @Test
  public void testEbookCountWords() {
    this.init();
    Assert.assertEquals(29, this.b1.countWords());
    Assert.assertEquals(56, this.b2.countWords());
    Assert.assertEquals(0, this.mttf1.countWords());
    Assert.assertEquals(0, this.mtp1.countWords());
    Assert.assertEquals(0, this.mts1.countWords());
    Assert.assertEquals(0, this.mtb1.countWords());
  }

  @Test
  public void testEbookContains() {
    this.init();
    Assert.assertTrue(this.b1.contains("text"));
    Assert.assertTrue(this.b1.contains("One"));
    Assert.assertTrue(this.b2.contains("Three"));
    Assert.assertFalse(this.b2.contains("Sect"));
    Assert.assertFalse(this.b2.contains("#"));
    Assert.assertFalse(this.b2.contains(""));
  }

  @Test
  public void testEbookFormat() {
    this.init();
    Assert.assertEquals("Section One\nThis is text flow\n#1. This is "
            + "text\nflow #2.\n\nThis is text flow\n#3. This is text\nflow "
            + "#4.\n\nSection Two\nThis is text flow\n#5.", this.b1.format(20));
    Assert.assertEquals("", this.mtb1.format(100));
  }

  @Test
  public void testHyperlinkCountWords() {
    this.init();
    Assert.assertEquals(3, this.h1.countWords());
    Assert.assertEquals(0, this.mth1.countWords());
  }

  @Test
  public void testHyperlinkContains() {
    this.init();
    Assert.assertTrue(this.h1.contains("URL"));
    Assert.assertTrue(this.h1.contains("Google"));
    Assert.assertFalse(this.h1.contains(""));
    Assert.assertFalse(this.mth1.contains("dog"));
  }

  @Test
  public void testHyperlinkFormat() {
    this.init();
    Assert.assertEquals("Google\nSearch URL", this.h1.format(10));
    Assert.assertEquals("", this.mth1.format(2));
  }

  @Test
  public void testParagraphCountWords() {
    this.init();
    Assert.assertEquals(10, this.p1.countWords());
    Assert.assertEquals(5, this.p3.countWords());
    Assert.assertEquals(0, this.mtp1.countWords());
  }

  @Test
  public void testParagraphContains() {
    this.init();
    Assert.assertTrue(this.p1.contains("text"));
    Assert.assertTrue(this.p1.contains("flow"));
    Assert.assertTrue(this.p3.contains("text"));
    Assert.assertTrue(this.p3.contains("flow"));
    Assert.assertFalse(this.p2.contains("hello"));
    Assert.assertFalse(this.p3.contains(""));
    Assert.assertFalse(this.mtp1.contains("text"));
  }

  @Test
  public void testParagraphFormat() {
    this.init();
    Assert.assertEquals("This is text flow\n#1. This is text\nflow #2.",
            this.p1.format(20));
    Assert.assertEquals("This is text\nflow #3.\nThis is text\nflow #4.",
            this.p2.format(12));
    Assert.assertEquals("This\nis\ntext\nflow\n#5.", this.p3.format(5));
    Assert.assertEquals("", this.mtp1.format(100));
  }

  @Test
  public void testSectionCountWords() {
    this.init();
    Assert.assertEquals(22, this.s1.countWords());
    Assert.assertEquals(7, this.s2.countWords());
    Assert.assertEquals(0, this.mts1.countWords());
    Assert.assertEquals(25, this.spts1.countWords());
  }

  @Test
  public void testSectionContains() {
    this.init();
    Assert.assertTrue(this.s1.contains("#3."));
    Assert.assertTrue(this.s1.contains("One"));
    Assert.assertFalse(this.s2.contains("Three"));
    Assert.assertFalse(this.mts1.contains("dog"));
    Assert.assertFalse(this.s2.contains("#1"));
  }

  @Test
  public void testSectionFormat() {
    this.init();
    Assert.assertEquals("Section One\nThis is text flow\n#1. This is "
            + "text\nflow #2.\n\nThis is text flow\n#3. This is text\nflow "
            + "#4.", this.s1.format(20));
    Assert.assertEquals("Section Two\nThis is text flow\n#5.", this.s2.format(20));
    Assert.assertEquals("Section Three\nThis is text flow\n#1. This "
            + "is text\nflow #2.\n\nThis is text flow\n#3. This is text\nflow #4.\n\nThis is "
            + "text flow\n#5.", this.s3.format(18));
    Assert.assertEquals("", this.mts1.format(100));
  }

  @Test
  public void testTextFlowCountWords() {
    this.init();
    Assert.assertEquals(5, this.tf1.countWords());
    Assert.assertEquals(5, this.tf2.countWords());
    Assert.assertEquals(0, this.mttf1.countWords());
    Assert.assertEquals(3, this.sptf1.countWords());
  }

  @Test
  public void testTextFlowContains() {
    this.init();
    Assert.assertTrue(this.tf1.contains("text"));
    Assert.assertTrue(this.tf2.contains("flow"));
    Assert.assertFalse(this.tf1.contains("flo"));
    Assert.assertFalse(this.tf2.contains("xt"));
    Assert.assertFalse(this.mttf1.contains("text"));
    Assert.assertFalse(this.sptf1.contains(""));
  }

  @Test
  public void testTextFlowFormat() {
    EBookFlow text1 = new TextFlow("This is text flow #1.");
    EBookFlow text2 = new TextFlow("This is the second text flow, I think.");
    EBookFlow text3 = new TextFlow("This is text flow #3. Each sentence should be a new line.");
    EBookFlow text4 = new TextFlow("");
    EBookFlow text5 = new TextFlow("   Multiple    spaces here.");
    Assert.assertEquals("This is text flow #1.", text1.format(100));
    Assert.assertEquals("This is the second text\nflow, I think.", text2.format(26));
    Assert.assertEquals("This is the second text flow,\nI think.", text2.format(29));
    Assert.assertEquals("", text4.format(100));
    Assert.assertEquals("Multiple spaces here.", text5.format(100));
  }

  @Test
  public void testExceptions() {
    this.init();
    Assert.assertThrows(IllegalArgumentException.class, () -> this.tf1.format(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.tf1.contains(null));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.tf1.contains(" "));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.tf1.contains("  "));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.s1.contains("hello there"));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.b1.contains(" "));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.b1.format(-1));
  }

  @Test
  public void testRemoveExtraWhiteSpacesAndTrailingSpaces() {
    String s = "   Multiple    spaces here.   ";
    String s2 = "       Hello .";
    Assert.assertEquals("Multiple spaces here.", Utils.removeExtraWhitespacesAndTrailingSpaces(s));
    Assert.assertEquals("Hello .", Utils.removeExtraWhitespacesAndTrailingSpaces(s2));
  }

  @Test
  public void testFullyFormatString() {
    String s = "This is the second text flow, I think.";
    String s2 = "This is text flow #3. Each sentence should be a new line.";
    Assert.assertEquals("This is the second text\nflow, I think.",
            Utils.fullyFormatString(s, 26));
    Assert.assertEquals("This is text\nflow #3. Each\nsentence should\nbe "
            + "a new line.", Utils.fullyFormatString(s2, 16));
  }

  @Test
  public void testConstrutorExceptions() {
    this.init();
    Assert.assertThrows(IllegalArgumentException.class, () -> new TextFlow("hello\n"));
    Assert.assertThrows(NullPointerException.class, () -> new Section(null, List.of(this.p1)));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Section("hello\n", null));
    Assert.assertThrows(IllegalArgumentException.class, () -> new EBook(null));
    Assert.assertThrows(NullPointerException.class, () -> new Hyperlink(null, "hello"));
    Assert.assertThrows(NullPointerException.class,
            () -> new Paragraph(List.of(new TextFlow(null))));
  }
}