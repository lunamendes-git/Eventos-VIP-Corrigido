import java.util.*;
import java.util.stream.Collectors;
import entidade.*;

public class Main {
    private static List<Evento> eventos = new ArrayList<>();
    private static List<Mesa> mesas = new ArrayList<>();
    private static List<Convidado> convidadosCadastrados = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DadosPredefinidos.inicializar();
        System.out.println("Sistema iniciado. Dados carregados.");

        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            try {
                int opcao = lerInteiro("");
                switch (opcao) {
                    case 1: menuEventos(); break;
                    case 2: menuMesas(); break;
                    case 3: menuConvidados(); break;
                    case 4: menuPedidos(); break;
                    case 5: menuPagamento(); break;
                    case 0:
                        System.out.println("Saindo...");
                        rodando = false;
                        break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== SISTEMA EVENTOS VIP ===");
        System.out.println("1. Gerenciar Eventos");
        System.out.println("2. Gerenciar Mesas");
        System.out.println("3. Gerenciar Convidados");
        System.out.println("4. Fazer Pedidos");
        System.out.println("5. Pagamentos e Contas");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    // modulo de eventos
    private static void menuEventos() {
        System.out.println("\n--- GERENCIAR EVENTOS ---");
        System.out.println("1. Criar Novo Evento");
        System.out.println("2. Listar Eventos Criados");
        System.out.println("0. Voltar");

        int op = lerInteiro("Opção: ");
        if (op == 1) criarEvento();
        else if (op == 2) listarEventos();
    }

    private static void criarEvento() {
        System.out.print("Nome do Evento: ");
        String nome = scanner.nextLine();
        System.out.print("Nome do Dono/Pagante: ");
        String dono = scanner.nextLine();

        System.out.println("\nEscolha um Tema:");
        List<Tema> temas = DadosPredefinidos.getTemas();
        for (int i = 0; i < temas.size(); i++) {
            System.out.println((i + 1) + ". " + temas.get(i).getNome());
        }
        int opTema = lerInteiro("Opção: ") - 1;
        if (opTema < 0 || opTema >= temas.size()) { System.out.println("Tema inválido!"); return; }
        Tema temaEscolhido = temas.get(opTema);

        System.out.println("\nDias disponíveis:");
        List<String> dias = DadosPredefinidos.getDiasDisponiveis();
        if (dias.isEmpty()) { System.out.println("Agenda cheia!"); return; }
        for (int i = 0; i < dias.size(); i++) {
            System.out.println((i + 1) + ". " + dias.get(i));
        }
        int opDia = lerInteiro("Opção: ") - 1;
        if (opDia < 0 || opDia >= dias.size()) return;
        String diaEscolhido = dias.get(opDia);

        System.out.println("\nHorários para " + diaEscolhido + ":");
        List<String> horarios = DadosPredefinidos.getHorariosParaDia(diaEscolhido);
        if (horarios.isEmpty()) return;
        for (int i = 0; i < horarios.size(); i++) {
            System.out.println((i + 1) + ". " + horarios.get(i));
        }
        int opHora = lerInteiro("Opção: ") - 1;
        if (opHora < 0 || opHora >= horarios.size()) return;
        String horaEscolhida = horarios.get(opHora);

        DadosPredefinidos.reservarHorario(diaEscolhido, horaEscolhida);
        Evento novoEvento = new Evento(eventos.size() + 1, nome, dono, temaEscolhido, diaEscolhido, horaEscolhida);
        eventos.add(novoEvento);
        System.out.println("Evento criado com sucesso! ID: " + novoEvento.getId());
    }

    private static void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("\nNenhum evento criado ainda.");
            return;
        }
        System.out.println("\n--- LISTA DE EVENTOS ---");
        for (Evento e : eventos) {
            System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome() +
                    " | Dono: " + e.getNomePagante() +
                    " | Data: " + e.getDia() + " às " + e.getHorario() +
                    " | Mesas: " + e.getMesas().size());
        }
    }

    // modulo de mesas
    private static void menuMesas() {
        System.out.println("\n--- GERENCIAR MESAS ---");
        System.out.println("1. Criar Mesa");
        System.out.println("2. Adicionar Convidado à Mesa");
        System.out.println("3. Atribuir Garçom");
        System.out.println("4. Listar Mesas");
        System.out.println("0. Voltar");

        int op = lerInteiro("Opção: ");
        switch (op) {
            case 1: criarMesa(); break;
            case 2: addConvidadoMesa(); break;
            case 3: atribuirGarcomMesa(); break;
            case 4: listarMesasDetalhado(); break;
        }
    }

    private static void criarMesa() {
        if (eventos.isEmpty()) { System.out.println("Crie um evento primeiro!"); return; }

        System.out.println("Vincular a qual Evento?");
        listarEventos();
        int idEvento = lerInteiro("Digite o ID do evento: ");
        Evento evento = eventos.stream().filter(e -> e.getId() == idEvento).findFirst().orElse(null);

        if (evento == null) { System.out.println("Evento não encontrado."); return; }

        // nova validacao da mesa
        System.out.println("O ID da mesa deve começar com o ID do evento (" + evento.getId() + ") e ter mais de 1 dígito.");
        System.out.print("Número da Mesa: ");
        int numero = lerInteiro("");
        String numStr = String.valueOf(numero);
        String idEventoStr = String.valueOf(evento.getId());

        // regra da criacao da mesa
        if (!numStr.startsWith(idEventoStr) || numStr.length() <= idEventoStr.length()) {
            System.out.println("ERRO: Número inválido! Para o Evento " + idEvento +
                    ", a mesa deve ser algo como " + idEvento + "1, " + idEvento + "2, etc.");
            return;
        }

        // verificacao de unicidade
        boolean existe = mesas.stream().anyMatch(m -> m.getNumero() == numero);
        if (existe) {
            System.out.println("ERRO: A Mesa número " + numero + " já existe! Escolha outro número.");
            return;
        }

        Mesa mesa = new Mesa(numero);
        mesas.add(mesa);
        evento.adicionarMesa(mesa);
        System.out.println("Mesa " + numero + " criada com sucesso!");
    }

    private static void listarMesasDetalhado() {
        if (mesas.isEmpty()) { System.out.println("Nenhuma mesa cadastrada."); return; }

        System.out.println("\n--- RELATÓRIO DE MESAS ---");
        for (Mesa m : mesas) {
            String nomeEvento = "Sem Evento";
            for (Evento e : eventos) {
                if (e.getMesas().contains(m)) {
                    nomeEvento = e.getNome();
                    break;
                }
            }
            String nomeGarcom = (m.getGarcom() != null) ? m.getGarcom().getNome() : "Sem Garçom";

            System.out.println("Mesa " + m.getNumero() +
                    " | Evento: " + nomeEvento +
                    " | Garçom: " + nomeGarcom +
                    " | Ocupação: " + m.getConvidados().size() + "/8");
        }
    }

    private static void addConvidadoMesa() {
        if (convidadosCadastrados.isEmpty()) { System.out.println("Cadastre convidados primeiro (Menu 3)."); return; }

        Set<Integer> idsSentados = new HashSet<>();
        for (Mesa m : mesas) {
            for (Convidado c : m.getConvidados()) {
                idsSentados.add(c.getId());
            }
        }

        List<Convidado> disponiveis = new ArrayList<>();
        for (Convidado c : convidadosCadastrados) {
            if (!idsSentados.contains(c.getId())) {
                disponiveis.add(c);
            }
        }

        if (disponiveis.isEmpty()) {
            System.out.println("Todos os convidados cadastrados já estão em mesas!");
            return;
        }

        System.out.println("\n--- CONVIDADOS DISPONÍVEIS ---");
        for (int i = 0; i < disponiveis.size(); i++) {
            System.out.println((i + 1) + ". " + disponiveis.get(i));
        }

        int idx = lerInteiro("Escolha o convidado: ") - 1;
        if (idx < 0 || idx >= disponiveis.size()) return;
        Convidado convidadoEscolhido = disponiveis.get(idx);

        listarMesasDetalhado();
        System.out.print("Número da Mesa para sentar: ");
        int numMesa = lerInteiro("");

        Mesa mesa = mesas.stream().filter(m -> m.getNumero() == numMesa).findFirst().orElse(null);
        if (mesa != null) {
            try {
                mesa.adicionarConvidado(convidadoEscolhido);
                System.out.println("Convidado adicionado!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Mesa não encontrada.");
        }
    }

    private static void atribuirGarcomMesa() {
        listarMesasDetalhado();
        int numMesa = lerInteiro("Número da Mesa: ");
        Mesa mesa = mesas.stream().filter(m -> m.getNumero() == numMesa).findFirst().orElse(null);

        if (mesa == null) return;
        if (mesa.getGarcom() != null) { System.out.println("Mesa já tem garçom!"); return; }

        List<Garcom> garcons = DadosPredefinidos.getGarcons();
        System.out.println("Garçons Disponíveis:");
        for (int i = 0; i < garcons.size(); i++) {
            System.out.println((i+1) + ". " + garcons.get(i).getNome());
        }
        int idx = lerInteiro("Opção: ") - 1;
        if (idx >= 0 && idx < garcons.size()) {
            try {
                mesa.atribuirGarcom(garcons.get(idx));
                System.out.println("Garçom atribuído!");
            } catch(Exception e) { System.out.println(e.getMessage()); }
        }
    }

    // modulo de convidados
    private static void menuConvidados() {
        System.out.println("\n--- NOVO CONVIDADO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Tipo: 1-VIP | 2-Regular");
        int tipoOp = lerInteiro("");
        String tipo = (tipoOp == 1) ? "VIP" : "REGULAR";

        Convidado c = new Convidado(convidadosCadastrados.size() + 1, nome, tipo);
        convidadosCadastrados.add(c);
        System.out.println("Convidado cadastrado!");
    }

    // modulo de pedidos
    private static void menuPedidos() {
        listarMesasDetalhado();
        int numMesa = lerInteiro("Número da Mesa para Pedir: ");
        Mesa mesa = mesas.stream().filter(m -> m.getNumero() == numMesa).findFirst().orElse(null);
        if (mesa == null) return;

        Evento evento = null;
        for (Evento e : eventos) { if (e.getMesas().contains(mesa)) evento = e; }
        if (evento == null) { System.out.println("Mesa sem evento!"); return; }

        List<ItemMenu> menu = evento.getCardapio();
        Pedido pedido = new Pedido(mesa);

        System.out.println("\n--- CARDÁPIO: " + evento.getTema().getNome() + " ---");
        while (true) {
            for (int i = 0; i < menu.size(); i++) {
                System.out.println((i+1) + ". " + menu.get(i));
            }
            System.out.println("0. Encerrar Pedido");
            int op = lerInteiro("Adicionar item: ");
            if (op == 0) break;

            if (op > 0 && op <= menu.size()) {
                try {
                    pedido.adicionarItem(menu.get(op-1));
                    System.out.println(">> Adicionado!");
                } catch (IllegalArgumentException e) {
                    System.out.println(">> BLOQUEADO: Item VIP requer convidado VIP na mesa!");
                }
            }
        }
        if (!pedido.getItens().isEmpty()) {
            mesa.adicionarPedido(pedido);
            System.out.println("Pedido confirmado! Valor: R$ " + pedido.calcularTotal());
        }
    }

    // modulo de pagamentos
    private static void menuPagamento() {
        System.out.println("\n--- FINANCEIRO ---");
        System.out.println("1. Conta da Mesa");
        System.out.println("2. Conta do Evento");
        System.out.println("0. Voltar");

        int op = lerInteiro("Opção: ");
        if (op == 1) verContaMesa();
        else if (op == 2) verContaEvento();
    }

    private static void verContaMesa() {
        listarMesasDetalhado();
        int numMesa = lerInteiro("Digite o número da mesa: ");
        Mesa mesa = mesas.stream().filter(m -> m.getNumero() == numMesa).findFirst().orElse(null);

        if (mesa != null) {
            System.out.println("\n--------------------------------");
            System.out.println("       CONTA DA MESA " + numMesa);
            System.out.println("--------------------------------");

            // mata: nome do intem - quantidade
            Map<String, Integer> contagem = new HashMap<>();
            double subtotalCalculado = 0;

            for (Pedido p : mesa.getPedidos()) {
                for (ItemMenu item : p.getItens()) {
                    contagem.put(item.getNome(), contagem.getOrDefault(item.getNome(), 0) + 1);
                    subtotalCalculado += item.getPreco();
                }
            }

            if (contagem.isEmpty()) {
                System.out.println("(Nenhum pedido realizado)");
            } else {
                for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
                    System.out.println("- " + entry.getKey() + " (x" + entry.getValue() + ")");
                }
            }

            System.out.println("--------------------------------");

            // mostra o detalhe do desconto se houver VIP
            double totalFinal = mesa.calcularContaTotal();

            if (mesa.temConvidadoVIP()) {
                System.out.println("Subtotal: R$ " + String.format("%.2f", subtotalCalculado));
                // mostra valor com desconto
                double valorDesconto = subtotalCalculado - totalFinal;
                System.out.println("Desconto VIP: -R$ " + String.format("%.2f", valorDesconto));
                System.out.println("TOTAL A PAGAR: R$ " + String.format("%.2f", totalFinal));
            } else {
                // mostra valor sem desconto
                System.out.println("TOTAL A PAGAR: R$ " + String.format("%.2f", totalFinal));
            }

            System.out.println("(Aguardando fechamento pelo Evento)");
        } else {
            System.out.println("Mesa não encontrada.");
        }
    }

    private static void verContaEvento() {
        listarEventos();
        int idEvento = lerInteiro("Digite o ID do Evento para fechar a conta: ");
        Evento evento = eventos.stream().filter(e -> e.getId() == idEvento).findFirst().orElse(null);

        if (evento != null) {
            System.out.println("\n$$$ FATURA DO EVENTO: " + evento.getNome() + " $$$");
            System.out.println("Pagante Responsável: " + evento.getNomePagante());
            System.out.println("==================================");

            double totalGeral = 0;
            for (Mesa m : evento.getMesas()) {
                double totalMesa = m.calcularContaTotal();
                System.out.println("Mesa " + m.getNumero() + ": R$ " + String.format("%.2f", totalMesa));
                totalGeral += totalMesa;
            }

            System.out.println("==================================");
            System.out.println("VALOR TOTAL DO EVENTO: R$ " + String.format("%.2f", totalGeral));
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    private static int lerInteiro(String msg) {
        System.out.print(msg);
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }
}