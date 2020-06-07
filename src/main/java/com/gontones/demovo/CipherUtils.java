package com.gontones.demovo;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import java.math.BigInteger;
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
            return new String(Base64.decodeBase64(input));
        }
        catch (Exception e) {
            return new String(Base64.decodeBase64(input.replace("'", "")));
        }
    }

    public static String shahash(final String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final BigInteger bigInt = new BigInteger(1, digest);
        String shaHex;
        for (shaHex = bigInt.toString(16); shaHex.length() < 32; shaHex = "0" + shaHex) {}
        return shaHex;
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

    public static String aescipher(final String key, final String gotv, final String salt) {
        final TextEncryptor encryptor = Encryptors.text(key, shahash(salt));
        return encryptor.encrypt(gotv);
    }

    public static String aesdecipher(final String key, final String decodeone, final String salt) {
        final TextEncryptor encryptor = Encryptors.text(key, shahash(salt));
        return encryptor.decrypt(decodeone);
    }
}