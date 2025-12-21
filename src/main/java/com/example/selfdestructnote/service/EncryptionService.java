package com.example.selfdestructnote.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${custom.security.encryption-key}")
    private String secretKey;

    @Value("${security.encryption.algorithm}")
    private String ALGORITHM;

    public String encrypt(String value) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes(StandardCharsets.UTF_8), this.ALGORITHM);
            Cipher cipher = Cipher.getInstance(this.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            // Превращаем байты в строку Base64, чтобы удобно хранить в БД
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public String decrypt(String encryptedValue) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes(StandardCharsets.UTF_8), this.ALGORITHM);
            Cipher cipher = Cipher.getInstance(this.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] originalBytes = cipher.doFinal(decodedBytes);

            return new String(originalBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}
