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
 * Il presente codice sorgente � di esclusiva propriet� di Paolo Zavalloni,
 * nessun diritto di distribuzione, vendita o modifica � riconosciuto a terzi.
 */
package ai.gebo.boot.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.crypto.NoSuchPaddingException;

/**
 * Main class responsible for bootstrapping the application.
 * AI generated comments
 */
public class Main {

    /**
     * Initializes the application system, setting up necessary resources.
     */
    static void boot_system() throws UnrecoverableKeyException, InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, NoSuchPaddingException, IOException {

        String _store = null;
        IClassLoader.__data = new int[0];
        if (IClassLoader.__data == null) {
            System.err.println("ERRORE ! SOFTWARE COPIATO! NO STORAGE KEY!");
            System.exit(0);
        } else {
            _classLoader = new IClassLoader();
            // Set the custom class loader as the context class loader
            Thread.currentThread().setContextClassLoader(_classLoader);
        }
    }

    /** Class loader used to load application classes */
    protected static IClassLoader _classLoader = null;
    
    /** Base directory of the application */
    protected static String _base_directory = System.getProperty("user.dir");

    /**
     * Entry point of the application.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            new Main();
            Properties _props = System.getProperties();
            try {
                // Set system property for Derby database
                System.setProperty("derby.storage.pageCacheSize", "4096");

            } catch (Throwable th12) {
                th12.printStackTrace();
            }
            Enumeration _enum = _props.keys();
            while (_enum.hasMoreElements()) {
                String key = (String) _enum.nextElement();
                // Iterate through system properties
            }
            boot_system();
            Properties _properties = new Properties();
            InputStream is = null;
            File _localCfg = new File(".\\config.properties");
            if (_localCfg.exists()) {
                // Load local configuration file if it exists
                FileInputStream fis = new FileInputStream(_localCfg);
                is = fis;
            } else {
                // Otherwise, load configuration from classpath
                is = _classLoader.getResourceAsStream("config/config.properties");
            }

            if (is != null) {
                // Load properties from input stream
                _properties.load(is);
                is.close();
            }

            // Process command-line arguments and override properties accordingly
            for (int i = 0; i < args.length; i++) {
                if (i + 1 < args.length) {
                    if (args[i].startsWith("-")) {
                        _properties.setProperty(args[i].substring(1, args[i].length()), args[i + 1]);
                    }
                }
            }

            // Load main application class and invoke its startup method
            Class _mainClass = _classLoader.loadClass("ai.gebo.monolithic.app.Main");
            Method startupMethod = _mainClass.getMethod("startup", new Class[] { Properties.class });
            Object _instance = _mainClass.newInstance();
            startupMethod.invoke(_instance, new Object[] { _properties });
        } catch (Throwable th12) {
            th12.printStackTrace();
        }
    }
}