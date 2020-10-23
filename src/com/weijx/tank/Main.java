package com.weijx.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * @Auther: weijx
 * @Date: 2020/10/19 - 10 - 19 - 20:44
 * @Description: com.weijx.tank
 * @version: 1.0
 * 主程序，思路
 * 1，要先有屏幕
 * 2，能在屏幕上画东西
 */
public class Main {
    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis()-10000);

        //创建屏幕
        TankFrame tankFrame = TankFrame.getTankFrame();
        TankUtil tankUtil = new TankUtil(tankFrame);
        //初始时敌方坦克
        for (int i = 0; i < 5; i++) {
            tankFrame.tanks.add(new Tank(100 + i*150,50 + i*20, Dir.DOWN,Camp.ENEMY, tankFrame));
        }

        while(tankFrame.myTank.isLiving() ){
            if(tankFrame.tanks.size() == 0){
                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //随机生产坦克
            if(random.nextInt(70)>65){
                tankUtil.productTank();
            }

            //重新画屏幕
            tankFrame.repaint();
        }
    }
}
