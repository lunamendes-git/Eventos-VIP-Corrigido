package entidade;

import java.util.*;

public class DadosPredefinidos {

    // Listas estáticas para guardar os dados fixos
    private static List<Tema> temas = new ArrayList<>();
    private static List<Garcom> garcons = new ArrayList<>();

    // Mapa de Dia -> Lista de Horários Disponíveis
    // Ex: "Terça" -> ["09:00", "14:00", "20:00"]
    private static Map<String, List<String>> agendaDisponivel = new HashMap<>();

    // Este método será chamado uma única vez no início do programa
    public static void inicializar() {
        if (temas.isEmpty()) {
            inicializarTemas();
            inicializarGarcons();
            inicializarAgenda();
        }
    }

    private static void inicializarTemas() {
        // --- 1. TEMA PARTY ---
        Tema party = new Tema("Party", "Ambiente festivo e animado");
        // 3 Itens Regulares
        party.adicionarItem(new ItemMenu(1, "Mini Burger", "Comida", 25.0, false));
        party.adicionarItem(new ItemMenu(2, "Coxinha Gourmet", "Comida", 15.0, false));
        party.adicionarItem(new ItemMenu(3, "Refrigerante", "Bebida", 8.0, false));
        // 2 Itens VIP
        party.adicionarItem(new ItemMenu(4, "Champagne Gold", "Bebida", 150.0, true));
        party.adicionarItem(new ItemMenu(5, "Caviar", "Comida", 200.0, true));
        temas.add(party);

        // --- 2. TEMA CASUAL ---
        Tema casual = new Tema("Casual", "Relaxado e confortável");
        casual.adicionarItem(new ItemMenu(6, "Batata Frita", "Comida", 20.0, false));
        casual.adicionarItem(new ItemMenu(7, "Frango a Passarinho", "Comida", 35.0, false));
        casual.adicionarItem(new ItemMenu(8, "Suco Natural", "Bebida", 12.0, false));
        casual.adicionarItem(new ItemMenu(9, "Vinho Tinto", "Bebida", 90.0, true)); // VIP
        casual.adicionarItem(new ItemMenu(10, "Picanha na Chapa", "Comida", 120.0, true)); // VIP
        temas.add(casual);

        // --- 3. TEMA EXCLUSIVO ---
        Tema exclusivo = new Tema("Exclusivo", "Luxo e sofisticação");
        exclusivo.adicionarItem(new ItemMenu(11, "Risoto de Camarão", "Comida", 60.0, false));
        exclusivo.adicionarItem(new ItemMenu(12, "Salada Caesar", "Comida", 40.0, false));
        exclusivo.adicionarItem(new ItemMenu(13, "Água Perrier", "Bebida", 20.0, false));
        exclusivo.adicionarItem(new ItemMenu(14, "Lagosta", "Comida", 250.0, true)); // VIP
        exclusivo.adicionarItem(new ItemMenu(15, "Whisky 18 Anos", "Bebida", 300.0, true)); // VIP
        temas.add(exclusivo);
    }

    private static void inicializarGarcons() {
        garcons.add(new Garcom(1, "Carlos"));
        garcons.add(new Garcom(2, "Ana"));
        garcons.add(new Garcom(3, "Roberto"));
        garcons.add(new Garcom(4, "Fernanda"));
    }

    private static void inicializarAgenda() {
        // Define os horários padrão
        List<String> horariosPadrao = Arrays.asList("09:00", "14:00", "20:00");

        // Cria a agenda para os 3 dias
        agendaDisponivel.put("Terça", new ArrayList<>(horariosPadrao));
        agendaDisponivel.put("Quinta", new ArrayList<>(horariosPadrao));
        agendaDisponivel.put("Sábado", new ArrayList<>(horariosPadrao));
    }

    // --- MÉTODOS PÚBLICOS PARA O SISTEMA USAR ---

    public static List<Tema> getTemas() {
        return temas;
    }

    public static List<Garcom> getGarcons() {
        return garcons;
    }

    // Retorna apenas os dias que ainda têm horário livre
    public static List<String> getDiasDisponiveis() {
        List<String> dias = new ArrayList<>();
        // Ordem fixa para ficar bonito no menu
        String[] ordem = {"Terça", "Quinta", "Sábado"};

        for (String dia : ordem) {
            if (agendaDisponivel.containsKey(dia) && !agendaDisponivel.get(dia).isEmpty()) {
                dias.add(dia);
            }
        }
        return dias;
    }

    // Retorna os horários livres daquele dia específico
    public static List<String> getHorariosParaDia(String dia) {
        return agendaDisponivel.getOrDefault(dia, new ArrayList<>());
    }

    // Remove o horário da lista (para ninguém mais pegar)
    public static void reservarHorario(String dia, String horario) {
        if (agendaDisponivel.containsKey(dia)) {
            agendaDisponivel.get(dia).remove(horario);
            System.out.println(">> Horário reservado: " + dia + " - " + horario);
        }
    }
}