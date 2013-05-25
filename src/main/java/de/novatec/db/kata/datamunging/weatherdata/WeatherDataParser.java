/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.novatec.db.kata.datamunging.weatherdata;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 <p/>
 @author novatec
 */
public class WeatherDataParser {

  private static final Pattern NUMERIC_PATTERN = Pattern.compile ("^[\\d]");

  List<String> getDataLines (final List<String> lines) {
    List<String> dataLines = new ArrayList<String> (0);
    for (String line : lines) {
      if (isDataLine (line)) {
	dataLines.add (line);
      }
    }
    return dataLines;
  }

  int getDay (final String line) {
    final String field = getField (line,
				   1);
    if (isNumeric (field)) {
      return Integer.parseInt (field);
    } else {
      throw new IllegalDataLineException ();
    }
  }

  int getMinTemp (final String line) {
    final String field = getField (line,
				   3);
    if (isNumeric (field)) {
      return Integer.parseInt (field);
    } else {
      throw new IllegalDataLineException ();
    }
  }

  int getMaxTemp (final String line) {
    final String field = getField (line,
				   2);
    if (isNumeric (field)) {
      return Integer.parseInt (field);
    } else {
      throw new IllegalDataLineException ();
    }
  }

  int getTempSpread (final String line) {
    return getMaxTemp (line) - getMinTemp (line);
  }

  private boolean isNumeric (final String value) {
    return NUMERIC_PATTERN.matcher (value.trim ()).find ();
  }

  private String getField (final String line,
			   final int fieldIndex) {
    StringTokenizer st = new StringTokenizer (line,
					      " *");
    if (st.countTokens () > fieldIndex) {
      int i = 1;
      while (st.hasMoreTokens ()) {
	if (i++ == fieldIndex) {
	  return st.nextToken ();
	}
	st.nextToken ();
      }
    }
    return "";
  }

  private boolean isDataLine (final String line) {
    final String potentialDataLine = cutUnwantedData (line);
    try {
      getDay (potentialDataLine);
      getMinTemp (potentialDataLine);
      getMaxTemp (potentialDataLine);
      return true;
    } catch (IllegalDataLineException e) {
      return false;
    }
  }

  private String cutUnwantedData (final String data) {
    return data.trim ();
  }
}
