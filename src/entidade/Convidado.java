package entidade;

import Desconto.Desconto;
import Desconto.DescontoRegular;
import Desconto.DescontoVIP;

public class Convidado extends Pessoa {
    private String tipo;
    private Desconto estrategiaDesconto;

    public Convidado(Integer id, String nome, String tipo) {
        super(id, nome);
        this.tipo = tipo.toUpperCase();


        if (!this.tipo.equals("VIP") && !this.tipo.equals("REGULAR")) {
            throw new IllegalArgumentException("Tipo de convidado inválido: " + tipo);
        }

        if (this.tipo.equals("VIP")) {
            this.estrategiaDesconto = new DescontoVIP();
        } else {
            this.estrategiaDesconto = new DescontoRegular();
        }
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isVIP() {
        return tipo.equals("VIP");
    }

    public double calcularValorFinal(double valor) {
        double desconto = estrategiaDesconto.calcularDesconto(valor);
        return valor - desconto;
    }

    public double getValorDesconto(double valor) {
        return estrategiaDesconto.calcularDesconto(valor);
    }

    public String getDescricaoDesconto() {
        return estrategiaDesconto.getDescricao();
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: " + tipo + ", " + estrategiaDesconto.getDescricao();
    }
}