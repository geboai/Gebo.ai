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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.Security;
import java.util.Iterator;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

/**
 * Utility class for PGP decryption operations.
 * AI generated comments
 */
class PgpDecryptionUtil {

    static {
        // Add Bouncy Castle security provider if not already added
        if (Objects.isNull(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME))) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private final char[] passCode; // Passphrase for the private key
    private final PGPSecretKeyRingCollection pgpSecretKeyRingCollection; // Collection of secret keys

    /**
     * Constructs a PgpDecryptionUtil with an InputStream for the private key and a passphrase.
     *
     * @param privateKeyIn Input stream containing the PGP private key data.
     * @param passCode     Passphrase for decrypting the private key.
     * @throws IOException  if an I/O error occurs.
     * @throws PGPException if a PGP error occurs.
     */
    public PgpDecryptionUtil(InputStream privateKeyIn, String passCode) throws IOException, PGPException {
        this.passCode = passCode.toCharArray();
        this.pgpSecretKeyRingCollection = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(privateKeyIn),
                new JcaKeyFingerprintCalculator());
    }

    /**
     * Constructs a PgpDecryptionUtil with a String for the private key and a passphrase.
     *
     * @param privateKeyStr String containing the PGP private key data.
     * @param passCode      Passphrase for decrypting the private key.
     * @throws IOException  if an I/O error occurs.
     * @throws PGPException if a PGP error occurs.
     */
    public PgpDecryptionUtil(String privateKeyStr, String passCode) throws IOException, PGPException {
        this(IOUtils.toInputStream(privateKeyStr, Charset.defaultCharset()), passCode);
    }

    /**
     * Finds and returns the secret key for the given key ID.
     *
     * @param keyID The ID of the key to be found.
     * @return The PGPPrivateKey if found, null otherwise.
     * @throws PGPException if a PGP error occurs.
     */
    private PGPPrivateKey findSecretKey(long keyID) throws PGPException {
        PGPSecretKey pgpSecretKey = pgpSecretKeyRingCollection.getSecretKey(keyID);
        return pgpSecretKey == null ? null : pgpSecretKey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder()
                .setProvider(BouncyCastleProvider.PROVIDER_NAME).build(passCode));
    }

    /**
     * Decrypts the provided encrypted input stream to the specified output stream.
     *
     * @param encryptedIn InputStream containing encrypted data.
     * @param clearOut    OutputStream to write the decrypted data to.
     * @throws PGPException if a PGP error occurs.
     * @throws IOException  if an I/O error occurs.
     */
    public void decrypt(InputStream encryptedIn, OutputStream clearOut) throws PGPException, IOException {
        // Removing armor and returning the underlying binary encrypted stream
        encryptedIn = PGPUtil.getDecoderStream(encryptedIn);
        JcaPGPObjectFactory pgpObjectFactory = new JcaPGPObjectFactory(encryptedIn);

        Object obj = pgpObjectFactory.nextObject();
        // The first object might be a marker packet
        PGPEncryptedDataList pgpEncryptedDataList = (obj instanceof PGPEncryptedDataList)
                ? (PGPEncryptedDataList) obj : (PGPEncryptedDataList) pgpObjectFactory.nextObject();

        PGPPrivateKey pgpPrivateKey = null;
        PGPPublicKeyEncryptedData publicKeyEncryptedData = null;

        Iterator<PGPEncryptedData> encryptedDataItr = pgpEncryptedDataList.getEncryptedDataObjects();
        while (pgpPrivateKey == null && encryptedDataItr.hasNext()) {
            publicKeyEncryptedData = (PGPPublicKeyEncryptedData) encryptedDataItr.next();
            pgpPrivateKey = findSecretKey(publicKeyEncryptedData.getKeyID());
        }

        if (Objects.isNull(publicKeyEncryptedData)) {
            throw new PGPException("Could not generate PGPPublicKeyEncryptedData object");
        }

        if (pgpPrivateKey == null) {
            throw new PGPException("Could Not Extract private key");
        }
        CommonUtils.decrypt(clearOut, pgpPrivateKey, publicKeyEncryptedData);
    }

    /**
     * Decrypts encrypted byte array data.
     *
     * @param encryptedBytes Array of bytes containing encrypted data.
     * @return Array of decrypted bytes.
     * @throws PGPException if a PGP error occurs.
     * @throws IOException  if an I/O error occurs.
     */
    public byte[] decrypt(byte[] encryptedBytes) throws PGPException, IOException {
        ByteArrayInputStream encryptedIn = new ByteArrayInputStream(encryptedBytes);
        ByteArrayOutputStream clearOut = new ByteArrayOutputStream();
        decrypt(encryptedIn, clearOut);
        return clearOut.toByteArray();
    }
}