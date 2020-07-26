package correcter;

import java.io.*;
import java.util.Random;

public class Send extends Operation {
    private static byte[] byteArray;

    public static void readFromFile(String pathName){
        File file = new File(pathName);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byteArray = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println("No file found: received.txt");
        }
    }

    public static void generateErrors(){
        readFromFile("encoded.txt");
        StringBuilder binaryText = hexToBinary(byteArray);
        StringBuilder errorText = simulateBinaryError(binaryText);
        writeToFile("received.txt", errorText);
    }

    public static StringBuilder simulateBinaryError(StringBuilder stringBuilder) {
        Random random = new Random();
        StringBuilder errorText = new StringBuilder();

        for (String byteString : stringBuilder.toString().split(" ")) {
            StringBuilder byteBuilder = new StringBuilder(byteString);
            int index = random.nextInt(8);
            if (byteBuilder.charAt(index) == '0') {
                byteBuilder.replace(index, index + 1, "1");
            } else {
                byteBuilder.replace(index, index + 1, "0");
            }
            errorText.append(byteBuilder).append(" ");
        }
        errorText.deleteCharAt(errorText.length() - 1);

        return errorText;
    }
}
