package commands;

import filesystem.Directory;
import filesystem.FileSystem;
import handling.InputParser;
import handling.StandardError;
import java.util.ArrayList;

/**
 * This class is responsible for making new directories in the file system given
 * the names of the directories to be made.
 * 
 * @author Brandon, Marcus, Danny
 */
public class MakeDirectory extends Command {

  /**
   * Setup valid argument counts for MakeDirectory and its documentation.
   * 
   * @param validArgumentCounts Stores All the valid number of Argument counts
   *        for MakeDirectory
   * 
   */
  public MakeDirectory(int[] validArgumentCounts) {
    super(validArgumentCounts);
    this.documentation = "mkdir DIR1 [DIR2...] "
        + "\n\tCreate directories, each of which may be relative to the "
        + "\n\tcurrent directory or may be a full path.";
  }


  /**
   * Makes a new directory in the file system.
   * 
   * @param parentDir Where a new directory will be made
   * @param name Name of the directory to be made
   */
  private Directory makeDir(Directory parentDir, String name) {

    Directory newDir = new Directory(name);
    parentDir.addToDir(newDir);
    newDir.setParentDirectory(parentDir);

    return newDir;
  }


  /**
   * Set up to make a new directory/directories, then make use of makeDir. Also
   * call error checking to check if filename is valid.
   * 
   * @param shellInput Arrays of command arguments
   * @return  the list of directories that was made
   */
  @Override
  public ArrayList<Directory> execute(String[] shellInput) {
    InputParser parser = FileSystem.getInstance().getParser();
    StandardError errorHandler = FileSystem.getInstance().getErrorHandler();
    String[] arguments = super.getArguments(shellInput);
    super.execute(arguments);
    if (!isCountValid) {
      return null;
    }
    ArrayList<Directory> dirList = new ArrayList<>();
    Directory parent = null;
    String newFileName;

    for (String path : arguments) {

      parent = parser.parsePathToSecondLastDirectory(path);
      newFileName = parser.parsePathToName(path);

      // if path or name is invalid, then don't create new file
      if (parent == null || !(errorHandler.isValidFileName(newFileName))
          || errorHandler.isDuplicateName(parent, newFileName)) {
        dirList.add(null);
        return dirList;
      }
      dirList.add(makeDir(parent, newFileName));
    }
    return dirList;
  }
}
