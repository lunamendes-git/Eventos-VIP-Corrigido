package entidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MenuConsole {
    private static List<Evento> eventos = new ArrayList<>();
    private static List<Garcom> garcons = new ArrayList<>();
    private static List<Mesa> mesas = new ArrayList<>();
    private static List<Convidado> convidados = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarDadosIniciais();
        exibirMenuPrincipal();
    }

    private static void carregarDadosIniciais() {
        // Garçons padrão
        garcons.add(new Garcom(1, "João"));
        garcons.add(new Garcom(2, "Maria"));

        // Cardápio padrão já é carregado pela Persistencia
        System.out.println("Sistema inicializado com sucesso!");
    }

    private static void exibirMenuPrincipal() {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n SISTEMA EVENTOS VIP ");
            System.out.println("1. Gerenciar Eventos");
            System.out.println("2. Gerenciar Mesas");
            System.out.println("3. Gerenciar Convidados");
            System.out.println("4. Gerenciar Pedidos");
            System.out.println("5. Relatórios e Pagamentos");
            System.out.println("6. Salvar Dados");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1:
                        menuEventos();
                        break;
                    case 2:
                        menuMesas();
                        break;
                    case 3:
                        menuConvidados();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 5:
                        menuRelatorios();
                        break;
                    case 6:
                        salvarDados();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        rodando = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void menuEventos() {
        while (true) {
            System.out.println("\n GERENCIAR EVENTOS");
            System.out.println("1. Criar Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("3. Voltar");
            System.out.print("Escolha: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        criarEvento();
                        break;
                    case 2:
                        listarEventos();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.nextLine();
            }
        }
    }

    private static void criarEvento() {
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();
        System.out.print("Tema do evento: ");
        String temaNome = scanner.nextLine();
        System.out.print("Descrição do tema: ");
        String temaDesc = scanner.nextLine();

        Tema tema = new Tema(temaNome, temaDesc);
        Evento evento = new Evento(eventos.size() + 1, nome, tema);
        eventos.add(evento);

        System.out.println("Evento criado com sucesso! ID: " + evento.getId());
    }

    private static void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println(" Nenhum evento cadastrado.");
            return;
        }

        System.out.println("\n EVENTOS CADASTRADOS:");
        for (Evento evento : eventos) {
            System.out.println("ID: " + evento.getId() +
                    " | " + evento.getNome() +
                    " | Tema: " + evento.getTema().getNome() +
                    " | Mesas: " + evento.getMesas().size() +
                    " | Faturamento: R$ " + String.format("%.2f", evento.calcularFaturamentoTotal()));
        }
    }

    private static void menuMesas() {
        while (true) {
            System.out.println("\n GERENCIAR MESAS");
            System.out.println("1. Criar Mesa");
            System.out.println("2. Listar Mesas");
            System.out.println("3. Adicionar Convidado à Mesa");
            System.out.println("4. Atribuir Garçom à Mesa");
            System.out.println("5. Voltar");
            System.out.print("Escolha: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        criarMesa();
                        break;
                    case 2:
                        listarMesas();
                        break;
                    case 3:
                        adicionarConvidadoMesa();
                        break;
                    case 4:
                        atribuirGarcomMesa();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.nextLine();
            }
        }
    }

    private static void criarMesa() {
        System.out.print("Número da mesa: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        // Verificar se a mesa já existe
        Optional<Mesa> mesaExistente = mesas.stream()
                .filter(m -> m.getNumero() == numero)
                .findFirst();

        if (mesaExistente.isPresent()) {
            System.out.println("Já existe uma mesa com o número " + numero + ".");
            return;
        }

        Mesa mesa = new Mesa(numero);
        mesas.add(mesa);

        System.out.println("Mesa criada. Número: " + numero);

        if (!eventos.isEmpty()) {
            System.out.println("\n EVENTOS DISPONÍVEIS:");
            for (int i = 0; i < eventos.size(); i++) {
                Evento e = eventos.get(i);
                System.out.println((i + 1) + ". " + e.getNome() + " (Mesas: " + e.getMesas().size() + ")");
            }

            System.out.print("Vincular a qual evento? (0 para nenhum): ");
            int escolhaEvento = scanner.nextInt();
            scanner.nextLine();

            if (escolhaEvento > 0 && escolhaEvento <= eventos.size()) {
                Evento evento = eventos.get(escolhaEvento - 1);
                evento.adicionarMesa(mesa);
                System.out.println("Mesa " + numero + " vinculada ao evento: " + evento.getNome());
            }
        }
    }

    private static void listarMesas() {
        if (mesas.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }

        System.out.println("\n MESAS CADASTRADAS:");
        for (Mesa mesa : mesas) {
            String nomeEvento = "Nenhum";

            for (Evento evento : eventos) {
                if (evento.getMesas().contains(mesa)) {
                    nomeEvento = evento.getNome();
                    break;
                }
            }

            System.out.println(mesa.toString() +
                    " | Garçom: " + (mesa.getGarcom() != null ? mesa.getGarcom().getNome() : "Nenhum") +
                    " | Evento: " + nomeEvento);
        }
    }

    private static void adicionarConvidadoMesa() {
        if (mesas.isEmpty() || convidados.isEmpty()) {
            System.out.println("É necessário ter mesas e convidados cadastrados.");
            return;
        }

        System.out.println("\n CONVIDADOS DISPONÍVEIS:");
        for (Convidado conv : convidados) {
            System.out.println("ID: " + conv.getId() + " | " + conv.getNome() + " | " + conv.getTipo());
        }

        System.out.print("\nID do convidado: ");
        int idConvidado = scanner.nextInt();

        System.out.println("\n MESAS DISPONÍVEIS:");
        for (Mesa m : mesas) {
            String nomeEvento = "Nenhum";

            for (Evento evento : eventos) {
                if (evento.getMesas().contains(m)) {
                    nomeEvento = evento.getNome();
                    break;
                }
            }

            System.out.println("Mesa: " + m.getNumero() +
                    " | Evento: " + nomeEvento +
                    " | Convidados: " + m.getConvidados().size() + "/8");
        }

        System.out.print("Número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Optional<Convidado> optConvidado = convidados.stream()
                .filter(c -> c.getId() == idConvidado)
                .findFirst();

        Optional<Mesa> optMesa = mesas.stream()
                .filter(m -> m.getNumero() == numeroMesa)
                .findFirst();

        if (optConvidado.isPresent() && optMesa.isPresent()) {
            Convidado convidado = optConvidado.get();
            Mesa mesa = optMesa.get();
            try {
                mesa.adicionarConvidado(convidado);
                System.out.println("Convidado " + convidado.getNome() + " adicionado à mesa " + numeroMesa);
                System.out.println("Mesa agora tem: " + mesa.getConvidados().size() + " convidados");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Mesa ou convidado não encontrado.");
            if (optConvidado.isEmpty())
                System.out.println("  - Convidado ID " + idConvidado + " não existe");
            if (optMesa.isEmpty())
                System.out.println("  - Mesa " + numeroMesa + " não existe");
        }
    }

    private static int contarMesasDoGarcom(Garcom garcom) {
        if (garcom == null)
            return 0;

        // Conta quantas mesas estão atualmente atribuídas a este garçom
        long count = mesas.stream()
                .filter(m -> m.getGarcom() != null && m.getGarcom().getId() == garcom.getId())
                .count();
        return (int) count;
    }

    private static void atribuirGarcomMesa() {
        if (mesas.isEmpty() || garcons.isEmpty()) {
            System.out.println("É necessário ter mesas e garçons cadastrados.");
            return;
        }

        // mesas disponíveis (sem garçom)
        List<Mesa> mesasDisponiveis = mesas.stream()
                .filter(m -> m.getGarcom() == null)
                .collect(Collectors.toList());

        System.out.println("\n MESAS DISPONÍVEIS (SEM GARÇOM):");
        if (mesasDisponiveis.isEmpty()) {
            System.out.println("Nenhuma mesa livre no momento.");

            // Se não houver mesas livres, pelo menos avisa o estado das mesas ocupadas
            List<Mesa> mesasComGarcom = mesas.stream().filter(m -> m.getGarcom() != null).collect(Collectors.toList());
            if (!mesasComGarcom.isEmpty()) {
                System.out.println("--- Status das Mesas Ocupadas ---");
                for (Mesa m : mesasComGarcom) {
                    System.out.println("Mesa " + m.getNumero() + " está com Garçom " + m.getGarcom().getNome());
                }
            }
            return;
        } else {
            for (Mesa m : mesasDisponiveis) {
                String nomeEvento = "Nenhum";
                for (Evento evento : eventos) {
                    if (evento.getMesas().contains(m)) {
                        nomeEvento = evento.getNome();
                        break;
                    }
                }
                System.out.println("Mesa: " + m.getNumero() + " | Evento: " + nomeEvento);
            }
        }

        System.out.print("\n Número da mesa para atribuir garçom: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Mesa mesa = mesas.stream()
                .filter(m -> m.getNumero() == numeroMesa)
                .findFirst()
                .orElse(null);

        if (mesa == null) {
            System.out.println("Mesa não encontrada.");
            return;
        }

        // A Mesa já tem Garçom? (Continua valendo)
        if (mesa.getGarcom() != null) {
            System.out.println("\n AVISO: A Mesa " + numeroMesa + " já está atribuída ao garçom "
                    + mesa.getGarcom().getNome() + ".");
            return;
        }

        System.out.println("\n GARÇONS DISPONÍVEIS:");
        List<Garcom> garconsDisponiveis = new ArrayList<>();

        for (int i = 0; i < garcons.size(); i++) {
            Garcom g = garcons.get(i);
            int mesasAtribuidas = contarMesasDoGarcom(g);

            if (mesasAtribuidas < 2) {
                // Garçom é considerado disponível se tiver menos de 2 mesas
                garconsDisponiveis.add(g);
                String status = (mesasAtribuidas == 0) ? "Livre" : ("Ocupado (1/2 mesas)");
                System.out.println((i + 1) + ". " + g.getNome() + " - " + status);
            } else {
                System.out.println((i + 1) + ". " + g.getNome() + " - Cheio (2/2 mesas) ");
            }
        }

        if (garconsDisponiveis.isEmpty()) {
            System.out.println("Nenhum garçom disponível para mais atribuições.");
            return;
        }

        System.out.print("Escolha o garçom (número): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= garcons.size()) {
            Garcom garcomEscolhido = garcons.get(escolha - 1);

            // Segunda verificação de capacidade (caso o usuário escolha um garçom ocupado)
            if (contarMesasDoGarcom(garcomEscolhido) >= 2) {
                System.out.println("ERRO: Garçom " + garcomEscolhido.getNome()
                        + " já está com 2 mesas e não pode receber mais atribuições.");
                return;
            }

            mesa.atribuirGarcom(garcomEscolhido);
            System.out.println(
                    "Garçom " + garcomEscolhido.getNome() + " atribuído à mesa " + numeroMesa + " com sucesso!");
        } else {
            System.out.println("Escolha de garçom inválida.");
        }
    }

    private static void menuConvidados() {
        while (true) {
            System.out.println("\n GERENCIAR CONVIDADOS");
            System.out.println("1. Cadastrar Convidado");
            System.out.println("2. Listar Convidados");
            System.out.println("3. Voltar");
            System.out.print("Escolha: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarConvidado();
                        break;
                    case 2:
                        listarConvidados();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.nextLine();
            }
        }
    }

    private static void cadastrarConvidado() {
        System.out.print("Nome do convidado: ");
        String nome = scanner.nextLine();

        System.out.println("\nTipo de convidado:");
        System.out.println("1. VIP (10% de desconto)");
        System.out.println("2. Regular (sem desconto)");
        System.out.print("Escolha: ");

        int tipoOpcao = scanner.nextInt();
        scanner.nextLine();

        String tipo;
        switch (tipoOpcao) {
            case 1:
                tipo = "VIP";
                break;
            case 2:
                tipo = "REGULAR";
                break;
            default:
                System.out.println("Opção inválida! Usando REGULAR como padrão.");
                tipo = "REGULAR";
        }

        Convidado convidado = new Convidado(convidados.size() + 1, nome, tipo);
        convidados.add(convidado);

        System.out.println("Convidado " + tipo + " cadastrado com sucesso! ID: " + convidado.getId());
    }

    private static void listarConvidados() {
        if (convidados.isEmpty()) {
            System.out.println("Nenhum convidado cadastrado.");
            return;
        }

        System.out.println("\n CONVIDADOS CADASTRADOS:");
        for (Convidado convidado : convidados) {
            System.out.println(convidado.toString());
        }
    }

    private static void menuPedidos() {
        if (mesas.isEmpty()) {
            System.out.println("É necessário ter mesas cadastradas.");
            return;
        }

        List<ItemMenu> cardapio = Persistencia.carregarCardapio();

        if (cardapio.isEmpty()) {
            System.out.println("O cardápio está vazio. Não é possível fazer pedidos.");
            return;
        }

        System.out.println("\n CARDÁPIO:");
        for (int i = 0; i < cardapio.size(); i++) {
            System.out.println((i + 1) + ". " + cardapio.get(i));
        }

        System.out.println("\n---------------------------------");
        System.out.println("MESAS CADASTRADAS:");
        if (mesas.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }
        for (Mesa mesa : mesas) {
            String nomeEvento = "Nenhum";
            for (Evento evento : eventos) {
                if (evento.getMesas().contains(mesa)) {
                    nomeEvento = evento.getNome();
                    break;
                }
            }
            System.out.println("Mesa: " + mesa.getNumero() +
                    " | Garçom: " + (mesa.getGarcom() != null ? mesa.getGarcom().getNome() : "Nenhum") +
                    " | Evento: " + nomeEvento);
        }
        System.out.println("---------------------------------");

        System.out.print("\nNúmero da mesa para o pedido: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Mesa mesa = mesas.stream()
                .filter(m -> m.getNumero() == numeroMesa)
                .findFirst()
                .orElse(null);

        if (mesa == null) {
            System.out.println("Mesa não encontrada.");
            return;
        }

        Pedido pedido = new Pedido(mesa);

        while (true) {
            System.out.print("Escolha um item (0 para finalizar): ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha == 0)
                break;

            if (escolha > 0 && escolha <= cardapio.size()) {
                try {
                    pedido.adicionarItem(cardapio.get(escolha - 1));
                    System.out.println("Item adicionado: " + cardapio.get(escolha - 1).getNome());
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else {
                System.out.println("Item inválido.");
            }
        }

        System.out.print("Observação do pedido: ");
        String obs = scanner.nextLine();
        pedido.setObservacao(obs);

        mesa.adicionarPedido(pedido);

        // Registrar no garçom se houver
        if (mesa.getGarcom() != null) {
            mesa.getGarcom().registrarPedido(pedido);
        }

        System.out.println("Pedido #" + pedido.getId() + " criado com sucesso!");
        System.out.println("Total: R$ " + String.format("%.2f", pedido.calcularTotal()));
    }

    private static void menuRelatorios() {
        while (true) {
            System.out.println("\n RELATÓRIOS E PAGAMENTOS");
            System.out.println("1. Calcular Conta da Mesa");
            System.out.println("2. Gerar Recibo");
            System.out.println("3. Relatório de Evento");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        calcularContaMesa();
                        break;
                    case 2:
                        gerarRecibo();
                        break;
                    case 3:
                        gerarRelatorioEvento();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida.");
                scanner.nextLine();
            }
        }
    }

    private static void calcularContaMesa() {
        if (mesas.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }

        // mesas cadastradas
        System.out.println("\n---------------------------------");
        System.out.println("MESAS CADASTRADAS:");
        for (Mesa mesa : mesas) {
            String nomeEvento = "Nenhum";
            for (Evento evento : eventos) {
                if (evento.getMesas().contains(mesa)) {
                    nomeEvento = evento.getNome();
                    break;
                }
            }
            System.out.println("Mesa: " + mesa.getNumero() +
                    " | Garçom: " + (mesa.getGarcom() != null ? mesa.getGarcom().getNome() : "Nenhum") +
                    " | Evento: " + nomeEvento);
        }
        System.out.println("---------------------------------");

        System.out.print("Número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Mesa mesa = mesas.stream()
                .filter(m -> m.getNumero() == numeroMesa)
                .findFirst()
                .orElse(null);

        if (mesa != null) {
            double total = mesa.calcularContaTotal();
            System.out.println("\n CONTA DA MESA " + numeroMesa);
            System.out.println("Total: R$ " + String.format("%.2f", total));

            if (mesa.temConvidadoVIP()) {
                System.out.println("Desconto VIP aplicado: 10%");
            }
        } else {
            System.out.println("Mesa não encontrada.");
        }
    }

    private static void gerarRecibo() {
        System.out.print("Número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Forma de pagamento: ");
        String formaPagamento = scanner.nextLine();

        Mesa mesa = mesas.stream()
                .filter(m -> m.getNumero() == numeroMesa)
                .findFirst()
                .orElse(null);

        if (mesa != null) {

            Map<String, Integer> contagemItens = new HashMap<>();
            double totalBruto = 0.0;

            // contagem dos itens
            for (Pedido pedido : mesa.getPedidos()) {
                for (ItemMenu item : pedido.getItens()) {
                    String nomeItem = item.getNome();
                    contagemItens.put(nomeItem, contagemItens.getOrDefault(nomeItem, 0) + 1);
                    totalBruto += item.getPreco();
                }
            }

            double totalFinal = mesa.calcularContaTotal();

            System.out.println("\n--- RECIBO MESA " + numeroMesa + " ---");

            System.out.println("\nITENS CONSUMIDOS:");
            if (contagemItens.isEmpty()) {
                System.out.println("Nenhum item consumido.");
            } else {
                for (Map.Entry<String, Integer> entry : contagemItens.entrySet()) {
                    String nome = entry.getKey();
                    int quantidade = entry.getValue();
                    String multiplicador = (quantidade > 1) ? " (x" + quantidade + ")" : "";

                    System.out.println("- " + nome + multiplicador);
                }
            }

            System.out.println("\nDETALHES DA CONTA:");
            System.out.println("Total Bruto: R$ " + String.format("%.2f", totalBruto));

            if (mesa.temConvidadoVIP()) {
                System.out.println("Desconto VIP (10%): R$ " + String.format("%.2f", totalBruto - totalFinal));
            }

            System.out.println("Forma de Pagamento: " + formaPagamento);
            System.out.println("TOTAL FINAL: R$ " + String.format("%.2f", totalFinal));
            System.out.println("---------------------------------");

        } else {
            System.out.println("Mesa não encontrada.");
        }
    }

    private static void gerarRelatorioEvento() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        System.out.println("Eventos disponíveis:");
        for (int i = 0; i < eventos.size(); i++) {
            System.out.println((i + 1) + ". " + eventos.get(i).getNome());
        }

        System.out.print("Escolha o evento: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= eventos.size()) {
            Evento evento = eventos.get(escolha - 1);
            RelatorioEvento relatorio = new RelatorioEvento(evento);
            System.out.println(relatorio.gerarRelatorio());
        } else {
            System.out.println("Evento inválido.");
        }
    }

    private static void salvarDados() {
        try {

            Persistencia.salvarEventos(eventos);

            List<ItemMenu> cardapio = Persistencia.carregarCardapio();
            Persistencia.salvarCardapio(cardapio);

            System.out.println("Dados salvos com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}