package correcter;

import java.io.*;

public class Decode extends Operation {
    private static byte[] byteArray;

    public static void readFromFile(String pathName){
        File file = new File(pathName);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byteArray = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println("No file found: received.txt");
        }
    }

    public static void decodeFile(){
        readFromFile("received.txt");
        StringBuilder binaryText = hexToBinary(byteArray);
        StringBuilder decodedText = decode(binaryText);
        writeToFile("decoded.txt", decodedText);
    }
    public static StringBuilder decode(StringBuilder stringBuilder) {
        StringBuilder tempString = new StringBuilder();
        StringBuilder decodedString = new StringBuilder();

        for (String byteWord: stringBuilder.toString().trim().split("\\s+")) {
            int numberOfParityErrors = 0;
            int sumOfParityIndexes = 0;
            StringBuilder temp = new StringBuilder(byteWord);

            int p1 = (Integer.parseInt(byteWord.substring(2, 3)) + Integer.parseInt(byteWord.substring(4, 5)) + Integer.parseInt(byteWord.substring(6, 7))) % 2 == 0 ? 0 : 1;
            int p2 = (Integer.parseInt(byteWord.substring(2, 3)) + Integer.parseInt(byteWord.substring(5, 6)) + Integer.parseInt(byteWord.substring(6, 7))) % 2 == 0 ? 0 : 1;
            int p4 = (Integer.parseInt(byteWord.substring(4, 5)) + Integer.parseInt(byteWord.substring(5, 6)) + Integer.parseInt(byteWord.substring(6, 7))) % 2 == 0 ? 0 : 1;

            if (p1 != Integer.parseInt(byteWord.substring(0, 1))) {
                numberOfParityErrors++;
                sumOfParityIndexes += 1;
            }
            if (p2 != Integer.parseInt(byteWord.substring(1, 2))) {
                numberOfParityErrors++;
                sumOfParityIndexes += 2;
            }
            if (p4 != Integer.parseInt(byteWord.substring(3, 4))) {
                numberOfParityErrors++;
                sumOfParityIndexes += 4;
            }
            if (numberOfParityErrors > 1) {
                int bitAtErrorIndex = Integer.parseInt(temp.substring(sumOfParityIndexes - 1, sumOfParityIndexes));
                temp.replace(sumOfParityIndexes - 1, sumOfParityIndexes, Integer.toString(Math.abs(bitAtErrorIndex - 1)));
            }

            tempString.append(temp.substring(2, 3)).append(temp.substring(4, 7));
        }

        for (int j = 0, index = 0; j < tempString.length() / 8; j++, index += 8) {
            decodedString.append(tempString.substring(index, index + 8)).append(" ");
        }
        return decodedString;
    }
}
