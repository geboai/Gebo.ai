/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.services;

import java.security.PrivateKey;
import java.security.PublicKey;

import ai.gebo.crypting.model.GeboCryptedKeySetRaw;
import ai.gebo.crypting.model.GeboKeyPair;
import ai.gebo.crypting.model.GeboKeySetRawData;

/**
 * AI generated comments
 * Interface defining cryptographic services provided by the Gebo application.
 * It includes methods for encrypting and decrypting data and managing keys.
 */
public interface IGeboCryptingService {

	/**
	 * Decrypts a string into binary data.
	 * @param data the string to decrypt
	 * @return the decrypted binary data as a byte array
	 * @throws GeboCryptSecretException if decryption fails
	 */
	public byte[] binarydecrypt(String data) throws GeboCryptSecretException;

	/**
	 * Encrypts binary data into a string representation.
	 * @param data the binary data to encrypt
	 * @return the encrypted data as a string
	 * @throws GeboCryptSecretException if encryption fails
	 */
	public String binarycrypt(byte[] data) throws GeboCryptSecretException;

	/**
	 * Encrypts a string.
	 * @param data the string to encrypt
	 * @return the encrypted string
	 * @throws GeboCryptSecretException if encryption fails
	 */
	public String crypt(String data) throws GeboCryptSecretException;

	/**
	 * Decrypts a string.
	 * @param data the string to decrypt
	 * @return the decrypted string
	 * @throws GeboCryptSecretException if decryption fails
	 */
	public String decrypt(String data) throws GeboCryptSecretException;

	/**
	 * Retrieves a raw key set.
	 * @return the raw key set data
	 * @throws GeboCryptSecretException if key retrieval fails
	 */
	public GeboKeySetRawData getKeySet() throws GeboCryptSecretException;

	/**
	 * Retrieves a key pair based on raw key data.
	 * @param raw the raw key set data
	 * @return the key pair
	 * @throws GeboCryptSecretException if key generation fails
	 */
	public GeboKeyPair getKeys(GeboKeySetRawData raw) throws GeboCryptSecretException;

	/**
	 * Encrypts a raw key set.
	 * @param raw the raw key set data to encrypt
	 * @return the encrypted key set
	 * @throws GeboCryptSecretException if encryption fails
	 */
	public GeboCryptedKeySetRaw crypt(GeboKeySetRawData raw) throws GeboCryptSecretException;

	/**
	 * Decrypts an encrypted key set.
	 * @param cryptes the encrypted key set
	 * @return the decrypted raw key set data
	 * @throws GeboCryptSecretException if decryption fails
	 */
	public GeboKeySetRawData decrypt(GeboCryptedKeySetRaw cryptes) throws GeboCryptSecretException;

	/**
	 * Encodes binary data to a Base64 string.
	 * @param pub the binary data to encode
	 * @return the Base64 encoded string
	 * @throws GeboCryptSecretException if encoding fails
	 */
	public String getBase64Encode(byte[] pub) throws GeboCryptSecretException;

	/**
	 * Decodes a Base64 string back to binary data.
	 * @param encValue the Base64 string to decode
	 * @return the decoded binary data
	 * @throws GeboCryptSecretException if decoding fails
	 */
	public byte[] getBase64Decode(String encValue) throws GeboCryptSecretException;

	/**
	 * Converts binary data to a PrivateKey object.
	 * @param data the binary data representing the private key
	 * @return the PrivateKey object
	 * @throws GeboCryptSecretException if conversion fails
	 */
	public PrivateKey privateKey(byte data[]) throws GeboCryptSecretException;

	/**
	 * Converts binary data to a PublicKey object.
	 * @param data the binary data representing the public key
	 * @return the PublicKey object
	 * @throws GeboCryptSecretException if conversion fails
	 */
	public PublicKey publicKey(byte data[]) throws GeboCryptSecretException;

	/**
	 * Encrypts data using a public key.
	 * @param data the data to encrypt
	 * @param publicKey the public key used for encryption
	 * @return the encrypted data as a byte array
	 * @throws GeboCryptSecretException if encryption fails
	 */
	public byte[] encryptData(byte[] data, PublicKey publicKey) throws GeboCryptSecretException;

	/**
	 * Encrypts data using a private key.
	 * @param data the data to encrypt
	 * @param privateKey the private key used for encryption
	 * @return the encrypted data as a byte array
	 * @throws GeboCryptSecretException if encryption fails
	 */
	public byte[] encryptData(byte[] data, PrivateKey privateKey) throws GeboCryptSecretException;

	/**
	 * Decrypts data using a private key.
	 * @param encryptedData the data to decrypt
	 * @param decryptionKey the private key used for decryption
	 * @return the decrypted data as a byte array
	 * @throws GeboCryptSecretException if decryption fails
	 */
	public byte[] decryptData(byte[] encryptedData, PrivateKey decryptionKey) throws GeboCryptSecretException;

	/**
	 * Decrypts data using a public key.
	 * @param encryptedData the data to decrypt
	 * @param decryptionKey the public key used for decryption
	 * @return the decrypted data as a byte array
	 * @throws GeboCryptSecretException if decryption fails
	 */
	public byte[] decryptData(byte[] encryptedData, PublicKey decryptionKey) throws GeboCryptSecretException;

}