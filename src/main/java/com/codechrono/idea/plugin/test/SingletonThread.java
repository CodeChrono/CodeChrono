package com.codechrono.idea.plugin.test;

public class SingletonThread {
    private static volatile SingletonThread instance; // 使用volatile保证多线程环境下的可见性和有序性
    private static volatile MyThread myThread ;
    private static volatile Thread t1;
    private SingletonThread() { // 私有构造函数，防止外部直接实例化
        // 初始化代码
    }

    public static synchronized SingletonThread getInstance() { // 使用synchronized关键字保证线程安全
        if (instance == null) {
            instance = new SingletonThread();
            instance.myThread=new MyThread();
            instance.t1=new Thread(myThread);

        }
        return instance;
    }

    public void run() {
        GlobalKeyListenerExample.main1();
      //  t1.start();
        System.out.println("当前线程：" + Thread.currentThread().getName());
    }



}
