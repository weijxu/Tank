package com.weijx.tank.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: weijx
 * @Date: 2020/10/23 - 10 - 23 - 11:29
 * @Description: com.weijx.tank
 * @version: 1.0
 */
public class TestConPro {

    private static List<String> list = new ArrayList<>();

    public TestConPro() {

    }

    public  List<String> getList() {
        return list;
    }

    public  void setList(List<String> list) {
        TestConPro.list = list;
    }
}
