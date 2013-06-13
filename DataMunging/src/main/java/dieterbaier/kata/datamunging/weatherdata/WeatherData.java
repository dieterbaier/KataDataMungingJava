package dieterbaier.kata.datamunging.weatherdata;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 <p/>
 @author Dieter Baier
 */
public class WeatherData {

  private String filename;
  private int dayWithMinSpread = 0;
  private int minSpread = Integer.MAX_VALUE;
  private final WeatherDataParser parser = new WeatherDataParser ();

  public int getDayWithMinimumSpread (final String filename) {
    this.filename = filename;
    final List<String> dataLines = parser.getDataLines (
	    readAllData ());
    if (dataLines.size () <= 0) {
      throw new IllegalFileException ();
    }
    for (String line : dataLines) {
      if (isTempSpreadSmaller (line)) {
	dayWithMinSpread = parser.getDay (line);
      }
    }
    return dayWithMinSpread;
  }

  private List<String> readAllData () {
    final Path fileLocation = Paths.get (loadFromClasspath ());
    try {
      return Files.readAllLines (fileLocation,
				 getCharset ());
    } catch (IOException ex) {
      throw new RuntimeException (ex);
    }
  }

  private URI loadFromClasspath () {
    try {
      final URL resource = this.getClass ().getClassLoader ().getResource (
	      filename);
      if (resource == null) {
	throw new IOException (filename + " not available.");
      }
      return resource.toURI ();
    } catch (Exception ex) {
      throw new RuntimeException (ex);
    }
  }

  private Charset getCharset () {
    return Charset.defaultCharset ();
  }

  private boolean isTempSpreadSmaller (final String line) {
    final int spread = parser.getTempSpread (line);
    if (spread >= minSpread) {
      return false;
    }
    minSpread = spread;
    return true;
  }
}
