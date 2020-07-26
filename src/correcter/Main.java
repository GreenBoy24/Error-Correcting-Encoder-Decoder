package correcter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Write a mode: ");
        switch (new Scanner(System.in).nextLine()) {
            case "encode":
                Encode.encodeFile();
            case "send":
                Send.generateErrors();
                break;
            case "decode":
                Decode.decodeFile();
                break;
            default:
                System.out.println("Wrong command");
                break;
        }
    }
}
