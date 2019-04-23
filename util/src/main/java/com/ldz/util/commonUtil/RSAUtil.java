package com.ldz.util.commonUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * RSA算法，实现数据的加密解密。 
 */
public class RSAUtil {
	private static Cipher cipher;  
	
	//使用公钥加密数据
	private final static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKoiAVdUCoWNiwMgxHGyssEp8m\n" +
            "ae0D1wzirU4EvYImg5oWsIII8ixJW+q20qZto6H8eJ19MFJa1SBZKvEaGKgr/ce+\n" +
            "a4gSBCJnsaUVo/nkE/iz6pZoJSBphmsyfYVhQTjXpLbRCZI/VBs6kRmX6bBTfdAk\n" +
            "rbhPeQpICnaC4Ojx/QIDAQAB";
	
	/** *//** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;  
    
    static{  
        try {  
            cipher = Cipher.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }  
    }
    
    /** 
     * 生成密钥对 
     * @param filePath 生成密钥的路径 
     * @return 
     */  
    public static Map<String,String> generateKeyPair(String filePath){  
        try {  
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
            // 密钥位数  
            keyPairGen.initialize(2048);  
            // 密钥对  
            KeyPair keyPair = keyPairGen.generateKeyPair();  
            // 公钥  
            PublicKey publicKey = keyPair.getPublic();  
            // 私钥  
            PrivateKey privateKey = keyPair.getPrivate();  
            //得到公钥字符串  
            String publicKeyString = getKeyString(publicKey);  
            //得到私钥字符串  
            String privateKeyString = getKeyString(privateKey);  
            //将密钥对写入到文件  
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");  
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");  
            BufferedWriter pubbw = new BufferedWriter(pubfw);  
            BufferedWriter pribw = new BufferedWriter(prifw);  
            pubbw.write(publicKeyString);  
            pribw.write(privateKeyString);  
            pubbw.flush();  
            pubbw.close();  
            pubfw.close();  
            pribw.flush();  
            pribw.close();  
            prifw.close();  
            //将生成的密钥对返回  
            Map<String,String> map = new HashMap<String,String>();  
            map.put("publicKey", publicKeyString);  
            map.put("privateKey", privateKeyString);  
            return map;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
    /** 
     * 得到公钥 
     *  
     * @param key 
     *            密钥字符串（经过base64编码） 
     * @throws Exception 
     */  
    public static PublicKey getPublicKey(String key) throws Exception {  
        byte[] keyBytes;  
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    }  
      
    /** 
     * 得到私钥 
     *  
     * @param key 
     *            密钥字符串（经过base64编码） 
     * @throws Exception 
     */  
    public static PrivateKey getPrivateKey(String key) throws Exception {  
        byte[] keyBytes;  
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);  
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
        return privateKey;  
    }
    
    /** 
     * 得到密钥字符串（经过base64编码） 
     *  
     * @return 
     */  
    public static String getKeyString(Key key) throws Exception {  
        byte[] keyBytes = key.getEncoded();  
        String s = (new BASE64Encoder()).encode(keyBytes);  
        return s;  
    }
    
    /** 
     * 使用公钥对明文进行加密，返回BASE64编码的字符串 
     * @param publicKey 
     * @param plainText 
     * @return 
     */  
    public static String encrypt(PublicKey publicKey, String plainText){  
        try {             
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] enBytes = cipher.doFinal(plainText.getBytes());            
            return (new BASE64Encoder()).encode(enBytes);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
     * 使用keystore对明文进行加密 
     * @param publicKeystore 公钥文件路径 
     * @param plainText      明文 
     * @return 
     */  
    public static String fileEncrypt(String publicKeystore, String plainText){  
        try {             
            FileReader fr = new FileReader(publicKeystore);  
            BufferedReader br = new BufferedReader(fr);  
            String publicKeyString="";  
            String str;  
            while((str=br.readLine())!=null){  
                publicKeyString+=str;  
            }  
            br.close();  
            fr.close();  
            cipher.init(Cipher.ENCRYPT_MODE,getPublicKey(publicKeyString));  
            byte[] enBytes = cipher.doFinal(plainText.getBytes());            
            return (new BASE64Encoder()).encode(enBytes);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
    /** 
     * 使用公钥对明文进行加密
     * @param plainText      明文 
     * @return 
     */  
    public static String encrypt(String plainText){  
        try {              
            cipher.init(Cipher.ENCRYPT_MODE,getPublicKey(publicKey));   
            // 加密时超过117字节就报错。为此采用分段加密的办法来加密  
    		ByteArrayOutputStream out = new ByteArrayOutputStream();  
    		byte[] data = plainText.getBytes();
    		int inputLen = data.length;  
    		int offSet = 0;  
            byte[] cache;  
            int i = 0;  
            // 对数据分段加密  
            while (inputLen - offSet > 0) {  
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
                } else {  
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);  
                }  
                out.write(cache, 0, cache.length);  
                i++;  
                offSet = i * MAX_ENCRYPT_BLOCK;  
            }  
            byte[] encryptedData = out.toByteArray();  
            out.close(); 
            return (new BASE64Encoder()).encode(encryptedData);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
      
    /** 
     * 使用私钥对明文密文进行解密 
     * @param privateKey 
     * @param enStr 
     * @return 
     */  
    public static String decrypt(PrivateKey privateKey, String enStr){  
        try {  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            // 解密时超过128字节就报错。为此采用分段加密的办法来解密
    		byte[] encryptedData = (new BASE64Decoder()).decodeBuffer(enStr.replaceAll("\r\n", ""));
    		int inputLen = encryptedData.length;  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            int offSet = 0;  
            byte[] cache;  
            int i = 0;  
            // 对数据分段解密  
            while (inputLen - offSet > 0) {  
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
                } else {  
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
                }  
                out.write(cache, 0, cache.length);  
                i++;  
                offSet = i * MAX_DECRYPT_BLOCK;  
            }  
            byte[] decryptedData = out.toByteArray();  
            out.close();  
            return new String(decryptedData);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
    /** 
     * 使用私钥对密文进行解密 
     * @param privateKey       私钥 
     * @param enStr            密文 
     * @return 
     */  
    public static String decrypt(String privateKey, String enStr){  
        try {           
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));  
         // 解密时超过128字节就报错。为此采用分段加密的办法来解密
    		byte[] encryptedData = (new BASE64Decoder()).decodeBuffer(enStr.replaceAll("\r\n", ""));
    		int inputLen = encryptedData.length;  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            int offSet = 0;  
            byte[] cache;  
            int i = 0;  
            // 对数据分段解密  
            while (inputLen - offSet > 0) {  
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
                } else {  
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
                }  
                out.write(cache, 0, cache.length);  
                i++;  
                offSet = i * MAX_DECRYPT_BLOCK;  
            }  
            byte[] decryptedData = out.toByteArray();  
            out.close();  
            return new String(decryptedData);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
    /** 
     * 使用keystore对密文进行解密 
     * @param privateKeystore  私钥路径 
     * @param enStr            密文 
     * @return 
     */  
    public static String fileDecrypt(String privateKeystore, String enStr){  
        try {  
            FileReader fr = new FileReader(privateKeystore);  
            BufferedReader br = new BufferedReader(fr);  
            String privateKeyString="";  
            String str;  
            while((str=br.readLine())!=null){  
                privateKeyString+=str;  
            }  
            br.close();  
            fr.close();           
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString));  
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));  
            return new String(deBytes);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
    public static void main(String[] args) {
//    	generateKeyPair("D:/RSA");
    	
    	String publicKey; 
    	String privateKey;

    	privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMqiIBV1QKhY2LAy\n" +
                "DEcbKywSnyZp7QPXDOKtTgS9giaDmhawggjyLElb6rbSpm2jofx4nX0wUlrVIFkq\n" +
                "8RoYqCv9x75riBIEImexpRWj+eQT+LPqlmglIGmGazJ9hWFBONekttEJkj9UGzqR\n" +
                "GZfpsFN90CStuE95CkgKdoLg6PH9AgMBAAECgYAXHKTSLbyBNxkIB71UwumKrlkO\n" +
                "tZg+DuI+XIwAiGj24Z3Kprk+MfkRKPW9gotPCBHqzQsRg1hxWcvFv9seId/UaIkb\n" +
                "Ua9meQ5XNph04Xavmh/k4xVZzTFU0MvFNHbiLOeIAh/5TzydBBca7vefJkQ+CLx1\n" +
                "TMiskV6QTYeoIo6gdQJBAPglPh4YhJwtJFg98AqbYpeTQYcnGj+6RJUSzf1D5OIY\n" +
                "7ZhSiyDsXavsgFFOzQJoVsnpYf/7rJleXPdsg4rKIM8CQQDRDBdXTwMH1OyaPqZo\n" +
                "GhO6rOvDJfv94aP2XA6ZqhcKX0Zs+GDsPyFeERpG1ABMkmWI+PHURCQ5mdjIcwvC\n" +
                "y7tzAkEAmU8ZI6ruMzdInyQVK9h4f0FBWOj1tK22NYvarEDIJlBUaqmEv6CI9vE1\n" +
                "DgbrbjNce36vR2IMbuz37v2h/5uFzQJAUWSgzejAqFL6EJBmIFBO8DcV0LTAabhq\n" +
                "K6jqMmBseihgdls6+aEW79Y+4eb3etjhWbR8DAbACAFjy6Anqx/CCwJBAJITfNqz\n" +
                "w5OJ2EBUrnbtasEKrCya5EB2v9fmO3CcDsqBh3wLN6XQ7+WUHXXJVjpETShef317\n" +
                "DIE91lioRtQl1kM=";
    	
    	
    	System.err.println("公钥加密——私钥解密");
        String source = "{\"token\":\"123123131231283910.2312312weqew231ewewq\",\"id\":\"526009928271992872\",\"phone\":\"1892099122333\",\"userName\":\"Lee TPhone\",\"hImg\":\"https://yt3.ggpht.com/a-/AAuE7mBs1Oghg-r2dMNrlsm3IJrNgqV1Ob5rWvsr7A=s288-mo-c-c0xffffffff-rj-k-no\",\"balance\":\"1000\"}";
        System.out.println("\r加密前文字：\r\n" + source);
        String aData = RSAUtil.encrypt(source);
        System.out.println("加密后文字：\r\n" + aData.replaceAll("\r\n", ""));
        String dData = RSAUtil.decrypt(privateKey, aData);
        System.out.println("解密后文字: \r\n" + dData);  	
	}
}