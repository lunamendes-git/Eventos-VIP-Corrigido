package entidade;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorId = 1;
    private Integer id;
    private Mesa mesa;
    private List<ItemMenu> itens;
    private String observacao;

    public Pedido(Mesa mesa) {
        this.id = contadorId++;
        this.mesa = mesa;
        this.itens = new ArrayList<>();
        this.observacao = "";
    }

    public void adicionarItem(ItemMenu item) {

        if (item.isExclusivoVIP() && !mesa.temConvidadoVIP()) {
            throw new IllegalArgumentException("Item VIP requer convidado VIP na mesa");
        }
        itens.add(item);
    }


    public void setObservacao(String obs) {
        this.observacao = obs;
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemMenu::getPreco).sum();
    }

    public Integer getId() {
        return id;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<ItemMenu> getItens() {
        return new ArrayList<>(itens);
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " - Mesa " + mesa.getNumero() +
                " (" + itens.size() + " itens) - R$ " + String.format("%.2f", calcularTotal());
    }
}
