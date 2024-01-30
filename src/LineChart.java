import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class LineChart {
    public static void DrawChart(ArrayList<Double> values){
        JFrame frame = new JFrame("Sin Function Output to Generation");
        frame.setSize(864,864);
        frame.setContentPane(new myPanel(values));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
class myPanel extends JPanel{
    private final int no_Gen;
    private final ArrayList<Double> gen_Best;
    myPanel(ArrayList<Double> gen_Best){
        setBackground(Color.white);
        no_Gen= gen_Best.size();
        this.gen_Best = new ArrayList<>(gen_Best);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.BLACK);
        //horizontal line
        //metric being used for graph is 50 - 750
        gd.drawLine(50,750,770,750);
        //horizontal label
        gd.drawString("Number of Generations", 350, 800);
        //Vertical line
        gd.drawLine(50,30,50,750);
        //vertical label
        AffineTransform at = new AffineTransform();
        at.rotate(Math.PI / 2);
//        AffineTransform at = AffineTransform.getQuadrantRotateInstance(1);
        gd.setTransform(at);
        gd.drawString("Sin Function Values", 300, -3);
        at = AffineTransform.getQuadrantRotateInstance(0);
        gd.setTransform(at);
        double max= Math.ceil(gen_Best.stream().max(Double::compare).isPresent()? gen_Best.stream().max(Double::compare).get():0.0);
        double min= gen_Best.stream().min(Double::compare).isPresent()? gen_Best.stream().min(Double::compare).get():0.0;

        double y = min;
        int x = no_Gen / 10;
        // ticks
        for(int i=0; i<700; i+=70)
        {
            gd.drawLine(45,680-i,55,680-i);
            //y tick measurement
            gd.drawString(String.format("%.2f",y),20,680-i);
            gd.drawLine(120+i,745,120+i,755);
            //x tick measurement
            gd.drawString(String.valueOf(x),120+i,777);
            x += no_Gen / 10;
            y += (max - min) /10;
        }

        double pointx1 = 50+700.0/30;
        double pointx2 = pointx1+700.0/30;
        double pointy1;
        double pointy2;
        for(int num=0; num<gen_Best.size()-1; num++){
            pointy1 = 750 - ((gen_Best.get(num) - min)/(max-min) * 700 +70);
            pointy2 = 750 - ((gen_Best.get(num+1) - min)/(max-min) * 700 +70);
            //Drawing point
            gd.setStroke(new BasicStroke(8));
            gd.setColor(Color.PINK);
            gd.draw(new Line2D.Double( pointx1,pointy1,pointx1,pointy1));
            //Drawing line
            gd.setStroke(new BasicStroke(4));
            gd.setColor(Color.ORANGE);
            gd.draw(new Line2D.Double( pointx1,pointy1,pointx2,pointy2));
            pointx1+=700.0/30;
            pointx2+=700.0/30;
        }
    }

}