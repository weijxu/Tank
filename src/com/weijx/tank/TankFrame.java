package com.weijx.tank;

import javax.lang.model.element.VariableElement;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: weijx
 * @Date: 2020/10/19 - 10 - 19 - 20:56
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class TankFrame extends Frame {
//创建坦克和子弹
   Tank myTank = new Tank(500,860,Dir.UP,Camp.HERO,this);
   //Bullet b = new Bullet(300,300,Dir.DOWN);
    //为了能打出很多子弹，所以用容器包装子弹
   List<Bullet> bullets = new ArrayList<>();
   //敌方坦克
    List<Tank> tanks = new ArrayList<>();
    //爆炸图片
    List<Explosion> explosions = new ArrayList<>();
   static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;


   //改为单例，饿汉式
    private static final TankFrame tankFrame = new TankFrame();

    public static TankFrame getTankFrame() {
        return tankFrame;
    }

    //空参构造器，会把屏幕定义好
    private TankFrame() throws HeadlessException {
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setTitle("tank war        子弹数量:"+bullets.size()+"   敌人数量:"+tanks.size()+"         积分:"+Tank.getScore());
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener(new MyKeyListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image offScreenImage = null;

    //防闪烁，先把整体画在内存中，之后每次repaint都是调用这个方法
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    //画屏幕中的物体
    @Override
    public void paint(Graphics g) {

        Color c = g.getColor();
        //g.setColor(Color.WHITE);
        this.setTitle("tank war        子弹数量:"+bullets.size()+"   敌人数量:"+tanks.size()+"         积分:"+Tank.getScore());
        myTank.paint(g);
        //画出每颗子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        //画坦克
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }
        //画爆炸
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(g);
        }
        //判断是否被击中，无队友伤害
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).collideWith(myTank);
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

        //判断是否相撞，无队友伤害
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).tankCrash(myTank);
            for (int j = 0; j < tanks.size(); j++) {
                if(i != j){
                    tanks.get(i).tankCrash(tanks.get(j));
                }
            }
        }
        if(tanks.size() == 0){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑",Font.BOLD+ Font.ITALIC,50));
            g.drawString("游戏胜利!!!",400,420);
            g.drawString("积分为:"+Tank.getScore()+"分",400,500);
        }

    }
//键盘监听
        class MyKeyListener extends KeyAdapter{


            boolean bL = false;
            boolean bU = false;
            boolean bR = false;
            boolean bD = false;

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode){
                    case KeyEvent.VK_LEFT:
                        bL = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        bR = true;
                        break;
                    case KeyEvent.VK_UP:
                        bU = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        bD = true;
                        break;
                    default:break;
                }
                //修改方向
                setMainTankDir();
            }

            @Override
            public void keyReleased(KeyEvent e) {

                int keyCode = e.getKeyCode();
                switch (keyCode){
                    case KeyEvent.VK_LEFT:
                        bL = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        bR = false;
                        break;
                    case KeyEvent.VK_UP:
                        bU = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        bD = false;
                        break;
                    case KeyEvent.VK_CONTROL:
                        myTank.fire();
                        break;
                    default:break;
                }
                //修改方向
                setMainTankDir();
            }

            private void setMainTankDir() {
                if(!bR && !bL && !bD && !bU){
                    myTank.setMoving(false);
                }else {
                    myTank.setMoving(true);
                    if(bD) myTank.setDir(Dir.DOWN);
                    if(bU) myTank.setDir(Dir.UP);
                    if(bL) myTank.setDir(Dir.LEFT);
                    if(bR)  myTank.setDir(Dir.RIGHT);
                }

            }
        }



}
