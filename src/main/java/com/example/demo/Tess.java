package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Tess {
    public String text;
    public void tessHandler() throws TesseractException, IOException{
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("Tess4J\\tessdata");

        CopyOfCleanLines.handler();
        text = tesseract.doOCR(new File("test2.png"));

        text = text.replace(" ", "");
        text = text.replace("\"", "");
        text = text.replace(".", "");
        text = text.replace("\n", "");
        System.out.println(text);
        System.out.println(text.length());
    }
    public BufferedImage clipImage(BufferedImage srcImage){
        return srcImage.getSubimage(1, 1, srcImage.getWidth() - 2, srcImage.getHeight() - 2);
    }
    public BufferedImage grayImage(BufferedImage srcImage){
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY ));
    }
    public BufferedImage copyImage(BufferedImage srcImage, BufferedImage destImage) {
        for(int y = 0; y < srcImage.getHeight(); ++y){
            for(int x = 0; x < srcImage.getWidth(); ++x){
                destImage.setRGB(x, y, srcImage.getRGB(x, y));
            }
        }
        return destImage;
    }


}
