package commands;

import filesystem.File;
import filesystem.FileSystem;
import handling.InputParser;

/**
 * The Concatenate command class is a class responsible for displaying the
 * contents of a file in the shell.
 * 
 * @author Marcus
 *
 */
public class Concatenate extends OutputCommand {

  /**
   * Creates a new Concatenate object with the commands valid arguments counts
   * and Concatenates specific documentation.
   * 
   * @param validArgumentCounts The array of valid argument counts
   */
  public Concatenate(int[] validArgumentCounts) {

    super(validArgumentCounts);
    this.documentation = "cat FILE1 [FILE2...]"
        + "\n\tDisplay the contents of FILE1 and other files (i.e. File2 ....) "
        + "\n\tconcatenated in the shell.";

  }

  /**
   * If the size of the input is valid, and shell input consists of valid files,
   * prints the file name and the contents of the file into the shell.
   * 
   * @param shellInput An array of arguments for the command.
   * @return A string with the files content or an error message.
   */
  @Override
  public String execute(String[] shellInput) {

    InputParser parser = FileSystem.getInstance().getParser();
    StringBuilder output = new StringBuilder();

    File file = null;
    String[] arguments = super.getArguments(shellInput);

    // Checks if the number of arguments is valid
    super.execute(arguments);
    if (!isCountValid || (hasRedirect() && outFile == null)) {
      return "Invalid arguments";
    }
    int currIteration = 1;

    for (String filePath : arguments) {
      file = parser.parsePathToFile(filePath);
      // if file doesn't exist, do nothing
      if (file == null) {
        output.append("Invalid file\n\n");
        return output.toString();
      }
      // add three lines between file contents
      if (currIteration > 1) {
        output.append("\n\n");
        handleNoNewlineOutput("\n\n");
      }
      // Prints each file name and its contents
      output.append(file.getName() + ":" + "\n" + file + "\n");
      handleNoNewlineOutput(file.getName() + ":" + "\n" + file + "\n");
      currIteration++;
    }
    return output.toString();

  }

}
