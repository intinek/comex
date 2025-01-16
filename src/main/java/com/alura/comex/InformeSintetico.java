package com.alura.comex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class InformeSintetico {
    private int totalDePedidosRealizados;
    private int totalDeProductosVendidos;
    private int totalDeCategorias;
    private BigDecimal montoDeVentas;
    private Pedido pedidoMasBarato;
    private Pedido pedidoMasCaro;

    public InformeSintetico(List<Pedido> pedidos) {
        this.totalDePedidosRealizados = pedidos.size();
        this.totalDeProductosVendidos = 0;
        this.montoDeVentas = BigDecimal.ZERO;
        this.totalDeCategorias = 0;
        this.pedidoMasBarato = null;
        this.pedidoMasCaro = null;

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();

        for (Pedido pedido : pedidos) {
            this.totalDeProductosVendidos += pedido.getCantidad();
            this.montoDeVentas = this.montoDeVentas.add(pedido.getPrecio().multiply(new BigDecimal(pedido.getCantidad())));

            if (pedidoMasBarato == null || pedido.getValorTotal().compareTo(pedidoMasBarato.getValorTotal()) < 0) {
                pedidoMasBarato = pedido;
            }

            if (pedidoMasCaro == null || pedido.getValorTotal().compareTo(pedidoMasCaro.getValorTotal()) > 0) {
                pedidoMasCaro = pedido;
            }

            if (!categoriasProcesadas.contains(pedido.getCategoria())) {
                totalDeCategorias++;
                categoriasProcesadas.add(pedido.getCategoria());
            }
        }
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
