import ImagePane.Map_1_Pane;
import MapClass.Map_1;
import javafx.application.Application;
import javafx.stage.Stage;

import static ImagePane.Map_1_Pane.*;

public class GameStart extends Application {
    Map_1_Pane start = new Map_1_Pane();
    public void start(Stage Primary_Stage)
    {
        start.start(Primary_Stage);
    }
}
