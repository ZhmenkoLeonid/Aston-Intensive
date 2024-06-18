package com.zhmenko;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) throws IOException {
        URL resourceAsStream = Test.class.getResource("a.txt");
        System.out.println(Files.readAllLines(Path.of(resourceAsStream.getPath())));
        //System.out.println(resourceAsStream.);
        //InputStream resourceAsStream = ;
    }
}
