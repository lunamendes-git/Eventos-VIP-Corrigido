package entidade;

public class RelatorioEvento {
    private Evento evento;

    public RelatorioEvento(Evento evento) {
        this.evento = evento;
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════╗\n");
        sb.append("║     RELATÓRIO DO EVENTO                ║\n");
        sb.append("╚════════════════════════════════════════╝\n\n");

        sb.append("Evento: ").append(evento.getNome()).append("\n");
        sb.append("Tema: ").append(evento.getTema().getNome()).append("\n");
        sb.append("Mesas: ").append(evento.getMesas().size()).append("\n\n");

        sb.append("--- Resumo por Mesa ---\n");
        double totalGeral = 0;
        for (Mesa mesa : evento.getMesas()) {
            double totalMesa = mesa.calcularContaTotal();
            totalGeral += totalMesa;
            sb.append(String.format("Mesa %d: R$ %.2f (%d convidados)%s\n",
                    mesa.getNumero(), totalMesa, mesa.getConvidados().size(),
                    mesa.temConvidadoVIP() ? "  VIP" : ""));
        }

        sb.append("\n--- Faturamento Total ---\n");
        sb.append(String.format("R$ %.2f\n", totalGeral));
        sb.append("\n════════════════════════════════════════\n");

        return sb.toString();
    }
}