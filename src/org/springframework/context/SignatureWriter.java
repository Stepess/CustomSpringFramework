package org.springframework.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SignatureWriter {
    public static void printSignature() {
        try {
            File file = new File("src/resources/sign.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            System.out.println();
            System.out.println(stringBuffer.toString());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
