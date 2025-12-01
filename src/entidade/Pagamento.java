package entidade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pagamento {
    private Integer id;
    private Mesa mesa;
    private double valorTotal;
    private double desconto;
    private double valorFinal;
    private String formaPagamento;
    private LocalDateTime dataHora;

    public Pagamento(Integer id, Mesa mesa, String formaPagamento) {
        this.id = id;
        this.mesa = mesa;
        this.formaPagamento = formaPagamento;
        this.dataHora = LocalDateTime.now();
        calcularValores();
    }

    private void calcularValores() {
        this.valorTotal = mesa.getPedidos().stream()
                .mapToDouble(Pedido::calcularTotal)
                .sum();

        if (mesa.temConvidadoVIP()) {
            Convidado vip = mesa.getConvidados().stream()
                    .filter(Convidado::isVIP)
                    .findFirst()
                    .orElse(null);
            if (vip != null) {
                this.desconto = vip.getValorDesconto(valorTotal);
            }
        }
        this.valorFinal = valorTotal - desconto;
    }

    public String gerarRecibo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== RECIBO ==========\n");
        sb.append("Data: ").append(dataHora.format(fmt)).append("\n");
        sb.append("Mesa: ").append(mesa.getNumero()).append("\n");
        sb.append("Convidados: ").append(mesa.getConvidados().size()).append("\n\n");

        sb.append("--- Pedidos ---\n");
        for (Pedido p : mesa.getPedidos()) {
            for (ItemMenu item : p.getItens()) {
                sb.append("• ").append(item.toString()).append("\n");
            }
        }

        sb.append("\n--- Totais ---\n");
        sb.append("Subtotal: R$ ").append(String.format("%.2f", valorTotal)).append("\n");
        if (desconto > 0) {
            sb.append("Desconto VIP: -R$ ").append(String.format("%.2f", desconto)).append("\n");
        }
        sb.append("TOTAL: R$ ").append(String.format("%.2f", valorFinal)).append("\n");
        sb.append("Pagamento: ").append(formaPagamento).append("\n");
        sb.append("============================\n");
        return sb.toString();
    }

    public double getValorFinal() {
        return valorFinal;
    }
}