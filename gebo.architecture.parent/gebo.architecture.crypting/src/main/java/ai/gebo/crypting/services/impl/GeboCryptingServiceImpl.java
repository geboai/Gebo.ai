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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.crypting.config.GeboCryptingConfig;
import ai.gebo.crypting.model.GeboCryptedKeySetRaw;
import ai.gebo.crypting.model.GeboKeyPair;
import ai.gebo.crypting.model.GeboKeySetRawData;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;

/**
 * GeboCryptingServiceImpl is a service implementation providing cryptographic
 * functionalities.
 * It includes methods for encryption, decryption, key management, and digital signing.
 * Implements IGeboCryptingService and listens for ContextRefreshedEvent.
 * <p>
 * AI generated comments
 */
@Service
@Scope("singleton")
public class GeboCryptingServiceImpl implements ApplicationListener<ContextRefreshedEvent>, IGeboCryptingService {
	private static final String KEYPAIR_PROVIDER = "BC";
	private static final String KEYPAIR_ALGO = "RSA";
	static final Logger LOGGER = LoggerFactory.getLogger(GeboCryptingServiceImpl.class);
	static final private String BUNDLED_KEYSTORE_TYPE = "PKCS12";
	static final private String BUNDLED_KEY_ALGORITHM = "AES";
	static final private String BUNDLED_MAIN_KEY_NAME = "gebo.ai.key";
	static final private String BUNDLED_KEYSTORE = "/keystore/bundled.gebo.ai.ks";
	static final private String BUNDLED_KEYSTORE_PWD = "TheFunkyHeadHunter1969Ad";
	static boolean usedBundled = false;
	static private KeyStore _keystore = null;
	static private Key key = null;
	final GeboCryptingConfig geboCryptConfig;
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * Constructor to initialize GeboCryptingServiceImpl with configuration.
	 * 
	 * @param geboConfig configuration for criptographic operations.
	 */
	public GeboCryptingServiceImpl(GeboCryptingConfig geboConfig) {
		this.geboCryptConfig = geboConfig;
	}

	/**
	 * Determines the algorithm to be used based on the configuration.
	 * 
	 * @return The name of the algorithm.
	 */
	private String getUsedAlgorithm() {
		if (!usedBundled) {
			return geboCryptConfig.getKeystoreConfig().getKeyAlgorithm();
		}
		return BUNDLED_KEY_ALGORITHM;
	}

	/**
	 * Encrypts byte data using the initialized cryptographic key.
	 * Encodes the encrypted data using Base64.
	 * 
	 * @param data The data to be encrypted.
	 * @return Base64 encoded string of encrypted data.
	 * @throws GeboCryptSecretException if encryption fails or key is uninitialized.
	 */
	public String binarycrypt(byte[] data) throws GeboCryptSecretException {
		try {
			if (key != null) {
				Cipher c = Cipher.getInstance(getUsedAlgorithm());
				c.init(Cipher.ENCRYPT_MODE, key);
				byte[] encValue = c.doFinal(data);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				new Base64Encoder().encode(encValue, 0, encValue.length, bos);
				return bos.toString();
			} else {
				throw new GeboCryptSecretException("Cannot encript, encription key not initialized");
			}
		} catch (Throwable e) {
			throw new GeboCryptSecretException("Cannot encript", e);
		}
	}

	/**
	 * Decrypts a Base64 encoded encrypted string using the initialized cryptographic key.
	 * 
	 * @param data Base64 encoded string of encrypted data.
	 * @return Decrypted byte array.
	 * @throws GeboCryptSecretException if decryption fails or key is uninitialized.
	 */
	public byte[] binarydecrypt(String data) throws GeboCryptSecretException {
		try {
			if (key != null) {
				Cipher c = Cipher.getInstance(getUsedAlgorithm());
				c.init(Cipher.DECRYPT_MODE, key);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] original = data.getBytes();
				int done = new Base64Encoder().decode(original, 0, original.length, bos);
				byte[] decordedValue = bos.toByteArray();
				byte[] decValue = c.doFinal(decordedValue);
				return decValue;
			} else {
				throw new GeboCryptSecretException("Cannot decript, encription key not initialized");
			}
		} catch (Throwable e) {
			throw new GeboCryptSecretException("Cannot dencript", e);
		}
	}

	/**
	 * Encrypts a string using the initialized cryptographic key.
	 * Encodes the encrypted data using Base64.
	 * 
	 * @param data The string to be encrypted.
	 * @return Base64 encoded string of encrypted data.
	 * @throws GeboCryptSecretException if encryption fails or key is uninitialized.
	 */
	public String crypt(String data) throws GeboCryptSecretException {
		try {
			if (key != null) {
				Cipher c = Cipher.getInstance(getUsedAlgorithm());
				c.init(Cipher.ENCRYPT_MODE, key);
				byte[] encValue = c.doFinal(data.getBytes());
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				new Base64Encoder().encode(encValue, 0, encValue.length, bos);
				return bos.toString();
			} else {
				throw new GeboCryptSecretException("Cannot encript, encription key not initialized");
			}
		} catch (Throwable e) {
			throw new GeboCryptSecretException("Cannot encript", e);
		}
	}

	/**
	 * Decrypts a Base64 encoded encrypted string using the initialized cryptographic key.
	 * 
	 * @param data Base64 encoded string of encrypted data.
	 * @return Decrypted string.
	 * @throws GeboCryptSecretException if decryption fails or key is uninitialized.
	 */
	public String decrypt(String data) throws GeboCryptSecretException {
		try {
			if (key != null) {
				Cipher c = Cipher.getInstance(getUsedAlgorithm());
				c.init(Cipher.DECRYPT_MODE, key);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] original = data.getBytes();
				int done = new Base64Encoder().decode(original, 0, original.length, bos);
				byte[] decordedValue = bos.toByteArray();
				byte[] decValue = c.doFinal(decordedValue);
				String decryptedValue = new String(decValue);
				return decryptedValue;
			} else {
				throw new GeboCryptSecretException("Cannot decript, encription key not initialized");
			}
		} catch (Throwable e) {
			throw new GeboCryptSecretException("Cannot dencript", e);
		}
	}

	/**
	 * Initializes the keystore and key upon application startup.
	 * Uses different keystores based on configuration and availability.
	 * 
	 * @param event ContextRefreshedEvent triggered on application startup.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Starting up security environment");
		if (_keystore == null) {
			if (geboCryptConfig.getKeystoreConfig() != null && geboCryptConfig.getKeystoreConfig().getKeyStorePath() != null) {
				Path path = Path.of(geboCryptConfig.getKeystoreConfig().getKeyStorePath());
				File keystore = path.toFile();
				try {
					if (keystore.exists()) {
						LOGGER.info("Reading custom keystore security data structures");
						FileInputStream fis = null;
						try {
							fis = new FileInputStream(keystore);
							_keystore = KeyStore.getInstance(geboCryptConfig.getKeystoreConfig().getKeyStoreType());
							_keystore.load(fis, geboCryptConfig.getKeystoreConfig().getKeyStorePassword().toCharArray());
							key = _keystore.getKey(geboCryptConfig.getKeystoreConfig().getKeyName(),
									geboCryptConfig.getKeystoreConfig().getKeyPassword().toCharArray());
							usedBundled = false;
						} finally {
							try {
								fis.close();
							} catch (Throwable t) {
							}
						}
						LOGGER.info("Security custom keystore data structures read!!");
					}
				} catch (Throwable th) {
					LOGGER.error(
							"/*******************************************************************************************");
					try {
						ObjectMapper mapper = new ObjectMapper();
						LOGGER.error("*" + mapper.writeValueAsString(geboCryptConfig.getKeystoreConfig()));
					} catch (Throwable th11) {
					}
					LOGGER.error("*Cannot access the keystore or the key correctly", th);
					LOGGER.error(
							"********************************************************************************************/");
					throw new RuntimeException("Erroneous state, cannot boot the software with invalid keystore", th);
				}
			} else {
				LOGGER.info("Reading security bundled data structures");
				InputStream is = null;
				try {
					is = getClass().getResourceAsStream(BUNDLED_KEYSTORE);
					_keystore = KeyStore.getInstance(BUNDLED_KEYSTORE_TYPE);
					_keystore.load(is, BUNDLED_KEYSTORE_PWD.toCharArray());
					key = _keystore.getKey(BUNDLED_MAIN_KEY_NAME, BUNDLED_KEYSTORE_PWD.toCharArray());
					usedBundled = true;
				} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException
						| IOException e) {
					LOGGER.error("Cannot access or create the bundled keystore, armful state", e);
					throw new RuntimeException("Erroneous state, cannot boot the software with invalid keystore", e);
				} finally {
					try {
						is.close();
					} catch (Throwable t) {
					}
				}
				LOGGER.info("Security bundled data structures read!!");
			}
		}
		LOGGER.info("Started up security environment!!");
	}

	/**
	 * Generates a new raw key set data using RSA algorithm.
	 * 
	 * @return GeboKeySetRawData containing encoded public and private keys.
	 * @throws GeboCryptSecretException if key generation fails.
	 */
	public GeboKeySetRawData getKeySet() throws GeboCryptSecretException {
		try {
			java.security.KeyPair keyPair = RSAUtils.defaultInstance.keyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			// A KeyFactory is used to convert encoded keys to their actual Java classes
			KeyFactory ecKeyFac = KeyFactory.getInstance(KEYPAIR_ALGO, KEYPAIR_PROVIDER);
			// Now do a round-trip for a private key,
			byte[] encodedPriv = privateKey.getEncoded();
			// And a round trip for the public key as well.
			byte[] encodedPub = publicKey.getEncoded();
			GeboKeySetRawData keySet = new GeboKeySetRawData(encodedPub, encodedPriv);
			return keySet;
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new GeboCryptSecretException("cannot generate raw keySet", e);
		}
	}

	/**
	 * Converts encoded byte data into a PrivateKey object.
	 * 
	 * @param data the byte array containing the encoded private key.
	 * @return a PrivateKey object.
	 * @throws GeboCryptSecretException if conversion fails.
	 */
	public PrivateKey privateKey(byte data[]) throws GeboCryptSecretException {
		try {
			KeyFactory ecKeyFac = KeyFactory.getInstance(KEYPAIR_ALGO, KEYPAIR_PROVIDER);
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(data);
			PrivateKey privateKey = ecKeyFac.generatePrivate(pkcs8EncodedKeySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
			throw new GeboCryptSecretException("cannot convert private key", e);
		}
	}

	/**
	 * Converts encoded byte data into a PublicKey object.
	 * 
	 * @param data the byte array containing the encoded public key.
	 * @return a PublicKey object.
	 * @throws GeboCryptSecretException if conversion fails.
	 */
	public PublicKey publicKey(byte data[]) throws GeboCryptSecretException {
		try {
			KeyFactory ecKeyFac = KeyFactory.getInstance(KEYPAIR_ALGO, KEYPAIR_PROVIDER);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(data);
			PublicKey publicKey = ecKeyFac.generatePublic(x509EncodedKeySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
			throw new GeboCryptSecretException("cannot convert public key", e);
		}
	}

	/**
	 * Converts raw key data into a complete GeboKeyPair.
	 * 
	 * @param raw a GeboKeySetRawData containing encoded keys.
	 * @return a GeboKeyPair with PublicKey and PrivateKey.
	 * @throws GeboCryptSecretException if conversion fails.
	 */
	public GeboKeyPair getKeys(GeboKeySetRawData raw) throws GeboCryptSecretException {
		try {
			KeyFactory ecKeyFac = KeyFactory.getInstance(KEYPAIR_ALGO, KEYPAIR_PROVIDER);
			// now take the encoded value and recreate the private key
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(raw.getPriv());
			PrivateKey privateKey = ecKeyFac.generatePrivate(pkcs8EncodedKeySpec);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(raw.getPub());
			PublicKey publicKey = ecKeyFac.generatePublic(x509EncodedKeySpec);
			return new GeboKeyPair(publicKey, privateKey);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
			throw new GeboCryptSecretException("cannot generate  keySet from raw keys data", e);
		}
	}

	/**
	 * Encrypts raw key data and returns it as a GeboCryptedKeySetRaw.
	 * 
	 * @param raw a GeboKeySetRawData containing encoded keys.
	 * @return a GeboCryptedKeySetRaw with encrypted keys.
	 * @throws GeboCryptSecretException if encryption fails.
	 */
	@Override
	public GeboCryptedKeySetRaw crypt(GeboKeySetRawData raw) throws GeboCryptSecretException {
		final GeboCryptedKeySetRaw out = new GeboCryptedKeySetRaw();
		out.setPriv(binarycrypt(raw.getPriv()));
		out.setPub(binarycrypt(raw.getPub()));
		return out;
	}

	/**
	 * Decrypts a GeboCryptedKeySetRaw into raw key data.
	 * 
	 * @param cryptes a GeboCryptedKeySetRaw with encrypted keys.
	 * @return a GeboKeySetRawData containing decoded keys.
	 * @throws GeboCryptSecretException if decryption fails.
	 */
	@Override
	public GeboKeySetRawData decrypt(GeboCryptedKeySetRaw cryptes) throws GeboCryptSecretException {
		byte pub[] = this.binarydecrypt(cryptes.getPub());
		byte priv[] = this.binarydecrypt(cryptes.getPriv());
		return new GeboKeySetRawData(pub, priv);
	}

	/**
	 * Encodes a byte array into a Base64 encoded string.
	 * 
	 * @param encValue the byte array to be encoded.
	 * @return a Base64 encoded string.
	 * @throws GeboCryptSecretException if encoding fails.
	 */
	@Override
	public String getBase64Encode(byte[] encValue) throws GeboCryptSecretException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			new Base64Encoder().encode(encValue, 0, encValue.length, bos);
			return bos.toString();
		} catch (IOException exc) {
			throw new GeboCryptSecretException("Cannot transform to base64", exc);
		}
	}

	/**
	 * Decodes a Base64 encoded string into a byte array.
	 * 
	 * @param encValue the Base64 encoded string.
	 * @return a byte array.
	 * @throws GeboCryptSecretException if decoding fails.
	 */
	public byte[] getBase64Decode(String encValue) throws GeboCryptSecretException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] original = encValue.getBytes();
			int done = new Base64Encoder().decode(original, 0, original.length, bos);
			byte[] decordedValue = bos.toByteArray();
			return decordedValue;
		} catch (IOException exc) {
			throw new GeboCryptSecretException("Cannot transform to base64", exc);
		}
	}

	// https://www.baeldung.com/java-bouncy-castle
	/**
	 * Encrypts data using a public key.
	 * 
	 * @param data      the byte array to be encrypted.
	 * @param publicKey the public key used for encryption.
	 * @return encrypted byte array.
	 * @throws GeboCryptSecretException if encryption fails.
	 */
	public byte[] encryptData(byte[] data, PublicKey publicKey) throws GeboCryptSecretException {
		try {
			return RSAUtils.defaultInstance.encrypt(data, publicKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException exc) {
			throw new GeboCryptSecretException("Cannot encrypt data", exc);
		}
	}

	/**
	 * Decrypts encrypted data using a private key.
	 * 
	 * @param encryptedData the encrypted byte array.
	 * @param decryptionKey the private key used for decryption.
	 * @return decrypted byte array.
	 * @throws GeboCryptSecretException if decryption fails.
	 */
	public byte[] decryptData(byte[] encryptedData, PrivateKey decryptionKey) throws GeboCryptSecretException {
		try {
			return RSAUtils.defaultInstance.decrypt(encryptedData, decryptionKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException exc) {
			throw new GeboCryptSecretException("Cannot decrypt data", exc);
		}
	}

	/**
	 * Signs data digitally using a private key and a certificate.
	 * 
	 * @param data              the byte array to be signed.
	 * @param signingCertificate the X509Certificate used for signing.
	 * @param signingKey         the PrivateKey used for signing.
	 * @return a byte array containing the signed data.
	 * @throws Exception if signing fails.
	 */
	public byte[] signData(byte[] data, X509Certificate signingCertificate, PrivateKey signingKey) throws Exception {
		byte[] signedMessage = null;
		List<X509Certificate> certList = new ArrayList<X509Certificate>();
		CMSTypedData cmsData = new CMSProcessableByteArray(data);
		certList.add(signingCertificate);
		Store certs = new JcaCertStore(certList);

		CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();
		ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(signingKey);
		cmsGenerator.addSignerInfoGenerator(
				new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
						.build(contentSigner, signingCertificate));
		cmsGenerator.addCertificates(certs);

		CMSSignedData cms = cmsGenerator.generate(cmsData, true);
		signedMessage = cms.getEncoded();
		return signedMessage;
	}

	/**
	 * Encrypts data using a private key.
	 * 
	 * @param data       the byte array to be encrypted.
	 * @param privateKey the private key used for encryption.
	 * @return encrypted byte array.
	 * @throws GeboCryptSecretException if encryption fails.
	 */
	@Override
	public byte[] encryptData(byte[] data, PrivateKey privateKey) throws GeboCryptSecretException {
		try {
			return RSAUtils.defaultInstance.encrypt(data, privateKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException exc) {
			throw new GeboCryptSecretException("Cannot encrypt data", exc);
		}
	}

	/**
	 * Decrypts encrypted data using a public key.
	 * 
	 * @param encryptedData the encrypted byte array.
	 * @param decryptionKey the public key used for decryption.
	 * @return decrypted byte array.
	 * @throws GeboCryptSecretException if decryption fails.
	 */
	@Override
	public byte[] decryptData(byte[] encryptedData, PublicKey decryptionKey) throws GeboCryptSecretException {
		try {
			return RSAUtils.defaultInstance.decrypt(encryptedData, decryptionKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException exc) {
			throw new GeboCryptSecretException("Cannot decrypt data", exc);
		}
	}

}