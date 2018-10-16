package com.miracle.michael.naoh.part5;

import java.io.File;

public class AAA {
    public static void main(String[] args) {

    }

    public static void renamePic(String strPath, String newName) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    renamePic(file.getAbsolutePath(), newName);
                } else {
                    if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")
                            || file.getName().endsWith(".jpeg") || file.getName().endsWith(".bmp")
                            || file.getName().endsWith(".gif")) {
                        file.renameTo(new File(file.getParent(), newName));
                    }
                }
            }
        }
    }
}
