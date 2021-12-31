package commands;

import filesystem.FileSystem;
import handling.InputParser;

/**
 * The Manual class is responsible for displaying the documentation of a
 * command.
 * 
 * @author Marcus
 *
 */
public class Manual extends OutputCommand {

  /**
   * Creates a new Manual object with the commands valid arguments counts and
   * Manuals specific documentation.
   * 
   * @param validArgumentCounts The array of valid argument counts
   */
  public Manual(int[] validArgumentCounts) {

    super(validArgumentCounts);
    this.documentation = "man CMD" + "\n\tPrint documentation for CMD(s)";

  }


  /**
   * If the input has at least one argument and the input consists of only valid
   * commands, print the documentation for each command.
   * 
   * @param shellInput An array of arguments for the command.
   * @return A string with the commands documentation, or an error message.
   */
  @Override
  public String execute(String[] shellInput) {

    String[] arguments = super.getArguments(shellInput);
    InputParser parser = FileSystem.getInstance().getParser();
    StringBuilder output = new StringBuilder();

    // Checks if the number of arguments is valid
    super.execute(arguments);

    if (!isCountValid || (hasRedirect() && outFile == null)) {
      return "Invalid arguments";
    }

    Command command = null;

    int currIteration = 1;
    for (String commandName : arguments) {
      command = parser.parseCommandArgumentToCommand(commandName);
      // If command doesn't exist, do nothing
      if (command == null) {
        return "Invalid command";
      }
      // add three lines between documentation contents
      if (currIteration > 1) {
        output.append("\n\n\n");
      }
      // Prints the documentation of each command
      output.append(command + "\n");
      currIteration++;
    }
    handleOutput(output.toString());
    return output.toString();
  }

}
