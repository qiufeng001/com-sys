package com.sys.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * auther: kiven on 2018/7/18/018 9:52
 * try it bast!
 */
public class DESUtils {
    protected static Log logger = LogFactory.getLog(DESUtils.class);
    public static String encode(String password) {
        return encode(password, "MD5");
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials string
     * is returned
     *
     * @param password
     *            Password or other credentials to use in authenticating this
     *            username
     * @param algorithm
     *            Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encode(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();
        MessageDigest md = null;
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            logger.error("Exception:{}", e);
            return password;
        }
        md.reset();
        md.update(unencodedPassword);
        byte[] encodedPassword = md.digest();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }
}
