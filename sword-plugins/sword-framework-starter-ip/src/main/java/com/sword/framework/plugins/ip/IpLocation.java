package com.sword.framework.plugins.ip;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.sword.framework.plugins.ip.model.LocationInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * IP位置工具类
 * Created by shujian.ou 2023/4/24 16:57
 */
@Slf4j
public class IpLocation {

    private Searcher searcher = null;

    /**
     * IP库下载地址
     */
    private static final String IP_DB_URL = "https://img-oss-test.1715.com/default/ip2region.xdb";


    private static final class InstanceHolder {
        private static final IpLocation INSTANCE = new IpLocation();
    }

    public static IpLocation getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public IpLocation() {
        try {
            File dbFile = FileUtil.file("ip/ip2region.xdb");
            if (!FileUtil.exist(dbFile)) {
                HttpUtil.downloadFile(IP_DB_URL, dbFile);
            }
            searcher = Searcher.newWithFileOnly(dbFile.getPath());
        } catch (IOException e) {
            log.error("加载IP库失败", e);
        }
    }

    /**
     * 获取客户端IP位置信息
     *
     * @param request 请求
     * @return IP位置信息
     */
    public LocationInfo getLocation(HttpServletRequest request) throws Exception {
        return getLocation(getClientIp(request));
    }

    /**
     * 获取位置信息
     *
     * @param ip ip地址
     * @return 位置信息
     */
    public LocationInfo getLocation(String ip) throws Exception {
        List<String> split = StrUtil.split(searcher.search(ip), "|");
        return LocationInfo.builder()
                .ip(ip)
                .country(getStr(split.get(0)))
                .region(getStr(split.get(1)))
                .province(replaceStrSuffix(getStr(split.get(2))))
                .city(replaceStrSuffix(getStr(split.get(3))))
                .isp(getStr(split.get(4))).build();
    }

    /**
     * 获取客户端IP国家信息
     *
     * @param request 请求
     * @return 国家
     */
    public String getCountry(HttpServletRequest request) throws Exception {
        String country = getLocation(request).getCountry();
        return getStr(country);
    }

    /**
     * 获取客户端IP国家信息
     *
     * @param ip ip地址
     * @return 国家
     */
    public String getCountry(String ip) throws Exception {
        String country = getLocation(ip).getCountry();
        return getStr(country);
    }

    /**
     * 获取客户端IP地区信息
     *
     * @param request 请求
     * @return 地区
     */
    public String getRegion(HttpServletRequest request) throws Exception {
        String region = getLocation(request).getRegion();
        return getStr(region);
    }


    /**
     * 获取客户端IP地区信息
     *
     * @param ip ip地址
     * @return 地区
     */
    public String getRegion(String ip) throws Exception {
        String region = getLocation(ip).getRegion();
        return getStr(region);
    }

    /**
     * 获取客户端IP省份信息
     *
     * @param request   请求
     * @param separator 分隔符
     * @return 省份
     */
    public String getProvince(HttpServletRequest request, String separator) throws Exception {
        LocationInfo location = getLocation(request);
        if ("0".equals(location.getCountry())) {
            return "未知";
        }
        if ("0".equals(location.getProvince())) {
            return location.getCountry();
        }
        return location.getCountry() + separator + location.getProvince();
    }


    /**
     * 获取客户端IP省份信息
     *
     * @param ip        ip地址
     * @param separator 分隔符
     * @return 省份
     */
    public String getProvince(String ip, String separator) throws Exception {
        LocationInfo location = getLocation(ip);
        if ("0".equals(location.getCountry())) {
            return "未知";
        }
        if ("0".equals(location.getProvince())) {
            return location.getCountry();
        }
        return location.getCountry() + separator + location.getProvince();
    }

    /**
     * 获取客户端IP简单省份信息
     *
     * @param request 请求
     * @return 城市
     */
    public String getSimpleProvince(HttpServletRequest request) throws Exception {
        String province = getLocation(request).getProvince();
        return getStr(province);
    }

    /**
     * 获取客户端IP简单省份信息
     *
     * @param ip ip地址
     * @return 城市
     */
    public String getSimpleProvince(String ip) throws Exception {
        String province = getLocation(ip).getProvince();
        return getStr(province);
    }

    /**
     * 获取客户端IP城市信息
     *
     * @param request   请求
     * @param separator 分隔符
     * @return 城市
     */
    public String getCity(HttpServletRequest request, String separator) throws Exception {
        LocationInfo location = getLocation(request);
        if ("0".equals(location.getCountry())) {
            return "未知";
        }
        if ("0".equals(location.getProvince())) {
            return location.getCountry();
        }

        if ("0".equals(location.getCity())) {
            return location.getCountry() + separator + location.getProvince();
        }
        return location.getCountry() + separator + location.getProvince() + separator + location.getCity();
    }

    /**
     * 获取客户端IP城市信息
     *
     * @param ip        ip地址
     * @param separator 分隔符
     * @return 城市
     */
    public String getCity(String ip, String separator) throws Exception {
        LocationInfo location = getLocation(ip);
        if ("0".equals(location.getCountry())) {
            return "未知";
        }
        if ("0".equals(location.getProvince())) {
            return location.getCountry();
        }

        if ("0".equals(location.getCity())) {
            return location.getCountry() + separator + location.getProvince();
        }
        return location.getCountry() + separator + location.getProvince() + separator + location.getCity();
    }


    /**
     * 获取客户端IP简单城市信息
     *
     * @param request 请求
     * @return 城市
     */
    public String getSimpleCity(HttpServletRequest request) throws Exception {
        String city = getLocation(request).getCity();
        return getStr(city);
    }

    /**
     * 获取客户端IP简单城市信息
     *
     * @param ip ip地址
     * @return 城市
     */
    public String getSimpleCity(String ip) throws Exception {
        String city = getLocation(ip).getCity();
        return getStr(city);
    }

    /**
     * 获取客户端IP运营商信息
     *
     * @param request 请求
     * @return 运营商
     */
    public String getIsp(HttpServletRequest request) throws Exception {
        String isp = getLocation(request).getIsp();
        return getStr(isp);
    }

    private String getStr(String str) {
        return replaceStrSuffix("0".equals(str) ? "未知" : str);
    }

    private String replaceStrSuffix(String str) {
        return StrUtil.replace(str, "省", "").replace("市", "");
    }

    /**
     * 获取客户端IP运营商信息
     *
     * @param ip ip地址
     * @return 运营商
     */
    public String getIsp(String ip) throws Exception {
        String isp = getLocation(ip).getIsp();
        return getStr(isp);
    }

    /**
     * 获取用户客户端IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public String getClientIp(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

}
