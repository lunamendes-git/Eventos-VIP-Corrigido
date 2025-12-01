package entidade;

public class Tema {
    private String nome;
    private String descricao;
    private String decoracaoPadrao;

    public Tema(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.decoracaoPadrao = "Decoração tema " + nome;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDecoracaoPadrao() { return decoracaoPadrao; }

    @Override
    public String toString() {
        return "Tema: " + nome + " - " + descricao;
    }
}