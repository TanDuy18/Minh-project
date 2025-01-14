package org.example.project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.project.Database.Database;
import org.example.project.Model.AdminDAO;
import org.example.project.Model.User;
import org.example.project.Model.UserDAO;

import java.sql.Connection;
import java.time.LocalDate;

public class AddController {
    @FXML private Button cancelButton;
    @FXML private TextField name_Input;
    @FXML private DatePicker pay_input;
    @FXML private TextField car_Input;
    @FXML private DatePicker rent_input;
    @FXML private Button saveButton;

    private UserDAO userDAO;
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
        saveButton.setOnAction(event -> addRenter());
    }

    private void addRenter() {
        String name = name_Input.getText();

        String car = car_Input.getText();

        LocalDate pay_date = pay_input.getValue();
        LocalDate rent_date = rent_input.getValue();
        if (name.isEmpty() || pay_date == null || car.isEmpty() || rent_date == null ) {
            // Nếu có trường nào rỗng, hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dữ liệu không đúng");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đủ dữ liệu");
            alert.showAndWait();
        } else {
            // Tiếp tục xử lý khi tất cả dữ liệu được nhập đầy đủ
            System.out.println("Dữ liệu hợp lệ. Thực hiện thêm người thuê.");
            System.out.println(name + " " + pay_date + " " + " " + car + " " + rent_date);

            if(pay_date.isBefore(rent_date)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi ngày");
                alert.setHeaderText(null);
                alert.setContentText("Ngày trả phải lớn hơn hoặc bằng ngày thuê.");
                alert.showAndWait();
            }else {
                User user = new User();
                user.setName(name);
                user.setXeThue(car);
                user.setNgayThue(rent_date.toString());
                user.setNgayTra(pay_date.toString());
                Boolean check = userDAO.insertUser(user) ;

                if(check) {
                    Stage currentStage = (Stage) saveButton.getScene().getWindow();
                    currentStage.close();
                } else {

                }
            }
        }

    }
}
