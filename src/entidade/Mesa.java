package entidade;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private Integer numero;
    private static final int CAPACIDADE_MAXIMA = 8;
    private List<Convidado> convidados;
    private Garcom garcom;
    private List<Pedido> pedidos;
    private String decoracao;
    private boolean reservadaVIP;

    public Mesa(Integer numero) {
        this.numero = numero;
        this.convidados = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.decoracao = "Padrão";
        this.reservadaVIP = false;
    }

    public void adicionarConvidado(Convidado convidado) {
        if (convidados.size() >= CAPACIDADE_MAXIMA) {
            throw new IllegalStateException("Mesa cheia (máx. 8 convidados)");
        }
        convidados.add(convidado);


        if (convidado.isVIP() && !reservadaVIP) {
            decoracao = "Decoração VIP Premium";
            reservadaVIP = true;
        }
    }

    public void atribuirGarcom(Garcom garcom) {
        this.garcom = garcom;
        garcom.atribuirMesa(this.numero);
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
        if (garcom != null) {
            garcom.registrarPedido(pedido);
        }
    }

    public boolean temConvidadoVIP() {
        return convidados.stream().anyMatch(Convidado::isVIP);
    }

    public double calcularContaTotal() {
        double total = pedidos.stream().mapToDouble(Pedido::calcularTotal).sum();


        if (temConvidadoVIP()) {
            Convidado vip = convidados.stream()
                    .filter(Convidado::isVIP)
                    .findFirst()
                    .orElse(null);
            if (vip != null) {
                double desconto = vip.getValorDesconto(total);
                total -= desconto;
            }
        }
        return total;
    }

    public void debugConta() {
        System.out.println("=== COMANDA ===");
        System.out.println("Número de pedidos: " + pedidos.size());
        for (Pedido p : pedidos) {
            System.out.println("Pedido " + p.getId() + ": R$ " + p.calcularTotal());
        }
        double subtotal = pedidos.stream().mapToDouble(Pedido::calcularTotal).sum();
        System.out.println("Subtotal: R$ " + subtotal);
        System.out.println("Tem VIP: " + (temConvidadoVIP() ? "sim" : "nao"));
        System.out.println("===================");
    }

    public Integer getNumero() { return numero; }
    public List<Convidado> getConvidados() { return new ArrayList<>(convidados); }
    public Garcom getGarcom() { return garcom; }
    public List<Pedido> getPedidos() { return new ArrayList<>(pedidos); }
    public String getDecoracao() { return decoracao; }
    public void setDecoracao(String dec) { this.decoracao = dec; }

    @Override
    public String toString() {
        return "Mesa " + numero + " [" + convidados.size() + "/" + CAPACIDADE_MAXIMA +
                "] - " + decoracao + (temConvidadoVIP() ? " (VIP)" : "");
    }
}