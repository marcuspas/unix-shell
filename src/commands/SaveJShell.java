package commands;

import filesystem.FileSystem;
import handling.StandardError;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class is responsible for saving the entire state of the current JShell
 * to a file.
 * 
 * @author Marcus
 */
public class SaveJShell extends Command {

  /**
   * Creates a new SaveJShell object with the commands valid arguments counts
   * and SaveJShell's specific documentation.
   * 
   * @param validArgumentCounts The array of valid argument counts
   */
  public SaveJShell(int[] validArgumentCounts) {
    super(validArgumentCounts);
    this.documentation = "saveJShell FileName"
        + "\n\tSaves the entire state of the program file FileName.";
  }

  /**
   * Loads all the contents of the current JShell to shellInput[0].
   * 
   * @param shellInput An array of arguments for the command.
   * @return A string with an error/success message.
   */
  public String execute(String[] shellInput) {

    String[] arguments = super.getArguments(shellInput);

    // Checks if the number of arguments is valid
    super.execute(arguments);
    if (!isCountValid) {
      return "Invalid Arguments";
    }

    StandardError errorHandler = new StandardError();

    if (!errorHandler.isValidFileName(shellInput[0])) {
      return "Invalid File Name";
    }

    try {
      // Creates the output streams for the new (or overwritten) file
      FileOutputStream fileOut;
      ObjectOutputStream objectOut;
      fileOut = new FileOutputStream("savedJShells/" + shellInput[0]);
      objectOut = new ObjectOutputStream(fileOut);
      // Writes the current JShell to the file
      objectOut.writeObject(FileSystem.getInstance());
      objectOut.close();
      fileOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Success";
  }

}
