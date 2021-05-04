package br.com.unicap.compiladores.parser;


import br.com.unicap.compiladores.analisadorlexico.ScannerNosso;
import br.com.unicap.compiladores.analisadorlexico.Token;
import br.com.unicap.compiladores.analisadorlexico.TokensID;
import br.com.unicap.compiladores.excecoes.SyntacticException;
import java.util.Stack;

public class Parser extends Terminal{
    private Token token;
    private static Parser p;
    private static ScannerNosso s;
    private Stack<Token> parenteses;
    private boolean flagElse;

    private Parser(ScannerNosso s) {
        Parser.s = s;
        parenteses = new Stack<Token>();
    }

    public static Parser getContrutor(ScannerNosso s) {
        if (p == null) {
            p = new Parser(s);
        } else {
            if(s == null) {
                Parser.s = s;
            }
        }
        return p;
    }

    public void getParser() {
        inicioClass();
    }

    public void inicioClass() {
        token = s.getToken();
        if(T(token.getTipo())) {
            nomeClasse();
            token = s.getToken();
        }
        else {
             throw new SyntacticException(SyntacticException.ERRO_TYPE_CLASS, s.getLinha(), s.getColuna());
        }
        if(token.getTipo() == TokensID.TK_EOF) {
            return;
        } 
        else {
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
        } else {
            return;
        }
    }
    
    private void bloco() {
        while(true) {
            if(!flagElse){
                token = s.getToken();
            }
            flagElse = false;
            if(T(token.getTipo())) V();
            else if(token.getTipo() == TokensID.TK_PR_IF) IF();
            else if(token.getTipo() == TokensID.TK_PR_WHILE) W();
            else if(token.getTipo() == TokensID.TK_PR_DO) D();
            else if (token.getTipo() == TokensID.TK_IDENTIFICADOR) AT();
            else  return;
        }
    }
    
    private void V(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_IDENTIFICADOR){
            AT();
        }else{throw new SyntacticException(SyntacticException.ERRO_DECLARATION, s.getLinha(), s.getColuna()); }
    }
    
    private void AT(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
            return;
        }
        else if (token.getTipo() == TokensID.TK_ATRIBUICAO){
            token = s.getToken();
            switch(token.getTipo()) {
                case TK_PR_TRUE: break;
                case TK_PR_FALSE: break;
                case TK_CHAR: break;
                case TK_NUMERO_INT: E(); break;
                case TK_NUMERO_FLT: E(); break;
                case TK_IDENTIFICADOR: E(); break;
                case TK_SEPARADOR_ABRE_PAR: E(); break;
                default: throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna()); 
            }
            if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO);
            else  throw new SyntacticException(SyntacticException.ERRO_CLOSE_DECLARATION, s.getLinha(), s.getColuna());
        }else { throw new SyntacticException(SyntacticException.ERRO_ATTRIBUTION, s.getLinha(), s.getColuna());}
    }
        
    private void IF(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
            El();
        } else {
            throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
        }
    }

    private void El() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_ELSE) {
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_CHA) {
                bloco();
                fechaBloco();
            } else if(token.getTipo() == TokensID.TK_PR_IF) {
                IF();
            } else {
                throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
            }
        } else {
            flagElse= true;
            return;
        }
    }

    private void W() {
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            C();
        } else {
            throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
        }
    }
    
    private void C(){
        token = s.getToken();
        if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE) {
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                abreBloco();
            } else {
                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
            }
        }
        else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR || ID(token.getTipo()) )N();
        else {
            throw new SyntacticException(SyntacticException.CONDICIONAL_MISSING, s.getLinha(), s.getColuna());
        }
    }

    private void N() {
        E();
        if(OR(token.getTipo())) {
            token = s.getToken();
            E();
            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) { 
                abreBloco();
            } else {
                throw new SyntacticException(SyntacticException.ERRO_CLOSE_COMPARING, s.getLinha(), s.getColuna());
            }
        } else {
            throw new SyntacticException(SyntacticException.ERRO_TYPE_COMPARING, s.getLinha(), s.getColuna());
        }
    }
 
        private void D(){
            abreBloco();
            token = s.getToken();
            if(token.getTipo() == TokensID.TK_PR_WHILE) {
                token = s.getToken();
                if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR){
                    token = s.getToken();
                    if(token.getTipo() == TokensID.TK_PR_TRUE || token.getTipo() == TokensID.TK_PR_FALSE) {
                        token = s.getToken();
                        if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                            token = s.getToken();
                            if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
                                return;
                            } else {
                                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                            }
                        } else {
                            throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                        }
                    }
                    else {
                        E();
                        if(OR(token.getTipo())) {
                            token = s.getToken();
                            E();
                            if(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR) {
                                token = s.getToken();
                                if(token.getTipo() == TokensID.TK_SEPARADOR_PONTO) {
                                    return;
                                } else {
                                    throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                                }
                            } else {
                                throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_CLOSE, s.getLinha(), s.getColuna());
                            }
                        } else {
                             throw new SyntacticException(SyntacticException.ERRO_TYPE_COMPARING, s.getLinha(), s.getColuna());
                        }
                    }
                }else{
                     throw new SyntacticException(SyntacticException.ERRO_CONDICIONAL_OPEN, s.getLinha(), s.getColuna());
                }
            } else {
                throw new SyntacticException(SyntacticException.STATIMANT_MISSING, s.getLinha(), s.getColuna());
            }
        }

    private void E(){
        System.out.println(token);
        if(ID(token.getTipo())) {
            A();
            if(!parenteses.isEmpty()){
                throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
            }
        }else if (token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            B();
        }
        else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }
//  a+(b+b)+a)
    private void A() {
        token = s.getToken();
        if(OP(token.getTipo())) {
            F();
        } else {
            return;
        }
    }
    // E -> 1 A -> ) F ->
    private void F() {
        token = s.getToken();
        if(ID(token.getTipo())) {
            A();
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            B();
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }

    private void Fl() {
        if(OP(token.getTipo())) {
            token = s.getToken();
            if(ID(token.getTipo())) {
                Al();
            }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
                parenteses.push(token);
                B();
            } else {
                throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
            }
        } else {
            return;
        }
    }

    private void Al() {
        token = s.getToken();
        if(OP(token.getTipo())) {
            Fl();
        } else if (token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR){
            do {
                if(parenteses.isEmpty()) {
                    throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna()); 
                } else {
                    parenteses.pop();
                }
                token = s.getToken();
            }while(token.getTipo() == TokensID.TK_SEPARADOR_FECHA_PAR);
            if(OP(token.getTipo())) {
                F();
            } else {
                return;
            }
        } else {
            throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna()); 
        }
    }

    private void B() {
        token = s.getToken();
        if(ID(token.getTipo())) {
            Al();
        }else if(token.getTipo() == TokensID.TK_SEPARADOR_ABRE_PAR) {
            parenteses.push(token);
            B();
        } else {
             throw new SyntacticException(SyntacticException.ERRO_EXPRESSION_FORMATION, s.getLinha(), s.getColuna());
        }
    }
}
