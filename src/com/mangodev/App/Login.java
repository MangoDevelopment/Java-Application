package com.mangodev.App;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@copyright(year = "2018")
@author(name = "Bodie Brewer")
public class Login implements ActionListener{
	public Login(){
		Logger LOGGER = Logger.getLogger(Login.class);
		PropertyConfigurator.configure("log4j.properties");
		LOGGER.info("Starting LOGIN FUNCTION");
		JFrame f = new JFrame("Welcome");
		JButton b = new JButton("Start");
		f.setVisible(true);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.add(b);
		b.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	public static String getEncryptedLogin(){return "";}
	public static String getEncryptedPass(){return "";}
	private static final String encryptionKey           = "ddfsfsdfagSFGSFsfgsfGasfSaGSfgDFbDfd";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    /**
     * Method for Encrypt Plain String Data
     * @param plainText
     * @return encryptedText
     * @throws UnsupportedEncodingException 
     * @throws BadPaddingException 
     * @throws NoSuchPaddingException 
     * @throws InvalidAlgorithmParameterException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchAlgorithmException 
     */
    public static String encrypt(String plainText) throws UnsupportedEncodingException, BadPaddingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException {
    	String encryptedText = "";
    	try {
        Cipher cipher   = Cipher.getInstance(cipherTransformation);
        byte[] key      = encryptionKey.getBytes(characterEncoding);
        SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
        IvParameterSpec ivparameterspec = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
        Base64.Encoder encoder = Base64.getEncoder();
        encryptedText = encoder.encodeToString(cipherText);
    	} catch(InvalidKeyException e1){
    		throw new InvalidKeyException("Cypher ERROR");
    	} catch(InvalidAlgorithmParameterException e2){
    		throw new InvalidAlgorithmParameterException("Cypher ERROR");
    	} catch(BadPaddingException e3){
    		throw new BadPaddingException("Cypher ERROR");
    	} catch(UnsupportedEncodingException e4){
    		throw new UnsupportedEncodingException("Cypher ERROR");
    	} catch(NoSuchPaddingException e5){
    		throw new NoSuchPaddingException("Cypher ERROR");
    	} catch(NoSuchAlgorithmException e6){
    		throw new NoSuchAlgorithmException("Cypher ERROR");
    	} catch(IllegalBlockSizeException e7){
    		throw new IllegalBlockSizeException("Cypher ERROR");
    	}
        return encryptedText;
    }
 
}
