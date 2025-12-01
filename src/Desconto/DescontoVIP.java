package Desconto;
public class DescontoVIP implements Desconto {
    private static final double TAXA_DESCONTO = 0.10; // 10%

    @Override
    public double calcularDesconto(double valor) {
        return valor * TAXA_DESCONTO;
    }
    @Override
    public String getDescricao(){
        return "Desconto Vip (10%)";
    }
}