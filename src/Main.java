import entidade.Garcom;
import entidade.Pedido;
import entidade.Mesa;
import entidade.Convidado;
import entidade.ItemMenu;
import entidade.Evento;
import entidade.Tema;
import entidade.Persistencia;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // DECLARA cardapio FORA do try
        List<ItemMenu> cardapio = null;

        try {
            System.out.println("=== SISTEMA EVENTOS VIP - DEMONSTRACAO ===");
            System.out.println();

            // CARREGA CARDÁPIO AGORA
            cardapio = Persistencia.carregarCardapio();

            // Cria garcom
            Garcom garcom = new Garcom(1, "Joao");
            System.out.println("Garcom criado: " + garcom.getNome());

            // Cria mesa
            Mesa mesa = new Mesa(5);
            System.out.println("Mesa criada: " + mesa.getNumero());

            // Adiciona convidados
            Convidado convidadoVIP = new Convidado(1, "Maria Silva", "VIP");
            Convidado convidadoRegular = new Convidado(2, "Jose Santos", "Regular");

            mesa.adicionarConvidado(convidadoVIP);
            mesa.adicionarConvidado(convidadoRegular);
            System.out.println("Convidados adicionados: " + mesa.getConvidados().size() + " convidados");

            // Garcom atribui mesa
            garcom.atribuirMesa(mesa.getNumero());
            System.out.println("Garcom atribuido a mesa");

            // USA CARDÁPIO CARREGADO em vez de criar novos itens
            System.out.println();
            System.out.println("Cardapio carregado:");
            for (ItemMenu item : cardapio) {
                System.out.println("  - " + item);
            }

            // Cria pedido
            Pedido pedido = new Pedido(mesa);
            // Usa itens do cardápio carregado
            pedido.adicionarItem(cardapio.get(0)); // Picanha
            pedido.adicionarItem(cardapio.get(1)); // Vinho VIP
            pedido.adicionarItem(cardapio.get(2)); // Água
            pedido.setObservacao("Pedido especial para aniversariante");
            mesa.adicionarPedido(pedido);

            System.out.println();
            System.out.println("Pedido criado: " + pedido);

            // Registra pedido no garçom
            garcom.registrarPedido(pedido);

            // Notifica garçom
            garcom.notificar("Pedido VIP pronto para servir!");

            // Calcula conta total
            mesa.debugConta();
            double contaTotal = mesa.calcularContaTotal();
            System.out.println();
            System.out.println("CONTA DA MESA:");
            System.out.println("  - Subtotal: R$ " + pedido.calcularTotal());
            System.out.println("  - Desconto VIP (10%): R$ " + convidadoVIP.getValorDesconto(pedido.calcularTotal()));
            System.out.println("  - TOTAL: R$ " + String.format("%.2f", contaTotal));

            // Exibe resumo
            System.out.println();
            System.out.println("RESUMO FINAL:");
            System.out.println("  - Mesa: " + mesa);
            System.out.println("  - Decoracao: " + mesa.getDecoracao());
            System.out.println("  - Garcom: " + garcom);
            System.out.println("  - Pedidos registrados: " + garcom.getPedidosRegistrados().size());

            // Libera mesa
            garcom.liberarMesa();
            System.out.println();
            System.out.println("Mesa liberada - Garcom disponivel");

            System.out.println();
            System.out.println("DEMONSTRACAO CONCLUIDA COM SUCESSO!");

            // Salva dados
            List<Evento> eventos = new ArrayList<>();
            Evento eventoDemo = new Evento(1, "Festa de Aniversario", new Tema("Festa", "Tema festivo"));
            eventos.add(eventoDemo);

            Persistencia.salvarEventos(eventos);
            Persistencia.salvarCardapio(cardapio);
            System.out.println(" Dados salvos com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}