package com.example.demo;
public enum TableCellType {
    // 學年期0 課號1 學分數3 選別4 課程名稱5 學期總成績7
    ACADEMICYEAR(0), CLASSCODE(1), CREDIT(3), MUST(4), CHIENSELESSON(5), OVERALLRESULT(7);
    private final int index;
    private TableCellType(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
}
