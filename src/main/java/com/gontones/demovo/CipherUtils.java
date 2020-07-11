package com.gontones.demovo;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

public class CipherUtils
{
    public static String base64encode(final String input) {
        final byte[] bytesEncoded = Base64.encodeBase64(input.getBytes());
        return new String(bytesEncoded);
    }

    public static String base64decode(final String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()));
        }
        catch (Exception e) {
            return new String(Base64.decodeBase64(input.replace("'", "").getBytes()));
        }
    }

    public static String shahash(final String st) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(st.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    public static String caesarDecode(final String input, final String key) {
        char sym = 'x';
        int x = 0;
        String original = "";
        while (x != input.length()) {
            sym = input.charAt(x);
            final long sdvig = Long.parseLong(key);
            final long caesar = sym - sdvig;
            original += (char)caesar;
            ++x;
        }
        return original;
    }

    public static String caesarEncode(final String input, final String key) {
        char sym = 'x';
        int x = 0;
        String gotv = "";
        while (x != input.length()) {
            sym = input.charAt(x);
            final long caesar = sym + Long.parseLong(key);
            gotv += (char)caesar;
            ++x;
        }
        return gotv;
    }

    public static String aescipher(final String key, final String gotv, final String salt) throws NoSuchAlgorithmException {
        final TextEncryptor encryptor = Encryptors.text(key, shahash(salt));
        return encryptor.encrypt(gotv);
    }

    public static String aesdecipher(final String key, final String decodeone, final String salt) throws NoSuchAlgorithmException {
        final TextEncryptor encryptor = Encryptors.text(key, shahash(salt));
        return encryptor.decrypt(decodeone);
    }
}
