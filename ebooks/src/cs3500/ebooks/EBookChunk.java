package cs3500.ebooks;

/**
 * An EBookChunk is a larger piece of content within an EBook, like a paragraph
 * or a section, whose position in the document is fixed relative to other chunks.
 */

public interface EBookChunk {

  /**
   * The total amount of words in this e-book chunk.
   *
   * @return the relevant count.
   */

  int countWords();

  /**
   * Does this ebook chunk contain the given word?  Only complete words count:
   * for instance, the document "hello" does not contain the word "ell".
   *
   * @return true iff the given word appears in this e-book
   * @throws IllegalArgumentException if the given word is null,
   *         or contains spaces.
   */

  boolean contains(String word);

  /**
   * Formats this e-book chunk into a string with the given line width.
   * @param lineWidth the width of each line.
   * @return a string representing this e-book chunk.
   */

  String format(int lineWidth);

}