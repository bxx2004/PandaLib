package net.bxx2004.pandalibloader;

import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerUtils {
    public static String getUrl(){
        try {
            URL url = new URL("https://gitee.com/bxx2004/security-verification/raw/master/url");
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String A = br.readLine();
            br.close();
            return A;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public static List<String> getPluginList(URL url){
        List<String> list = new ArrayList<>();
        try {
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            Iterator<String> i = br.lines().iterator();
            while (i.hasNext()){
                list.add(i.next());
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static String get256(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}

