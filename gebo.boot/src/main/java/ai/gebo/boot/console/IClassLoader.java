/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/*
 * Created on 21-mag-2007
 * By Paolo Zavalloni
 * Il presente codice sorgente è di esclusiva proprietà di Paolo Zavalloni,
 * nessun diritto di distribuzione, vendita o modifica è riconosciuto a terzi.
 */

package ai.gebo.boot.console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

/**
 * Custom class loader capable of loading classes and resources from encrypted
 * archives.
 * AI generated comments
 */
public class IClassLoader extends ClassLoader {
    // Flag to indicate if all resources have been cached
    boolean cachedAll = false;

    /**
     * Inner class extending CipherInputStream to handle encrypted streams.
     */
    public class CryptInvolucro extends CipherInputStream {
        // Original InputStream being wrapped
        InputStream is = null;

        /**
         * Constructor for CryptInvolucro.
         * 
         * @param is     InputStream to wrap
         * @param cipher Cipher object for decryption
         */
        public CryptInvolucro(InputStream is, Cipher cipher) {
            super(is, cipher);
            this.is = is;
        }

        /**
         * Closes the stream after ensuring the input stream is also closed.
         */
        public void close() throws IOException {
            try {
                super.close();
            } catch (Throwable th12) {
            }
            try {
                is.close();
            } catch (Throwable th12) {
            }
        }

    };

    // Static volatile data array used for encryption
    static volatile int __data[] = null;

    // Password and key information for keystore
    volatile String pwd1 = "#()8932389qwe&";

    volatile String pwd2 = "OpP(()&%fuck";

    volatile String key_name = "CCAENTRY";

    volatile String algo = "TripleDES";

    volatile KeyStore keystore = null;

    // Buffer to store encrypted data
    volatile byte buffer[] = new byte[__data.length];

    // Key used for decryption
    volatile Key _key = null;

    /**
     * Constructor to initialize the class loader.
     */
    public IClassLoader() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
            UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException {

        for (int i = 0; i < __data.length; i++) {
            buffer[i] = (byte) __data[i];
        }
        keystore = KeyStore.getInstance("JCEKS");
        keystore.load(new ByteArrayInputStream(buffer), pwd1.toCharArray());
        _key = keystore.getKey(key_name, pwd2.toCharArray());
        cipher = Cipher.getInstance(algo);
        cipher.init(Cipher.DECRYPT_MODE, _key);
    }

    // Cipher object for encryption and decryption
    volatile Cipher cipher = null;

    /**
     * Constructor to initialize the class loader with a parent class loader.
     * 
     * @param p Parent ClassLoader
     */
    public IClassLoader(ClassLoader p) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException {
        super(p);

        for (int i = 0; i < __data.length; i++) {
            buffer[i] = (byte) __data[i];
        }
        keystore = KeyStore.getInstance("JCEKS");
        keystore.load(new ByteArrayInputStream(buffer), pwd1.toCharArray());
        _key = keystore.getKey(key_name, pwd2.toCharArray());
        cipher = Cipher.getInstance(algo);
        cipher.init(Cipher.DECRYPT_MODE, _key);

    }

    // Cache for storing loaded classes
    Hashtable _classesCache = new Hashtable();

    // Cache for storing loaded resources
    Hashtable _resourcesCache = new Hashtable();

    /**
     * Inner class representing a cache item for resources.
     */
    class RCCacheItem {
        String resourceName = null;

        byte buffer[] = null;

        File archive = null;
    };

    /**
     * Loads all classes and resources from archives in the current directory.
     */
    void load_classes() throws Throwable {
        File console = new File("./console");
        File files[] = console.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".car")) {
                FileInputStream _fis = new FileInputStream(files[i]);
                CryptInvolucro _cinfo = new CryptInvolucro(_fis, cipher);
                ZipInputStream zip = new ZipInputStream(_cinfo);

                ZipEntry entry = null;
                byte _buffer[] = new byte[512];
                while ((entry = zip.getNextEntry()) != null) {
                    int _ntoread = (int) entry.getSize();
                    if (!entry.isDirectory()) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();

                        int _nr = 0;
                        while ((_nr = zip.read(_buffer)) > 0) {
                            bos.write(_buffer, 0, _nr);
                        }
                        bos.flush();
                        bos.close();
                        byte _data[] = bos.toByteArray();
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replaceAll(".class", "").replace('/', '.').replace('\\',
                                    '.');
                            int _lastPoint = entry.getName().lastIndexOf('.');
                            String _name = entry.getName().substring(0, _lastPoint).replace('/', '.').replace('\\',
                                    '.');

                            _classesCache.put(_name, _data);
                        } else {
                            RCCacheItem _item = new RCCacheItem();
                            _item.archive = files[i];
                            _item.resourceName = entry.getName();
                            _item.buffer = _data;

                            this._resourcesCache.put(_item.resourceName, _item);
                        }
                    } else {
                        zip.skip(_ntoread);
                    }

                }
                _cinfo.close();
                zip.close();
                _fis.close();

            }
        }
        cachedAll = true;
    }

    /**
     * Retrieves a resource as a stream, first loading classes if necessary.
     * 
     * @param arg0 Name of the resource to retrieve
     * @return InputStream of the resource
     */
    public InputStream getResourceAsStream(String arg0) {
        if (!cachedAll) {
            try {
                load_classes();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        InputStream _is = super.getResourceAsStream(arg0);
        if (_is != null)
            return _is;
        _is = getParent().getResourceAsStream(arg0);
        if (_is != null)
            return _is;
        String _searched = arg0;
        if (_searched.startsWith("/")) {
            _searched = _searched.substring(1);
        }
        if (this._resourcesCache.containsKey(_searched)) {
            RCCacheItem _cacheItem = (RCCacheItem) this._resourcesCache.get(_searched);
            // com.zconsultancies.threelayers.util.LogUtil.out.println(_searched + " found
            // ");
            if (_cacheItem.buffer != null) {
                _is = new ByteArrayInputStream(_cacheItem.buffer);
                // _cacheItem.buffer = null;
            } else {
                FileInputStream _fis = null;
                CryptInvolucro _cinfo = null;
                ZipInputStream zip = null;
                try {
                    _fis = new FileInputStream(_cacheItem.archive);
                    _cinfo = new CryptInvolucro(_fis, cipher);
                    zip = new ZipInputStream(_cinfo);
                    ZipEntry _entry = null;

                    ByteArrayInputStream bis = null;
                    while ((_entry = zip.getNextEntry()) != null) {
                        if (_entry.getName().equals(_searched)) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte _buffer[] = new byte[512];
                            int nr = 0;
                            while ((nr = zip.read(_buffer)) >= 0) {
                                bos.write(_buffer, 0, nr);
                            }
                            bos.flush();
                            bos.close();
                            bis = new ByteArrayInputStream(bos.toByteArray());
                        }
                    }
                    _is = bis;
                } catch (Throwable th12) {
                    th12.printStackTrace();
                    throw new IllegalStateException("NESTED: " + th12.getMessage());
                } finally {
                    try {
                        zip.close();
                    } catch (Throwable th12) {
                    }
                    try {
                        _cinfo.close();
                    } catch (Throwable th12) {
                    }
                    try {
                        _fis.close();
                    } catch (Throwable th12) {
                    }
                }
            }
        }
        return _is;
    }

    /**
     * Loads a class with the specified name, first loading classes if necessary.
     * 
     * @param clss Name of the class to load
     * @return Class object representing the loaded class
     */
    public Class loadClass(String clss) throws ClassNotFoundException {
        if (!cachedAll) {
            try {
                load_classes();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (_classesCache.containsKey(clss)) {
            Object _object = _classesCache.get(clss);
            if (_object != null) {
                Class out = null;
                if (_object instanceof Class) {
                    out = (Class) _object;
                } else {
                    byte buffer[] = (byte[]) _object;
                    _classesCache.put(clss, out = defineClass(clss, buffer, 0, buffer.length));
                }
                return out;
            }

        }

        return getParent().loadClass(clss);
    }

}