package com.alura.comex;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ProcesadorDeCsv procesador = new ProcesadorDeCsv();
        List<Pedido> pedidos = procesador.leerPedidosDesdeCsv("pedidos.csv");

        // Crear el informe sintético utilizando la lista de pedidos
        InformeSintetico informe = new InformeSintetico(pedidos);

        // Imprimir el informe sintético
        System.out.println("#### INFORME SINTÉTICO");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", informe.getTotalDePedidosRealizados());
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", informe.getTotalDeProductosVendidos());
        System.out.printf("- TOTAL DE CATEGORÍAS: %s\n", informe.getTotalDeCategorias());
        System.out.printf("- MONTO DE VENTAS: %s\n", informe.getMontoDeVentas());
        System.out.printf("- PEDIDO MÁS BARATO: %s\n", informe.getPedidoMasBarato());
        System.out.printf("- PEDIDO MÁS CARO: %s\n", informe.getPedidoMasCaro());
    }
}
