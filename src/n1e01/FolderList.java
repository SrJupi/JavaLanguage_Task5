package n1e01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderList {

    // C:\Users\Lucas\javaMockDir

    public static void main(String[] args) {
        Path myPath = Paths.get(args[0]);
        List <Path> myPaths = listFiles(myPath);
        if (myPaths != null){
            List <String> names = new ArrayList<>();
            for (Path p :
                    myPaths) {
                if(p.getNameCount() != myPath.getNameCount()){
                    names.add(p.getName(p.getNameCount()-1).toString());
                }
            }
            Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
            for (String file : names){
                System.out.println(file);
            }
        }
    }

    public static List<Path> listFiles(Path path){
        try (Stream<Path> filepath = Files.walk(path, 1)){
            return filepath
                    .collect(Collectors.toList());
        }catch (IOException e) {
            System.out.println("Directory does not exist!");
        }
        return null;
    }


}
