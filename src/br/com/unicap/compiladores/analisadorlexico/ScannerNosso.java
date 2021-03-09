package br.com.unicap.compiladores.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class ScannerNosso {
    private char[] conteudo;
    private int posição;

    public ScannerNosso(String file){
        try {
            String s;
            s = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
            conteudo = s.toCharArray();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Token getNextToken() {
        if(isEndoOfFile()) {
            return null;
        }
        int estado = 0;
        char charAtual = getNextChar();
        Stack<Character> pilha = new Stack<Character>();
        char[] texto;
        int linha = 1;
        int coluna = 1;
        while (true) {
            switch(estado) {
                case 0:
                    if(isIgnorable(charAtual)) {
                        estado = 0;
                        charAtual = getNextChar();
                    } else if (isLetter(charAtual) || isUnderscore(charAtual)) {
                        estado = 1;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if(isDigit(charAtual)) {
                        estado = 2;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if(isSimpleQuotes(charAtual)) {
                        estado = 4;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if(isQuotes(charAtual)) {
                        estado = 5;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if(isOperatorRel(charAtual)) {
                        estado = 6;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if(isMathOperator(charAtual)) {
                        estado = 7;
                        pilha.push(charAtual);

                    }
                    coluna++;
                    if(charAtual == '\n') {
                        coluna = 1;
                        linha++;
                    }
                    break;
                case 1:
                    if(isLetter(charAtual) || isDigit(charAtual) || isUnderscore(charAtual)) {
                        estado = 1;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if (!isIgnorable(charAtual) && !isOperatorRel(charAtual) && !isPointComa(charAtual)) {
                        //erro
                    } else {
                        texto = new char[pilha.size()];
                        int i = 0;
                        while(!pilha.isEmpty()) {
                            texto[i] = pilha.pop();
                            i++;
                        }
                        goBack();
                        return new Token(Token.TK_IDENTIFICADOR, texto.toString());
                    }
                    coluna++;
                    if(charAtual == '\n') {
                        coluna = 1;
                        linha++;
                    }
                    break;
                case 2:
                    if(isDigit(charAtual)) {
                        estado = 2;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if (isPoint(charAtual)) {
                        estado = 3;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else {
                        texto = new char[pilha.size()];
                        int i = 0;
                        while(!pilha.isEmpty()) {
                            texto[i] = pilha.pop();
                            i++;
                        }
                        goBack();
                        return new Token(Token.TK_NUMERO_INT, texto.toString());
                    }
                    coluna++;
                    if(charAtual == '\n') {
                        coluna = 1;
                        linha++;
                    }
                    break;
                case 3:
                    if(isDigit(charAtual)) {
                        estado = 3;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else {
                        texto = new char[pilha.size()];
                        int i = 0;
                        while(!pilha.isEmpty()) {
                            texto[i] = pilha.pop();
                            i++;
                        }
                        goBack();
                        return new Token(Token.TK_NUMERO_FLT, texto.toString());
                    }
                    coluna++;
                    if(charAtual == '\n') {
                        coluna = 1;
                        linha++;
                    }
                    break;
                case 4:
                    if(isLetter(charAtual) || isDigit(charAtual)) {
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                        if(isSimpleQuotes(charAtual)) {
                            texto = new char[pilha.size()];
                            int i = 0;
                            while(!pilha.isEmpty()) {
                                texto[i] = pilha.pop();
                                i++;
                            }
                            goBack();
                            return new Token(Token.TK_CHAR, texto.toString());
                        } else {
                            //erro
                        }
                    }
                    coluna++;
                    if(charAtual == '\n') {
                        coluna = 1;
                        linha++;
                    }
                    break;
                case 5:
                    if(!isQuotes(charAtual) && charAtual != '\n') {
                        estado = 5;
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                    } else if(charAtual == '\n') {
                        //erro
                    } else {
                        texto = new char[pilha.size()];
                        int i = 0;
                        while(!pilha.isEmpty()) {
                            texto[i] = pilha.pop();
                            i++;
                        }
                        goBack();
                        return new Token(Token.TK_STRING, texto.toString());
                    }
                    coluna++;
                    if(charAtual == '\n') {
                        coluna = 1;
                        linha++;
                    }
                    break;
                case 6:
                    if(isEqual(charAtual)) {
                        pilha.push(charAtual);
                        charAtual = getNextChar();
                        if(!isEqual(charAtual)) {
                            texto = new char[pilha.size()];
                            int i = 0;
                            while(!pilha.isEmpty()) {
                                texto[i] = pilha.pop();
                                i++;
                            }
                            goBack();
                            return new Token(Token.TK_OPER_REL, texto.toString());
                        } else {
                            //erro
                        }
                    }
                    break;
            }
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

    private boolean isOperatorRel(char c) {
        return (c == '>' || c == '<' || c == '=' || c == '!');
    }

    private boolean isMathOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private char getNextChar() {
        char c = conteudo[posição];
        posição++;
        return c;
    }

    private boolean isEndoOfFile() {
        return posição == conteudo.length;
    }

    private void goBack() {
        posição--;
    }

    private boolean isPoint(char c) {
        return c == '.';
    }

    private boolean isSimpleQuotes(char c) {
        return c == '\'';
    }

    private boolean isQuotes(char c) {
        return c == '\"';
    }

    private boolean isUnderscore(char c) {
        return c == '_';
    }

    private boolean isPointComa(char c) {
        return c == ';';
    }

    private boolean isEqual(char c) {
        return c == '=';
    }
}
