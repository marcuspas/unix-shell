package commands;

import filesystem.FileSystem;
import handling.StandardError;

/**
 * The Command class is an abstract class which provides the structure for each
 * command to inherit from.
 * 
 * @author Marcus
 * 
 */
public abstract class Command {

  /** An array of valid argument counts for the command. */
  protected int[] validArgumentCounts;
  /** The documentation for the command. */
  protected String documentation;
  /** A boolean used to keep track if if the command's count is valid. */
  protected boolean isCountValid;


  /**
   * Creates a Command object with the valid argument counts.
   * 
   * @param validArgumentCounts The array of valid argument counts
   */
  public Command(int[] validArgumentCounts) {
    this.validArgumentCounts = validArgumentCounts;
  }

  /**
   * Returns the valid argument counts for a command.
   * 
   * @return The commands valid argument counts.
   */
  public int[] getValidArgumentCounts() {
    return validArgumentCounts;
  }

  /**
   * Returns the string representation of a command. More specifically, it's
   * documentation.
   * 
   * @return The command's documentation
   */
  @Override
  public String toString() {
    return documentation;
  }

  /**
   * Executes the command on the shell.
   * 
   * @param shellInput The input parameters for the execution.
   * @return  The command subclasses's return
   */
  public Object execute(String[] shellInput) {

    StandardError errorHandler = FileSystem.getInstance().getErrorHandler();
    isCountValid =
        errorHandler.checkArgumentCount(validArgumentCounts, shellInput);
    return null;
  }

  /**
   * Returns each space separated string in the shell input as an array of
   * strings.
   * 
   * @param shellInput The input string received from user
   * @return The shell input as an array of strings
   */
  protected String[] getArguments(String[] shellInput) {
    if (shellInput.length == 0) {
      return shellInput;
    }

    return shellInput[0].trim().split("\\s+");
  }

}
