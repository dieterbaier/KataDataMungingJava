/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dieterbaier.kata.datamunging.weatherdata;

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

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void GetDayWithInvalidLine () {
    parser.getDay (
	    "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void GetDayWithPotentialValidLine () {
    parser.getDay (
	    "   x  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void MaxTempWithInvalidLine () {
    parser.getMaxTemp (
	    "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void MaxTempWithPotentialValidLine () {
    parser.getMaxTemp (
	    "   9  xx    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void TempSpreadWithInvalidLine () {
    parser.getTempSpread (
	    "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void TempSpreadWithPotentialValidLine () {
    parser.getTempSpread (
	    "   9  xx    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void MinTempWithIllegalLine () {
    parser.getMinTemp (
	    "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP");
  }

  @Test (expectedExceptions = IllegalDataLineException.class)
  public void IllegalMinTempWithPotentialValidLine () {
    parser.getMinTemp (
	    "   9  86    xx*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6");
  }

  @Test
  public void DayOfOneLine () {
    assertEquals (parser.getDay (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  9);
  }

  @Test
  public void MinTempOfOneLine () {
    assertEquals (parser.getMinTemp (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  32);
  }

  @Test
  public void MaxTempOfOneLine () {
    assertEquals (parser.getMaxTemp (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  86);
  }

  @Test
  public void TemperatureSpreadOfOneLine () {
    assertEquals (parser.getTempSpread (
	    "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6"),
		  54);
  }
}
