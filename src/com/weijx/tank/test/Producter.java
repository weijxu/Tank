package com.weijx.tank.test;

/**
 * @Auther: weijx
 * @Date: 2020/10/23 - 10 - 23 - 11:33
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class Producter implements Runnable {
    private TestConPro testConPro;

    public Producter(TestConPro testConPro) {
        this.testConPro = testConPro;
    }

    @Override
    public void run() {
        synchronized (testConPro) {
            while (true) {
                if (testConPro.getList().size() == 0) {
                    for (int i = 0; i < 20; i++) {
                        testConPro.getList().add(Thread.currentThread().getName() + "哇哈哈"+ i);
                        System.out.println("生产者" + Thread.currentThread().getName() + "生产了商品" + testConPro.getList().get(i) );
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("生产者"+Thread.currentThread().getName()+"叫醒消费者");
                    testConPro.notifyAll();

                } else if (testConPro.getList().size() > 0 ) {
                    try {
                        System.out.println("生产者"+Thread.currentThread().getName()+"等待");
                        testConPro.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }
}
