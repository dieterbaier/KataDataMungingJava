import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class WeatherDatTest {
  @Test
  public void CheckFileNum() throws IOException, URISyntaxException {
    assertThat(getContent().size(), is(30));
  }

  @Test
  public void checkGetDay() {
    assertThat(getDay("  9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
        is(9));
  }

  @Test
  public void checkGetMaxTemperature() {
    assertThat(
        getMaxTemperature("  9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
        is(86));
  }

  @Test
  public void checkGetMinTemperature() {
    assertThat(
        getMinTemperature("  9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
        is(32));
  }

  @Test
  void checkFindMinDay() {
    assertThat(getMinSpread(), is(14));
  }

  private List<String> getContent() throws IOException, URISyntaxException {
    final URL path = this.getClass().getClassLoader().getResource("weather.dat");

    final List<String> lines = Files.readAllLines(Paths.get(path.toURI()), Charset.defaultCharset());
    final List<String> results = new ArrayList<>();
    for (final String line : lines)
      if (line.matches("^\\s+[0-9]+.*[0-9]+.*[0-9]")) {
        results.add(line.trim().replaceAll("\\*", ""));
        System.out.println(line);
      }

    return results;
  }

  private int getDay(final String string) {
    return getValues(string)[0];
  }

  private int getMaxTemperature(final String string) {
    return getValues(string)[1];
  }

  private int getMinSpread() {
    int result = -1;
    try {
      final List<String> content = getContent();
      int diff = Integer.MAX_VALUE;
      for (final String line : content) {
        final int max = getMaxTemperature(line);
        final int min = getMinTemperature(line);
        if (diff > max - min) {
          diff = max - min;
          result = getDay(line);
        }
      }
    }
    catch (final Exception e) {
      // TODO: handle exception
    }
    return result;
  }

  private int getMinTemperature(final String string) {
    return getValues(string)[2];
  }

  private int[] getValues(final String string) {
    final int[] arr = new int[3];
    final String[] strArr = string.trim().replaceAll("\\*", "").split("\\s+");

    for (int j = 0; j < 3; ++j)
      arr[j] = Integer.parseInt(strArr[j]);

    return arr;
  }

}
