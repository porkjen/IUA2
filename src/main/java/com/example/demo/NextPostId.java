package com.example.demo;

public class NextPostId {
    public String getNextHouseString(String inputString) {
        // 提取數字部分
        String numberPart = inputString.substring(1); // 從第二個字元開始截取
        int number = Integer.parseInt(numberPart);

        // 遞增數字
        int nextNumber = number + 1;

        // 格式化為五位數字的字串
        String nextNumberString = String.format("%05d", nextNumber);

        // 構造下一個字串
        String nextString = "H" + nextNumberString;

        return nextString;
    }

}
