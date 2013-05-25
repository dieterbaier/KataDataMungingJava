/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.novatec.db.kata.datamunging.weatherdata;

import de.novatec.db.kata.datamunging.weatherdata.WeatherDataParser;
import de.novatec.db.kata.datamunging.weatherdata.IllegalDataLineException;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 <p/>
 @author novatec
 */
public class WeatherDataParserTest {

  private WeatherDataParser parser;

  @BeforeMethod
  public void beforeMethod () {
    parser = new WeatherDataParser ();
  }

  @Test
  public void testIllegalDataLineException () {
    String illegalLine = "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP";
    try {
      parser.getDay (illegalLine);
    } catch (Exception e) {
      assertTrue (e instanceof IllegalDataLineException);
    }
    try {
      parser.getMinTemp (illegalLine);
    } catch (Exception e) {
      assertTrue (e instanceof IllegalDataLineException);
    }
    try {
      parser.getMaxTemp (illegalLine);
    } catch (Exception e) {
      assertTrue (e instanceof IllegalDataLineException);
    }
    try {
      parser.getTempSpread (illegalLine);
    } catch (Exception e) {
      assertTrue (e instanceof IllegalDataLineException);
    }
  }

  @Test
  public void testDayOfOneLine () {
    assertEquals (parser.getDay (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  9);
  }

  @Test
  public void testMinTempOfOneLine () {
    assertEquals (parser.getMinTemp (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  32);
  }

  @Test
  public void testMaxTempOfOneLine () {
    assertEquals (parser.getMaxTemp (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  86);
  }

  @Test
  public void testTemperatureSpreadOfOneLine () {
    assertEquals (parser.getTempSpread (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  54);
  }
}
