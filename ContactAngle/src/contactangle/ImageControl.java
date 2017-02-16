/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactangle;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;



/**
 *
 * @author d.yigit
 */
public class ImageControl extends JPanel
{
    BufferedImage img;
    
    private int x1,y1,x2,y2;
    private boolean valid = false;
    
    private ArrayList<Point> choosenPoints;
    
    public static BufferedImage copyByPixel(BufferedImage ii) {
        BufferedImage oi = new BufferedImage(ii.getWidth(), ii.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x=0; x < ii.getWidth(); x++) {
            for (int y=0; y < ii.getHeight(); y++) {
                int pixelData = ii.getRGB(x,y);
                oi.setRGB(x,y,pixelData);
            }
        }
        return oi;
    }
    
    public ImageControl()
    {
        img = null;
        setPreferredSize( new Dimension(200,50));
        AreaListener aL = new AreaListener();
        addMouseMotionListener( aL);
        addMouseListener( aL);
        choosenPoints = new ArrayList<Point>();
        
    }
    
    @Override
    public void paintComponent( Graphics g)
    {
        super.paintComponent( g);
        
        if( img != null)
            g.drawImage(img, 0, 0, this);
        else
            g.drawString("NO IMAGE", 0, 15);
        
        g.setColor( Color.RED);
        
        if( x1 < x2 && y1 < y2)
            g.drawRect(x1, y1, x2-x1, y2-y1);
        else if( x1 >= x2 && y1 < y2)
            g.drawRect(x2, y1, x1-x2, y2-y1);  
        else if( x1 < x2 && y1 >= y2)
            g.drawRect(x1, y2, x2-x1, y1-y2);
        else
            g.drawRect(x2, y2, x1-x2, y1-y2);
            
        if(valid && img != null)
        {
            
            if( x1 >= x2){
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }  
            if(y1 >= y2){
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            
            choosenPoints = new ArrayList<Point>();
            for (int y=y1; y < y2; y++) {
                for (int x=x1; x < x2; x++) {
                    int pixelData = img.getRGB(x,y);
                    if(pixelData == -1)
                        choosenPoints.add( new Point(x, y));
                }
            }
            
            SimpleRegression reg = new SimpleRegression();
            for( Point p : choosenPoints)
            {
                reg.addData( p.x, p.y);
            }
            
            
            
            int firstX = choosenPoints.get(0).x;
            int firstY = choosenPoints.get(0).y;
            double slope = reg.getSlope();
            g.setColor( Color.GREEN);
            g.drawLine( firstX, firstY, firstX + (70), firstY + (int) (slope*(70)) );
            g.drawLine( firstX, firstY, firstX - (70), firstY - (int) (slope*(70)) );
            
            
            
            double contactDegrees = (Math.atan( reg.getSlope()) / (2*Math.PI))*360.0;
            
            
            DecimalFormat d = new DecimalFormat("##.###");
            
            g.drawString("Contact Angle = " , 25, 25);
            g.drawString(d.format(contactDegrees) + " degrees", 25, 38);
        }
            
        
    }
    
    public void setImage( BufferedImage img)
    {
        this.img = applyEdgeDetect( img);
        
        
        if( img != null)
            setPreferredSize( new Dimension(img.getWidth(), img.getHeight()));
        else
            setPreferredSize( new Dimension(200,50));
    }
    
    
    //TO BE DONE
    public BufferedImage applyEdgeDetect( BufferedImage input)
    {
        BufferedImage result = copyByPixel( input);
        
        CannyEdgeDetector ced = new CannyEdgeDetector();
        ced.setSourceImage(result);
        ced.process();
        result = ced.getEdgesImage();
        
        return result;
    }
    
    private class AreaListener implements MouseListener, MouseMotionListener
    {
        private boolean flag = false;
        
        @Override
        public void mouseDragged(MouseEvent e) 
        {
           
            if( flag)
            {
                x2 = e.getX();
                y2 = e.getY();
                
                repaint();
            }
            else
            {
                valid = false;
                
                x1 = e.getX();
                y1 = e.getY();
                
                flag = true;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            flag = false;
            valid = true;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
}
