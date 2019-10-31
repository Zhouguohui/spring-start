package com.spring.start.cache.impl;


import com.spring.start.spring.SpringUtils;

/**
 * 通用配置数据获取
 */
public class TopUP {

    private static final TopConfig topConfig = SpringUtils.getBean(TopConfig.class);
    private static final TopInfo topInfo = SpringUtils.getBean(TopInfo.class);
    /**
     * 获取统一配置文件
     * @param sKey
     * @param sParms
     * @return
     */
    public static String upConfig(String sKey, Object... sParms){
        String str = topConfig.upValue(sKey);
        if(sParms.length==0){
            return str;
        }
        return String.format(str,sParms);
    }

    /**
     * 获取顶级配置文件
     * @param sKey
     * @param sParms
     * @return
     */
    public static String upInfo(String sKey, Object... sParms){
        String str = topInfo.upValue(sKey);
        if(sParms.length==0){
            return str;
        }
        return String.format(str,sParms);
    }

}
