package n3e01;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

public class EncryptDecrypt {

    public static void encryptDecrypt (UnsafeDataStorage dataStorage, int cipherMode,
                                       Path inputPath, Path outputPath)
            throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, InvalidParameterSpecException {
        //Get streams from paths
        InputStream inputStream = Files.newInputStream(inputPath);
        OutputStream outputStream = Files.newOutputStream(outputPath);

        //create cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        //check ciphermode
        if(cipherMode == Cipher.ENCRYPT_MODE){
            cipher.init(Cipher.ENCRYPT_MODE, dataStorage.getKey(), new IvParameterSpec(dataStorage.getIv()));
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            writeFile(cipherInputStream, outputStream);
        }else if (cipherMode == Cipher.DECRYPT_MODE){
            cipher.init(Cipher.DECRYPT_MODE, dataStorage.getKey(), new IvParameterSpec(dataStorage.getIv()));
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            writeFile(inputStream, cipherOutputStream);
        }
    }

    private static void writeFile (InputStream inputStream, OutputStream outputStream)
            throws IOException {
        byte [] buffer = new byte [64];
        int numberOfBytesRead;
        while ((numberOfBytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, numberOfBytesRead);
        }
        inputStream.close();
        outputStream.close();
    }
}
