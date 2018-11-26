package org.springframework.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SignatureWriter {
    public static final String PATH_TO_SIGNATURE = "src/resources/sign.txt";

    public static void printSignature() {
        try {
            File file = new File(PATH_TO_SIGNATURE);
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
