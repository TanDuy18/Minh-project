package org.example.project.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.project.Database.Database;
import org.example.project.HelloApplication;
import org.example.project.Model.Admin ;
import org.example.project.Model.AdminDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class FirstScreenController {
    @FXML private AnchorPane loginPage;
    @FXML private Button loginPaneButton;
    @FXML private AnchorPane registerPage;
    @FXML private TextField registerUsername;
    @FXML private Button signUpButtom;
    @FXML private PasswordField signUpPass;
    @FXML private TextField signUpPassValue;
    @FXML private Button showPass;
    @FXML private Button closePass;
    @FXML private Button showPass1;
    @FXML private Button closePass1;
    @FXML private PasswordField confirmPass;
    @FXML private TextField confirmPassValue;
    @FXML private Button SignUp;
    @FXML private Button SignIn;
    @FXML private TextField userName;
    @FXML private TextField passWord1;
    @FXML private PasswordField passWord;
    @FXML private CheckBox Checkbox_show;


    private AdminDAO userDAO;
    private static Connection con ;
    static {
        try {
            con = Database.initializeConnection() ;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize() {
        userDAO = new AdminDAO(con);
        registerPage.setVisible(false);
        loginPage.setVisible(true);
        signUpPassValue.setVisible(false);
        confirmPassValue.setVisible(false);
        closePass1.setVisible(false);
        closePass.setVisible(false);
        passWord1.setVisible(false);
        signUpButtom.setOnAction(event -> {
            registerPage.setVisible(true);
            loginPage.setVisible(false);
        });
        loginPaneButton.setOnAction(event -> {
            loginPage.setVisible(true);
            registerPage.setVisible(false);
        }) ;
        showPass.setOnAction(event -> {
           String pass = signUpPass.getText();
           signUpPassValue.setText(pass);

           signUpPass.setVisible(false);
           signUpPassValue.setVisible(true);

           showPass.setVisible(false);
           closePass.setVisible(true);
        });
        closePass.setOnAction(event -> {
            String pass = signUpPassValue.getText();
            signUpPass.setText(pass);

            signUpPass.setVisible(true);
            signUpPassValue.setVisible(false);

            showPass.setVisible(true);
            closePass.setVisible(false);
        }) ;
        showPass1.setOnAction(event -> {
            String pass = confirmPass.getText();
            confirmPassValue.setText(pass);

            confirmPass.setVisible(false);
            confirmPassValue.setVisible(true);

            showPass1.setVisible(false);
            closePass1.setVisible(true);
        });
        closePass1.setOnAction(event -> {
            String pass = confirmPassValue.getText();
            confirmPass.setText(pass);

            confirmPassValue.setVisible(false);
            confirmPass.setVisible(true);

            showPass1.setVisible(true);
            closePass1.setVisible(false);
        }) ;
        SignUp.setOnAction(event -> {
            String confirm_pass = confirmPass.getText();
            String pass = signUpPass.getText() ;
            String username = registerUsername.getText();

            if (confirm_pass.equals(pass)) {
                Admin user = new Admin();
                user.setName(username);
                user.setPassword(pass);

                try {
                    boolean addUser = userDAO.insertUser(user) ;
                    if (addUser) {
                        System.out.println("User added");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {

            }
        });
        Checkbox_show.setOnAction(event -> {
            if(Checkbox_show.isSelected()) {
                passWord.setVisible(false);
                passWord1.setVisible(true);

                String pass = passWord.getText() ;
                passWord1.setText(pass);
            } else {
                passWord.setVisible(true);
                passWord1.setVisible(false);

                String pass = passWord1.getText() ;
                passWord.setText(pass);
            }
        });
        SignIn.setOnAction(event -> {
            Admin user = new Admin();
                if(!passWord.isVisible()) {
                    user.setName(userName.getText());
                    user.setPassword(passWord1.getText());
                }else {
                    user.setName(userName.getText());
                    user.setPassword(passWord.getText());
                }
            System.out.println(user.getName() + " " + user.getPassword());

            Boolean check ;
            try {
                check = userDAO.selectUser(user) ;
                System.out.println(check);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (check) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/org/example/project/SecondScreen.fxml")); // Thay thế bằng đường dẫn thực tế
                System.out.println(HelloApplication.class.getResource("/org/example/project/SecondScreen.fxml"));

                Parent newRoot = null;
                try {
                    newRoot = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Nếu cần truyền dữ liệu, sử dụng controller của màn hình mới
                MainController controller = loader.getController();
                controller.setAdmin(user); // Nếu cần truyền thông tin User

                // Lấy Stage hiện tại và đặt giao diện mới
                Stage currentStage = (Stage) SignIn.getScene().getWindow();
                currentStage.setScene(new Scene(newRoot));
            }

        }) ;
    }
}