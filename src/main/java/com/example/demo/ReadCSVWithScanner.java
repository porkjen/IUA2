package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class ReadCSVWithScanner {

    public static void HandleCSV() throws IOException {
        // open file input stream
        FileInputStream fis = new FileInputStream("C:\\Users\\elain\\Downloads\\excel.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "Big5"));

        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<ExcelDataBean> empList = new ArrayList<>();
//        for(int i = 0; i < 2; i++){
//            if((line = reader.readLine()) != null) {
//                scanner = new Scanner(line);
//                scanner.useDelimiter(",");
//                while (scanner.hasNext()) {
//                    index++;
//                }
//                index = 0;
//            }
//        }

        while ((line = reader.readLine()) != null) {
            ExcelDataBean emp = new ExcelDataBean();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    emp.setAcademicYear(data);
                else if (index == 1)
                    emp.setClassNumber(data);
                else if (index == 3)
                    emp.setCredit(data);
                else if (index == 4)
                    emp.setCategory(data);
                else if (index == 5)
                    emp.setClassName(data);
                else if (index == 11)
                    emp.setScore(data);
                index++;
            }
            index = 0;
            empList.add(emp);
        }

        //close reader
        reader.close();

        System.out.println(empList);

    }


}
