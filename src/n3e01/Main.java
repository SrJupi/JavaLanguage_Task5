package n3e01;


import n1e05.Person;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import static n1e04.FolderList.readReportFile;
import static n1e05.Main.readObjectFromFile;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, InvalidParameterSpecException, InvalidKeyException {

        //Create permanent key and iv based on a string password
        UnsafeDataStorage uds = new UnsafeDataStorage("12345678");


        // encrypt and decrypt txt file from n2e01
        System.out.println("Txt files: ");
        encryptDecryptTxt(uds);

        System.out.println("\n\n\n");

        // encrypt and decrypt obj Person file from n1e05
        System.out.println("Person object files: ");
        encryptDecryptObj(uds);



    }

    private static void encryptDecryptObj(UnsafeDataStorage uds) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException {
        Path inputObjPath = Paths.get("C:\\Users\\Lucas\\Desktop\\PersonFolder\\person.ser");
        Path encryptedObjPath = Paths.get("C:\\Users\\Lucas\\Desktop\\PersonFolder\\encrypted.ser");
        Path decryptedObjPath = Paths.get("C:\\Users\\Lucas\\Desktop\\PersonFolder\\decrypted.ser");
        EncryptDecrypt.encryptDecrypt(uds, Cipher.ENCRYPT_MODE, inputObjPath, encryptedObjPath);
        EncryptDecrypt.encryptDecrypt(uds, Cipher.DECRYPT_MODE, encryptedObjPath, decryptedObjPath);

        printPerson(inputObjPath, encryptedObjPath, decryptedObjPath);
    }

    private static void printPerson(Path inputObjPath, Path encryptedObjPath, Path decryptedObjPath) {
        //try to read encrypted person
        try{
            Person readPerson = readObjectFromFile(inputObjPath);
            if(readPerson != null){
                System.out.println(readPerson.toString());
            }

        }catch(Exception e){
            System.out.println("Could not read normal file.");
        }

        //try to read encrypted person
        try{
            Person readPerson = readObjectFromFile(encryptedObjPath);
            if(readPerson != null){
                System.out.println(readPerson.toString());
            }

        }catch(Exception e){
            System.out.println("Could not read encrypted file.");
        }

        //try to read decrypted person
        try{
            Person readPerson = readObjectFromFile(decryptedObjPath);
            if(readPerson != null){
                System.out.println(readPerson.toString());
            }

        }catch(Exception e){
            System.out.println("Could not read decrypted file.");
        }

    }

    private static void encryptDecryptTxt(UnsafeDataStorage uds) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException {
        Path inputPath = Paths.get("C:\\Users\\Lucas\\Desktop\\output\\report.txt");
        Path encryptedPath = Paths.get("C:\\Users\\Lucas\\Desktop\\output\\encrypted.txt");
        Path decryptedPath = Paths.get("C:\\Users\\Lucas\\Desktop\\output\\decrypted.txt");
        EncryptDecrypt.encryptDecrypt(uds, Cipher.ENCRYPT_MODE, inputPath, encryptedPath);
        EncryptDecrypt.encryptDecrypt(uds, Cipher.DECRYPT_MODE, encryptedPath, decryptedPath);

        printTxt(inputPath, encryptedPath, decryptedPath);
    }

    private static void printTxt(Path inputPath, Path encryptedPath, Path decryptedPath) {
        readReportFile(inputPath);
        readReportFile(encryptedPath);
        readReportFile(decryptedPath);

    }
}
