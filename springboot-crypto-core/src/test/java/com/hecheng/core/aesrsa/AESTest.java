package com.hecheng.core.aesrsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESTest {

  public static String Encrypt(String sSrc, String sKey) throws Exception {
    if (sKey == null) {
      System.out.print("Key为空null");
      return null;
    }
    // 判断Key是否为16位
    if (sKey.length() != 16) {
      System.out.print("Key长度不是16位");
      return null;
    }
    byte[] raw = sKey.getBytes("utf-8");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

    return new Base64().encodeToString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
  }

  public static void main(String[] args) {
    try {
      System.out.println(AESTest.Encrypt("test", "9999999999999999"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
