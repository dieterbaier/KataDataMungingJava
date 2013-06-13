package dieterbaier.kata.datamunging;

import dieterbaier.kata.datamunging.weatherdata.IllegalFileException;
import dieterbaier.kata.datamunging.weatherdata.WeatherData;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 <p/>
 @author Dieter Baier
 */
public class WeatherDataTest {

  private static final String TESTFILE = "weather.dat";
  private CauseMatcher causeMatcher = null;

  @AfterMethod
  public void afterMethod (ITestResult result) {
    if (hasToCheckTheCause (result)) {
      assertThat (
	      result.getThrowable (),
	      causeMatcher);
    }
    cleanUp ();
  }

  @Test
  public void DayOfSmallestTemperatureSpread () {
    assertEquals (new WeatherData ().getDayWithMinimumSpread (TESTFILE),
		  14);
  }

  @Test (expectedExceptions = RuntimeException.class)
  public void FileNotReadable () throws URISyntaxException {
    causeMatcher = new CauseMatcher (IOException.class);
    try {
      makeFileUnreadable (TESTFILE);
      new WeatherData ().getDayWithMinimumSpread (TESTFILE);
    } finally {
      makeFileReadable (TESTFILE);
    }

  }

  @Test (expectedExceptions = RuntimeException.class)
  public void FileNotAvailable () {
    causeMatcher = new CauseMatcher (IOException.class);
    new WeatherData ().getDayWithMinimumSpread ("unknownFile");
  }

  @Test (expectedExceptions = IllegalFileException.class)
  public void NoDataLinesAvailable () {
    new WeatherData ().getDayWithMinimumSpread ("weatherWithoutData.dat");
  }

  private void makeFileUnreadable (final String filename) throws URISyntaxException {
    getFile (filename).setReadable (
	    false);
  }

  private void makeFileReadable (final String filename) throws URISyntaxException {
    getFile (filename).setReadable (
	    true);
  }

  private File getFile (final String filename) throws URISyntaxException {
    return Paths.get (
	    this.getClass ().getClassLoader ().getResource (filename).toURI ()).toFile ();
  }

  private boolean hasToCheckTheCause (ITestResult result) {
    return result.isSuccess () && causeMatcher != null;
  }

  private void cleanUp () {
    causeMatcher = null;
  }
}
