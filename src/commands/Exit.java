package commands;

import filesystem.FileSystem;

/**
 * The Exit class is a Command that exits the program.
 *
 * @author Aliel
 */
public class Exit extends Command {

  /**
   * Constructs a new Exit with its valid argument counts and documentation.
   *
   * @param validArgumentCounts Stores all the valid argument counts for Exit.
   */
  public Exit(int[] validArgumentCounts) {
    super(validArgumentCounts);
    this.documentation = "exit \n\tQuit the program";
  }

  /**
   * If the size of arguments is in validArgumentCounts then the JShell is set
   * to be terminated, and returns true. Otherwise, return false.
   *
   * @param shellInput An array of arguments for the command.
   * @return True if the size of arguments is in validArgumentCounts, or false.
   */
  @Override
  public Boolean execute(String[] shellInput) {
    // Splits the arguments of shellInput into an array of arguments
    String[] arguments = super.getArguments(shellInput);

    // Checks if the arguments is a valid argument count
    super.execute(arguments);

    if (!isCountValid) {
      return false;
    }

    // Set closeShell to True to terminate the program
    FileSystem.getInstance().setCloseShell(true);

    return true;
  }
}
