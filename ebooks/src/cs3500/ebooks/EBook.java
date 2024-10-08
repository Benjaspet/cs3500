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

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple representation of an e-book, that can contain an arbitrary
 * vertical collection of horizontally-wrapped text content.
 *
 * @implNote: The starter code given to you uses loops and stream operations
 *            interchangeably (for instance, {@link EBook#countWords()} and
 *            {@link Paragraph#countWords()} compute very similar things) ---
 *            this is to give examples of how to use streams fluently.
 */

public final class EBook {

  private final List<EBookChunk> chunks;

  public EBook(List<EBookChunk> chunks) {
    validateChunks(chunks);
    this.chunks = new ArrayList<>(chunks);
  }

  private static void validateChunks(List<EBookChunk> chunks) {
    if (chunks == null) {
      throw new IllegalArgumentException("Chunk list cannot be null.");
    }
    if (chunks.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Chunk list cannot contain null chunk.");
    }
  }

  /**
   * Counts the total number of words throughout this e-book.
   * @return int representing the number of words.
   */

  public int countWords() {
    int ans = 0;
    for (EBookChunk chunk : this.chunks) {
      ans += chunk.countWords();
    }
    return ans;
  }

  /**
   * Determines if this e-book contain the given *whole* word or not.
   * @param word the word to search for.
   * @return true if the word is contained in this e-book, false otherwise.
   */

  public boolean contains(String word) {
    return this.chunks.stream().anyMatch(c -> c.contains(word));
  }

  /**
   * Formats this e-book into a string with the given line width.
   * @param lineWidth the width of each line.
   * @return a string representing this e-book.
   */

  public String format(int lineWidth) {
    StringBuilder sb = new StringBuilder();
    for (EBookChunk chunk : this.chunks) {
      sb.append(chunk.format(lineWidth));
      sb.append("\n\n");
    }
    return sb.substring(0, sb.length() - 2);
  }
}