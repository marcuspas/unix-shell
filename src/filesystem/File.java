package filesystem;


/**
 * This is the class the represent a common file(doesn't include directory).
 * This class inherit from FileObj class.
 * 
 * @author Danny
 */
public class File extends FileObj {

  private static final long serialVersionUID = 1L;
  /**
   * This variable is the textual representation of the file.
   */
  private StringBuilder stringContent;

  /**
   * Constructor of File. Once called, Initialize the name of the file.
   * 
   * @param name Name of the file.
   */
  public File(String name) {
    super(name);
    // initialize stringContent
    stringContent = new StringBuilder("");
  }

  /**
   * Overloaded constructor of File. Once called, Initialize the name of the
   * file and it's text content.
   * 
   * @param name Name of the file.
   * @param stringContent Text content of the file.
   */
  public File(String name, String stringContent) {
    /*
     * both name and stringContent in the parameter is local variable, not the
     * instance variable
     */
    super(name);
    this.stringContent = new StringBuilder(stringContent);
  }

  /**
   * This method append text content to the end of the file.
   * 
   * @param textContent Text content to be added into the file
   */
  public void addToTextContent(String textContent) {
    this.stringContent.append(textContent);
  }

  /**
   * This method empty the text content of the file.
   */
  public void clearTextContent() {
    this.stringContent.setLength(0);
  }

  /**
   * This method gives back the text content of the file.
   * 
   * @return This returns text content of the file
   */
  public String toString() {
    return this.stringContent.toString();
  }

}
