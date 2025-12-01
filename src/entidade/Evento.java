package entidade;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private Integer id;
    private String nome;
    private Tema tema;
    private List<Mesa> mesas;
    private List<Garcom> garcons;
    private List<ItemMenu> cardapio;

    public Evento(Integer id, String nome, Tema tema) {
        this.id = id;
        this.nome = nome;
        this.tema = tema;
        this.mesas = new ArrayList<>();
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
    }

    public void adicionarMesa(Mesa mesa) {
        mesas.add(mesa);
    }

    public void adicionarGarcom(Garcom garcom) {
        garcons.add(garcom);
    }

    public void adicionarItemCardapio(ItemMenu item) {
        cardapio.add(item);
    }

    public Garcom buscarGarcomDisponivel() {
        return garcons.stream()
                .filter(Garcom::isDisponivel)
                .findFirst()
                .orElse(null);
    }

    public double calcularFaturamentoTotal() {
        return mesas.stream().mapToDouble(Mesa::calcularContaTotal).sum();
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public Tema getTema() { return tema; }
    public List<Mesa> getMesas() { return new ArrayList<>(mesas); }
    public List<ItemMenu> getCardapio() { return new ArrayList<>(cardapio); }

    @Override
    public String toString() {
        return "Evento: " + nome + " | " + tema.getNome() +
                " | " + mesas.size() + " mesas";
    }
}