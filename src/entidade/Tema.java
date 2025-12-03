package entidade;

import java.util.ArrayList;
import java.util.List;

public class Tema {
    private String nome;
    private String descricao;
    private List<ItemMenu> cardapio;

    public Tema(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.cardapio = new ArrayList<>();
    }

    public void adicionarItem(ItemMenu item) {
        cardapio.add(item);
    }

    public List<ItemMenu> getCardapio() {
        return new ArrayList<>(cardapio);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return nome + " - " + descricao;
    }
}