package com.example.guiex1;

import com.example.guiex1.controller.LoginController;
import com.example.guiex1.controller.UtilizatorController;
import com.example.guiex1.domain.PrietenieValidator;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.UtilizatorValidator;
import com.example.guiex1.repository.Repository;
import com.example.guiex1.repository.dbrepo.FriendshipDbRepository;
import com.example.guiex1.repository.dbrepo.UtilizatorDbRepository;
import com.example.guiex1.services.UtilizatorService;
import com.example.guiex1.services.config.ApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class Login extends Application {

    Repository<Long, Utilizator> utilizatorRepository;
    UtilizatorService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        System.out.println(Date.valueOf(LocalDate.now().toString()));

        System.out.println("Reading data from file");
        String username="postgres";
        String pasword="oanniko2002";
        String url="jdbc:postgresql://localhost:5432/Socialnetwork2";
        Repository<Long, Utilizator> utilizatorRepository =
                new UtilizatorDbRepository(url,username, pasword,  new UtilizatorValidator());

        utilizatorRepository.findAll().forEach(x-> System.out.println(x));


        FriendshipDbRepository friendrepo = new  FriendshipDbRepository(url,username,pasword,new PrietenieValidator());
      friendrepo.findAll().forEach(x-> System.out.println(x));
        service =new UtilizatorService(utilizatorRepository, friendrepo);
        initView(primaryStage);
        primaryStage.setTitle("Instagram");
        primaryStage.setWidth(800);
        primaryStage.show();


    }

    private void initView(Stage primaryStage) throws IOException {

        // FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("com/example/guiex1/views/UtilizatorView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/login-view.fxml"));

        AnchorPane userLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));

      LoginController loginController = fxmlLoader.getController();
      loginController.setUtilizatorService(service);

    }
}
