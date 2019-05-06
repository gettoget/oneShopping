package com.ldz.util.commonUtil;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Encoder;

/**
 * 生成RSA密钥文件，用于设备商数据交换加密用
 * @author Lee
 *
 */
public class RSAUtils {
	public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKoiAVdUCoWNiwMgxHGyssEp8mae0D1wzirU4EvYImg5oWsIII8ixJW+q20qZto6H8eJ19MFJa1SBZKvEaGKgr/ce+a4gSBCJnsaUVo/nkE/iz6pZoJSBphmsyfYVhQTjXpLbRCZI/VBs6kRmX6bBTfdAkrbhPeQpICnaC4Ojx/QIDAQAB";
	public static final String PRIVATEKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMqiIBV1QKhY2LAyDEcbKywSnyZp7QPXDOKtTgS9giaDmhawggjyLElb6rbSpm2jofx4nX0wUlrVIFkq8RoYqCv9x75riBIEImexpRWj+eQT+LPqlmglIGmGazJ9hWFBONekttEJkj9UGzqRGZfpsFN90CStuE95CkgKdoLg6PH9AgMBAAECgYAXHKTSLbyBNxkIB71UwumKrlkOtZg+DuI+XIwAiGj24Z3Kprk+MfkRKPW9gotPCBHqzQsRg1hxWcvFv9seId/UaIkbUa9meQ5XNph04Xavmh/k4xVZzTFU0MvFNHbiLOeIAh/5TzydBBca7vefJkQ+CLx1TMiskV6QTYeoIo6gdQJBAPglPh4YhJwtJFg98AqbYpeTQYcnGj+6RJUSzf1D5OIY7ZhSiyDsXavsgFFOzQJoVsnpYf/7rJleXPdsg4rKIM8CQQDRDBdXTwMH1OyaPqZoGhO6rOvDJfv94aP2XA6ZqhcKX0Zs+GDsPyFeERpG1ABMkmWI+PHURCQ5mdjIcwvCy7tzAkEAmU8ZI6ruMzdInyQVK9h4f0FBWOj1tK22NYvarEDIJlBUaqmEv6CI9vE1DgbrbjNce36vR2IMbuz37v2h/5uFzQJAUWSgzejAqFL6EJBmIFBO8DcV0LTAabhqK6jqMmBseihgdls6+aEW79Y+4eb3etjhWbR8DAbACAFjy6Anqx/CCwJBAJITfNqzw5OJ2EBUrnbtasEKrCya5EB2v9fmO3CcDsqBh3wLN6XQ7+WUHXXJVjpETShef317DIE91lioRtQl1kM=";
	
	/** *//** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;  

	/**
	 * 创建公私钥对
	 * @param publicKeyPath		公钥存储地址
	 * @param privateKeyPath	私钥存储地址
	 * @throws Exception
	 */
	public static void createKeyPair(String publicKeyPath, String privateKeyPath) throws Exception {
		KeyPairGenerator keyPairGen= null;  
        try {  
            keyPairGen= KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        keyPairGen.initialize(1024, new SecureRandom());  
        KeyPair keyPair= keyPairGen.generateKeyPair();  
        //获取公钥
  		Key publicKey = keyPair.getPublic();
  		//获取私钥
  		Key privateKey = keyPair.getPrivate(); 
        
		//保存公钥对象和私钥对象为持久化文件
  		System.out.println("公钥："+new BASE64Encoder().encode(publicKey.getEncoded()).replaceAll("\r\n", ""));
  		System.out.println("私钥："+new BASE64Encoder().encode(privateKey.getEncoded()).replaceAll("\r\n", ""));
		//FileUtils.writeStringToFile(new File(publicKeyPath), new BASE64Encoder().encode(publicKey.getEncoded()), "UTF-8");
		//FileUtils.writeStringToFile(new File(privateKeyPath), new BASE64Encoder().encode(privateKey.getEncoded()), "UTF-8");
	}
    
	/**
	 * RSA加密并转换base64字符串
	 * @param str				待加密字符串
	 * @param privateKeyPath	私钥文件路径。接收数据方使用公钥解密
	 * @return
	 * @throws Exception
	 */
	public static String encryptWithRSA(String str) throws Exception {
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(PUBLICKEY));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);

		//获取一个加密算法为RSA的加解密器对象cipher。
		Cipher cipher = Cipher.getInstance("RSA");
		//设置为加密模式,并将公钥给cipher。
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密  
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		byte[] data = str.getBytes();
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
		//进行Base64编码
		return Base64.encodeBase64String(encryptedData);
	}

	//
	/**
	 * RSA解密，解密数据是base64字符串
	 * @param secret			base64编码字符串
	 * @param privateKeyPath	私钥文件路径。数据发送方使用公钥加密数据
	 * @return
	 * @throws Exception
	 */
	public static String decryptWithRSA(String secret) throws Exception {
		PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(Base64.decodeBase64(PRIVATEKEY));  
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
        Key privateKey = keyFactory.generatePrivate(keySpec);
		
		Cipher cipher = Cipher.getInstance("RSA");
		//传递私钥，设置为解密模式。
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 解密时超过128字节就报错。为此采用分段加密的办法来解密
		byte[] encryptedData = Base64.decodeBase64(secret.replaceAll("\r\n", ""));
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
		//解密器解密由Base64解码后的密文,获得明文字节数组
		return new String(decryptedData, "GBK");
	}
	
	/**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	/** 
	 * 使用私钥对数据进行加密签名 
	 * @param data 数据 
	 * @param privateKeyString 私钥 
	 * @return 加密后的签名 
	 */  
	public static String sign(String data) throws Exception {  
	    KeyFactory keyf = KeyFactory.getInstance("RSA");  
	    PrivateKey privateKey = keyf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(PRIVATEKEY)));  
	    java.security.Signature signet = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
	    signet.initSign(privateKey);  
	    signet.update(data.getBytes("utf-8"));  
	    byte[] signed = signet.sign();  
	    return Base64.encodeBase64String(signed);  
	}  
	
	/** 
	 * 使用公钥判断签名是否与数据匹配 
	 * @param data 数据 
	 * @param sign 签名 
	 * @param publicKeyString 公钥 
	 * @return 是否篡改了数据 
	 */  
	public static boolean verify(String data, String sign) throws Exception {  
	    KeyFactory keyf = KeyFactory.getInstance("RSA");  
	    PublicKey publicKey = keyf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(PUBLICKEY)));  
	    java.security.Signature signet = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
	    signet.initVerify(publicKey);  
	    signet.update(data.getBytes("utf-8"));  
	    return signet.verify(Base64.decodeBase64(sign));  
	} 
	
	
	public static void main(String[] args) throws Exception{
		//createKeyPair(PUBLICPREFIX, PRIVATEPREFIX);
		String data = "1527947446186&{\"param1\":\"参数1\",\"param2\":\"参数2\"}";
		String encryData = sign(data);
		System.out.println("加密内容字符串："+encryData);
		String sign = "Vhf/Y08xacpJQlALxHsD2srDaETwOm1tzHAzozwdqQfcmdeB2nKs0NyKMO4PwonH19/MYbUX2FGJVbzGRBDtqI+ejc7/vVJ11CHWxgoPEyt5/oamu2ue2XodPdy60KEIPYFwNMFX/sTJvTH1TdxEP2dg/Km4mmzM/5y/Oh0fckY=";
		System.out.println(verify(data, sign));
		//encryData = "W6vunw1u6EjNcVgelXcufWXslPGPS7yfUeQKwdEfO3G27BpiUxWesRLXP0PyueY8mOYOL0/ububJoPC+X0XkvcCrOnIiuVM3/oRQu87YrYUuetyi+81k4S3f37CtR9rPiqsAF1LGvWkCO72ISVNOVqeuzNp2ieHLF6Ix0g7z+Q8=";
		//System.out.println("解密内容字符串："+verify(data, encryData));
		//String source = "{\"token\":\"123123131231283910.2312312weqew231ewewq\",\"id\":\"526009928271992872\",\"phone\":\"1892099122333\",\"userName\":\"Lee TPhone\",\"hImg\":\"https://yt3.ggpht.com/a-/AAuE7mBs1Oghg-r2dMNrlsm3IJrNgqV1Ob5rWvsr7A=s288-mo-c-c0xffffffff-rj-k-no\",\"balance\":\"1000\"}";
		//String data = encryptWithRSA(source);
		//String data = "lG4goHe1R4e3rB19h3xw8DqZdKFD3/Q/x5UuZpOvlBdGJ9PeunxsYj2FZ5unDahHfZc1bf9VxjhpBUXL7zxN4NO/E4wYSxhVbhCC0fvpCH+s1b37NBF8VUHmQ/bRnLD4XR1ANoiSUz/aY1zUJMzDNuxxBxiRchv2GmWJLKNcMBqZOOD+LmhmSCmHxRZQp4qjr8b0HVfG7rPs/X0djkLj50YHgRbifYXk6Zka3++31NBZh1pioNLdUbuLLuTxQcxezhU0ehniF8Fg/TIkXiI93ZNbU0cmYthmT7aoMpVFUr0McnuuB7JEiwm4aweGfLyNna9UqFFbuM/oGf4HnHC9VB/LzBehwKz/7FwtwRE8/nlMnF6F/OrjsWocsWzsJ35JAdVzVT+lUT6zOrA1IWv1fNMCVEtip4HiKm5FdjEvgmFwDanaqsL9KJzUZWbru3Fm5cINtOdPJ6CvRmfwU5EhdAuyjwsa6jz5r1iPWq50D56wpNfOPQDj5zc8XRJeKbzo";
		//System.out.println("加密内容字符串："+data);
		//System.out.println("解密内容字符串："+decryptWithRSA(data));
	}
}