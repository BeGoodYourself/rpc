package com.github.begoodyourself.api.util;

import com.google.protobuf.GeneratedMessageV3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProtoUtils {
    private ProtoUtils(){}

    private static Map<String, GeneratedMessageV3> caches = new HashMap<>();

    public static GeneratedMessageV3 find(String clsName)throws Exception{
        GeneratedMessageV3 r = caches.get(clsName);
        synchronized (ProtoUtils.class){
            r = caches.get(clsName);
            if(r == null){
                r = (GeneratedMessageV3) Class.forName(clsName).getMethod("getDefaultInstance").invoke(null);
                caches.put(clsName, r);
            }
        }
        return r;
    }

}
