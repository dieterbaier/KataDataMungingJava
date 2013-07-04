package dieterbaier.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherData {

  private static final String ANYNUMBER              = "\\d";

  private static final String ANYWHITESPACE          = "\\s";

  private static final String ASTERISK               = "\\*";

  public static final String  FILENOTINCLASSPATHTEXT = " not within classpath";

  private static final String START                  = "^";

  public Integer getDayWithMinSpread(final String fileName) {
    final List<String> dataLines = getDataLines(fileName);
    int day = 0;
    int minSpread = Integer.MAX_VALUE;
    for (final String line : dataLines) {
      final String[] values = getValuableValues(line);
      final int spread = getSpread(values);
      if (spread < minSpread) {
        minSpread = spread;
        day = getDay(values);
      }
    }
    return day;
  }

  private List<String> getDataLines(final String fileName) {
    final List<String> dataLines = new ArrayList<>();
    for (final String line : FileReader.readFile(fileName))
      if (isDataLine(line))
        dataLines.add(line);
    return dataLines;
  }

  private Integer getDay(final String[] values) {
    return Integer.parseInt(values[0]);
  }

  private Integer getSpread(final String[] values) {
    return Integer.parseInt(values[1]) - Integer.parseInt(values[2]);
  }

  private String[] getValuableValues(final String line) {
    return line.trim().split("[" + START + ANYNUMBER + "]+", 4);
  }

  private boolean isDataLine(final String line) {
    final Matcher parser = Pattern.compile(
        START + ANYWHITESPACE + "*" + ANYNUMBER + "{1,2}" + "[" + ANYWHITESPACE + "*" + ANYNUMBER + "*" + ASTERISK
            + "{0,1}" + "]" + "{2}")
        .matcher(line);
    return parser.find();
  }

}
