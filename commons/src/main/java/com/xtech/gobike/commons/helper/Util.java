package com.xtech.gobike.commons.helper;

import com.xtech.gobike.commons.exception.XhException;
import com.xtech.gobike.commons.log.Log;
import com.xtech.gobike.commons.log.LogFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util {

    private static Log log = LogFactory.getLog(Util.class);

    public static <T> T defaultValue(T object, T defaultValue) {
        return object != null ?
                object
                : defaultValue;
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }


    public static void writeFile(String path, String content) {

        try {
            File file = new File(path);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(path, false);
            String c = content + "\r\n";
            fw.write(c);
            fw.close();
        } catch (IOException e) {
            log.error(new XhException(e));
        }

    }

    /**
     * 读取数据
     *
     * @return
     */
    public static String readFile(String path) {

        StringBuffer content = new StringBuffer();
        try {
            File file = new File(path);
            if(file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
                String data = "";
                while ((data = br.readLine()) != null) {
                    content.append(data);
                }
                br.close();
            }
        } catch (IOException e) {
            log.error(new XhException(e));
        }
        return content + "";
    }

    /**
     * 按行读取数据
     * @param path
     * @return
     */
    public static List<String>readFileLines(String path){
        List<String> lines = new ArrayList<String>();
        try {
            File file = new File(path);
            if(file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
                String data = "";
                while ((data = br.readLine()) != null) {
                    lines.add(data);
                }
                br.close();
            }
        } catch (IOException e) {
            log.error(new XhException(e));
        }
        return lines;
    }

    /**
     * 逐行写入
     * @param path
     * @param content
     */
    public static void writeFileLine(String path,String content){
        try {
            File file = new File(path);
            if (!file.exists()) {
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
            }
            FileWriter fw = new FileWriter(path, true);
            String c = content + "\r\n";
            fw.write(c);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取md5值
     *
     * @return md5值
     */
    public static String getMd5(String oldStr) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.reset();
            mdInst.update(oldStr.getBytes("UTF-8"));
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            log.error(new XhException(e));
            return null;
        }
    }

    /**
     * 获取文件md5值
     *
     * @param file
     * @return md5值
     */
    public static String getFileMd5(File file) {

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte[] md = digest.digest();
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        String ddd = new String(str).toLowerCase();

        return ddd;
    }

    /**
     * 解压指定zip文件
     *
     * @param zipfile
     *            压缩文件的路径
     * @param destFileDir
     *            　　　解压到的目录　
     */
    public static void unZipFile(String zipfile, String destFileDir) {

        byte[] buf = new byte[512];
        int readedBytes;
        try {
            FileOutputStream fileOut;
            File file;
            InputStream inputStream;
            ZipFile zipFile = new ZipFile(zipfile);

            for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                file = new File(destFileDir + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // 如果指定文件的目录不存在,则创建之
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    inputStream = zipFile.getInputStream(entry);
                    fileOut = new FileOutputStream(file);
                    while ((readedBytes = inputStream.read(buf)) > 0) {
                        fileOut.write(buf, 0, readedBytes);
                    }
                    fileOut.close();
                    inputStream.close();
                }

            }
            zipFile.close();
        } catch (Exception e) {
            log.error(new XhException(e));
        }
    }

    /**
     * 压缩
     * @param str
     * @return
     */
    public static byte[] compressToByte(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes("UTF-8"));
            gzip.close();
        } catch (IOException e) {
            log.error(new XhException(e));
        }
        return out.toByteArray();
    }



    public static String unZip(byte[] data) {
        String reData = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            reData = out.toString("UTF-8");
        } catch (IOException e) {
            log.error(new XhException(e));
        }
        return reData;

    }

    public static final String toHex(byte hash[]) {
        if (hash == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }


    public static String toStr(byte[] datas) {
        if (datas == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (datas != null && datas.length > 0) {
            for (byte d : datas) {
                sb.append((char) (d));
            }
        }
        return sb.toString();
    }

    /**十六进制转换为十进制*/
    public static int makeUint8(byte b) {
        return (0xff & b);
    }

    public static int makeUint16(byte b, byte c) {
        return (0xff & b) << 8 | (0xff & c);
    }

    public static int makeUint3(byte d,byte b, byte c) {
        return ((0xff & d) << 16)|((0xff & b) << 8) | (0xff & c);
    }

    public static int makeUint32(byte b, byte c, byte d, byte e) {
        return (((0xff & b) << 24) | ((0xff & c) << 16) | ((0xff & d) << 8) | ((0xff & e) << 0));
    }
    /**十进制转换为2进制*/
    public static byte[] makeByte4(int v) {
        byte[] buf = { (byte) ((v & 0xff000000) >> 32),
                (byte) ((v & 0xff0000) >> 16), (byte) ((v & 0xff00) >> 8),
                (byte) (v & 0xff) };
        return buf;
    }

    public static byte[] makeByte3(int v) {
        byte[] buf = { (byte) ((v & 0xff0000) >> 16),
                (byte) ((v & 0xff00) >> 8), (byte) (v & 0xff) };
        return buf;
    }

    public static byte[] makeByte2(int v) {
        byte[] buf = { (byte) ((v & 0xff00) >> 8), (byte) (v & 0xff) };
        return buf;
    }
    /**十进制转换为十六进制*/
    public static byte makeByte1(int v) {
        return (byte) (v & 0xff);
    }

    /**long转byte**/
    public static byte[] longToByte8(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();//
                    temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * byte 转long
     * @param b
     * @return
     */
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    public static byte[] toAssciiByte(String str){
        char[] strs = str.toCharArray();

        byte[] strB = new byte[strs.length];
        for(int i = 0;i<strs.length;i++){
            char s = strs[i];
            strB[i] = Integer.valueOf(s).byteValue();
        }

        return strB;
    }

    public static int calFCC(byte[] buffer,int beginNo, int endNo){
        int sumOfFCC = 0;
        for(int i=beginNo; i<=endNo; i++){
            sumOfFCC +=  makeUint8(buffer[i-1]);
        }
        return sumOfFCC;
    }

    public static String getExtensionName(File file) {
        if(!file.exists() && file.isDirectory()){
            return null;
        }
        String filename = file.getName();
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(new XhException(e));
        }
    }

}
