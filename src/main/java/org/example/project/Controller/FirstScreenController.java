package org.example.project.Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.project.Database.Database;
import org.example.project.Model.User;
import org.example.project.Model.UserDAO;

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
    private void initialize() {
        userDAO = new UserDAO(con);
        registerPage.setVisible(false);
        loginPage.setVisible(true);
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
                User user = new User();
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
    }
}