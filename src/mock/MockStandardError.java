package mock;

import filesystem.Directory;
import handling.StandardError;

/**
 * This is a mock class for StandardError, the methods only obey signature and
 * return type, it bypass the implementation.
 * 
 * @author danny
 */
public class MockStandardError extends StandardError {

  private static final long serialVersionUID = 1L;

  /**
   * mock method for isValidFile().
   * 
   * @param parentDir parent directory
   * @param fileName name of the file
   * @return return true for testing
   */
  public boolean isValidFile(Directory parentDir, String fileName) {
    return true;
  }

  /**
   * mock method for isValidFileObj().
   * 
   * @param parentDir parent directory
   * @param fileName name of the file
   * @return return true for testing
   */
  public boolean isValidFileObj(Directory parentDir, String fileName) {
    return true;
  }

  /**
   * mock method for isValidPath().
   * 
   * @param argument argument in string
   * @param fileNum index number of file in the path
   * @return return true for testing
   */
  public boolean isValidPath(String argument, int fileNum) {
    return true;
  }

  /**
   * mock method for isValidPath().
   * 
   * @param argument argument in string
   * @return return true for testing
   */
  public boolean isValidPath(String argument) {
    return true;
  }

  /**
   * mock method for isValidNumber().
   * 
   * @param argument argument in string
   * @return return true for testing
   */
  public boolean isValidNumber(String argument) {
    return true;
  }

  /**
   * mock method for isValidString().
   * 
   * @param argument argument in string
   * @return return true for testing
   */
  public boolean isValidString(String argument) {
    return true;
  }

  /**
   * mock method for isValidCommand().
   * 
   * @param argument argument in string
   * @return return true for testing
   */
  public boolean isValidCommand(String argument) {
    return true;
  }
}
