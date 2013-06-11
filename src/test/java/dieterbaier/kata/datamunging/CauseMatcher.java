package dieterbaier.kata.datamunging;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 @author Dieter Baier
 */
public class CauseMatcher<E extends Throwable>
	extends BaseMatcher<E> {

  private final Class<E> cause;
  private Throwable foundCause;

  CauseMatcher (
	  Class<E> cause) {
    this.cause = cause;
  }

  public boolean matches (Object item) {
    if (!(item instanceof Throwable)) {
      return false;
    }
    final Throwable t = (Throwable) item;
    foundCause = t.getCause ();
    return cause.isInstance (foundCause);
  }

  public void describeTo (Description description) {
    description.appendText ("Found cause ").appendValue (foundCause).appendText (
	    " does not match to expected cause ").appendValue (cause);
  }
}
