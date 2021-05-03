package br.com.unicap.compiladores.parser;


import br.com.unicap.compiladores.analisadorlexico.ScannerNosso;
import br.com.unicap.compiladores.analisadorlexico.Token;
import br.com.unicap.compiladores.analisadorlexico.TokensID;
import br.com.unicap.compiladores.excecoes.SyntacticException;
import java.util.Stack;

public class Parser {
    private Token token;
    private static Parser p;
    private static ScannerNosso s;
    private Stack<Token> pilha;

    private Parser(ScannerNosso s) {
        Parser.s = s;
    }

    public Parser getContrutor(ScannerNosso s) {
        if (p == null) {
            p = new Parser(s);
        } else {
            if(s == null) {
                Parser.s = s;
            }
        }
        return p;
    }

    private void inicioClass() {
        token = s.getToken();
        if(T(token.getTipo())) {
            nomeClasse();
        } else {
            throw new SyntacticException(SyntacticException.ERRO_TYPE_CLASS, s.getLinha(), s.getColuna());
        }
    }

    private void nomeClasse(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_MAIN || token.getTipo() == TokensID.TK_IDENTIFICADOR){
            if(token.getTipo() == TokensID.TK_IDENTIFICADOR) {
                token.setTipo(TokensID.TK_CLASS);
            }
            abreBloco();
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_NAME_CLASS, s.getLinha(), s.getColuna());
        }
    }

    private void abreBloco() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_CHA) {
            bloco();
            fechaBloco();
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_OPEN_BLOCK, s.getLinha(), s.getColuna());
        }
    }

    private void fechaBloco() {
        if(token.getTipo() != TokensID.TK_SEPARADOR_FECHA_CHA) {
            throw new SyntacticException(SyntacticException.ERRO_CLOSE_BLOCK, s.getLinha(), s.getColuna());
        }
    }

    private void bloco() {
        token = s.getToken();
        if(T(token.getTipo())){
            V();
        }else if(token.getTipo() == TokensID.TK_PR_IF){
            IF();
        }else if(token.getTipo() == TokensID.TK_PR_WHILE){
            C();
        }else if(token.getTipo() == TokensID.TK_PR_DO){
            D();
        }else{
            //erro
        }

    }
    private void V(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR){
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO);
            else if (token.getTipo() == TokensID.TK_ATRIBUICAO){
                E();
            }else { /*erro*/}
        }else{/*erro*/ }
    }
    private void IF(){

    }
    private void C(){

    }
    private void D(){

    }
    private void E(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_NUMERO_INT || token.getTipo() == TokensID.TK_NUMERO_FLT || 
        token.getTipo() == TokensID.TK_IDENTIFICADOR) {
            A();
        }else if (token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            B();
        } else if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO){
            /*erro*/
        }
    }

    private void A() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_OPER_MAT_DIV || token.getTipo() == TokensID.TK_OPER_MAT_MULT ||
         token.getTipo() == TokensID.TK_OPER_MAT_SOMA  || token.getTipo() == TokensID.TK_OPER_MAT_SUB) {
            F();
        } else {
            /*erro*/
        }
    }

    private void F() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR) {
            A();
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            B();
        }
    }
}
