package cs3500.ebooks;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

/**
 * A Paragraph of an e-book consists of a list of chunks of plain text.
 */

public class Paragraph implements EBookChunk {

  private final List<EBookFlow> contents;

  public Paragraph(List<EBookFlow> contents) {
    validateContents(contents);
    this.contents = new ArrayList<>(contents);
  }

  private static void validateContents(List<EBookFlow> content) {
    if (content == null) {
      throw new IllegalArgumentException("Contentlist cannot be null");
    }
    if (content.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Content list cannot contain null content");
    }
  }

  /**
   * Counts the total number of words throughout this paragraph.
   * @return int representing the number of words.
   */

  public int countWords() {
    return this.contents.stream().mapToInt(EBookFlow::countWords).sum();
  }

  /**
   * Determines if this paragraph contain the given *whole* word or not.
   * @param word the word to search for.
   * @return true if the word is contained in this paragraph, false otherwise.
   */

  public boolean contains(String word) {
    return this.contents.stream().anyMatch(c -> c.contains(word));
  }

  /**
   * Formats this paragraph into a string with the given line width.
   * @param lineWidth the width of each line.
   * @return a string representing this paragraph.
   * @throws IllegalArgumentException if the line width 0 or less.
   */

  public String format(int lineWidth) {
    if (lineWidth <= 0) {
      throw new IllegalArgumentException("Invalid line width provided.");
    }
    StringBuilder sb = new StringBuilder();
    for (EBookFlow content : this.contents) {
      sb.append(content.format(lineWidth));
      sb.append(" ");
    }
    EBookFlow flow = new TextFlow(sb.toString().replaceAll("\n", " "));
    return flow.format(lineWidth);
  }
}