package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import handling.InputParser;
import handling.StandardError;
import output.Output;


/**
 * This class handle commands with output and potential redirection.
 * 
 * @author danny
 *
 */
abstract class OutputCommand extends Command {

  /** either no redirection, or symbol will be ">" or ">>". */
  protected String redirectSymbol = "";
  /** path of the outFile to be redirected to. */
  protected String outfilePath = "";
  /** check if outFile is created or found. */
  protected boolean gotOutFile = false;
  /** the outFile to be redirected to. */
  protected File outFile = null;

  /**
   * Store correct argument count for a specific output command.
   * 
   * @param validArgumentCounts All possible correct arguments counts for that
   *        command
   */
  public OutputCommand(int[] validArgumentCounts) {
    // for command class to check argument count(used only once)
    super(validArgumentCounts);
  }

  /**
   * Get outFile if the command arguments has redirection.
   * 
   * @param arguments command arguments
   * @return String content of output
   */
  public String execute(String[] arguments) {
    super.execute(arguments);
    if (!isCountValid) {
      return null;
    }
    
    // create outFile and clear content if symbol is ">"
    if (hasRedirect()) {
      outFile = getOutfile();
      // create outFile if it's not yet created
      if (outFile == null) {
        // not valid file name
        return null;
      }
      if (redirectSymbol.equals(">")) {
        // clear outfile content
        outFile.clearTextContent();
      }
    }
    return null;
  }

  /**
   * Separate redirection from the command arguments.
   * 
   * @param shellInput command arguments
   * @return command arguments without redirection
   */
  protected String[] getArguments(String[] shellInput) {
    // Separated command arguments in strings
    String stringArg = getArgumentsString(shellInput);
    // if command arguments is empty, return an empty array
    if (stringArg == null || stringArg.equals("")) {
      return new String[0];
    }
    return stringArg.trim().split("\\s+");
  }

  /**
   * Separate redirection from the command arguments, and return it in a string
   * format.
   * 
   * @param shellInput command arguments
   * @return command arguments in string without redirection
   */
  protected String getArgumentsString(String[] shellInput) {
    // reset all value when calling a output command
    initialize();

    // check for []
    if (shellInput.length == 0) {
      return null;
    } else if (shellInput[0].lastIndexOf(">>") != -1) {
      // if redirection ">>" exist
      int symbolInd = shellInput[0].lastIndexOf(">>");

      redirectSymbol = ">>";
      outfilePath = shellInput[0].substring(symbolInd + 2).trim();
      // remove the redirection part and deal with it later
      shellInput[0] = shellInput[0].substring(0, symbolInd);
    } else if (shellInput[0].lastIndexOf(">") != -1) {
      // if redirection ">" exist
      int symbolInd = shellInput[0].lastIndexOf(">");

      redirectSymbol = ">";
      outfilePath = shellInput[0].substring(symbolInd + 1).trim();
      shellInput[0] = shellInput[0].substring(0, symbolInd);
    }
    // check for [""]

    if (shellInput.length == 1 && shellInput[0].equals("")) {
      return "";
    }

    return shellInput[0].trim();
  }

  /**
   * If an append or overwrite operation is given, get the file from the given
   * path to store the text in , if the file doesn't exist, create a new one.
   * 
   * @param path The path to the file to store text in.
   * @return Return the file to store text in.
   */
  private File getOutfile() {

    InputParser parser = FileSystem.getInstance().getParser();
    StandardError errorHandler = FileSystem.getInstance().getErrorHandler();

    // check for invalid path
    Directory parentDir = parser.parsePathToSecondLastDirectory(outfilePath);
    if (parentDir == null) {
      return null;
    }
    String fileName = parser.parsePathToName(outfilePath);

    // Outfile already exist
    if (parentDir.getSubFile(fileName) instanceof File) {
      return (File) parentDir.getSubFile(fileName);
    }

    // check for bad name or exist directory of the same name
    if (!(errorHandler.isValidFileName(fileName))
        || errorHandler.isDuplicateName(parentDir, fileName)) {
      return null;
    }

    // create the file as it doesn't exist.
    File outFile = new File(fileName);
    parentDir.addToDir(outFile);
    outFile.setParentDirectory(parentDir);

    gotOutFile = true;
    return outFile;
  }


  /**
   * return the outFile at its current state.
   * 
   * @return the outFile at its current state
   */
  public File getFile() {
    return outFile;
  }

  protected boolean hasRedirect() {
    return (redirectSymbol.equals(">") || redirectSymbol.equals(">>"));
  }


  /**
   * Determine to whether print output or store output in file.
   * 
   * @param output  the output of a command
   */
  protected void handleOutput(String output) {
    if (hasRedirect()) {
      redirect(output + "\n");
    } else {
      Output.println(output);
    }
  }


  /**
   * Determine to whether print output 
   * or store output in file with no new line.
   * 
   * @param output  the output of a command
   */
  protected void handleNoNewlineOutput(String output) {
    if (hasRedirect()) {
      redirect(output);
    } else {
      Output.print(output);
    }
  }

  /**
   * Redirect the output to the outFile.
   * 
   * @param output String output to be redirected
   */
  private void redirect(String output) {
    if (!hasRedirect()) {
      return;
    }
    outFile.addToTextContent(output);
  }

  /**
   * Reset relevant values for redirection.
   */
  private void initialize() {
    redirectSymbol = "";
    outfilePath = "";
    gotOutFile = false;
    outFile = null;
  }

}
