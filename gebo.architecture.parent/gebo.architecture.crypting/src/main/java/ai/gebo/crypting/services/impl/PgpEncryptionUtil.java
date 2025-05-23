/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.services.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

// https://medium.com/@kthsingh.ms/encrypt-and-decrypt-anything-bytes-files-streams-with-pgp-using-bouncy-castle-and-java-ad335ae9f747
/**
 * Utility class for PGP encryption using Bouncy Castle.
 * It provides methods to encrypt data using public key encryption.
 *
 * AI generated comments
 */
class PgpEncryptionUtil {

    static {
        // Add Bouncy Castle provider to JVM if not already present
        if (Objects.isNull(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME))) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    // Algorithm used for compression, defaults to ZIP
    private int compressionAlgorithm = CompressionAlgorithmTags.ZIP;
    
    // Algorithm used for symmetric key encryption, defaults to AES-128
    private int symmetricKeyAlgorithm = SymmetricKeyAlgorithmTags.AES_128;
    
    // Determines if the output should be ASCII armored
    private boolean armor = true;
   
    // Determines if the output should have an integrity check
    private boolean withIntegrityCheck = true;
    
    // Buffer size for data processing
    private int bufferSize = 1 << 16;

    /**
     * Encrypts data from an InputStream and writes the encrypted data to an OutputStream.
     *
     * @param encryptOut the OutputStream where encrypted data will be written
     * @param clearIn the InputStream of the clear (unencrypted) data
     * @param length the length of the clear data
     * @param publicKeyIn the InputStream containing the public key for encryption
     * @throws IOException if an I/O error occurs
     * @throws PGPException if a PGP-related error occurs
     */
    public void encrypt(OutputStream encryptOut, InputStream clearIn, long length, InputStream publicKeyIn)
            throws IOException, PGPException {
        // Initialize compressed data generator with configured algorithm
        PGPCompressedDataGenerator compressedDataGenerator =
                new PGPCompressedDataGenerator(compressionAlgorithm);

        // Initialize encrypted data generator with configured algorithm and integrity check
        PGPEncryptedDataGenerator pgpEncryptedDataGenerator = new PGPEncryptedDataGenerator(
                new JcePGPDataEncryptorBuilder(symmetricKeyAlgorithm)
                        .setWithIntegrityPacket(withIntegrityCheck)
                        .setSecureRandom(new SecureRandom())
                        .setProvider(BouncyCastleProvider.PROVIDER_NAME)
        );

        // Adding public key for encryption method
        pgpEncryptedDataGenerator.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(
                CommonUtils.getPublicKey(publicKeyIn)));

        // Wrap the output stream in an ASCII Armor if specified
        if (armor) {
            encryptOut = new ArmoredOutputStream(encryptOut);
        }

        // Open output stream with the encrypted data generator
        OutputStream cipherOutStream = pgpEncryptedDataGenerator.open(encryptOut, new byte[bufferSize]);

        // Copy the clear data as literal data to the cipher output stream
        CommonUtils.copyAsLiteralData(compressedDataGenerator.open(cipherOutStream), clearIn, length, bufferSize);

        // Closing all output streams in sequence
        compressedDataGenerator.close();
        cipherOutStream.close();
        encryptOut.close();
    }

    /**
     * Encrypts clear data from a byte array using the public key, returning the encrypted data as a byte array.
     *
     * @param clearData the byte array of clear data to be encrypted
     * @param pubicKeyIn the InputStream containing the public key for encryption
     * @return a byte array containing the encrypted data
     * @throws PGPException if a PGP-related error occurs
     * @throws IOException if an I/O error occurs
     */
    public byte[] encrypt(byte[] clearData, InputStream pubicKeyIn) throws PGPException, IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(clearData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encrypt(outputStream, inputStream, clearData.length, pubicKeyIn);
        return outputStream.toByteArray();
    }

    /**
     * Encrypts data from an InputStream using the public key and returns an InputStream of the encrypted data.
     *
     * @param clearIn the InputStream of clear data to be encrypted
     * @param length the length of the clear data
     * @param publicKeyIn the InputStream containing the public key for encryption
     * @return an InputStream of the encrypted data
     * @throws IOException if an I/O error occurs
     * @throws PGPException if a PGP-related error occurs
     */
    public InputStream encrypt(InputStream clearIn, long length, InputStream publicKeyIn)
            throws IOException, PGPException {
        // Create a temporary file to store encrypted data
        File tempFile = File.createTempFile("pgp-", "-encrypted");

        // Encrypt and write to the temporary file
        encrypt(Files.newOutputStream(tempFile.toPath()), clearIn, length, publicKeyIn);

        // Return input stream from the temporary encrypted file
        return Files.newInputStream(tempFile.toPath());
    }

    /**
     * Encrypts clear data from a byte array using a public key string, returning the encrypted data as a byte array.
     *
     * @param clearData the byte array of clear data to be encrypted
     * @param publicKeyStr the string representing the public key for encryption
     * @return a byte array containing the encrypted data
     * @throws PGPException if a PGP-related error occurs
     * @throws IOException if an I/O error occurs
     */
    public byte[] encrypt(byte[] clearData, String publicKeyStr) throws PGPException, IOException {
        return encrypt(clearData, IOUtils.toInputStream(publicKeyStr, Charset.defaultCharset()));
    }

    /**
     * Encrypts data from an InputStream using a public key string and returns an InputStream of the encrypted data.
     *
     * @param clearIn the InputStream of clear data to be encrypted
     * @param length the length of the clear data
     * @param publicKeyStr the string representing the public key for encryption
     * @return an InputStream of the encrypted data
     * @throws IOException if an I/O error occurs
     * @throws PGPException if a PGP-related error occurs
     */
    public InputStream encrypt(InputStream clearIn, long length, String publicKeyStr) throws IOException, PGPException {
        return encrypt(clearIn, length, IOUtils.toInputStream(publicKeyStr, Charset.defaultCharset()));
    }
}