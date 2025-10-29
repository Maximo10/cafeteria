package com.example.cafeteria;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class HelloController {

    @FXML
    private ListView<String> listCamaAlex;

    @FXML
    private ListView<String> listCamaPerez;

    @FXML
    private ListView<String> listPendientes;

    @FXML
    private ListView<String> listAtendidos;

    @FXML
    private Button btnIniciar;

    private ArrayList<cliente> clientes = new ArrayList<>();
    private ArrayList<cliente> atendidos = new ArrayList<>();

    public void initialize() {
        // Crear clientes
        Collections.addAll(clientes,
                new cliente("Pedro", 3000),
                new cliente("Antón", 3000),
                new cliente("Angel", 3000),
                new cliente("Gonzalo", 3000),
                new cliente("Ana", 3000),
                new cliente("Carlos", 3000)
        );

        actualizarPendientes();
    }

    @FXML
    public void iniciarSimulacion() {
        btnIniciar.setDisable(true);

        // Crear camareros "adaptados" para la UI
        Thread alex = new Thread(() -> atenderCliente("Alex", listCamaAlex));
        Thread perez = new Thread(() -> atenderCliente("Perez", listCamaPerez));

        alex.start();
        perez.start();
    }

    private void atenderCliente(String nombreCamarero, ListView<String> listView) {
        while (true) {
            cliente cli = null;

            synchronized (clientes) {
                if (!clientes.isEmpty()) {
                    cli = clientes.remove(0);
                    Platform.runLater(this::actualizarPendientes);
                } else {
                    break;
                }
            }

            if (cli != null) {
                int tiempoPrepa = (int) (Math.random() * 3000) + 2000;
                String mensaje = nombreCamarero + " prepara café para " + cli.getNombre() +
                        " (tardará " + tiempoPrepa / 1000 + "s)";
                Platform.runLater(() -> listView.getItems().add(mensaje));

                try {
                    Thread.sleep(tiempoPrepa);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (tiempoPrepa <= cli.getTiempo_espera()) {
                    Platform.runLater(() -> {
                        listView.getItems().add(cli.getNombre() + " recibe su café");
                        atendidos.add(cli);
                        actualizarAtendidos();
                    });
                } else {
                    Platform.runLater(() -> listView.getItems().add(cli.getNombre() + " se fue sin su café"));
                }
            }
        }

        Platform.runLater(() -> listView.getItems().add(nombreCamarero + " ha terminado su turno"));
    }

    private void actualizarPendientes() {
        listPendientes.getItems().clear();
        for (cliente c : clientes) {
            listPendientes.getItems().add(c.getNombre());
        }
    }

    private void actualizarAtendidos() {
        listAtendidos.getItems().clear();
        for (cliente c : atendidos) {
            listAtendidos.getItems().add(c.getNombre());
        }
    }
}
