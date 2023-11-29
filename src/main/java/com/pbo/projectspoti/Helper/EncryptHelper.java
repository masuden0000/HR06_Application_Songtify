/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.projectspoti.Helper;

import com.pbo.projectspoti.Model.User;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author User
 */
public class EncryptHelper {
    private static final String SECRET_KEY = "YourSecretKey123";
    
    public static void saveEncryptedUser(User user, String filePath) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Enkripsi data pengguna
            byte[] encryptedUsername = cipher.doFinal(user.getUsername().getBytes());
            byte[] encryptedPassword = cipher.doFinal(user.getPassword().getBytes());
            byte[] encryptedUserid = cipher.doFinal(user.getUserId().getBytes());
            byte[] encrytedFullname = cipher.doFinal(user.getFullName().getBytes());

            // Simpan data enkripsi ke file
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                outputStream.writeObject(Base64.getEncoder().encodeToString(encryptedUsername));
                outputStream.writeObject(Base64.getEncoder().encodeToString(encryptedPassword));
                outputStream.writeObject(Base64.getEncoder().encodeToString(encryptedUserid));
                outputStream.writeObject(Base64.getEncoder().encodeToString(encrytedFullname));
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public static User loadEncryptedUser(String filePath) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Baca data enkripsi dari file
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                String encodedUsername = (String) inputStream.readObject();
                String encodedPassword = (String) inputStream.readObject();
                String encodedUserid = (String) inputStream.readObject();
                String encodedFullname = (String) inputStream.readObject();

                // Dekripsi data pengguna
                String decryptedUsername = new String(cipher.doFinal(Base64.getDecoder().decode(encodedUsername)));
                String decryptedPassword = new String(cipher.doFinal(Base64.getDecoder().decode(encodedPassword)));
                String decryptedUserid = new String(cipher.doFinal(Base64.getDecoder().decode(encodedUserid)));
                String decryptedFullname = new String(cipher.doFinal(Base64.getDecoder().decode(encodedFullname)));

                return new User(decryptedUserid, decryptedUsername, decryptedFullname, decryptedPassword);
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException e) {
            return null;
        }
    }
    
    public static void deleteLogin(String filePath) {
        // Hapus file saat logout
        if (deleteFile(filePath)) {
            System.out.println("Logout berhasil.");
        } else {
            System.out.println("Gagal hapus");
        }
    }

    private static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }
}
