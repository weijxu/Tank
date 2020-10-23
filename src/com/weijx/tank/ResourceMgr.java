package com.weijx.tank;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Auther: weijx
 * @Date: 2020/10/22 - 10 - 22 - 14:43
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class ResourceMgr {

    public  static BufferedImage tankGoodL, tankGoodU, tankGoodR, tankGoodD,tankBadL, tankBadU, tankBadR, tankBadD;
    public static BufferedImage bulletL, bulletR, bulletD, bulletU;
    //爆炸图片,可以用数组
    public static BufferedImage[] explosions = new BufferedImage[16];

    static {
        try {
            System.out.println("总资源加载开始");

            System.out.println("我方坦克图片资源加载开始");
            tankGoodU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            tankGoodL = ImageUtil.rotateImage(tankGoodU, -90);
            tankGoodR = ImageUtil.rotateImage(tankGoodU, 90);
            tankGoodD = ImageUtil.rotateImage(tankGoodU, 180);
            System.out.println("我方坦克图片资源加载完成");

            System.out.println("敌方坦克图片资源加载开始");
            tankBadU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            tankBadL = ImageUtil.rotateImage(tankBadU, -90);
            tankBadR = ImageUtil.rotateImage(tankBadU, 90);
            tankBadD = ImageUtil.rotateImage(tankBadU, 180);
            System.out.println("敌方坦克图片资源加载完成");

            System.out.println("子弹图片资源加载开始");
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU,-90);
            bulletR = ImageUtil.rotateImage(bulletU,90);
            bulletD = ImageUtil.rotateImage(bulletU,180);
            System.out.println("子弹图片资源加载完成");

            System.out.println("爆炸图片资源加载开始");
            for (int i = 0; i < 16; i++) {
                explosions[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }
            System.out.println("爆炸图片资源加载完成");

            System.out.println("总资源加载完成");
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
