package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileObj;
import filesystem.FileSystem;
import handling.InputParser;
import java.util.ArrayList;
import output.ErrorOutput;

/**
 * This class is responsible for copying a file or directory in the file system
 * given the path of the destination directory to be copied to.
 * 
 * @author Brandon
 */
public class Copy extends Command {

  /**
   * Setup valid argument counts for Copy and its documentation.
   * 
   * @param validArgumentCounts Stores All the valid number of Argument counts
   *        for Copy
   */
  public Copy(int[] validArgumentCounts) {
    super(validArgumentCounts);
    this.documentation = "mv OLDPATH NEWPATH"
        + "\n\tThis is a new command in Assignment2B Like mv, but don’t remove "
        + "\n\tOLDPATH. If OLDPATHis a directory, recursively copy the "
        + "\n\tcontents.";
  }

  /**
   * Makes a new file given an existing file in the file system or a new
   * directory given an existing directory in the file system. If a directory is
   * being replicated, then recursively copy file and directories under the
   * directory being replicated is also created.
   * 
   * @param objToCopy The existing file or directory to be replicated
   */
  public FileObj replicateFileObj(FileObj objToCopy) {
    FileObj result;
    if (objToCopy instanceof File) {
      File fileToCopy = (File) objToCopy;
      File replicaFile = new File(fileToCopy.getName());
      replicaFile.addToTextContent(fileToCopy.toString());

      result = (FileObj) replicaFile;
    } else {
      Directory dirToCopy = (Directory) objToCopy;
      Directory replicaDir = new Directory(dirToCopy.getName());
      for (String i : dirToCopy.subFileNameList()) {
        FileObj replicaSubFile = dirToCopy.getSubFile(i);
        replicaDir.addToDir(replicateFileObj(replicaSubFile));
      }
      result = (FileObj) replicaDir;
    }
    return result;
  }

  // private ArrayList<Directory> getParentsOfWorkingDirectory() {
  // // helper function to be implemented
  // ArrayList<Directory> workingDirectoryParents = new ArrayList<Directory>();
  // Directory tempWorkingDir = FileSystem.getInstance().getWorkingDirectory();
  // Directory rootDir = FileSystem.getInstance().getRootDirectory();
  // while (tempWorkingDir != rootDir) {
  // workingDirectoryParents.add(tempWorkingDir);
  // tempWorkingDir = tempWorkingDir.getParentDirectory();
  // }
  // workingDirectoryParents.add(rootDir);
  // return workingDirectoryParents;
  // }

  private ArrayList<Directory> getParentsOfDirectory(Directory dir) {
    ArrayList<Directory> directoryParents = new ArrayList<Directory>();
    Directory tempDir = dir;
    Directory rootDir = FileSystem.getInstance().getRootDirectory();
    while (tempDir != rootDir) {
      directoryParents.add(tempDir);
      tempDir = tempDir.getParentDirectory();
    }
    directoryParents.add(rootDir);
    return directoryParents;
  }

  /**
   * Check if cp command is being used correctly (correct number of arguments,
   * copying an existing file/directory, etc.). If cp is being used correctly,
   * then create a copy of the target file/directory in the designated path.
   * 
   * @param shellInput Arrays of command arguments
   */
  public FileObj execute(String[] shellInput) {
    // Check if DIR given is in Current Working Directory of one of it's parents

    InputParser parser = FileSystem.getInstance().getParser();
    String[] arguments = super.getArguments(shellInput);
    super.execute(arguments);
    if (!isCountValid) {
      return null;
    }

    // get target item to copy
    FileObj itemToCopy = parser.parsePathToFileObj(arguments[0]);
    // get destination to copy to
    Directory destination = parser.parsePathToDirectory(arguments[1]);
    // if the item or destination doesn't exist, return.
    if (destination == null || itemToCopy == null) {
      return null;
    } else {
      // ArrayList<Directory> parentsOfWorkingDir =
      // getParentsOfWorkingDirectory();
      // for (int i = 0; i < parentsOfWorkingDir.size(); i++) {
      // if (itemToCopy == parentsOfWorkingDir.get(i)) {
      // ErrorOutput.println("Cannot copy a parent of the "
      // + "working directory");
      // return null;
      // }
      // }

      // NEW: You cannot move or copy a parent directory into any of its
      // children directories
      ArrayList<Directory> parentsOfDest = getParentsOfDirectory(destination);
      for (int i = 0; i < parentsOfDest.size(); i++) {
        if (itemToCopy == parentsOfDest.get(i)) {
          ErrorOutput.println("Cannot copy a parent directory to into "
              + "any of it's children");
          return null;
        }
      }

      FileObj copyOfItem = replicateFileObj(itemToCopy);
      destination.addToDir(copyOfItem);
      copyOfItem.setParentDirectory(destination);
      return copyOfItem;
    }
  }
}
