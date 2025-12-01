package Desconto;

public class DescontoRegular implements Desconto {
    @Override
    public double calcularDesconto(double valor) {
        return 0.0;
    }

    @Override
    public String getDescricao() {
        return "Nao possui desconto";
    }
}