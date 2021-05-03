package br.com.unicap.compiladores.analisadorlexico;

public abstract class Scan {    
    protected boolean isDigit(char c) {
        return c >='0' && c <= '9';
    }

    protected boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }

    protected boolean isIgnorable(char c) {
        return (c == ' ' || c =='\n' || c == '\t' || c == '\r');
    }

    protected boolean isOperatorRel(char c) {
        return (c == '>' || c == '<' || c == '=' || c == '!');
    }

    protected boolean isMathOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    protected boolean isPoint(char c) {
        return c == '.';
    }

    protected boolean isSimpleQuotes(char c) {
        return c == '\'';
    }

    protected boolean isUnderscore(char c) {
        return c == '_';
    }

    protected boolean isSeparator(char c) {
        return (c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == ';');
    }

    protected boolean isComa(char c) {
        return c == ',';
    }

    protected boolean isOpenBras(char c) {
        return c == '(';
    }

    protected boolean isCloseBras(char c) {
        return c == ')';
    }

    protected boolean isOpenBracket(char c) {
        return c == '{';
    }

    protected boolean isDotComa(char c) {
        return c == ';';
    }
    
    protected boolean isCloseBracket(char c) {
        return c == '}';
    }

    protected boolean isEqual(char c) {
        return c == '=';
    }

    protected boolean isMoreThan(char c) {
        return c == '>';
    }

    protected boolean isLessThan(char c) {
        return c == '<';
    }

    protected boolean isPlus(char c) {
        return c == '+';
    }

    protected boolean isMinus(char c) {
        return c == '-';
    }

    protected boolean isDivisor(char c) {
        return c == '/';
    }

    protected boolean isMultiplier(char c) {
        return c == '*';
    }

    protected boolean isFimDePilha(char c) {
        return c == '$';
    }
}
