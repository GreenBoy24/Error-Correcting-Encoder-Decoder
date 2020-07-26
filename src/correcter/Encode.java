package correcter;

import java.io.*;

public class Encode extends Operation {
    private static byte[] byteArray;

    public static void readFromFile(String pathName){
        File file = new File(pathName);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byteArray = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println("No file found: send.txt");
        }
    }

    public static void encodeFile(){
        readFromFile("send.txt");
        StringBuilder encodedText = encodeText(byteArray);
        writeToFile("encoded.txt", encodedText);
    }

    public static StringBuilder encodeText(byte[] byteArray) {
        StringBuilder builderEncoded = new StringBuilder();
        for (byte thisByte : byteArray) {
            StringBuilder encodedText = new StringBuilder(String.format("%8s", Integer.toBinaryString(thisByte & 0xFF)).replace(' ', '0'));

            int p11 = (Integer.parseInt(encodedText.substring(0, 1)) + Integer.parseInt(encodedText.substring(1, 2)) + Integer.parseInt(encodedText.substring(3, 4))) % 2 == 0 ? 0 : 1;
            int p12 = (Integer.parseInt(encodedText.substring(0, 1)) + Integer.parseInt(encodedText.substring(2, 3)) + Integer.parseInt(encodedText.substring(3, 4))) % 2 == 0 ? 0 : 1;
            int p14 = (Integer.parseInt(encodedText.substring(1, 2)) + Integer.parseInt(encodedText.substring(2, 3)) + Integer.parseInt(encodedText.substring(3, 4))) % 2 == 0 ? 0 : 1;
            int p21 = (Integer.parseInt(encodedText.substring(4, 5)) + Integer.parseInt(encodedText.substring(5, 6)) + Integer.parseInt(encodedText.substring(7, 8))) % 2 == 0 ? 0 : 1;
            int p22 = (Integer.parseInt(encodedText.substring(4, 5)) + Integer.parseInt(encodedText.substring(6, 7)) + Integer.parseInt(encodedText.substring(7, 8))) % 2 == 0 ? 0 : 1;
            int p24 = (Integer.parseInt(encodedText.substring(5, 6)) + Integer.parseInt(encodedText.substring(6, 7)) + Integer.parseInt(encodedText.substring(7, 8))) % 2 == 0 ? 0 : 1;

            builderEncoded.append(p11)
                    .append(p12)
                    .append(encodedText.charAt(0))
                    .append(p14)
                    .append(encodedText.substring(1, 4))
                    .append("0 ")
                    .append(p21)
                    .append(p22)
                    .append(encodedText.charAt(4))
                    .append(p24)
                    .append(encodedText.substring(5, 8))
                    .append("0 ");
        }
        return  builderEncoded;
    }
}
