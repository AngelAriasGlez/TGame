/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.JLabel;

/**
 *
 * @author aariasgonzalez
 */
public class Box extends JLabel{

    private Player mPlayer;
    private Point mPosition;
    
    public Box(Point p){
        mPosition = p;
    }
    public Point getPosition(){
        return mPosition;
    }
    
    public void setPlayer(Player type){
        mPlayer = type;
        repaint();
        validate();
    }
    public Player getPlayer(){
        return mPlayer;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Rectangle rect = g.getClipBounds();
        
        g.setColor(Color.GRAY);
        g.drawRect(rect.x-1, rect.y, rect.width, rect.height);
        
        if(mPlayer != null){
        
            int thick = 10;
            g2.setStroke(new BasicStroke(10));
            if(mPlayer.getId() == 0){
                g.setColor(Color.GREEN);
                g.drawArc(rect.x + thick, rect.y + thick, rect.width - (thick*2), rect.height - (thick*2), 0, 180);
                g.drawArc(rect.x + thick, rect.y + thick, rect.width - (thick*2), rect.height - (thick*2), 180, 360);
            }else if(mPlayer.getId() == 1){
                g.setColor(Color.BLUE);
                g.drawLine(rect.x + thick, rect.y + thick, rect.width - thick, rect.height - thick);
                g.drawLine(rect.width - thick, rect.y + thick, rect.x + thick, rect.height - thick);
            }else if(mPlayer.getId() == 2){
                g.setColor(Color.BLACK);
                g.drawRect(rect.x + thick, rect.y + thick, rect.width - (thick*2), rect.height - (thick*2));
            }else if(mPlayer.getId() == 3){
                g.setColor(Color.MAGENTA);
                //g.drawRect(rect.x + thick, rect.y + thick, rect.width - (thick*2), rect.height - (thick*2));
                g2.setStroke(new BasicStroke(8));
                Polygon p = new Polygon();
                p.addPoint(rect.x + thick + (int)((float)thick * 0.1f), rect.height - thick - (int)((float)thick * 0.2f));
                p.addPoint((rect.width / 2), rect.y + (int)((float)thick * 1.4f));
                p.addPoint(rect.width - thick - (int)((float)thick * 0.1f), rect.height - thick - (int)((float)thick * 0.2f));

                g.drawPolygon(p);

            }
        }
        /*Polygon shape3 = new Polygon();
        shape3.addPoint(rect.x, rect.y + rect.height - 1);
        shape3.addPoint(rect.x + rect.width - 10, rect.y + rect.height - 1);
        shape3.addPoint(rect.x + rect.width - 1, rect.y + rect.height / 2);
        shape3.addPoint(rect.x + rect.width - 10, rect.y);
        shape3.addPoint(rect.x, rect.y);

        g.setColor(Color.BLUE);
        g.fillPolygon(shape3);

        g.setColor(Color.LIGHT_GRAY);
        g.drawPolygon(shape3);*/
    }
    
}
