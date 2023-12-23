package cs3500.ebooks;

/**
 * Represents utility methods.
 */

public class Utils {

  /**
   * Removes extra whitespaces and trailing spaces from the given string.
   * @param toFormat the string to format.
   * @return the formatted string.
   */

  public static String removeExtraWhitespacesAndTrailingSpaces(String toFormat) {
    return toFormat.strip().replaceAll(" +", " ");
  }

  /**
   * Fully formats the given string to fit the given line width.
   * @param input the string to format.
   * @param lineWidth the width of each line.
   * @return the formatted string.
   * @throws IllegalArgumentException if the line width is invalid.
   */

  public static String fullyFormatString(String input, int lineWidth) {
    String[] words = input.strip().split("\\s+");
    StringBuilder formatted = new StringBuilder();
    StringBuilder currLine = new StringBuilder();
    int currentLineWidth = 0;
    for (String word : words) {
      if (word.length() > lineWidth) {
        throw new IllegalArgumentException("Word is too long to fit in a line.");
      }
      if (currLine.length() == 0) {
        currLine.append(word);
        currentLineWidth += word.length();
      } else if (currentLineWidth + 1 + word.length() <= lineWidth) {
        currLine.append(" ").append(word);
        currentLineWidth += 1 + word.length();
      } else {
        formatted.append(currLine).append("\n");
        currLine.setLength(0);
        currLine.append(word);
        currentLineWidth = word.length();
      }
    }
    if (currLine.length() > 0) {
      formatted.append(currLine);
    }
    return formatted.toString().strip().replaceAll(" +", " ");
  }

}