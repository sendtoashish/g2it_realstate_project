package com.g2it.realestate.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.logging.Logger;

public class PasswordUtils {

	private static final Logger log = Logger.getLogger(PasswordUtils.class);

	private static final byte[] KEYDATA = "]Y7kS!btB+".getBytes();
	private static final String ALGORITHM = "Blowfish";
	
	public static String encrypt(String phrase) {
		
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(KEYDATA, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM) ;
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] tmp = cipher.doFinal(phrase.getBytes());
			return new String(Base64.getEncoder().encode(tmp));
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static String decrypt(String phrase) {
		
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(KEYDATA, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM) ;
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] tmp = cipher.doFinal(Base64.getDecoder().decode(phrase));
			return new String(tmp);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(encrypt("Ashish"));
		//System.out.println(decrypt("iPTX0HiTyn0="));
	}
}
