/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactangle;

/**
 *
 * @author d.yigit
 */
public class LineSegment 
{
    double x1, y1, x2, y2;
    
    public LineSegment( double x1, double y1, double x2, double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x1 = x2;
        this.y1 = y2;
    }
    
    public double getSlope()
    {
        return ( y2-y1)/( x2-x1);
    }
    
    
}
