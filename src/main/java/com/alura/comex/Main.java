package com.alura.comex;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ProcesadorDeCsv procesador = new ProcesadorDeCsv();
        List<Pedido> pedidos = procesador.leerPedidosDesdeCsv("pedidos.csv");

        int totalDeProductosVendidos = 0;
        int totalDePedidosRealizados = 0;
        BigDecimal montoDeVentas = BigDecimal.ZERO;
        Pedido pedidoMasBarato = null;
        Pedido pedidoMasCaro = null;

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();
        int totalDeCategorias = 0;

        for (Pedido pedidoActual : pedidos) {
            if (pedidoMasBarato == null || pedidoActual.getPrecio().multiply(new BigDecimal(pedidoActual.getCantidad())).compareTo(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad()))) < 0) {
                pedidoMasBarato = pedidoActual;
            }

            if (pedidoMasCaro == null || pedidoActual.getPrecio().multiply(new BigDecimal(pedidoActual.getCantidad())).compareTo(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad()))) > 0) {
                pedidoMasCaro = pedidoActual;
            }

            montoDeVentas = montoDeVentas.add(pedidoActual.getPrecio().multiply(new BigDecimal(pedidoActual.getCantidad())));
            totalDeProductosVendidos += pedidoActual.getCantidad();
            totalDePedidosRealizados++;

            if (!categoriasProcesadas.contains(pedidoActual.getCategoria())) {
                totalDeCategorias++;
                categoriasProcesadas.add(pedidoActual.getCategoria());
            }
        }

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrency
