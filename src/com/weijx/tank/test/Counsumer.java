package com.weijx.tank.test;

/**
 * @Auther: weijx
 * @Date: 2020/10/23 - 10 - 23 - 11:33
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class Counsumer implements Runnable {
    private TestConPro testConPro;

    public Counsumer(TestConPro testConPro) {
        this.testConPro = testConPro;
    }

    @Override
    public  void run() {
        synchronized (testConPro){
            while (true){
                if(testConPro.getList().size() == 0){
                    try {
                        System.out.println("消费者"+Thread.currentThread().getName()+"等待");
                        testConPro.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(testConPro.getList().size() > 0){
                    for (int i = 0; i < testConPro.getList().size(); i++) {
                        System.out.println("消费者"+Thread.currentThread().getName()+"消费了商品"+testConPro.getList().get(i));
                        testConPro.getList().remove(i);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("消费者"+Thread.currentThread().getName()+"叫醒生产者");
                    testConPro.notifyAll();

                }
            }
        }

    }
}
