package com.github.begoodyourself.api.util;

import java.util.regex.Pattern;

/**
 * Created with rpc
 *
 * @author ; BEGOODYOURSELF
 *         DATE : 2016/9/17
 */
public interface Constants {
    String INTERFACE_KEY = "";
    String BACKUP_KEY = "backup";
    String DEFAULT_KEY_PREFIX ="default.";
    Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
    String LOCALHOST_KEY = "localhost";
    String ANYHOST_KEY = "anyhost";
    String ANYHOST_VALUE = "0.0.0.0";
    String GROUP_KEY = "group";
    String VERSION_KEY = "version";
    String COMMA_SEPARATOR = ",";
    String THREAD_NAME_KEY = "threadname";
    String DEFAULT_THREAD_NAME = "RPC";
    String CORE_THREADS_KEY = "corethreads";
    int DEFAULT_CORE_THREADS = 0;
    String THREADS_KEY = "threads";
    String QUEUES_KEY = "queues";
    int DEFAULT_QUEUES = 0;
    String ALIVE_KEY = "alive";
    int DEFAULT_ALIVE = 60 * 1000;
    String deafult_registryPath ="/path";
}
