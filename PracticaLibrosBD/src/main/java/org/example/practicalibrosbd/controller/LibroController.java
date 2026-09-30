package org.example.practicalibrosbd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.practicalibrosbd.LibroApplication;
import org.example.practicalibrosbd.connection.DatabaseConnection;
import org.example.practicalibrosbd.model.Libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroController {
    @FXML
    public TextField txtTitulo;
    @FXML
    public TextField txtAutor;
    @FXML
    public ComboBox<String> cmbCategoria;
    @FXML
    public TextField txtPrecio;
    @FXML
    public TextField txtStock;
    @FXML
    public TableView<Libros> tblLibro;
    @FXML
    public TableColumn<Libros, String> colId;
    @FXML
    public TableColumn<Libros, String> colTItulo;
    @FXML
    public TableColumn<Libros, String> colAutor;
    @FXML
    public TableColumn<Libros, Double> colPrecio;
    @FXML
    public TableColumn<Libros, String> colCategoria;
    @FXML
    public TableColumn<Libros, Integer> colStock;

    private final ObservableList<Libros> libros = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurarTabla();

        tblLibro.setItems(libros);

        cmbCategoria.getItems().addAll(
                "Science Fiction",
                "Fantasy",
                "Childrens Literature",
                "Programacion",
                "Dystopian Fiction",
                "Novel"
        );
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTItulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
    }

    public void registrarLibro(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }
        String sql = "INSERT INTO libro(titulo, autor, categoria, precio, stock) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

        ) {
            statement.setString(1, txtTitulo.getText());
            statement.setString(2, txtAutor.getText());
            statement.setString(3, cmbCategoria.getValue());
            statement.setDouble(4, Double.parseDouble(txtPrecio.getText()));
            statement.setInt(5, Integer.parseInt(txtStock.getText()));
            statement.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Registro almacenado",
                    "Libro Registrado",
                    "El libro se ha almacenado existosamente :)"
            );

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String mensaje){

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);

    }

    private boolean validarCampos() {
        return true;
    }

    public void actualizarRegistro(ActionEvent actionEvent) {
        libros.clear();

        String sql = "SELECT * FROM libro";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

        ) {
            while (resultSet.next()) {
                Libros libro = new Libros();

                libro.setId(resultSet.getInt("id"));
                libro.setTitulo(resultSet.getString("titulo"));
                libro.setAutor(resultSet.getString("autor"));
                libro.setPrecio(resultSet.getDouble("precio"));
                libro.setStock(resultSet.getInt("stock"));

                libros.add(libro);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }



    }



    public void limpiarCampo(ActionEvent actionEvent) {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtPrecio.setText("");
        txtStock.setText("");

    }





}

