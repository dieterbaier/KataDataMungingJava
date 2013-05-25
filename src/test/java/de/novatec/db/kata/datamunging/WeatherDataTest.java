/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.novatec.db.kata.datamunging;

import de.novatec.db.kata.datamunging.weatherdata.IllegalFileException;
import de.novatec.db.kata.datamunging.weatherdata.WeatherData;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 <p/>
 @author novatec
 */
public class WeatherDataTest {

  private static final String TESTFILE = "weather.dat";

  @Test
  public void testGettingDayOfSmallestTemperatureSpread () {
    assertEquals (new WeatherData ().getDayWithMinimumSpread (TESTFILE),
		  14);
  }

  @Test
  public void testFileNotReadable () throws URISyntaxException {
    File file = Paths.get (
	    this.getClass ().getClassLoader ().getResource (TESTFILE).toURI ()).toFile ();
    file.setReadable (false);
    try {
      new WeatherData ().getDayWithMinimumSpread (TESTFILE);
      fail ("RuntimeException with cause of IOException expected");
    } catch (RuntimeException e) {
      assertTrue (e.getCause () instanceof IOException);
    } finally {
      file.setReadable (true);
    }
  }

  @Test
  public void testFileNotAvailable () {
    try {
      new WeatherData ().getDayWithMinimumSpread ("unknownFile");
    } catch (RuntimeException e) {
      assertTrue (e.getCause () instanceof IOException);
    }
  }

  @Test (expectedExceptions = IllegalFileException.class)
  public void testNoDataLinesAvailable () {
    new WeatherData ().getDayWithMinimumSpread ("weatherWithoutData.dat");
  }
}
