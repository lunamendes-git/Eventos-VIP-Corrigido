package Desconto;

public class DescontoVIP implements Desconto {

    @Override
    public double calcularDesconto(double valorTotal) {
        return valorTotal * 0.10;
    }

    @Override
    public String getDescricao() {
        return "Desconto VIP (10%)";
    }
}