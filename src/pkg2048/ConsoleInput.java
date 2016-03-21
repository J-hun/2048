/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import java.util.Scanner;

/**
 *
 * @author Joco
 */
public final class ConsoleInput {

    public static String eloszt(int size, String... columns) {
        int maradek = size % columns.length;
        size = size / columns.length;
        System.out.println(size);
        StringBuilder sb = new StringBuilder();
        for (String column : columns) {
            if (maradek > 0) {
                sb.append(toMiddle(column, size + 1));
            } else {
                sb.append(toMiddle(column, size));
            }
        }
        return sb.append("vege").toString();
    }

    public static String toMiddle(String s, int size) {
        if (s.length() < size - 1) {
            int halfSize = (size - s.length()) / 2;
            int maradek = (size - s.length()) % 2;
            String format = "%-" + halfSize + "s%s%" + (halfSize + maradek) + "s";
            return String.format(format, "|", s, "|").replace(" ", "-");
        }

        return String.format("%-" + size + "s", s).replace(" ", "-");

    }

    public static String askString(String question, String regex) {
        return askString(question, regex, "");
    }

    public static String askString(String question, String regex, String help) {
        String answer = "";
        boolean matches = false;
        System.out.print(messageBox(question));
        while (!matches) {
            answer = new Scanner(System.in).next();
            if (answer.matches(regex)) {
                matches = true;
            } else {
                System.out.print(messageBox(help));
            }
        }

        return question;
    }

    public static String messageBox(String... messages) {

        if (messages.length > 0) {
            int maxLength = messages[0].length();
            for (int i = 1; i < messages.length; i++) {
                if (maxLength < messages[i].length()) {
                    maxLength = messages[i].length();
                }
            }
            if (maxLength > 0) {
                StringBuilder sb = new StringBuilder("");
                sb.append(String.format("%s%-" + maxLength + "s%s", "+", " ", "+\n").replace(" ", "-"));
                for (String message : messages) {
                    if (message.length() > 0) {
                        sb.append("+").append(String.format("%-" + maxLength + "s", message)).append("+\n");
                    }
                }
                sb.append(String.format("%s%-" + maxLength + "s%s", "+", " ", "+").replace(" ", "-"));
                return sb.toString();
            }
        }
        return "+-+\n"
                + "| |\n"
                + "+-+";
    }

    public static int askInt(String question) {
        int answer = -1;
        boolean matches = false;
        do {

            try {
                System.out.print(messageBox(question));
                answer = new Scanner(System.in).nextInt();
                matches=true;
            } catch (Exception e) {
                System.out.println(messageBox("Only numbers are accepted...!"));
            }
        } while (!matches);

        return answer;
    }

//    public static String emailInput(String question, String help) {
//        String answer = "";
//        boolean matches = false;
//        do {
//            System.out.print(question);
//            answer = new Scanner(System.in).next();
//            if (answer.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
//                matches = true;
//            } else {
//                System.out.println(help);
//            }
//        } while (!matches);
//
//        return question;
//    }
}
