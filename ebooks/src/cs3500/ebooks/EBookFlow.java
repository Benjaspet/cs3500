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
 * An EBookFlow is a contiguous chunk of text, like a paragraph, that can
 * line-wrap and flow to resize to fit the e-book reader.
 */

public interface EBookFlow {

  /**
   * How many words are in this flow?  Only complete, non-empty words count.
   *
   * @return the relevant count
   */

  int countWords();

  /**
   * Does this flow contain the given word?  Only complete words count:
   * for instance, the document "hello" does not contain the word "ell".
   *
   * @return true iff the given word appears in this e-book
   * @throws IllegalArgumentException if the given word is null,
   *         or contains spaces.
   */

  boolean contains(String word);

  /**
   * Renders the e-book such that it fits on a screen of the given width
   * (measured in characters), without any horizontal overflow.
   *
   * @param lineWidth the width of the line, in characters.
   * @return String
   */

  String format(int lineWidth);

}