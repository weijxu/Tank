package com.weijx.tank;

import java.util.Random;

/**
 * @Auther: weijx
 * @Date: 2020/10/23 - 10 - 23 - 16:57
 * @Description: com.weijx.tank
 * @version: 1.0
 *
 * 随机产生敌人坦克
 */
public class TankUtil {

    private int x=0,y=0;
    private Random random = new Random(System.currentTimeMillis());

    private TankFrame tankFrame;

    public TankUtil(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
    }

    //产生坦克，但是坦克总数不能大于10
    public void  productTank(){
        if(tankFrame.tanks.size() > 9){
            return;
        }
        if(random.nextInt(100) >95){

            tankFrame.tanks.add(new Tank(random.nextInt(750) ,random.nextInt(250)+50 , Dir.DOWN,Camp.ENEMY, tankFrame));
        }
    }
}
