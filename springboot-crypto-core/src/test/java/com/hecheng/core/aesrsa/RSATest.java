package com.hecheng.core.aesrsa;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSATest {
  /**
   * RSA公钥加密
   *
   * @param str 加密字符串
   * @param publicKey 公钥
   * @return 密文
   * @throws Exception 加密过程中的异常信息
   */
  public static String encrypt(String str, String publicKey) throws Exception {
    // base64编码的公钥
    byte[] decoded = Base64.decodeBase64(publicKey);
    RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
        .generatePublic(new X509EncodedKeySpec(decoded));
    // RSA加密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
    return outStr;
  }

  public static void main(String[] args) {
    try {
      String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCghbotDm7Un/8eUanDQ+FtryIQyx+454Wggyr5b5q0SfXdP6JL+9RzVfHAupAB9JR+SLNuzom8RobjGe5QF3MP+/phUBs7vYzYVhsm9Uo0T2xBo3ZhoyRwspDebJqmCB/NY2T6Swglv2/HiZd1rfVR0CgpVhxYGOcn0egTiLQVIwIDAQAB";
      System.out.println(RSATest.encrypt("9999999999999999", key));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
