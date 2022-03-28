package com;

import com.view.BaseView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.net.URL;

public class RootView extends Application {

    private final static Log LOG = LogFactory.getLog(RootView.class);
    public static Stage PRIMARY_STAGE;

    @Override
    public void start(Stage primaryStage) throws Exception {
        RootView.PRIMARY_STAGE = primaryStage;

        Parent root = FXMLLoader.load(BaseView.class.getResource("root.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
