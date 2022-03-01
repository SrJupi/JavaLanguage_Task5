package n2e01;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderList {

    // C:\Users\Lucas\javaMockDir

    public static void main(String[] args){
        Path userHomeDir = Paths.get(System.getProperty("user.home"));
        Properties basicConfig = getConfigurations (userHomeDir);
        Path originalPath = userHomeDir.resolve(basicConfig.getProperty("readPath"));
        List <Path> allPaths = listFiles(originalPath, includeSubFolders());
        resultOutput (allPaths, originalPath, basicConfig, userHomeDir);

    }

    private static Properties getConfigurations(Path user) {
        Path configPath = user.resolve("Desktop\\folderList.properties");
        Properties configProprieties = new Properties();

        try {
            configProprieties.load(Files.newInputStream(configPath));

        } catch (IOException e) {
            System.out.println("Configuration file not found.");
        }
        return configProprieties;
    }

    private static void resultOutput(List<Path> allPaths, Path originalPath,
                                     Properties basicConfig, Path userHomeDir) {
        if (allPaths != null){
            List <String> names = new ArrayList<>();
            for (Path p :
                    allPaths) {
                if(p.getNameCount() != originalPath.getNameCount()){
                    String fileOrFolder = getFileOrFolder(p);
                    String lastTimeModified = getLastTimeModified(p);
                    names.add(p.subpath(originalPath.getNameCount(), p.getNameCount()) + " - "
                            + fileOrFolder + " - "
                            + lastTimeModified);
                }
            }
            Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
            saveToTxt(names, originalPath, basicConfig, userHomeDir);

        }
    }
    private static void saveToTxt(List<String> names, Path originalPath,
                                  Properties basicConfig, Path userHomeDir) {
        Path outputDir = userHomeDir.resolve(basicConfig.getProperty("savePath"));

        Path report = outputDir.resolve(basicConfig.getProperty("fileName"));

        try {
            Files.createDirectories(outputDir);
            try {
                //create file
                Files.writeString(report, "Report Created at: " + new Timestamp(new Date().getTime()) +
                        "\nFolder searched: " + originalPath + "\n");
                //write to file
                for (String filepath :
                        names) {
                    Files.writeString(report, filepath + "\n",
                            StandardCharsets.UTF_8,
                            StandardOpenOption.APPEND);
                }
                System.out.println("Report created: " + report);
            } catch (IOException e) {
                System.out.println("Report could not be created");
            }
        } catch (IOException e) {
            System.out.println("Folder could not be created!");
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
    private static String getFileOrFolder(Path p) {
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
    public static String receivePathInput() {
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
        }else{
            try (Stream<Path> filepath = Files.walk(path, 1)){
                return filepath
                        .collect(Collectors.toList());
            }catch (IOException e) {
                System.out.println("Directory does not exist!");
            }
        }
        return null;

    }


}
