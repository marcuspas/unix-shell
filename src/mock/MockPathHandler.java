package mock;

import handling.PathHandler;


/**
 * This is a mock class for PathHandler, the methods only obey signature and
 * return type, it bypass the implementation.
 * 
 * @author danny
 */
public class MockPathHandler extends PathHandler {

  private static final long serialVersionUID = 1L;
  
  /**
   * mock method for getName().
   * 
   * @param path  file path
   * @param index  index in the path
   * @return  file name at that index
   */
  public String getName(String path, int index) {
    return "FileName";
  }

}
