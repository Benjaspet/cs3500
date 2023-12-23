/*
 * Copyright © 2023 Ben Petrillo. All rights reserved.
 *
 * Project licensed under the MIT License: https://www.mit.edu/~amini/LICENSE.md
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * All portions of this software are available for public use, provided that
 * credit is given to the original author(s).
 */

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
