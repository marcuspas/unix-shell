package output;

/**
 * This class is used to output messages to the shell. This class is the parent
 * of ErrorOutput class.
 * 
 * @author danny
 */
public class Output {
  StringBuilder output = new StringBuilder("");

  public void storeOutput(String output) {
    this.output.append(output);
  }
  
  /**
   * Get the stored output.
   * 
   * @return  return the stored output
   */
  public String releaseStoredOutput() {
    String resultOutput = this.output.toString();
    this.output.setLength(0);
    return resultOutput;
  }

  /**
   * Output to shell. Print a new line after.
   * 
   * @param str A string to be output on the shell.
   */
  public static void println(String str) {
    System.out.println(str);
  }

  /**
   * Output to shell without printing a new line after.
   * 
   * @param str A string to be output on the shell.
   */
  public static void print(String str) {
    System.out.print(str);
  }

}
