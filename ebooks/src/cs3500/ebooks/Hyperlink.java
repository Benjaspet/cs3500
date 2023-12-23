package cs3500.ebooks;

import java.util.Objects;

/**
 * A Hyperlink consists first of a flow of content to show, and second a
 * link destination.
 */

public class Hyperlink implements EBookFlow {

  private final EBookFlow contentToShow;
  private final String url;

  public Hyperlink(EBookFlow contentToShow, String url) {
    this.contentToShow = Objects.requireNonNull(contentToShow);
    this.url = Objects.requireNonNull(url);
  }

  /**
   * Counts the total number of words throughout this paragraph.
   * @return int representing the number of words.
   */

  public int countWords() {
    return this.contentToShow.countWords();
  }

  /**
   * Determines if this hyperlink's content contain the given *whole* word or not.
   * @param word the word to search for.
   * @return true if the word is contained in this hyperlink's content, false otherwise.
   */

  public boolean contains(String word) {
    return this.contentToShow.contains(word);
  }

  /**
   * Formats this hyperlink's content into a string with the given line width.
   * @param lineWidth the width of the line, in characters.
   * @return a string representing this hyperlink's content.
   */

  public String format(int lineWidth) {
    return this.contentToShow.format(lineWidth);
  }
}
