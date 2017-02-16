/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactangle;

import java.awt.*;
import javax.swing.JFrame;

/**
 *
 * @author d.yigit
 */
public class ContactAngle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        JFrame app = new JFrame("Contact Angle");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        app.add( new ApplicationPane(app));
        app.pack();
        
        app.setLocation( new Point(400,400));
        app.setVisible(true);
    }
    
}
