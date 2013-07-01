package dieterbaier.kata;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.testng.ITestResult;

public class CauseMatcher<E extends Class<Exception>> extends BaseMatcher<E> {

  private final String message;

  private final E      exceptionClass;

  private String       description;

  public CauseMatcher(final E exceptionClass, final String message) {
    this.exceptionClass = exceptionClass;
    this.message = message;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(this.description);
  }

  @Override
  public boolean matches(final Object objectToTest) {
    if (!(objectToTest instanceof ITestResult)) {
      description = "The object to test must be of type " + ITestResult.class.getName();
      return false;
    }
    final ITestResult testResult = (ITestResult) objectToTest;
    if (testResult.getThrowable() == null) {
      description = "The testresult must transport a exception thrown during the testcase";
      return false;
    }
    if (!exceptionClass.isInstance(testResult.getThrowable().getCause())) {
      description = "The transported exception must have a cause of type " + exceptionClass.getClass().getName();
      return false;
    }
    final Throwable cause = testResult.getThrowable().getCause();
    if (message != null && !message.equals(cause.getMessage())) {
      description = "Expected message is " + message + "; actual message = " + cause.getMessage();
      return false;
    }
    return true;
  }

}
