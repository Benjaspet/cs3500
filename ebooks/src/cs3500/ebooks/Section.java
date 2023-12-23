package cs3500.ebooks;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

/**
 * A Section of an e-book consists of a section title, followed by
 * a sequence of e-book chunks (which could be paragraphs, sub-sections, etc.)
 */

public class Section implements EBookChunk {

  private final String title;
  private final List<EBookChunk> contents;

  /**
   * Constructs a Section with the given title and contents.
   * @param title the title of this section.
   * @param contents the contents of this section.
   * @throws IllegalArgumentException if the title contains a line break.
   */

  public Section(String title, List<EBookChunk> contents) {
    this.title = Objects.requireNonNull(title).strip().replaceAll(" +", " ");
    if (title.contains("\n")) {
      throw new IllegalArgumentException("Titles cannot contain line breaks.");
    }
    this.contents = new ArrayList<>(Objects.requireNonNull(contents));
  }

  /**
   * Counts the total number of words throughout this section.
   * @return int representing the number of words.
   */

  public int countWords() {
    if (this.title.isBlank()) {
      return this.contents.stream().mapToInt(EBookChunk::countWords).sum();
    }
    return this.title.strip().replaceAll(" +", " ").split(" ").length
            + this.contents.stream().mapToInt(EBookChunk::countWords).sum();
  }

  /**
   * Determines if this section contain the given *whole* word or not.
   * @param word the word to search for.
   * @return true if the word is contained in this section, false otherwise.
   * @throws IllegalArgumentException if the word is null or contains whitespace.
   */

  public boolean contains(String word) {
    if (word == null || word.contains(" ")) {
      throw new IllegalArgumentException("Invalid word provided.");
    }
    for (String s : this.title.strip().replaceAll(" +", " ").split(" ")) {
      if (s.equals(word)) {
        return true;
      }
    }
    return this.contents.stream().anyMatch(c -> c.contains(word));
  }

  /**
   * Formats this section into a string with the given line width.
   * @param lineWidth the width of each line.
   * @return a string representing this section.
   * @throws IllegalArgumentException if the line width is 0 or less.
   */

  public String format(int lineWidth) {
    if (lineWidth <= 0) {
      throw new IllegalArgumentException("Invalid line width provided.");
    }
    if (this.contents.isEmpty()) {
      return this.title;
    }
    StringBuilder builder = new StringBuilder();
    builder.append(Utils.fullyFormatString(this.title.strip(), lineWidth)).append("\n");
    for (EBookChunk chunk : this.contents) {
      builder.append(chunk.format(lineWidth));
      builder.append("\n\n");
    }
    return builder.toString().strip();
  }
}