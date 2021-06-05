package br.com.unicap.compiladores.analisadorlexico;

public class Token{

    private TokensID tipo;
    private String texto;
        
    Token(TokensID tipo, String texto) {
        setTipo(tipo);
        setTexto(texto);
    }

    public TokensID getTipo() {
        return tipo;
    }

    public void setTipo(TokensID tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    private void setTexto(String texto) {
        this.texto = texto;
    }

    private String getTKString(TokensID tipo) {
        return tipo.toString();
    }

    public String toString() {
        return "[tipo: " + getTKString(tipo) + " | texto: " + texto + "]\n";
    }
}
