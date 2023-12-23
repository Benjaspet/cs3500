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

import cs3500.ebooks.EBookChunk;
import cs3500.ebooks.EBookFlow;
import cs3500.ebooks.Paragraph;
import cs3500.ebooks.Section;
import cs3500.ebooks.TextFlow;

import org.junit.Assert;
import org.junit.Test;

/**
 * Examplar tests for the EBook class and affiliated classes and methods.
 */

public class ExamplarEBooks {

  /**
   * This chaff occurs when the provided code counts the length of section
   * content as words, rather than the amount of words in each flow of content.
   */

  @Test
  public void exampleSectionCount() {
    EBookFlow text = new TextFlow("our founders");
    EBookFlow text2 = new TextFlow("our goals");
    EBookChunk section = new Section("people", List.of(new Paragraph(List.of(text, text2))));
    Assert.assertEquals(5, section.countWords());
  }

  /**
   * This chaff occurs when the provided code marks a chunk of a word as
   * existent in the EBook; this should not happen, as only full words should be counted.
   */

  @Test
  public void exampleSectionContains() {
    EBookFlow text = new TextFlow("our goals section");
    EBookChunk section = new Section("introduction", List.of(new Paragraph(List.of(text))));
    Assert.assertFalse(section.contains("goal"));
  }

  /**
   * This chaff occurs when the provided code does not throw an exception when contains() is run
   * on a section title with a parameter that contains a space.
   */

  @Test
  public void exampleSectionContainsWithSpaceInTitle() {
    EBookFlow text = new TextFlow("our goals section");
    EBookChunk section = new Section("introduction #1", List.of(new Paragraph(List.of(text))));
    Assert.assertThrows(IllegalArgumentException.class, () -> section.contains("introduction #1"));
  }

  /**
   * This chaff occurs when the provided code detects a portion of a word in the
   * section title as a hit for the contains() method, when it should be an entire, whole word.
   */

  @Test
  public void exampleSectionContainsDoubleHit() {
    EBookFlow text = new TextFlow("our goals section");
    EBookChunk section = new Section("introduction", List.of(new Paragraph(List.of(text))));
    Assert.assertFalse(section.contains("intro"));
  }

  /**
   * This chaff occurs when the provided code fails to throw exceptions when the paramater of the
   * contains() method in TextFlow is null or contains a space.
   */

  @Test
  public void exampleTextContainsNullAndSpace() {
    EBookFlow text = new TextFlow("northeastern");
    Assert.assertThrows(IllegalArgumentException.class, () -> text.contains("north eastern"));
    Assert.assertThrows(IllegalArgumentException.class, () -> text.contains(null));
  }
}
