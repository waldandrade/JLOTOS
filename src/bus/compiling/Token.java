package bus.compiling;

public class Token {

    private String conteudo;
    private String tipo;
    private int inicio;
    private int fim;
    private boolean local; // tá dentro do método
    
    public Token(String conteudo, String tipo, int inicio, int fim, boolean local) {
        this.conteudo = conteudo;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fim = fim;
        this.local = local;
    }

    public Token() {
    }
    
    public void setToken(String conteudo, String tipo, int inicio, int fim, boolean local) {
        this.conteudo = conteudo;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fim = fim;
        this.local = local;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getFim() {
        return fim;
    }

    public void setFim(int fim) {
        this.fim = fim;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public boolean getLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
