package ImagePane;

import MapClass.Map;
import MyThread.Generator;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static ImagePane.AllImage.*;
import static MapClass.Map.*;

public class MapPane {
    public static Pane Main_Pane=new Pane();
    public static Pane Title_Pane=new Pane();
    public static Scene Title_Scene=new Scene(Title_Pane,Map_Width,Map_Height);
    public static Scene Main_Scene=new Scene(Main_Pane,Map_Width,Map_Height);

    public static void Initialize_Game(Stage Primary_Stage)
    {
        Primary_Stage.setScene(Main_Scene);
        Main_Pane.getChildren().add(ImageView_Background_1);
        Map.Initialize_Game();
        Main_Scene.setOnKeyPressed(e->{
            switch(e.getCode())
            {
                case UP:My_Flighter.Up();break;
                case RIGHT:My_Flighter.Right();My_Flighter.Flighter_Image.setImage(Image_Right_Flighter);break;
                case DOWN:My_Flighter.Down();break;
                case LEFT:My_Flighter.Left();My_Flighter.Flighter_Image.setImage(Image_Left_Flighter);break;
            }
        });
        Main_Scene.setOnKeyReleased(e->{
            switch(e.getCode())
            {
                case UP:My_Flighter.Up_Signal=0;break;
                case RIGHT:My_Flighter.Right_Signal=0;My_Flighter.Flighter_Image.setImage(Image_Self_Flighter);break;
                case DOWN:My_Flighter.Down_Signal=0;break;
                case LEFT:My_Flighter.Left_Signal=0;My_Flighter.Flighter_Image.setImage(Image_Self_Flighter);break;
            }
        });
        new Thread(new Generator()).start();
    }

    public void start(Stage Primary_Stage)
    {
        Map.Initialize_Title(Primary_Stage);
        Main_Pane.setPickOnBounds(false);

        Primary_Stage.setTitle("飞机大战");
        Primary_Stage.setResizable(false);

        Primary_Stage.setScene(Title_Scene);
        Primary_Stage.show();
        Primary_Stage.setOnCloseRequest(e->{System.exit(0);});

    }
}

