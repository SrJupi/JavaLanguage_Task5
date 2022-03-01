package n1e02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderList {

    // C:\Users\Lucas\javaMockDir

    public static void main(String[] args){
        Path myPath = Paths.get(receiveInput());
        boolean includeSubFolders = includeSubFolders();
        List <Path> myPaths = listFiles(myPath, includeSubFolders);
        if (myPaths != null){
            List <String> names = new ArrayList<>();
            for (Path p :
                    myPaths) {
                if(p.getNameCount() != myPath.getNameCount()){
                    String fileOrFolder = getFileOrFilder(p);
                    String lastTimeModified = getLastTimeModified(p);
                    names.add(p.subpath(myPath.getNameCount(), p.getNameCount()) + " - "
                            + fileOrFolder + " - "
                            + lastTimeModified);

                }
            }
            Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
            for (String file : names){
                System.out.println(file);
            }
        }
    }

    private static String getLastTimeModified(Path p) {
        String tmp;
        try{
            tmp = Files.getLastModifiedTime(p).toString();
        }catch (IOException e) {
            tmp = "";
        }
        return tmp;


    }

    private static String getFileOrFilder(Path p) {
        String fileOrFolder;
        if (Files.isDirectory(p)){
            fileOrFolder = "(D)";
        }else{
            fileOrFolder = "(F)";
        }
        return fileOrFolder;
    }

    public static boolean includeSubFolders (){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Include subfolders files? (Y/N)");
        String userInput = myObj.nextLine();  // Read user input
        return userInput.equalsIgnoreCase("y");
    }
    public static String receiveInput() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter path:");

        return myObj.nextLine();
    }

    public static List<Path> listFiles(Path path, boolean includeSubFolders){
        if (includeSubFolders){
            try (Stream<Path> filepath = Files.walk(path)){
                return filepath
                        .collect(Collectors.toList());
            }catch (IOException e) {
                System.out.println("Directory does not exist!");
            }
            return null;
        }else{
            try (Stream<Path> filepath = Files.walk(path, 1)){
                return filepath
                        .collect(Collectors.toList());
            }catch (IOException e) {
                System.out.println("Directory does not exist!");
            }
            return null;
        }

    }


}
