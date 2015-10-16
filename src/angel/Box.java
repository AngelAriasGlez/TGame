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
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author aariasgonzalez
 */
public class Box extends JLabel{
    public enum DType{
        LOCAL, REMOTE, EMPTY
    }
    private DType mType = DType.EMPTY;
    
    public Box(){
        
    }
    
    public void setDrawType(DType type){
        mType = type;
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Rectangle rect = g.getClipBounds();
        
        g.setColor(Color.GRAY);
        g.drawRect(rect.x-1, rect.y, rect.width, rect.height);
        
        int thick = 10;
        g2.setStroke(new BasicStroke(10));
        if(mType == DType.REMOTE){
            g.setColor(Color.BLACK);
            g.drawArc(rect.x + thick, rect.y + thick, rect.width - (thick*2), rect.height - (thick*2), 0, 180);
            g.drawArc(rect.x + thick, rect.y + thick, rect.width - (thick*2), rect.height - (thick*2), 180, 360);
        }else if(mType == DType.LOCAL){
            g.setColor(Color.BLUE);
            g.drawLine(rect.x + thick, rect.y + thick, rect.width - thick, rect.height - thick);
            g.drawLine(rect.width - thick, rect.y + thick, rect.x + thick, rect.height - thick);
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
