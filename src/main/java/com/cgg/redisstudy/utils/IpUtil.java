package com.cgg.redisstudy.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
public class IpUtil {


    private IpUtil() {
        throw new IllegalStateException("Utility class");
    }


    public static String getIpAddress(HttpServletRequest request) {
        String unknown = "unknown";
        String localIp = "127.0.0.1";
        String localIp6 = "0:0:0:0:0:0:0:1";
        String ip = request.getHeader("x-forwarded-for");

        if (getResult(unknown, ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (getResult(unknown, ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (getResult(unknown, ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (getResult(unknown, ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (getResult(unknown, ip)) {
            ip = request.getRemoteAddr();
            if (localIp.equals(ip) || localIp6.equals(ip)) {
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("Ip解析失败",e);
                    throw new IllegalArgumentException("Ip解析失败");
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }


    private static boolean getResult(String unknown, String ip) {
        return StringUtils.isBlank(ip) || StringUtils.endsWithIgnoreCase(unknown, ip);
    }
}
