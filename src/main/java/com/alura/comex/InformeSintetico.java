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
        int total = 0;
        for (Pedido pedido : pedidos) {
            total += pedido.getCantidad();
        }
        return total;
    }

    private BigDecimal calcularMontoDeVentas(List<Pedido> pedidos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Pedido pedido : pedidos) {
            total = total.add(pedido.getValorTotal());
        }
        return total;
    }

    private int calcularTotalDeCategorias(List<Pedido> pedidos) {
        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();
        int total = 0;
        for (Pedido pedido : pedidos) {
            if (!categoriasProcesadas.contains(pedido.getCategoria())) {
                total++;
                categoriasProcesadas.add(pedido.getCategoria());
            }
        }
        return total;
    }

    private Pedido calcularPedidoMasBarato(List<Pedido> pedidos) {
        Pedido masBarato = null;
        for (Pedido pedido : pedidos) {
            if (masBarato == null || pedido.getValorTotal().compareTo(masBarato.getValorTotal()) < 0) {
                masBarato = pedido;
            }
        }
        return masBarato;
    }

    private Pedido calcularPedidoMasCaro(List<Pedido> pedidos) {
        Pedido masCaro = null;
        for (Pedido pedido : pedidos) {
            if (masCaro == null || pedido.getValorTotal().compareTo(masCaro.getValorTotal()) > 0) {
                masCaro = pedido;
            }
        }
        return masCaro;
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
        return String.format("%s (%s)", 
                NumberFormat.getCurrencyInstance(new Locale("es", "AR"))
                .format(pedidoMasBarato.getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), 
                pedidoMasBarato.getProducto());
    }

    public String getPedidoMasCaro() {
        return String.format("%s (%s)", 
                NumberFormat.getCurrencyInstance(new Locale("es", "AR"))
                .format(pedidoMasCaro.getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), 
                pedidoMasCaro.getProducto());
    }
}
