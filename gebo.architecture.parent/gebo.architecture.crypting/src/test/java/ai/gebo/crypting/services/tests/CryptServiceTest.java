/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.services.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ai.gebo.config.GeboConfig;
import ai.gebo.crypting.model.GeboKeyPair;
import ai.gebo.crypting.model.GeboKeySetRawData;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.crypting.services.impl.GeboCryptingServiceImpl;


/**
 * AI generated comments
 * Test class for the GeboCryptingService. This class contains unit tests
 * to verify the functionality of crypting services such as encryption,
 * decryption, key generation, and signing.
 */
@SpringBootTest(classes = Main.class)
public class CryptServiceTest {

    // Logger instance for logging test information
    private static Logger LOGGER = LoggerFactory.getLogger(CryptServiceTest.class);

    // Autowired service for cryptographic operations
    private @Autowired IGeboCryptingService cryptService;

    /**
     * Constructor for the CryptServiceTest class
     */
    public CryptServiceTest() {
    }

    /**
     * Test for standard encryption and decryption
     * Verifies that encrypting and then decrypting a string returns the original string
     * 
     * @throws GeboCryptSecretException if cryptography-related operation fails
     */
    @Test
    public void testStandardCrypt() throws GeboCryptSecretException {
        IGeboCryptingService cs = cryptService;
        String clear = "in mezzo del cammin di nostra vita ci ritrovammo in una selva oscura";

        String crypted = cs.crypt(clear);
        LOGGER.info(clear + "=>" + crypted);
        String original = cs.decrypt(crypted);
        assertEquals(clear, original, "By crypting and decript we must obtain original value");
    }

    /**
     * Test for encryption and decryption using simple key pairs
     * Generates a key pair, encrypts random UUID strings, and ensures
     * that decrypting returns the original strings.
     * 
     * @throws GeboCryptSecretException if cryptography-related operation fails
     */
    @Test
    public void simpleKeyPairsCryptDecryptTest() throws GeboCryptSecretException {
        GeboKeySetRawData keySet = cryptService.getKeySet();
        GeboKeyPair keyPair = cryptService.getKeys(keySet);
        String original = "";
        for (int i = 0; i < 1; i++) {
            original += UUID.randomUUID().toString() + "-";
        }
        byte[] crypted = cryptService.encryptData(original.getBytes(), keyPair.getPub());
        byte[] decrypted = cryptService.decryptData(crypted, keyPair.getPriv());
        String decryptedString = new String(decrypted).trim();
        assertEquals(decryptedString, original,
                "By crypting and decript with full priv/pub keyring we must obtain original value");
    }

    /**
     * Test for encryption, signing, and decryption using key pairs
     * Validates that encrypting, signing, and then decrypting a message
     * with different key pairs yields the original message.
     * 
     * @throws GeboCryptSecretException if cryptography-related operation fails
     */
    @Test
    public void testKeyPairCryptSignDecrypting() throws GeboCryptSecretException {
        GeboKeySetRawData keySet = cryptService.getKeySet();
        GeboKeySetRawData keySet1 = cryptService.getKeySet();
        GeboKeyPair keyPair = cryptService.getKeys(keySet);
        GeboKeyPair keyPair1 = cryptService.getKeys(keySet1);
        String original = "in mezzo del cammin di nostra vita ci ritrovammo in una selva oscura";
        byte[] firstCrypt = cryptService.encryptData(original.getBytes(), keyPair.getPub());
        String firstCryptBase64 = cryptService.getBase64Encode(firstCrypt);
        byte[] sign = cryptService.encryptData(firstCrypt, keyPair1.getPriv());
        String signBase64 = cryptService.getBase64Encode(sign);
        byte[] firstDecrypt = cryptService.decryptData(sign, keyPair1.getPub());
        String firstDecryptBase64 = cryptService.getBase64Encode(firstDecrypt);
        assertEquals(firstCryptBase64, firstDecryptBase64,
                "Base64 of both first crypt and first decrypt have to be the same");
        byte[] secondDecrypt = cryptService.decryptData(firstDecrypt, keyPair.getPriv());
        String secondDecryptBase64 = cryptService.getBase64Encode(secondDecrypt);
        String endDecoding = new String(secondDecrypt);
        assertEquals(endDecoding, original,
                "By crypting and decript with full priv/pub keyring we must obtain original value");
    }
}