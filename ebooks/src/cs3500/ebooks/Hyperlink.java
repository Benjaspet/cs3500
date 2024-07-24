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

import java.util.Objects;

/**
 * A Hyperlink consists first of a flow of content to show, and second a
 * link destination.
 */

public class Hyperlink implements EBookFlow {

  private final EBookFlow contentToShow;

  public Hyperlink(EBookFlow contentToShow, String url) {
    this.contentToShow = Objects.requireNonNull(contentToShow);
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