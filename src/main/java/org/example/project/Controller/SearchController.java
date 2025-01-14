package org.example.project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.project.Database.Database;
import org.example.project.Model.User;
import org.example.project.Model.UserDAO;

import java.sql.Connection;
import java.util.List;

public class SearchController {
    @FXML private DatePicker fromDate;
    @FXML private Button searchButton;
    @FXML private TextField search_input;
    @FXML private DatePicker toDate;

    private UserDAO userDAO ;
    private static Connection con ;

    static {
        try {
            con = Database.initializeConnection() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Hàm khởi tạo
    @FXML
    public void initialize() {
        userDAO = new UserDAO(con); // Khởi tạo userDAO
        // Gán sự kiện cho nút tìm kiếm
        searchButton.setOnAction(event -> handleSearch());
    }

    private void handleSearch() {
        String searchText = search_input.getText();
        String fromDateText = fromDate.getValue() != null ? fromDate.getValue().toString() : null;
        String toDateText = toDate.getValue() != null ? toDate.getValue().toString() : null;

        List<User> searchResults = userDAO.searchUsers(searchText);
        // Truyền kết quả tìm kiếm cho MainController để cập nhật bảng
        if (searchResults != null && mainController != null) {
            mainController.updateSearchResults(searchResults);
        }
    }
}
