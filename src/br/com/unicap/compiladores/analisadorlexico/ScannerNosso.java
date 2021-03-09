package br.com.unicap.compiladores.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScannerNosso {
    private char[] conteudo;

    public ScannerNosso(String file){
        try {
            String s;
            s = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
            conteudo = s.toCharArray();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private boolean isDigit(char c) {
        return c >='0' && c <= '9';
    }

    private boolean isLowCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private boolean isLetter(char c) {
        return (isLowCase(c) || isUpperCase(c));
    }

    private boolean isIgnorable(char c) {
        return (c == ' ' || c =='\n' || c == '\t' || c == '\r');
    }

    private boolean isOperator(char c) {
        return (c == '>' || c == '<' || c == '=' || c == '!');
    }
}
