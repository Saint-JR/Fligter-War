package BulletClass;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import static ImagePane.AllImage.Image_Self_Basic_Bullet;
import static ImagePane.Map_1_Pane.Main_Pane;
import static MapClass.Map_1.All_Flighter;
//import static MapClass.Map_1.Flighter_Num;

public class Self_Basic_Bullet extends Bullet{
    int Image_Width=48;
    int Image_Height=181;
    PathTransition pt;
    InvalidationListener listener;

    class Bullet_Thread implements Runnable//集合播放移动动画、越界销毁和击中目标
    {
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pt.play();
                }
            });
            while(true)
            {
                Bullet_Judge();
                if(Bullet_Y<=-10||Exi==0)
                {
                    Exi=0;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pt.stop();
                            Bullet_Image.setVisible(false);
                            Bullet_Image.translateYProperty().removeListener(listener);
                            Main_Pane.getChildren().remove(Bullet_Image);
                        }
                    });
                    Thread.currentThread().interrupt();
                    break;
                }
                try {
                    Thread.sleep(50);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Initialize(int X,int Y,double Radius)
    {
        Bullet_X=X;
        Bullet_Y=Y-60;
        int Origin_Y=Bullet_Y;
        listener=new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Bullet_Y=Origin_Y+(int)Bullet_Image.getTranslateY();
            }
        };
        Judge_Radius=9;
        Damage=1;
        Launch_Interval=300;
        Speed=6;
        Exi=1;
        Bullet_Image=new ImageView(Image_Self_Basic_Bullet);
        Bullet_Image.setFitWidth(Image_Width/3);
        Bullet_Image.setFitHeight(Image_Height/3);
        Bullet_Image.setX(Bullet_X-8);
        Bullet_Image.setY(Bullet_Y-11);
        Bullet_Image.translateYProperty().addListener(listener);
        pt=new PathTransition(Duration.millis(((double)Bullet_Y+50)/(Speed/10)),new Line(Bullet_X,Bullet_Y,Bullet_X,-50),Bullet_Image);
        pt.setInterpolator(Interpolator.LINEAR);
        new Thread(new Bullet_Thread()).start();
    }

    public void Bullet_Judge()
    {
        for(int i = 1; i< All_Flighter.size(); i++)
        {
            if(All_Flighter.get(i).Exi!=0)
            {
                double Dis=Math.pow(Math.pow((All_Flighter.get(i).X-Bullet_X),2)+Math.pow((All_Flighter.get(i).Y-Bullet_Y),2),0.5);
                if(Dis<=Judge_Radius+ All_Flighter.get(i).Judge_Radius)
                {
                    All_Flighter.get(i).Health-=Damage;
                    if(All_Flighter.get(i).Exi!=0)
                        Exi=0;
                }
            }
        }
      }
}
