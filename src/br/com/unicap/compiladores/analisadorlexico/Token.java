package br.com.unicap.compiladores.analisadorlexico;

public class Token{

    private Enum tipo;
    private String texto;
        
    Token(Enum tipo, String texto) {
        setTipo(tipo);
        setTexto(texto);
    }

    Enum getTipo() {
        return tipo;
    }

    private void setTipo(Enum tipo) {
        this.tipo = tipo;
    }

    String getTexto() {
        return texto;
    }

    private void setTexto(String texto) {
        this.texto = texto;
    }

    private String getTKString(Enum tipo) {
        return tipo.toString();
    }

    public String toString() {
        return "[tipo: " + getTKString(tipo) + " | texto: " + texto + "]\n";
    }
}
