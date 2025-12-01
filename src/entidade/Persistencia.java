package entidade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private static final String ARQUIVO_EVENTOS = "data/eventos.json";
    private static final String ARQUIVO_CARDAPIO = "data/cardapio.json";

    public static void salvarEventos(List<Evento> eventos) {
        try {
            File diretorio = new File("data");
            if (!diretorio.exists())
                diretorio.mkdir();

            FileWriter writer = new FileWriter(ARQUIVO_EVENTOS);
            writer.write("[\n");

            for (int i = 0; i < eventos.size(); i++) {
                Evento evento = eventos.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + evento.getId() + ",\n");
                writer.write("    \"nome\": \"" + evento.getNome() + "\",\n");
                writer.write("    \"tema\": \"" + evento.getTema().getNome() + "\",\n");
                writer.write("    \"mesas\": " + evento.getMesas().size() + "\n");
                writer.write("  }");
                if (i < eventos.size() - 1)
                    writer.write(",");
                writer.write("\n");
            }

            writer.write("]");
            writer.close();
            System.out.println("Eventos salvos em: " + ARQUIVO_EVENTOS);

        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    public static void salvarCardapio(List<ItemMenu> cardapio) {
        try {
            File diretorio = new File("data");
            if (!diretorio.exists())
                diretorio.mkdir();

            FileWriter writer = new FileWriter(ARQUIVO_CARDAPIO);
            writer.write("[\n");

            for (int i = 0; i < cardapio.size(); i++) {
                ItemMenu item = cardapio.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + item.getId() + ",\n");
                writer.write("    \"nome\": \"" + item.getNome() + "\",\n");
                writer.write("    \"categoria\": \"" + item.getCategoria() + "\",\n");
                writer.write("    \"preco\": " + item.getPreco() + ",\n");
                writer.write("    \"vip\": " + item.isExclusivoVIP() + "\n");
                writer.write("  }");
                if (i < cardapio.size() - 1)
                    writer.write(",");
                writer.write("\n");
            }

            writer.write("]");
            writer.close();
            System.out.println("Cardápio salvo em: " + ARQUIVO_CARDAPIO);

        } catch (IOException e) {
            System.out.println("Erro ao salvar cardápio: " + e.getMessage());
        }
    }

    public static List<ItemMenu> carregarCardapio() {
        List<ItemMenu> cardapio = new ArrayList<>();
        try {
            File arquivo = new File(ARQUIVO_CARDAPIO);
            if (!arquivo.exists()) {
                System.out.println("Arquivo de cardápio não encontrado. Criando cardápio padrão.");
                return criarCardapioPadrao();
            }

            System.out.println("Cardápio carregado do arquivo.");

        } catch (Exception e) {
            System.out.println("Erro ao carregar cardápio: " + e.getMessage());
        }
        return criarCardapioPadrao();
    }

    private static List<ItemMenu> criarCardapioPadrao() {
        List<ItemMenu> cardapio = new ArrayList<>();
        cardapio.add(new ItemMenu(1, "Picanha Grelhada", "Refeicao", 80.0, false));
        cardapio.add(new ItemMenu(2, "Vinho Reserva Especial", "Bebida", 120.0, true));
        cardapio.add(new ItemMenu(3, "Agua Mineral", "Bebida", 8.0, false));
        cardapio.add(new ItemMenu(4, "Salada Caesar", "Entrada", 25.0, false));
        return cardapio;
    }
}