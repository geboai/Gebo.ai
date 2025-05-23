/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RSAUtils is a utility class that provides methods to generate RSA key pairs
 * and to encrypt and decrypt data using RSA algorithm.
 * <p>
 * It supports multiple RSA key lengths and uses the BouncyCastle security provider.
 * </p>
 * AI generated comments
 */
public class RSAUtils {

    // Constants for symmetric encryption algorithm and provider
    private static final String SIMMETRIC_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String KEYPAIR_PROVIDER = "BC";
    private static final String KEYPAIR_ALGO = "RSA";
    private static final RSAKeylenghts DEFAULT_KEYLENGTH = RSAKeylenghts.KEY2048;

    /**
     * Enum for defining available RSA key lengths.
     */
    public enum RSAKeylenghts {
        KEY1024(1024), KEY2048(2048), KEY3072(3072), KEY4096(4096);

        private final int length;

        private RSAKeylenghts(int l) {
            length = l;
        }

        /**
         * Returns the length of the RSA key.
         * 
         * @return the key length
         */
        public int getLength() {
            return length;
        }
    }

    // Fields for key length and chunk sizes for encryption/decryption
    private final int KEYLENGTH;
    private final int ENCRYPT_CHUNKSIZE;
    private final int DECRYPT_CHUNKSIZE;

    // Static initializer block to add BouncyCastle provider if it's not already present
    static {
        if (Security.getProvider(KEYPAIR_PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    // A default instance of RSAUtils with default key length
    public static final RSAUtils defaultInstance = new RSAUtils();

    /**
     * Constructs an RSAUtils instance with the default key length.
     */
    public RSAUtils() {
        this(DEFAULT_KEYLENGTH);
    }

    /**
     * Constructs an RSAUtils instance with a specified key length.
     * 
     * @param keyLength the RSA key length to be used
     */
    public RSAUtils(RSAKeylenghts keyLength) {
        this.KEYLENGTH = keyLength.getLength();
        ENCRYPT_CHUNKSIZE = (KEYLENGTH / 8) - 11;
        DECRYPT_CHUNKSIZE = ENCRYPT_CHUNKSIZE + 11;
    }

    /**
     * Generates a new RSA key pair.
     * 
     * @return the generated key pair
     * @throws NoSuchAlgorithmException   if the RSA algorithm is not available
     * @throws NoSuchProviderException    if the BouncyCastle provider is not available
     */
    public KeyPair keyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEYPAIR_ALGO, KEYPAIR_PROVIDER);
        keyPairGenerator.initialize(KEYLENGTH, new SecureRandom());
        java.security.KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // A KeyFactory is used to convert encoded keys to their actual Java classes
        KeyFactory ecKeyFac = KeyFactory.getInstance(KEYPAIR_ALGO, KEYPAIR_PROVIDER);
        return keyPair;
    }

    /**
     * Encrypts the given data using the provided PublicKey.
     * 
     * @param original the data to be encrypted
     * @param key the public key used for encryption
     * @return the encrypted byte array
     * @throws NoSuchAlgorithmException   if the RSA algorithm is not available
     * @throws NoSuchPaddingException     if padding is not available for the algorithm
     * @throws InvalidKeyException        if the provided key is invalid
     * @throws IllegalBlockSizeException  if the data is not a multiple of the block size
     * @throws BadPaddingException        if the padding of data does not match
     */
    public byte[] encrypt(byte[] original, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance(SIMMETRIC_ALGORITHM);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        int size = encryptCipher.getOutputSize(original.length);
        int encSize = (int) (Math.ceil(((double) original.length) / ((double) ENCRYPT_CHUNKSIZE)) * ((double) size));
        int idx = 0;
        ByteBuffer buf = ByteBuffer.allocate(encSize);
        while (idx < original.length) {
            int len = Math.min(original.length - idx, ENCRYPT_CHUNKSIZE);
            byte[] encChunk = encryptCipher.doFinal(original, idx, len);
            buf.put(encChunk);
            idx += len;
        }
        byte[] encryptedMessageBytes = buf.array();
        return encryptedMessageBytes;
    }

    /**
     * Decrypts the given encrypted byte array using the provided PrivateKey.
     * 
     * @param encryptedMessageBytes the byte array to be decrypted
     * @param key the private key used for decryption
     * @return the decrypted byte array
     * @throws IllegalBlockSizeException  if the data is not a multiple of the block size
     * @throws BadPaddingException        if the padding of data does not match
     * @throws NoSuchAlgorithmException   if the RSA algorithm is not available
     * @throws NoSuchPaddingException     if padding is not available for the algorithm
     * @throws InvalidKeyException        if the provided key is invalid
     * @throws IOException                if an I/O error occurs
     */
    public byte[] decrypt(byte encryptedMessageBytes[], Key key) throws IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
        Cipher rsaCipher = Cipher.getInstance(SIMMETRIC_ALGORITHM);
        rsaCipher.init(Cipher.DECRYPT_MODE, key);
        int size = rsaCipher.getOutputSize(encryptedMessageBytes.length);

        int idx = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (idx < encryptedMessageBytes.length) {
            int len = Math.min(encryptedMessageBytes.length - idx, DECRYPT_CHUNKSIZE);
            byte[] chunk = rsaCipher.doFinal(encryptedMessageBytes, idx, len);
            bos.write(chunk);
            idx += len;
        }
        // Fully decrypted data
        byte[] decryptedData = bos.toByteArray();
        return decryptedData;
    }

}