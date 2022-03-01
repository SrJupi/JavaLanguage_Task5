package n1e05;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String userHomeDir = System.getProperty("user.home");
        Path personsFolder = Paths.get(userHomeDir + "\\Desktop\\PersonFolder");
        try {
            Files.createDirectories(personsFolder);
            Path saveFile = Paths.get(personsFolder + "\\person.ser");

            System.out.println("Try to read Person object at " + saveFile);
            Person readPerson = readObjectFromFile(saveFile);
            if (readPerson != null){
                System.out.println(readPerson.toString());
            }

            System.out.println("Create new random Person: ");
            Person newPerson = createNewPerson();
            System.out.println(newPerson.toString());
            writeObjectToFile(newPerson, saveFile);

            System.out.println("Try to read Person object at " + saveFile);
            readPerson = readObjectFromFile(saveFile);
            if (readPerson != null){
                System.out.println(readPerson.toString());
            }

        } catch (IOException e) {
            System.out.println("Cannot create folder");
        }

    }

    private static Person createNewPerson() {
/*        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("New person name:");
        String name = myObj.nextLine();
        System.out.println("New person age:");
        int age = Integer.parseInt(myObj.nextLine());
        System.out.println("New person job:");
        String job = myObj.nextLine();
        System.out.println("New person salary:");
        int salary = Integer.parseInt(myObj.nextLine());*/


        return new Person();
    }

    public static Person readObjectFromFile(Path pathToRead) {

        if(Files.exists(pathToRead)){
            try {

                InputStream fileIn = Files.newInputStream(pathToRead);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Object returnPerson = objectIn.readObject();
                objectIn.close();
                System.out.println("The Object was successfully read at: " + pathToRead);
                return (Person) returnPerson;

            } catch (Exception ex) {
                System.out.println("Object could not be read: "+ pathToRead);
                return null;
            }
        }else{
            System.out.println("Save file does not exist");
            return null;
        }

    }

    public static void writeObjectToFile(Object serObj, Path pathToWrite) {

        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(pathToWrite));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object was successfully written to " + pathToWrite);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean writeOrRead() {
        String userInput = "";
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while(!userInput.equalsIgnoreCase("C") &&
                !userInput.equalsIgnoreCase("R")) {

            System.out.println("Press C and Enter to create a new person and serialize it"+
                    "\nPress R and Enter to read a serialized person");
            userInput = myObj.nextLine();
        }
        return userInput.equalsIgnoreCase("C");
    }
}
