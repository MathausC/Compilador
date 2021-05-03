package br.com.unicap.compiladores.parser;
import br.com.unicap.compiladores.analisadorlexico.TokensID;

public abstract class Terminal {
  
    protected boolean ID(TokensID t){
        return (t == TokensID.TK_IDENTIFICADOR || t == TokensID.TK_NUMERO_INT || t == TokensID.TK_NUMERO_FLT );        
    }
    protected boolean OP(TokensID t){
        switch(t){
        case TK_OPER_MAT_SOMA: return true; 
        case TK_OPER_MAT_SUB: return true;
        case TK_OPER_MAT_MULT: return true; 
        case TK_OPER_MAT_DIV: return true;
        default: return false;
        }
    }
    protected boolean OR(TokensID t){
        switch(t){
            case TK_OPER_REL_IGUAL: return true; 
            case TK_OPER_REL_MENOR: return true;
            case TK_OPER_REL_MAIOR: return true; 
            case TK_OPER_REL_DIFERENTE: return true;
            case TK_OPER_REL_MAIOR_IGUAL: return true; 
            case TK_OPER_REL_MENOR_IGUAL: return true;
            default: return false;
        }
    }
    protected boolean T(TokensID t) {
        switch(t) {
            case TK_PR_INT: return true;
            case TK_PR_FLOAT: return true;
            case TK_PR_CHAR: return true;
            default: return false; 
        }
    }
}
