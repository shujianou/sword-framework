package com.sword.framework.plugins.ip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 位置信息
 * Created by shujian.ou 2023/4/24 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationInfo implements Serializable {

    /**
     * IP
     */
    private String ip;

    /**
     * 国家
     */
    private String country;

    /**
     * 地区
     */
    private String region;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商
     */
    private String isp;
}
