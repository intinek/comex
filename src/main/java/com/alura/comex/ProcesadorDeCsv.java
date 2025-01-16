package com.alura.comex;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProcesadorDeCsv {

    public List<Pedido> leerPedidosDesdeCsv(String nombreArchivo) throws IOException, URISyntaxException {
        List<Pedido> pedidos = new ArrayList<>();

        URL recursoCSV = ClassLoader.getSystemResource(nombreArchivo);
        Path caminoDelArchivo = Path.of(recursoCSV.toURI());

        try (Scanner lectorDeLineas = new Scanner(caminoDelArchivo)) {
            lectorDeLineas.nextLine(); // Saltar la cabecera

            while (lectorDeLineas.hasNextLine()) {
                String linea = lectorDeLineas.nextLine();
                String[] registro = linea.split(",");

                String categoria = registro[0];
                String producto = registro[1];
                BigDecimal precio = new BigDecimal(registro[2]);
                int cantidad = Integer.parseInt(registro[3]);
                LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cliente = registro[5];

                Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
                pedidos.add(pedido);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Archivo " + nombreArchivo + " no localizado!");
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir Scanner para procesar archivo!");
        }

        return pedidos;
    }
}
