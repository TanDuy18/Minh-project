package org.example.project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.project.Database.Database;
import org.example.project.HelloApplication;
import org.example.project.Model.Admin;
import org.example.project.Model.User;
import org.example.project.Model.UserDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User , String> carColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> ngThueColumn;
    @FXML private TableColumn<User, String> ngTraColumn;
    @FXML private TableColumn<User, String> trThaiColumn;
    @FXML private TableColumn<User, Void> actionsColumn;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML private Button addAction;
    @FXML private Button carAction;
    @FXML private Button closeAction;
    @FXML private Button refreshAction;
    @FXML private Button searchAction;

    Admin user = new Admin();
    public void setAdmin(Admin user) {this.user = user;}
    public Admin getAdmin() {return user;}

    private UserDAO userDAO ;
    private static Connection con ;
    static {
        try {
            con = Database.initializeConnection() ;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        userDAO = new UserDAO(con);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        carColumn.setCellValueFactory(new PropertyValueFactory<>("xeThue"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ngThueColumn.setCellValueFactory(new PropertyValueFactory<>("ngayThue"));
        ngTraColumn.setCellValueFactory(new PropertyValueFactory<>("ngayTra"));
        trThaiColumn.setCellValueFactory(new PropertyValueFactory<>("valid"));

        idColumn.setStyle("-fx-alignment: CENTER ;");
        carColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER;");
        ngThueColumn.setStyle("-fx-alignment: CENTER;");
        ngTraColumn.setStyle("-fx-alignment: CENTER;");
        trThaiColumn.setStyle("-fx-alignment: CENTER;");

        addActionColumns() ;
        loadData() ;
        addAction.setOnAction(event -> {addValue();}) ;
        refreshAction.setOnAction(event -> {refreshTable() ;}) ;
        searchAction.setOnAction(event -> {search();});
    }

    private void loadData() {
        List<User> users = userDAO.getAllUser() ;

        userList.addAll(users);
        tableView.setItems(userList);
    }
    private void addActionColumns() {
        actionsColumn = new TableColumn<>("Action");
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final HBox buttonContainer = new HBox(15);

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Tạo mới ImageView cho mỗi nút
                            ImageView editView = new ImageView(new Image(getClass().getResource("/org/example/project/Image/icons8-edit-30.png").toExternalForm()));
                            ImageView deleteView = new ImageView(new Image(getClass().getResource("/org/example/project/Image/icons8-delete-30.png").toExternalForm()));

                            editView.setFitWidth(20);   // Chiều rộng (ví dụ: 20 pixel)
                            editView.setFitHeight(20); // Chiều cao (ví dụ: 20 pixel)

                            deleteView.setFitWidth(20);
                            deleteView.setFitHeight(20);


                            // Tạo các nút với hình ảnh
                            Button editButton = new Button("", editView);
                            Button deleteButton = new Button("", deleteView);

                            // Đặt hành động cho nút Edit
                            editButton.setOnAction(event -> {
                                User user = getTableView().getItems().get(getIndex());
                                try {
                                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/org/example/project/EditScreen.fxml"));
                                    Parent root = loader.load();

                                    EditController editController = loader.getController();
                                    editController.setMainController(MainController.this);
                                    editController.setUser(user);
                                    editController.setId(user.getId());

                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Sửa người thuê");
                                    stage.initOwner(editButton.getScene().getWindow());
                                    stage.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            // Đặt hành động cho nút Delete
                            deleteButton.setOnAction(event -> {
                                User user = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Xác nhận");
                                alert.setHeaderText(null);
                                alert.setContentText("Bạn có chắc chắn muốn xóa " + user.getName() + "?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    userDAO.deleteUser(user);
                                    refreshTable();
                                }
                            });

                            // Căn chỉnh các nút và thêm vào HBox
                            buttonContainer.setAlignment(Pos.CENTER);
                            buttonContainer.getChildren().setAll(editButton, deleteButton);

                            setGraphic(buttonContainer);
                        }
                    }
                };
            }
        };
        actionsColumn.setCellFactory(cellFactory);
        actionsColumn.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(actionsColumn);
    }
    private void addValue() {
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/org/example/project/AddScreen.fxml"));
            Parent root = loader.load();

            AddController addController = loader.getController();
            addController.setMainController(this); // Truyền MainController sang AddController

            // Tạo một Stage mới cho AddScreen
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Thêm người thuê");

            // Đặt kiểu stage là NON-MODAL (cho phép cả hai màn hình hiển thị)
            stage.initOwner(addAction.getScene().getWindow());
            stage.show();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void refreshTable() {
        userList.clear();
        userList.addAll(userDAO.getAllUser());
        tableView.refresh();
    }
    public void search() {
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/org/example/project/SearchScreen.fxml"));
            Parent root = loader.load();
            SearchController searchController = loader.getController();
            searchController.setMainController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tìm kiếm");

            // Hiển thị cửa sổ tìm kiếm
            stage.initOwner(searchAction.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateSearchResults(List<User> searchResults) {
        userList.clear();
        if (searchResults != null && !searchResults.isEmpty()) {
            System.out.println(searchResults.getFirst().getName());
            userList.addAll(searchResults);
        } else {
            System.out.println("No results found.");
        }
        tableView.setItems(userList);
    }

}
