/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactangle;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author d.yigit
 */
public class ApplicationPane extends JPanel
{
    ImageControl ic;
    JFrame appFrame;
    
    public ApplicationPane( JFrame appFrame)
    {
        this.appFrame = appFrame;
        //File Control
        JButton b = new JButton("Choose Image");
        JFileChooser fc = new JFileChooser();
        b.addActionListener( new ClickListener( fc));
        add( b);
        
        //
        ic = new ImageControl();
        add( ic);
        
    }
    
    public void paintComponent( Graphics g)
    {
        super.paintComponent(g);
        
    }
    
    private class ClickListener implements ActionListener
    {
        JFileChooser fc;
        
        public ClickListener( JFileChooser jfc)
        {
            fc = jfc;
        }
        
        @Override
        public void actionPerformed( ActionEvent e)
        {
            BufferedImage img;
            int value = fc.showOpenDialog( null);
            
            if( value == JFileChooser.APPROVE_OPTION)
            {
                //Image Control
                try 
                {
                    File f = fc.getSelectedFile();
                    img = ImageIO.read( f);
                    ic.setImage(img);
                    appFrame.setPreferredSize( new Dimension( img.getWidth(), img.getHeight() + 50) );
                    appFrame.pack();
                    invalidate();
                    appFrame.repaint();
                } 
                catch (IOException ex) 
                {
                    ic.setImage(null);
                }
                
                repaint();
            }
            
        }
        
    }
}
