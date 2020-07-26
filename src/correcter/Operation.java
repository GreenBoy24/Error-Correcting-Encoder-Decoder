package correcter;

import java.io.*;

abstract class Operation {

    public static StringBuilder hexToBinary(byte[] byteArray) {
        StringBuilder binaryText = new StringBuilder();
        for (byte thisByte : byteArray) {
            binaryText.append(String.format("%8s", Integer.toBinaryString(thisByte & 0xFF)).replace(' ', '0')).append(" ");
        }
        return binaryText;
    }

    public static void writeToFile(String pathName, StringBuilder builder){
        int numberOfBytes = (builder.length() + 1) / 9;
        int index = 0;
        try (FileOutputStream writer = new FileOutputStream(pathName);) {
            for (int i = 0; i < numberOfBytes; i++) {
                writer.write(Integer.parseInt(builder.toString().substring(index, index + 8), 2));
                index += 9;
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
}
