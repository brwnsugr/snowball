package com.example.snowball.util;

import javax.servlet.http.HttpServletRequest;

public class NetUtil {

  public static String getRemoteIp(HttpServletRequest request) {
    String ip = request.getHeader("X-FORWARDED-FOR");

    if(ip == null || ip.length() == 0) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if(ip == null || ip.length() == 0) {
      ip = request.getRemoteAddr();
    }

    return ip;
  }
}
