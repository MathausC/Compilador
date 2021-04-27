package br.com.unicap.compiladores.analisadorlexico;

public class Token{

    private Enum<TokensID> tipo;
    private String texto;
        
    Token(Enum<TokensID> tipo, String texto) {
        setTipo(tipo);
        setTexto(texto);
    }

    Enum<TokensID> getTipo() {
        return tipo;
    }

    private void setTipo(Enum<TokensID> tipo) {
        this.tipo = tipo;
    }

    String getTexto() {
        return texto;
    }

    private void setTexto(String texto) {
        this.texto = texto;
    }

    private String getTKString(Enum<TokensID> tipo) {
        return tipo.toString();
    }

    public String toString() {
        return "[tipo: " + getTKString(tipo) + " | texto: " + texto + "]\n";
    }
}
