package entidade;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private Integer id;
    private String nome;
    private String nomePagante;
    private Tema tema;
    private String dia;
    private String horario;

    private List<Mesa> mesas;  //mesas deste evento

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

    // o cardapio vem direto do Tema
    public List<ItemMenu> getCardapio() {
        return tema.getCardapio();
    }

    public double calcularFaturamentoTotal() {
        return mesas.stream().mapToDouble(Mesa::calcularContaTotal).sum();
    }

    // getters

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