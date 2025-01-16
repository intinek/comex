package com.alura.comex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InformeSintetico {
    private int totalDePedidosRealizados;
    private int totalDeProductosVendidos;
    private int totalDeCategorias;
    private BigDecimal montoDeVentas;
    private Pedido pedidoMasBarato;
    private Pedido pedidoMasCaro;

    public InformeSintetico(List<Pedido> pedidos) {
        this.totalDePedidosRealizados = calcularTotalDePedidosRealizados(pedidos);
        this.totalDeProductosVendidos = calcularTotalDeProductosVendidos(pedidos);
        this.montoDeVentas = calcularMontoDeVentas(pedidos);
        this.totalDeCategorias = calcularTotalDeCategorias(pedidos);
        this.pedidoMasBarato = calcularPedidoMasBarato(pedidos);
        this.pedidoMasCaro = calcularPedidoMasCaro(pedidos);
    }

    private int calcularTotalDePedidosRealizados(List<Pedido> pedidos) {
        return pedidos.size();
    }

    private int calcularTotalDeProductosVendidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .mapToInt(Pedido::getCantidad)
                .sum();
    }

    private BigDecimal calcularMontoDeVentas(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int calcularTotalDeCategorias(List<Pedido> pedidos) {
        return (int) pedidos.stream()
                .map(Pedido::getCategoria)
                .distinct()
                .count();
    }

    private Pedido calcularPedidoMasBarato(List<Pedido> pedidos) {
        return pedidos.stream()
                .min((p1, p2) -> p1.getValorTotal().compareTo(p2.getValorTotal()))
                .orElse(null);
    }

    private Pedido calcularPedidoMasCaro(List<Pedido> pedidos) {
        return pedidos.stream()
                .max((p1, p2) -> p1.getValorTotal().compareTo(p2.getValorTotal()))
                .orElse(null);
    }

    public int getTotalDePedidosRealizados() {
        return totalDePedidosRealizados;
    }

    public int getTotalDeProductosVendidos() {
        return totalDeProductosVendidos;
    }

    public int getTotalDeCategorias() {
        return totalDeCategorias;
    }

    public String getMontoDeVentas() {
        return NumberFormat.getCurrencyInstance(new Locale("es", "AR"))
                .format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN));
    }

    public String getPedidoMasBarato() {
        return pedidoMasBarato != null ? String.format("%s (%s)", 
                NumberFormat.getCurrencyInstance(new Locale("es", "AR"))
                .format(pedidoMasBarato.getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), 
                pedidoMasBarato.getProducto()) : "No hay pedidos";
    }

    public String getPedidoMasCaro() {
        return pedidoMasCaro != null ? String.format("%s (%s)", 
                NumberFormat.getCurrencyInstance(new Locale("es", "AR"))
                .format(pedidoMasCaro.getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), 
                pedidoMasCaro.getProducto()) : "No hay pedidos";
    }
}
