package cs3500.ebooks;

import java.util.Arrays;
import java.util.Objects;

/**
 * A TextFlow consists of a sequence of words.
 */

public class TextFlow implements EBookFlow {

  private final String content;

  /**
   * Constructs a TextFlow with the given content.
   * @param content the content of this text flow.
   * @throws IllegalArgumentException if the content contains a line break.
   */

  public TextFlow(String content) {
    this.content = Objects.requireNonNull(content);
    if (content.contains("\n")) {
      throw new IllegalArgumentException("Text flows cannot contain line breaks");
    }
  }

  /**
   * Counts the total number of words throughout this text flow.
   * @return int representing the number of words.
   */

  public int countWords() {
    return Arrays.stream(this.content.strip().split(" "))
            .filter(s -> !s.isEmpty()).toArray().length;
  }

  /**
   * Determines if this text flow contain the given *whole* word or not.
   * @param word the word to search for.
   * @return true if the word is contained in this text flow, false otherwise.
   * @throws IllegalArgumentException if the word is null or contains whitespace.
   */

  public boolean contains(String word) {
    if (word == null || word.contains(" ")) {
      throw new IllegalArgumentException("Invalid word");
    }
    String cleanedContent = this.content.strip().replaceAll(" +", " ");
    for (String s : cleanedContent.split(" ")) {
      if (s.equals(word)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Formats this text flow into a string with the given line width.
   * @param lineWidth the width of the line, in characters.
   * @return a string representing this text flow.
   * @throws IllegalArgumentException if the line width is invalid.
   */

  public String format(int lineWidth) {
    if (lineWidth <= 0) {
      throw new IllegalArgumentException("Invalid line width provided.");
    }
    return Utils.fullyFormatString(this.content, lineWidth);
  }
}