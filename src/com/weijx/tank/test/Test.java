package com.weijx.tank.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: weijx
 * @Date: 2020/10/22 - 10 - 22 - 15:26
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class Test extends Thread{

    private static ReentrantLock reentrantLock = new ReentrantLock(true);
    int a = 20;
    @Override
    public void run() {
        for(int i = 0; i < 50; i++){
            try {
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName()+"正在执行"+i+"a为"+a);
               if(a > 0){
                   a--;
               }
            }finally {
                reentrantLock.unlock();
            }

        }
    }

    public static void main(String[] args) {

        TestConPro testConPro = new TestConPro();
        Counsumer counsumer1 = new Counsumer(testConPro);
        Counsumer counsumer2 = new Counsumer(testConPro);
        Producter producter1 = new Producter(testConPro);
        Producter producter2 = new Producter(testConPro);
        Producter producter3 = new Producter(testConPro);

        Thread t1 = new Thread(counsumer1,"一");
        Thread t2 = new Thread(counsumer2,"二");
        Thread t3 = new Thread(producter1,"一");
        Thread t4 = new Thread(producter2,"二");
        Thread t5 = new Thread(producter3,"三");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
