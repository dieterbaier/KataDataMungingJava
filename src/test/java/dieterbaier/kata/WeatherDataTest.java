package dieterbaier.kata;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WeatherDataTest {

  private static final String TESTFILE = "weather.dat";

  private CauseMatcher        causeMatcher;

  private WeatherData         weatherData;

  @AfterMethod
  public void afterMethod(final ITestResult testResult) {
    if (hasToCheckCause(testResult))
      assertThat(testResult, causeMatcher);
    causeMatcher = null;
  }

  @BeforeMethod
  public void beforeMethod() {
    weatherData = new WeatherData();
  }

  @Test
  public void getDayWithMinSpread() {
    assertThat(weatherData.getDayWithMinSpread(TESTFILE), is(14));
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void invalidFile() {
    final String fileName = "unknownFile";
    causeMatcher = new CauseMatcher(IOException.class, fileName + WeatherData.FILENOTINCLASSPATHTEXT);
    weatherData.getDayWithMinSpread(fileName);
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void notreadableFile() {
    causeMatcher = new CauseMatcher(AccessDeniedException.class, null);
    setReadable(TESTFILE, false);
    try {
      weatherData.getDayWithMinSpread(TESTFILE);
    }
    finally {
      setReadable(TESTFILE, true);
    }
  }

  private boolean hasToCheckCause(final ITestResult testResult) {
    return testResult.isSuccess() && causeMatcher != null;
  }

  private void setReadable(final String fileName, final boolean isReadable) {
    new File(FileReader.getFromClasspath(fileName)).setReadable(isReadable);
  }

}
