package com.flc.coinmarket.core.util;

public class RandomNumberUtil {
    /*产生numSize位16进制的数*/
    public static String getRandomValue(int numSize) {
        String str = "";
        for (int i = 0; i < numSize; i++) {
            char temp = 0;
            int key = (int) (Math.random() * 2);
            switch (key) {
                case 0:
                    temp = (char) (Math.random() * 10 + 48);//产生随机数字
                    break;
                case 1:
                    temp = (char) (Math.random() * 6 + 'a');//产生a-f
                    break;
                default:
                    break;
            }
            str = str + temp;
        }
        return str;
    }

    public static void main(String[] args) {
        String randomValue = getRandomValue(40);
        System.out.println("randomValue = [" + randomValue + "]");
    }
}
