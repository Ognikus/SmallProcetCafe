package com.example.proectcafe;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class CoffeeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddCoffeeBTN;

    @FXML
    private Button AddZakazBTN;

    @FXML
    private Pane CoffeAllPage;

    @FXML
    private TableView<Coffee> CoffeeTable;

    @FXML
    private TableColumn<Orders, Integer> CountZakazColum;

    @FXML
    private TextField CountZakazField;

    @FXML
    private Button DeleteCoffeBTN;

    @FXML
    private Button DeleteZakazBTN;

    @FXML
    private TableColumn<Coffee, Integer> IDCoffeColum;

    @FXML
    private TextField IDCoffeFieldForRedact;

    @FXML
    private TableColumn<Orders, Integer> IDZakazColum;

    @FXML
    private TextField IDZakazFieldForRedact;

    @FXML
    private Button MenuCafeBTN;

    @FXML
    private TableColumn<Coffee, String> NameCoffeColum;

    @FXML
    private TextField NameCoffeeField;

    @FXML
    private TableColumn<Coffee, String> NameZakazColum;

    @FXML
    private TextField NameZakazField;

    @FXML
    private TableColumn<Coffee, Integer> PriceCoffeColum;

    @FXML
    private TextField PriceCoffeeField;

    @FXML
    private Button RedactCoffeeBTN;

    @FXML
    private Button RedactZakazBTN;

    @FXML
    private TableColumn<Orders, String> SellerZakazColum;

    @FXML
    private TextField SellerZakazField;

    @FXML
    private TableColumn<Orders, String> SizeCoffeColum;

    @FXML
    private TextField SizeCoffeeField;

    @FXML
    private TableColumn<Orders, String> SizeZakazColum;

    @FXML
    private TextField SizeZakazField;

    @FXML
    private TableColumn<Orders, Integer> SummZakazColum;

    @FXML
    private Pane ZakazAllPage;

    @FXML
    private Button ZakazCafeBTN;

    @FXML
    private Label TotalAmountLB;

    @FXML
    private TableView<Orders> ZakazTable;

    DataBaseHandler dbHandler = new DataBaseHandler();

    private final ObservableList<Coffee> dtcoffe = FXCollections.observableArrayList();
    private final ObservableList<Orders> dtorders = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        updateTotalAmount();
        UpdateCoffeeTableColumn();
        UpdateOrdersTableColumn();

        //Таблицы
        CoffeeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Coffee selectedCoffee = CoffeeTable.getSelectionModel().getSelectedItem();
                if (selectedCoffee!= null) {
                    // Здесь вы можете установить значения выбранной игры в текстовые поля
                    IDCoffeFieldForRedact.setText(String.valueOf((selectedCoffee.getId())));
                    NameCoffeeField.setText(selectedCoffee.getCoffename());
                    SizeCoffeeField.setText(selectedCoffee.getCoffesize());
                    PriceCoffeeField.setText(String.valueOf(selectedCoffee.getCoffeprice()));
                }
            }
        });
        ZakazTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Orders selectedOrders = ZakazTable.getSelectionModel().getSelectedItem();
                if (selectedOrders!= null) {
                    // Здесь вы можете установить значения выбранной игры в текстовые поля
                    IDZakazFieldForRedact.setText(String.valueOf((selectedOrders.getId())));
                    NameZakazField.setText(selectedOrders.getOrdersname());
                    SizeZakazField.setText(selectedOrders.getOrderssize());
                    CountZakazField.setText(String.valueOf(selectedOrders.getOrderscount()));
                    SellerZakazField.setText(selectedOrders.getOrdersseller());
                }
            }
        });

        //Показ панелей
        MenuCafeBTN.setOnAction(actionEvent -> {
            CoffeAllPage.setVisible(true);
            ZakazAllPage.setVisible(false);
        });

        ZakazCafeBTN.setOnAction(actionEvent -> {
            CoffeAllPage.setVisible(false);
            ZakazAllPage.setVisible(true);
        });

        //Кнопки основные кофе
        AddCoffeeBTN.setOnAction(actionEvent -> {
            Coffee coffee = new Coffee("",NameCoffeeField.getText(), SizeCoffeeField.getText(), PriceCoffeeField.getText());
            dtcoffe.clear();
            try {
                dbHandler.insertCoffee(coffee);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении данных в список", e);
            }
            UpdateCoffeeTableColumn();
        });

        RedactCoffeeBTN.setOnAction(actionEvent -> {
            Coffee selectedCoffee = CoffeeTable.getSelectionModel().getSelectedItem();
            if (selectedCoffee != null) {
                // Получите обновленные значения из текстовых полей
                String id = IDCoffeFieldForRedact.getText();
                String updatedName = NameCoffeeField.getText();
                String updatedSize = SizeCoffeeField.getText();
                String updatedPrice = PriceCoffeeField.getText();

                //Обновление
                selectedCoffee.setId(Integer.valueOf(id));
                selectedCoffee.setCoffename(updatedName);
                selectedCoffee.setCoffesize(updatedSize);
                selectedCoffee.setCoffeprice(Integer.valueOf(updatedPrice));



                // Здесь вызовите метод для обновления игры в базе данных
                try {
                    dbHandler.updateCoffee(selectedCoffee);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при обновлении данных в базе данных", e);
                }

                // Очистите текстовые поля после обновления
                dtcoffe.clear();
                IDCoffeFieldForRedact.clear();
                NameCoffeeField.clear();
                SizeCoffeeField.clear();
                PriceCoffeeField.clear();
                UpdateCoffeeTableColumn();
            } else {
                System.out.println("Не найдено");
            }
        });

        DeleteCoffeBTN.setOnAction(actionEvent -> {
            try {
                int coffeeIdToDelete = Integer.parseInt(IDCoffeFieldForRedact.getText()); // Идентификатор категории, которую нужно удалить
                dbHandler.deleteCoffee(coffeeIdToDelete);
                System.out.println("Запись успешно удалена");
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при удалении записи из базы данных", e);
            }
            dtcoffe.clear();
            IDCoffeFieldForRedact.clear();
            NameCoffeeField.clear();
            SizeCoffeeField.clear();
            PriceCoffeeField.clear();
            UpdateCoffeeTableColumn();
        });

        //Кнопки основные заказ
        AddZakazBTN.setOnAction(actionEvent -> {
            Orders orders = new Orders("", NameZakazField.getText(), SizeZakazField.getText(), CountZakazField.getText(),"",  SellerZakazField.getText());
            dtorders.clear();
            try {
                dbHandler.insertOrders(orders);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении данных в список", e);
            }
            UpdateOrdersTableColumn();
            updateTotalAmount();
        });

        RedactZakazBTN.setOnAction(actionEvent -> {
            Orders selectedOrders = ZakazTable.getSelectionModel().getSelectedItem();
            if (selectedOrders != null) {
                // Получите обновленные значения из текстовых полей
                String updatedName = NameZakazField.getText();
                String updatedSize = SizeZakazField.getText();
                String updatedCount = CountZakazField.getText();
                String updatedSeller = SellerZakazField.getText();

                //Обновление
                selectedOrders.setOrderscount(Integer.valueOf(updatedCount));
                selectedOrders.setOrderscount(Integer.valueOf(updatedCount));
                selectedOrders.setOrdersseller(updatedSeller);
                selectedOrders.setOrdersname(updatedName);
                selectedOrders.setOrderssize(updatedSize);



                // Здесь вызовите метод для обновления игры в базе данных
                try {
                    dbHandler.updateOrders(selectedOrders);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при обновлении данных в базе данных", e);
                }

                // Очистите текстовые поля после обновления
                dtorders.clear();
                IDZakazFieldForRedact.clear();
                NameZakazField.clear();
                SizeZakazField.clear();
                CountZakazField.clear();
                SellerZakazField.clear();
                UpdateOrdersTableColumn();
                updateTotalAmount();
            } else {
                System.out.println("Не найдено");
            }

        });

        DeleteZakazBTN.setOnAction(actionEvent -> {
            try {
                int ordersIdToDelete = Integer.parseInt(IDZakazFieldForRedact.getText()); // Идентификатор
                dbHandler.deleteOrders(ordersIdToDelete);
                System.out.println("Запись успешно удалена");
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при удалении записи из базы данных", e);
            }
            dtorders.clear();
            IDZakazFieldForRedact.clear();
            NameZakazField.clear();
            SizeZakazField.clear();
            CountZakazField.clear();
            SellerZakazField.clear();
            UpdateOrdersTableColumn();
            updateTotalAmount();
        });
    }

    //Функции для кофе
    private void UpdateCoffeeTableColumn(){
        try {
            addInfAboutCoffee();
            IDCoffeColum.setCellValueFactory(new PropertyValueFactory<>("Id"));
            NameCoffeColum.setCellValueFactory(new PropertyValueFactory<>("Coffename"));
            SizeCoffeColum.setCellValueFactory(new PropertyValueFactory<>("Coffesize"));
            PriceCoffeColum.setCellValueFactory(new PropertyValueFactory<>("Coffeprice"));
            CoffeeTable.setItems(dtcoffe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addInfAboutCoffee() throws  SQLException{
        ResultSet coffees = dbHandler.getCoffee();
        while (coffees.next()){
            Coffee coffee = new Coffee(
                    coffees.getString(1),
                    coffees.getString(2),
                    coffees.getString(3),
                    coffees.getString(4));
            dtcoffe.add(coffee);
        }
    }

    //Функции для продаж
    private void UpdateOrdersTableColumn(){
        try {
            addInfAboutOrders();
            IDZakazColum.setCellValueFactory(new PropertyValueFactory<>("Id"));
            NameZakazColum.setCellValueFactory(new PropertyValueFactory<>("Ordersname"));
            SizeZakazColum.setCellValueFactory(new PropertyValueFactory<>("Orderssize"));
            CountZakazColum.setCellValueFactory(new PropertyValueFactory<>("Orderscount"));
            SummZakazColum.setCellValueFactory(new PropertyValueFactory<>("Orderssum"));
            SellerZakazColum.setCellValueFactory(new PropertyValueFactory<>("Ordersseller"));
            ZakazTable.setItems(dtorders);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addInfAboutOrders() throws  SQLException{
        ResultSet order = dbHandler.getOrders();
        while (order.next()){
            Orders orders = new Orders(
                    order.getString(1),
                    order.getString(2),
                    order.getString(3),
                    order.getString(4),
                    order.getString(5),
                    order.getString(6));
            dtorders.add(orders);
        }
    }

    private void updateTotalAmount(){
        try {
            int totalAmount = dbHandler.getTotalAmount();
            String label = String.valueOf(totalAmount);
            TotalAmountLB.setText(label);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
