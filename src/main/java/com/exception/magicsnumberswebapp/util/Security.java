
package com.exception.magicsnumberswebapp.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author fpimentel
 * Since 16-dic-2013
 */
public class Security {
    private static final Logger LOG = Logger.getLogger(Security.class.getName());
   
    public static void forward(final String url) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + url);
    }
    public static void logoutSession(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
    }
    
    public static String encryptToMD5(final String password) {
        if (password.isEmpty()) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());
            String secured = new BigInteger(1, digest.digest()).toString(16);
            return secured;
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.SEVERE, "encript to md5", e);
            e.printStackTrace();
        }
        return null;
    }
}
