package dieterbaier.kata;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class FileReader {

  public static URI getFromClasspath(final String fileName) {
    try {
      return findInClasspath(fileName).toURI();
    }
    catch (final URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<String> readFile(final String fileName) {
    try {
      return Files.readAllLines(
          Paths.get(getFromClasspath(fileName)), Charset.defaultCharset());
    }
    catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static URL findInClasspath(final String fileName) throws IOException {
    final URL url = FileReader.class.getClassLoader().getResource(fileName);
    if (url == null)
      throw new IOException(new StringBuilder(fileName).append(WeatherData.FILENOTINCLASSPATHTEXT).toString());
    return url;
  }
}
