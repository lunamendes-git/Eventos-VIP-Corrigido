package entidade;

public class ItemMenu {
    private Integer id;
    private String nome;
    private String categoria;
    private double preco;
    private boolean exclusivoVIP;

    public ItemMenu(Integer id, String nome, String categoria, double preco, boolean exclusivoVIP) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.exclusivoVIP = exclusivoVIP;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public double getPreco() { return preco; }
    public boolean isExclusivoVIP() { return exclusivoVIP; }

    @Override
    public String toString() {
        String vip = exclusivoVIP ? " [VIP]" : "";
        return nome + " - R$ " + String.format("%.2f", preco) + vip;
    }
}