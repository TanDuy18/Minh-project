package org.example.project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class MainController {
    @FXML private TableView<User> tableView;
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
        carColumn.setCellValueFactory(new PropertyValueFactory<>("xeThue"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ngThueColumn.setCellValueFactory(new PropertyValueFactory<>("ngayThue"));
        ngTraColumn.setCellValueFactory(new PropertyValueFactory<>("ngayTra"));
        trThaiColumn.setCellValueFactory(new PropertyValueFactory<>("valid"));


        carColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER;");
        ngThueColumn.setStyle("-fx-alignment: CENTER;");
        ngTraColumn.setStyle("-fx-alignment: CENTER;");
        trThaiColumn.setStyle("-fx-alignment: CENTER;");

        addActionColumns() ;
        loadData() ;
        addAction.setOnAction(event -> {addValue();}) ;
    }

    private void loadData() {
        List<User> users = userDAO.getAllUser() ;

        userList.addAll(users);
        tableView.setItems(userList);
    }
    private void addActionColumns() {
        actionsColumn = new TableColumn<>("Action");
        Image delete = new Image(getClass().getResource("/org/example/project/Image/icons8-delete-30.png").toExternalForm());
        ImageView deleteView = new ImageView(delete);

        Image edit = new Image(getClass().getResource("/org/example/project/Image/icons8-edit-30 (1).png").toExternalForm()) ;
        ImageView editView = new ImageView(edit);
        Callback<TableColumn<User,Void>, TableCell<User,Void>> cellFactory = new Callback<>() {

            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> userVoidTableColumn) {
                return new TableCell<>() {
                    private final Button editButton = new Button("",editView);
                    private final Button deleteButton = new Button("", deleteView);
                    private final HBox buttonContainer = new HBox(15, editButton, deleteButton);

                    {
                        // Hành động của nút Edit
                        editButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println("Edit clicked for user: " + user.getName());
                            // Gọi phương thức chỉnh sửa user ở đây
                        });

                        // Hành động của nút Delete
                        deleteButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println("Delete clicked for user: " + user.getName());
                            // Gọi phương thức xóa user ở đây
                        });
                        buttonContainer.setAlignment(Pos.CENTER);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonContainer); // Thêm HBox chứa các nút vào ô
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
            Scene scene = new Scene(loader.load()) ;

            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
