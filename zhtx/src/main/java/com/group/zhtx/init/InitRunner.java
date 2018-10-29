package com.group.zhtx.init;



import com.group.zhtx.thread.AsyncThreadManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;




/*
    程序启动前进行一系列初始化，例如线程池初始化
 */
@Component
public class InitRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(InitRunner.class);



    @Override
    public void run(String... args) throws Exception {

        /*
            设置创建5个线程，5个优先级，默认循环时间为100毫秒
         */
        AsyncThreadManager.init(5,5,100);
        System.out.println("启动线程");
    }
}
