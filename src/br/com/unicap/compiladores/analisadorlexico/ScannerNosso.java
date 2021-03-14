package br.com.unicap.compiladores.analisadorlexico;
import br.com.unicap.compiladores.excecoes.LexicalException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ScannerNosso extends Scan{
    private char[] c;
    private int pos;
    private ArrayList<Token> tokens = new ArrayList<Token>();

    public ScannerNosso(String file){
        try {
            String s;
            s = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
            c = s.toCharArray();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public ArrayList<Token> getTokens() {
        Token t;
        do {
            t = getToken();
            tokens.add(t);
        }while(t != null);
        return tokens;
    }

    private Token getToken(){
        int estado = 0;
        char atual = getCharAtual();
        while(true) {
            if(isEndOfFile()) return null;
            switch (estado) {
                case 0:
                    if(isIgnorable(atual)) {
                        estado = 0;
                        atual = getCharAtual();
                    } else if(isLetter(atual) || isUnderscore(atual)) {
                        retrocede();
                        estado = 1;
                    } else if(isDigit(atual)) {
                        retrocede();
                        estado = 2;
                    } else if(isSimpleQuotes(atual)) {
                        retrocede();
                        estado = 3;
                    } else if (isOperatorRel(atual)) {
                        retrocede();
                        estado = 4;
                    } else if (isMathOperator(atual))   {
                        retrocede();
                        estado = 5;
                    } else if(isSeparator(atual)){
                        retrocede();
                        estado = 6;
                    } else {
                        retrocede();
                        estado = -1;
                    }
                    break;
                case 1: return getTokenID();
                case 2: return getTokenDig();
                case 3: return getTokenChar();
                case 4: return getTokenOpRel();
                case 5: return getTokenOpMat();
                case 6: return getTokenSep();
                default: throw new LexicalException("Caractere não reconhecido!");
            }
        }
    }

    //(letra|_)(letra|_|digito)*
    private Token getTokenID() {
        String termo = "";
        char aux;
        aux = getCharAtual();
        if(isUnderscore(aux) || isLetter(aux)) {
            termo += aux;
            aux = getCharAtual();
            while(isLetter(aux) || isUnderscore(aux) || isDigit(aux)){
                termo += aux;
                aux = getCharAtual();
            }
            retrocede();
        }
        Token t = new Token();
        t.run();
        Integer tk = t.pRHashtable.get(termo);
        System.out.println("Valor vindo do hashtable: " + tk);
        if(tk != null) {
            return new Token(tk.intValue(), termo);
        }
        return new Token(Token.TK_IDENTIFICADOR, termo);
    }
    //(digito)+ || (digito).(digito)+
    private Token getTokenDig() {
        String termo  = "";
        char aux = getCharAtual();
        while(isDigit(aux)) {
            termo += aux;
            aux = getCharAtual();
        }
        if(isPoint(aux)) {
            char temp = aux;
            aux = getCharAtual();
            if(isDigit(aux)) {
                termo += temp;
                termo += aux;
                while(isDigit(aux)) {
                    termo += aux;
                    aux = getCharAtual();
                }
                retrocede();
                return new Token(Token.TK_NUMERO_FLT, termo);
            } else {
                retrocede();
                retrocede();
                throw new LexicalException("Digito invalido!");
            }
        } else retrocede();
        return new Token(Token.TK_NUMERO_INT, termo);
    }
    //'(letra|digito)'
    private Token getTokenChar() {
        String termo = "";
        char aux = getCharAtual();
        if(isSimpleQuotes(aux)){
            termo += aux;
            aux = getCharAtual();
            if(isLetter(aux) || isDigit(aux)) {
                termo += aux;
                aux = getCharAtual();
                if(!isSimpleQuotes(aux)){
                    throw new LexicalException("Caractere invalido!");
                }
                else {
                    termo += aux;
                }
                retrocede();
            }
        }
        return new Token(Token.TK_CHAR, termo);
    }
    // < || > || <= || >= || == || !=
    private Token getTokenOpRel() {
        String termo = "";
        char aux = getCharAtual();
        char temp = aux;
        if(isEqual(aux)) {
            aux = getCharAtual();
            if(isEqual(aux)) {
                termo += temp;
                termo += aux;
                return new Token(Token.TK_OPER_REL_IGUAL, termo);
            } else {
                termo += temp;
                retrocede();
                return new Token(Token.TK_ATRIBUICAO, termo);
            }
        } else {
            aux = getCharAtual();
            if(isEqual(aux)) {
                termo += temp;
                termo += aux;
                if(isMoreThan(temp)) return new Token(Token.TK_OPER_REL_MAIOR_IGUAL, termo);
                else if(isLessThan(temp)) return new Token(Token.TK_OPER_REL_MENOR_IGUAL, termo);
                else return new Token(Token.TK_OPER_REL_DIFERENTE, termo);                
            } else {
                termo += temp;
                retrocede();
                if(isMoreThan(temp)) return new Token(Token.TK_OPER_REL_MAIOR, termo);
                else if(isLessThan(temp)) return new Token(Token.TK_OPER_REL_MENOR, termo);
                else throw new LexicalException("Operador invalido!");
            }
        }
    }
    //+||-||/||*
    private Token getTokenOpMat(){
        String termo = "";
        char aux = getCharAtual();
        termo += aux;
        if (isPlus(aux)) return new Token(Token.TK_OPER_MAT_SOMA, termo);
        else if(isMinus(aux)) return new Token(Token.TK_OPER_MAT_SUB, termo);
        else if(isMultiplier(aux)) return new Token(Token.TK_OPER_MAT_MULT, termo);
        else {
            aux = getCharAtual();
            if(isDivisor(aux)){
                ignoreLine();
                return null;                
            } else {
                retrocede();
                return new Token(Token.TK_OPER_MAT_DIV, termo);
            }
        }
    }

    private Token getTokenSep() {
        String termo = "";
        char aux = getCharAtual();
        termo += aux;
        if(isOpenBras(aux)) return new Token(Token.TK_SEPARADOR_ABRE_CHAV, termo);
        else if(isCloseBras(aux)) return new Token(Token.TK_SEPARADOR_FECHA_CHAV, termo);
        else if(isOpenBracket(aux)) return new Token(Token.TK_SEPARADOR_ABRE_COLCH, termo);
        else if(isCloseBracket(aux))return new Token(Token.TK_SEPARADOR_FECHA_COLCH, termo);
        else if(isComa(aux)) return new Token(Token.TK_SEPARADOR_VIRGULA, termo);
        else return new Token(Token.TK_SEPARADOR_PONTO, termo);
    }

    private void ignoreLine() {
        char aux = getCharAtual();
        while(aux != '\n') {
            aux = getCharAtual();
        }
    }

    private boolean isEndOfFile() {
        return pos == c.length;
    }

    private void retrocede() {
        pos--;
    }

    private char getCharAtual() {
        char ret = c[pos];
        pos++;
        return ret;
    }
}    
