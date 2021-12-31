package commands;

import filesystem.Directory;
import filesystem.FileSystem;
import handling.InputParser;

/**
 * This class is responsible for changing current directory on the mock shell
 * given a target directory, change that to the current working directory.
 * 
 * @author Marcus, Danny
 *
 */
public class ChangeDirectory extends Command {

  /**
   * Setup the valid argument counts for changeDirectory and its documentation.
   * 
   * @param validArgumentCounts Stores all the valid number of Argument counts
   *        for changeDirectory
   */
  public ChangeDirectory(int[] validArgumentCounts) {
    super(validArgumentCounts);
    this.documentation = "cd DIR"
        + "\n\tChange directory to DIR, which may be relative to the current "
        + "\n\tdirectory or may be a full path.";
  }


  /**
   * Changes the current directory, with the help of parser to get the directory
   * and error checking class for potential errors.
   * 
   * @param shellInput  Arrays of command arguments
   * @return  the new working directory 
   */
  public Directory execute(String[] shellInput) {

    FileSystem fileSystem = FileSystem.getInstance();
    InputParser parser = FileSystem.getInstance().getParser();
    String[] arguments = super.getArguments(shellInput);

    // check for argument counts
    super.execute(arguments);
    if (!isCountValid) {
      return null;
    }

    // get the target directory, check if it's valid then change it to current.

    String path = arguments[0];

    Directory newWorkingDir = parser.parsePathToDirectory(path);

    if (newWorkingDir == null) {
      return null;
    } else {
      fileSystem.setWorkingDirectory(newWorkingDir);
    }
    return newWorkingDir;
  }
}
