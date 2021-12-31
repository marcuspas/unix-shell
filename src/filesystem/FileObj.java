package filesystem;

import java.io.Serializable;

/**
 * This is an abstract class to represent a general file. Which include both a
 * file and a directory. This class is the parent of Directory and File class.
 * 
 * @author Danny
 */
public abstract class FileObj implements Serializable {


  private static final long serialVersionUID = 1L;
  /**
   * Name is the file object's name, and parentDirectory is its parent
   * directory.
   */
  protected String name;
  protected Directory parentDirectory;

  /**
   * Constructor of FileObj. Once called, Initialize the name of the file object
   * 
   * @param name Name of the file object.
   */
  public FileObj(String name) {
    /*
     * "this.name" is instance variable inherit from File class, "name" is the
     * local variable
     */
    this.name = name;
  }

  /**
   * Setter for name.
   * 
   * @param name Name of the file object to be set on this file
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for name.
   * 
   * @return Return the name of this file object
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter for parent directory.
   * 
   * @return Return the parent directory
   */
  public Directory getParentDirectory() {
    return parentDirectory;
  }

  /**
   * Setter for parent directory.
   * 
   * @param parentDirectory set the file object's parent directory to the given
   *        directory
   */
  public void setParentDirectory(Directory parentDirectory) {
    this.parentDirectory = parentDirectory;
  }

  /**
   * Check if two file objects is equal.
   * 
   * @param fo The file object to be compared to,
   * @return Return true if the fileObjs are equals, false if they are not
   *         equals
   */
  public boolean equals(FileObj fo) {
    /*
     * Check if they have the same type, the same name, and the same parent
     * directory
     */
    return (this.getClass().equals(fo.getClass())
        && this.getName().equals(fo.getName())
        && ((this.getParentDirectory() == null
            && fo.getParentDirectory() == null)
            || this.getParentDirectory().equals(fo.getParentDirectory())));

  }
  
  
  /**
   * Returns the depth of a fileobj (distance from root).
   * 
   * @return The depth of the fileobj
   */
  public int getDepth() {
    if (name.equals("/")) {
      return 0;
    } else {
      return 1 + parentDirectory.getDepth();
    }
  }



}
