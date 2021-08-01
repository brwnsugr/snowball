package com.example.snowball.util;

import java.nio.charset.Charset;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Util {
  public static String bin2hex(byte[] data)
  {
    if (data==null)
    {
      return null;
    }

    int len = data.length;
    StringBuffer str = new StringBuffer();
    for (int i=0; i<len; i++) {
      if ((data[i]&0xFF)<16)
        str.append("0" + java.lang.Integer.toHexString(data[i]&0xFF));
      else
        str.append(java.lang.Integer.toHexString(data[i]&0xFF));
    }
    return str.toString();
  }

  public static byte[] hex2bin(String str) {
    if (str==null) {
      return null;
    } else if (str.length() < 2) {
      return null;
    } else {
      int len = str.length() / 2;
      byte[] buffer = new byte[len];
      for (int i=0; i<len; i++) {
        buffer[i] = (byte) Integer.parseInt(str.substring(i*2,i*2+2),16);
      }
      return buffer;
    }
  }

  public static String encrypt(String plaintext, String pwd) throws Exception {
    String salt = pwd;
    String iv = pwd;
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    KeySpec spec = new PBEKeySpec(pwd.toCharArray(), hex2bin(salt), 1024, 256);
    SecretKey tmp = factory.generateSecret(spec);
    SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

    byte[] iv_bytes = hex2bin(iv);
//    String value = new String(iv_bytes, "UTF-8");
    IvParameterSpec ivspec = new IvParameterSpec(iv_bytes);

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secret, ivspec);

    byte[] encrypted = cipher.doFinal(plaintext.getBytes(Charset.forName("UTF-8")));

    return bin2hex(encrypted);
  }

  public static String decrypt(String payload, String pwd) throws Exception {
    String salt = pwd;
    String iv = pwd;
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    KeySpec spec = new PBEKeySpec(pwd.toCharArray(), hex2bin(salt), 1024, 256);
    SecretKey tmp = factory.generateSecret(spec);
    SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

    byte[] iv_bytes = hex2bin(iv);
//    String value = new String(iv_bytes, "UTF-8");
    IvParameterSpec ivspec = new IvParameterSpec(iv_bytes);

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secret, ivspec);

    byte[] payload_bytes = hex2bin(payload);
    byte[] decrypted = cipher.doFinal(payload_bytes);

    return new String(decrypted, "UTF-8");
  }

}
