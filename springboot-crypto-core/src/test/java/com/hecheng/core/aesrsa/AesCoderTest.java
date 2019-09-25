package com.hecheng.core.aesrsa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hecheng.core.common.utils.crypto.aesrsa.AESCoder;

/**
 * AES 对称加解密数据用例
 *
 * @author steellee
 * @version V1.0.0
 * @date 2019/01/19
 */
public class AesCoderTest {

  @Test
  public void test() {
    //PzaEznCCAI5BTMTR4no6LA==
    String inputStr = "test";

    // 针对CBC模式，需要16位
    String key = "9999999999999999";
    System.err.println("原文:\t" + inputStr);
    System.err.println("密钥:\t" + key);

    String inputData = AESCoder.encryptToBase64(inputStr, key);

    System.err.println("加密后:\t" + inputData);

    String outputData = AESCoder.decryptFromBase64(inputData, key);

    System.err.println("解密后:\t" + outputData);

    assertEquals(inputStr, outputData);
  }
}
