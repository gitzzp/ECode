package com.gitzzp.ecode.baselib.utils;

import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 创建人：gitzzp
 * 创建日期:17/3/24 14:28
 * 类描述:
 */
public class SecurityUtil {

    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private static String desKey = "@$s1j4jz";

    public static enum EncryptMode{
        ENCRYPT,DECRYPT;
    }


    public static @Nullable byte[] DESEncrypt(byte[] data){
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(desKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            //des加密之后是byte数组 不要转字符串 直接base64 否则结果不同
//            return new String(Base64.encode(bytes, 0, bytes.length, Base64.NO_WRAP), "UTF-8");
            return bytes;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static @Nullable byte[] DESDecrypt(byte[] data){
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(desKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            //des加密之后是byte数组 不要转字符串 直接base64 否则结果不同
//            return new String(Base64.encode(bytes, 0, bytes.length, Base64.NO_WRAP), "UTF-8");
            return bytes;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static void DESEncryptFile(String tempPath, String path){
        LogUtil.d("开始加密","时间为："+ SystemClock.currentThreadTimeMillis());
        File f = new File(tempPath);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = new DataOutputStream(new FileOutputStream(path));
            inputStream = new FileInputStream(f);
            byte[] bytes = new byte[1024*1024];
            while (inputStream.read(bytes)!=-1){
                byte[] encryptByte = DESEncrypt(bytes);
                outputStream.write(encryptByte,0,encryptByte.length);
                //不管加密长度是多少 每次加密之后都会多8个字节的长度
//                LogUtil.d("加密长度","每次1024*1024字节加密后的长度为:"+encryptByte.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.flush();
                inputStream.close();
                outputStream.close();
                f.delete();
            }catch (Exception e){

            }
        }
        LogUtil.d("结束加密","时间为："+ SystemClock.currentThreadTimeMillis());
    }

    public static String MD5(String value) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        try {
            byte[] byteArray = value.getBytes("utf-8");

            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return "";
    }

}
