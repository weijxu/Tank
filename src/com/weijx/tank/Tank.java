package com.weijx.tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Auther: weijx
 * @Date: 2020/10/20 - 10 - 20 - 20:31
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class Tank {
    
    //位置
    private int x,y;
    //方向
    private Dir dir =Dir.DOWN;
    //速度
    private static  int SPEED = 5;

    private boolean moving = false;
    //是否存活
    private boolean living = true;

    //随机数，用于敌方坦克开火和
    private Random random = new Random((long)(Math.random()*1000));
    //坦克阵营标注
    private Camp tankCamp = Camp.ENEMY;

    private TankFrame tankFrame = null;

    //计算积分
    private static int score = 0;

    private static int WIDTH = ResourceMgr.tankGoodD.getWidth(), HEIGHT = ResourceMgr.tankGoodD.getHeight();
    //坦克图片
    private BufferedImage imageD = ResourceMgr.tankBadD;
    private BufferedImage imageU = ResourceMgr.tankBadU;
    private BufferedImage imageL = ResourceMgr.tankBadL;
    private BufferedImage imageR = ResourceMgr.tankBadR;

    public void paint(Graphics g) {
        Color c = g.getColor();
        if(!living){
            if(Camp.ENEMY == this.tankCamp){
                    score += 10;
                    tankFrame.tanks.remove(this);
                    tankFrame.explosions.add(new Explosion(this.x,this.y,tankFrame));
            }else {
                g.setColor(Color.RED);
                g.setFont(new Font("微软雅黑",Font.BOLD+ Font.ITALIC,50));
                g.drawString("游戏失败!!!",400,420);
                g.drawString("积分为:"+this.score+"分",400,500);
            }

        }
        if(Dir.DOWN == dir){
            g.drawImage(imageD,x,y,null);
        }
        if(Dir.UP == dir){
            g.drawImage(imageU,x,y,null);
        }
        if(Dir.LEFT == dir){
            g.drawImage(imageL,x,y,null);
        }
        if(Dir.RIGHT == dir){
            g.drawImage(imageR,x,y,null);
        }
        move();

    }

    private void move(){
        //判断是敌方坦克，对方向和开火以及运动做处理
        this.enemyTankDo();

        //坦克公用的方法
        if(!moving){
            return;
        }
        //坦克方向，及控制坦克越界问题
        switch (dir){
            case LEFT:
                x -=SPEED;
                if(x <= 0){
                  x = 0;
                }
                break;
            case RIGHT:
                x +=SPEED;
                if(x >= TankFrame.GAME_WIDTH - Tank.WIDTH){
                    x = TankFrame.GAME_WIDTH - Tank.WIDTH;
                }
                break;
            case DOWN:
                y +=SPEED;
                if(y >= TankFrame.GAME_HEIGHT - Tank.HEIGHT){
                    y = TankFrame.GAME_HEIGHT- Tank.HEIGHT;
                }
                break;
            case UP:
                y -=SPEED;
                if(y <= 0 + 40){
                    y = 0 + 40;
                }
                break;

        }


    }


    //判断是敌方坦克，对方向和开火以及运动做处理
    private void enemyTankDo() {
        if(Camp.ENEMY == this.tankCamp ){
            if(!moving){
                this.moving = true;
            }
            //判断是否开火
            int f = random.nextInt(20);
            if(f > 18){
                fire();
            }
            //判断是否转换方向
            int d = random.nextInt(70);
            if( d > 65){
                switch (d){
                    case 66:
                        dir = Dir.DOWN;
                        break;
                    case 67:
                        dir = Dir.UP;
                        break;
                    case 68:
                        dir = Dir.LEFT;
                        break;
                    case 69:
                        dir = Dir.RIGHT;
                        break;
                    default:break;
                }
            }
            if(x == 0 || x == TankFrame.GAME_WIDTH - Tank.WIDTH || y == TankFrame.GAME_HEIGHT- Tank.HEIGHT || y == 0 + 40){this.dirRev();}


        }
    }

    //开火
    public void fire(){
        //每次开火都是向容器中放对象，子弹的坐标是此时坦克的xy（子弹中会处理实际的位置），方向是坦克的方向
       tankFrame.bullets.add(new Bullet(this.x,this.y,this.dir,this.tankCamp,tankFrame));
    }

    public static int getScore() {
        return score;
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

    public static int getSPEED() {
        return SPEED;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Tank.WIDTH = WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Tank.HEIGHT = HEIGHT;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Camp getTankCamp() {
        return tankCamp;
    }

//    public void setTankCamp(Camp tankCamp) {
//        this.tankCamp = tankCamp;
//    }


    public Tank() {
    }
    //构造方法
    public Tank(int x, int y, Dir dir, Camp tankCamp, TankFrame tankFrame) {
        System.out.println(tankCamp+"诞生");
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankCamp = tankCamp;
        this.tankFrame = tankFrame;
        if(Camp.ENEMY == tankCamp){
            SPEED = 3;
        }
        if(Camp.HERO == tankCamp){
            imageD = ResourceMgr.tankGoodD;
            imageU = ResourceMgr.tankGoodU;
            imageR = ResourceMgr.tankGoodR;
            imageL = ResourceMgr.tankGoodL;
        }
    }

    //判断坦克死亡
    public void die() {
        System.out.println(tankCamp+"死亡");
        this.living = false;
    }

    public void tankCrash(Tank tank) {
        Rectangle rectangleThis = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rectangleTank = new Rectangle(tank.getX(), tank.getY(), Tank.getWIDTH(), Tank.getHEIGHT());

            //判断是否碰撞
            if(rectangleThis.intersects(rectangleTank)){
                System.out.println("碰撞");
                //判断是否为友军
                if(this.tankCamp == tank.getTankCamp()){
                    //处理代码
                    if(this.dir == tank.getDir()){
                        this.dirRev();
                        System.out.println("反转一个");
                    }else{
                        this.dirRev();
                        tank.dirRev();
                        System.out.println("反转两个");
                    }


                }else{
                    //非友军
                tank.die();
                this.die();
            }
        }
    }

    //方向反转
    private void dirRev() {
        switch (dir){
            case LEFT:
                dir = Dir.RIGHT;
                break;
            case DOWN:
                dir = Dir.UP;
                break;
            case UP:
                dir = Dir.DOWN;
                break;
            case RIGHT:
                dir = Dir.LEFT;
                break;
            default:break;
        }
    }




}
