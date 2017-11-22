package me.david.messagecore.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Gettigs the harwareid and the mac from current machine
 */
public final class AuthUtil {

    public AuthUtil() {}

    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder s = new StringBuilder();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
        byte[] md5 = messageDigest.digest(main.getBytes("UTF-8"));
        int i = 0;
        for (byte b : md5) {
            s.append(Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3));
            if (i != md5.length - 1) s.append("-");
            i++;
        }
        return s.toString();
    }

    public static String getMAC(InetAddress ip){
        StringBuilder sb = new StringBuilder();
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            for (int i = 0; i < mac.length; i++)
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
