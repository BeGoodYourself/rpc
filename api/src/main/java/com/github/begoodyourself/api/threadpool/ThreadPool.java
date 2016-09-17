package com.github.begoodyourself.api.threadpool;


import com.github.begoodyourself.api.bo.URL;

import java.util.concurrent.Executor;

/**
 * Created with rpc
 * @author ; BEGOODYOURSELF
 * DATE : 2016/9/17
 *
 */
public interface ThreadPool {

    Executor getExecutor(URL url);
}
