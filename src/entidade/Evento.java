package entidade;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private Integer id;
    private String nome;       // Nome do evento (ex: Aniversário)
    private String nomePagante;// Dono do evento
    private Tema tema;         // O tema escolhido
    private String dia;        // Terça, Quinta ou Sábado
    private String horario;    // 09:00, 14:00 ou 20:00

    private List<Mesa> mesas;  // Mesas deste evento

    public Evento(Integer id, String nome, String nomePagante, Tema tema, String dia, String horario) {
        this.id = id;
        this.nome = nome;
        this.nomePagante = nomePagante;
        this.tema = tema;
        this.dia = dia;
        this.horario = horario;
        this.mesas = new ArrayList<>();
    }

    public void adicionarMesa(Mesa mesa) {
        mesas.add(mesa);
    }

    // Agora o cardápio vem direto do TEMA
    public List<ItemMenu> getCardapio() {
        return tema.getCardapio();
    }

    public double calcularFaturamentoTotal() {
        return mesas.stream().mapToDouble(Mesa::calcularContaTotal).sum();
    }

    // --- GETTERS ---

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getNomePagante() { return nomePagante; }
    public Tema getTema() { return tema; }
    public String getDia() { return dia; }
    public String getHorario() { return horario; }

    public List<Mesa> getMesas() {
        return new ArrayList<>(mesas);
    }

    @Override
    public String toString() {
        return "Evento: " + nome + " (" + dia + " às " + horario + ") | Tema: " + tema.getNome();
    }
}