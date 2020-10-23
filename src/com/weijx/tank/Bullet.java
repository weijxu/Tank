package com.weijx.tank;

import java.awt.*;

/**
 * @Auther: weijx
 * @Date: 2020/10/21 - 10 - 21 - 11:14
 * @Description: com.weijx.tank
 * @version: 1.0
 * 子弹
 */
public class Bullet {
    private static  final  int SPEED = 5;
    //坦克的坐标
    private int x,y;
    private Dir dir;
    private static int WIDTH = ResourceMgr.bulletL.getWidth(), HEIGHT = ResourceMgr.bulletL.getHeight();
    //子弹的坐标
    private int bulletX , bulletY ;
    //子弹阵营标注
    private Camp bulletCamp = Camp.ENEMY;
    //是否存活
    private boolean live = true;
    TankFrame tankFrame = null;

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir,Camp bulletCamp, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.bulletCamp = bulletCamp;
        this.tankFrame = tankFrame;
        bulletX = x+(Tank.getWIDTH() / 2) - (WIDTH / 2);
        bulletY = y+(Tank.getHEIGHT() / 2) - (HEIGHT / 2);
    }

    public static int getSPEED() {
        return SPEED;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }


    public void paint(Graphics g) {
        //如果子弹飞出边界或则与敌人相撞，子弹死亡
        if(!live){

            tankFrame.bullets.remove(this);
        }
        Color c = g.getColor();
        if(Dir.DOWN == dir){
            g.drawImage(ResourceMgr.bulletD,bulletX,bulletY,null);
        }
        if(Dir.UP == dir){
            g.drawImage(ResourceMgr.bulletU,bulletX,bulletY,null);
        }
        if(Dir.LEFT == dir){
            g.drawImage(ResourceMgr.bulletL,bulletX,bulletY,null);
        }
        if(Dir.RIGHT == dir){
            g.drawImage(ResourceMgr.bulletR,bulletX,bulletY,null);
        }
        move();

    }


    private void move(){

        switch (dir){
            case LEFT:
                bulletX -=SPEED;
                break;
            case RIGHT:
                bulletX +=SPEED;
                break;
            case DOWN:
                bulletY +=SPEED;
                break;
            case UP:
                bulletY -=SPEED;
                break;

        }
        if(bulletX < 0 || bulletY < 0 || bulletX > TankFrame.GAME_WIDTH || bulletY > TankFrame.GAME_HEIGHT){

           live = false;
        }

    }

    //子弹打击坦克，共同死亡，但是无队友伤害，子弹会穿过友军坦克
    public void collideWith(Tank tank) {

        //判断是否为友军
        if(this.bulletCamp == tank.getTankCamp()){
            return;
        }else{
            //非友军
            Rectangle rectangleBullet = new Rectangle(this.bulletX, this.bulletY, WIDTH, HEIGHT);
            Rectangle rectangleTank = new Rectangle(tank.getX(), tank.getY(), Tank.getWIDTH(), Tank.getHEIGHT());
            if(rectangleBullet.intersects(rectangleTank)){
                tank.die();
                this.die();
            }
        }

    }

    private void die() {
        this.live = false;
    }
}
