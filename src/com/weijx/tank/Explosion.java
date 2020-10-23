package com.weijx.tank;

import java.awt.*;

/**
 * @Auther: weijx
 * @Date: 2020/10/21 - 10 - 21 - 11:14
 * @Description: com.weijx.tank
 * @version: 1.0
 * 子弹
 */
public class Explosion {
    //爆炸的坐标
    private int x,y;
    private static int WIDTH = ResourceMgr.explosions[0].getWidth(), HEIGHT = ResourceMgr.explosions[0].getHeight();
    //是否存活
    private boolean live = true;
    TankFrame tankFrame = null;
    //爆炸图片执行张数
    private  int count = 0;

    public Explosion() {
    }

    public Explosion(int x, int y,  TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;


    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }




    public void paint(Graphics g) {
        //如果子弹飞出边界或则与敌人相撞，子弹死亡
        if(count == 16){
            die();
        }
        if(!live){
            tankFrame.explosions.remove(this);
        }
        Color c = g.getColor();

        if(count < ResourceMgr.explosions.length) {
            g.drawImage(ResourceMgr.explosions[count], x, y, null);
            count++;
            //添加爆炸音效，太卡了
//            if(count == 3){
//                new Audio("audio/explode.wav").play();
//            }

        }


    }








    private void die() {
        this.live = false;
    }
}
