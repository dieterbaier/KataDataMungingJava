package dieterbaier.kata;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class FileReader {

  public static URL findInClasspath(final String fileName) throws IOException {
    final URL url = FileReader.class.getClassLoader().getResource(fileName);
    if (url == null)
      throw new IOException(new StringBuilder(fileName).append(WeatherData.FILENOTINCLASSPATHTEXT).toString());
    return url;
  }

  public static URI getFromClasspath(final String fileName) {
    try {
      return findInClasspath(fileName).toURI();
    }
    catch (final URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
