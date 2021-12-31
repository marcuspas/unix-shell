package commands;

import filesystem.Directory;
import filesystem.FileObj;
import filesystem.FileSystem;
import handling.InputParser;
import handling.StandardError;

/**
 * This class is responsible for moving a file or directory in the file system
 * given the path of the destination directory to be moved to.
 * 
 * @author Brandon
 */
public class Move extends Command {

  /**
   * Setup valid argument counts for Move and its documentation.
   * 
   * @param validArgumentCounts Stores All the valid number of Argument counts
   *        for Move
   */
  public Move(int[] validArgumentCounts) {
    super(validArgumentCounts);
    this.documentation = "mv OLDPATH NEWPATH"
        + "\n\tMove item OLDPATH to NEWPATH. Both OLD- PATH and NEWPATH may "
        + "\n\tbe relative to the current directory or may be full paths. "
        + "\n\tIf NEWPATH is a directory, move the item into the directory.";
  }

  /**
   * Check if mv command is being used correctly (correct number of arguments,
   * copying an existing file/directory, etc.). If mv is being used correctly,
   * then move the target file/directory to the designated path.
   * 
   * @param shellInput Arrays of command arguments
   */
  public FileObj execute(String[] shellInput) {
    // Check if DIR given is in Current Working Directory of one of it's parents

    InputParser parser = FileSystem.getInstance().getParser();
    StandardError errorHandler = FileSystem.getInstance().getErrorHandler();
    String[] arguments = super.getArguments(shellInput);
    super.execute(arguments);
    if (!isCountValid) {
      return null;
    }

    // get target directory to move
    FileObj itemToMove = parser.parsePathToFileObj(arguments[0]);
    // get destination to move to
    Directory destination = parser.parsePathToDirectory(arguments[1]);
    // if the item or destination doesn't exist, return.
    if (itemToMove == null || destination == null) {
      return null;
    } else if (!errorHandler.isValidMove(itemToMove, destination)) {
      return null;
    }
    Directory itemToMoveParent = itemToMove.getParentDirectory();
    destination.addToDir(itemToMove);
    itemToMove.setParentDirectory(destination);
    itemToMoveParent.removeFromDir(itemToMove);
    return itemToMove;

  }
}
