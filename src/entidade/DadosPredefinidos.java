package entidade;

import java.util.*;

public class DadosPredefinidos {

    // listas estáticas para guardar os dados fixos
    private static List<Tema> temas = new ArrayList<>();
    private static List<Garcom> garcons = new ArrayList<>();

    // lista horários disponiveis
    private static Map<String, List<String>> agendaDisponivel = new HashMap<>();

    // esse metodo é chamdado uma vez no inicio do programa
    public static void inicializar() {
        if (temas.isEmpty()) {
            inicializarTemas();
            inicializarGarcons();
            inicializarAgenda();
        }
    }

    private static void inicializarTemas() {
        // tema Party
        Tema party = new Tema("Party", "Ambiente festivo e animado");
        // 3 Itens Regulares
        party.adicionarItem(new ItemMenu(1, "Mini Burger", "Comida", 25.0, false));
        party.adicionarItem(new ItemMenu(2, "Coxinha Gourmet", "Comida", 15.0, false));
        party.adicionarItem(new ItemMenu(3, "Refrigerante", "Bebida", 8.0, false));
        // 2 Itens VIP
        party.adicionarItem(new ItemMenu(4, "Champagne Gold", "Bebida", 150.0, true));
        party.adicionarItem(new ItemMenu(5, "Caviar", "Comida", 200.0, true));
        temas.add(party);

        // tema Casual
        Tema casual = new Tema("Casual", "Relaxado e confortável");
        casual.adicionarItem(new ItemMenu(6, "Batata Frita", "Comida", 20.0, false));
        casual.adicionarItem(new ItemMenu(7, "Frango a Passarinho", "Comida", 35.0, false));
        casual.adicionarItem(new ItemMenu(8, "Suco Natural", "Bebida", 12.0, false));
        casual.adicionarItem(new ItemMenu(9, "Vinho Tinto", "Bebida", 90.0, true)); // VIP
        casual.adicionarItem(new ItemMenu(10, "Picanha na Chapa", "Comida", 120.0, true)); // VIP
        temas.add(casual);

        // tema Executivo
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
        // define os horarios padrao
        List<String> horariosPadrao = Arrays.asList("09:00", "14:00", "20:00");

        // Cria a agenda para os 3 dias
        agendaDisponivel.put("Terça", new ArrayList<>(horariosPadrao));
        agendaDisponivel.put("Quinta", new ArrayList<>(horariosPadrao));
        agendaDisponivel.put("Sábado", new ArrayList<>(horariosPadrao));
    }

    // metodos publicos pro sistema usar

    public static List<Tema> getTemas() {
        return temas;
    }

    public static List<Garcom> getGarcons() {
        return garcons;
    }

    // mostra apenas os dias que tem horarios livres
    public static List<String> getDiasDisponiveis() {
        List<String> dias = new ArrayList<>();
        String[] ordem = {"Terça", "Quinta", "Sábado"};

        for (String dia : ordem) {
            if (agendaDisponivel.containsKey(dia) && !agendaDisponivel.get(dia).isEmpty()) {
                dias.add(dia);
            }
        }
        return dias;
    }

    // mostra os horaios disponiveis daquele dia especifico
    public static List<String> getHorariosParaDia(String dia) {
        return agendaDisponivel.getOrDefault(dia, new ArrayList<>());
    }

    // remove o horario da lista
    public static void reservarHorario(String dia, String horario) {
        if (agendaDisponivel.containsKey(dia)) {
            agendaDisponivel.get(dia).remove(horario);
            System.out.println(">> Horário reservado: " + dia + " - " + horario);
        }
    }
}