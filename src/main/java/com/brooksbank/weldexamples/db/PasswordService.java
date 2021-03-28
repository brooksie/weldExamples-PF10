/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

public final class PasswordService {

    // The higher the number of ITERATIONS the more expensive computing the 
    // hash is for us and also for an attacker.
    private static final int ITERATIONS = 2048;
    private static final int SALT_LEN_BYTES = 32;
    private static final int DESIRED_KEY_LEN_BITS = 256;
    private static final String SALT_ALGORITHM = "SHA1PRNG";
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String SEPARATOR = ":";
    private static final int SALT_POSITION = 2;
    private static final int HASH_POSITION = 3;

    /**
     * *
     * Computes a salted PBKDF2 hash of given plaintext password suitable for
     * storing in a database. Empty passwords are not supported.
     *
     * @param password
     * @return String hashed to be stored in the database
     * @throws java.lang.Exception
     */
    public static String getSaltedHash(char[] password) throws Exception {

        byte[] salt = SecureRandom.getInstance(SALT_ALGORITHM).generateSeed(SALT_LEN_BYTES);
        // store the salt with the password
        // format must be...
        // algorithm : iterations : base64(salt) : base64(hash)
        return HASH_ALGORITHM + SEPARATOR 
                + ITERATIONS + SEPARATOR  
                + Base64.encodeBase64String(salt) + SEPARATOR 
                + hash(password, salt);
    }

    /**
     * Checks whether given plaintext password corresponds to a stored salted
     * hash of the password.
     *
     * @param password
     * @param storedHash
     * @return true if password is correct; false otherwise
     * @throws java.lang.Exception
     */
    public static boolean check(char[] password, String storedHash) throws Exception {
        String[] saltAndHash = storedHash.split("\\:");
        if (saltAndHash.length != 4) {
            throw new IllegalStateException(
                    "The stored password must have the form 'algoritm:iterations:salt:hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[SALT_POSITION]));
        return hashOfInput.equals(saltAndHash[HASH_POSITION]);
    }

    /**
     * using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt cf.
     * http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
     *
     * @param password
     * @param salt
     * @return
     * @throws Exception
     */
    private static String hash(char[] password, byte[] salt) throws Exception {
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance(HASH_ALGORITHM);
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password, salt, ITERATIONS, DESIRED_KEY_LEN_BITS));
        return Base64.encodeBase64String(key.getEncoded());
    }

// OLD VERSION
    
//    // The higher the number of ITERATIONS the more expensive computing the 
//    // hash is for us and also for an attacker.
//    private static final int ITERATIONS = 20 * 1000;
//    private static final int SALT_LEN_BYTES = 32;
//    private static final int DESIRED_KEY_LEN_BITS = 256;
//    private static final String SALT_ALGORITHM = "SHA1PRNG";
//    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
//
//    /**
//     * *
//     * Computes a salted PBKDF2 hash of given plaintext password suitable for
//     * storing in a database. Empty passwords are not supported.
//     *
//     * @param password
//     * @return String hashed to be stored in the database
//     * @throws java.lang.Exception
//     */
//    public static String getSaltedHash(char[] password) throws Exception {
//
//        byte[] salt = SecureRandom.getInstance(SALT_ALGORITHM).generateSeed(SALT_LEN_BYTES);
//        // store the salt with the password
//        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
//    }
//
//    /**
//     * Checks whether given plaintext password corresponds to a stored salted
//     * hash of the password.
//     *
//     * @param password
//     * @param storedHash
//     * @return true if password is correct; false otherwise
//     * @throws java.lang.Exception
//     */
//    public static boolean check(char[] password, String storedHash) throws Exception {
//        String[] saltAndHash = storedHash.split("\\$");
//        if (saltAndHash.length != 2) {
//            throw new IllegalStateException(
//                    "The stored password must have the form 'salt$hash'");
//        }
//        String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]));
//        return hashOfInput.equals(saltAndHash[1]);
//    }
//
//    /**
//     * using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt cf.
//     * http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
//     *
//     * @param password
//     * @param salt
//     * @return
//     * @throws Exception
//     */
//    private static String hash(char[] password, byte[] salt) throws Exception {
//        if (password == null || password.length == 0) {
//            throw new IllegalArgumentException("Empty passwords are not supported.");
//        }
//        SecretKeyFactory f = SecretKeyFactory.getInstance(HASH_ALGORITHM);
//        SecretKey key = f.generateSecret(new PBEKeySpec(
//                password, salt, ITERATIONS, DESIRED_KEY_LEN_BITS));
//        return Base64.encodeBase64String(key.getEncoded());
//    }

}
