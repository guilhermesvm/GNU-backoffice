package utils;

import java.util.regex.*;

public class LinkValidator {
    public static void main(String[] args) {
        String link = "www.google.com"; 
        String regex = "(https?://)?(www\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {
            System.out.println("formato válido");
        } else {
            System.out.println("formato inválido");
        }
    }
}