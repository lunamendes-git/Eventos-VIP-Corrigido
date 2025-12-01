package entidade;

import java.util.ArrayList;
import java.util.List;

public class Garcom extends Pessoa {
    private Integer mesaAtendida;
    private List<Pedido> pedidosRegistrados;
    private boolean disponivel;

    public Garcom(Integer id, String nome) {
        super(id, nome);
        this.mesaAtendida = null;
        this.pedidosRegistrados = new ArrayList<>();
        this.disponivel = true;
    }

    public Integer getMesaAtendida(){
        return mesaAtendida;
    }

    public boolean isDisponivel(){
        return disponivel;
    }

    public List<Pedido> getPedidosRegistrados(){
        return new ArrayList<>(pedidosRegistrados);
    }

    public void atribuirMesa(int numeroMesa){
        if (!disponivel){
            throw new IllegalStateException("Garçom está atendendo mesa " + mesaAtendida);
        }
        this.mesaAtendida = numeroMesa;
        this.disponivel = false;
        System.out.println("Garçom " + nome + " está atendendo a Mesa " + numeroMesa);
    }


    public void liberarMesa(){
        if (mesaAtendida == null){
            return;
        }
        System.out.println("Garçom " + nome + " liberado da mesa " + mesaAtendida);
        this.mesaAtendida = null;
        this.disponivel = true;
    }

    public void registrarPedido(Pedido pedido){
        if (mesaAtendida == null){
            throw new IllegalStateException("Garçom não está atendendo nenhuma mesa");
        }
        pedidosRegistrados.add(pedido);
        System.out.println("Pedido efetuado por " + nome);
    }

    public void notificar(String mensagem){
        System.out.println("Notificação para " + nome + ": " + mensagem);
    }

    @Override
    public String toString(){
        String status = disponivel ? "Disponível" : "Mesa " + mesaAtendida;
        return super.toString() + ", Status: " + status;
    }
}