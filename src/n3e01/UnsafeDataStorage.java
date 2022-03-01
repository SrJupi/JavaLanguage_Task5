package n3e01;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

class UnsafeDataStorage {
    private SecretKey key;
    private byte[] iv;


    public UnsafeDataStorage(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException {
        this.key = generateKey(password);
        this.iv = generateIv(this.key);
    }

    private byte[] generateIv(SecretKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        return iv;
    }

    public static SecretKeySpec generateKey(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);
        char[] charPassword = password.toCharArray();

        KeySpec keySpec = new PBEKeySpec(charPassword, salt, 1000, 256);
        SecretKey secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(keySpec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        return secretKeySpec;

    }

    public SecretKey getKey() {
        return key;
    }

    public byte[] getIv() {
        return iv;
    }

}
