/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dext
 */
public class testPoints 
{
    private String param = "(1.3,5);(3,4);(3,5.4)";
    private double[][] points;
    
    public testPoints()
    {
        String auxPoints = param;
        String [] pares = auxPoints.split(";"); /* pares [0] => (1.3,5)  pares[1] => (3,4) */
        this.points = new double[pares.length][2];
        for(int i = 0; i< pares.length;i++)
        {
            pares[i] = pares[i].replace('(', ' '); // pares[0] => 1.3,5)
            pares[i] = pares[i].replace(')', ' '); // pares[0] => 1.3,5
            String [] xy = pares[i].split(","); // xy[0] => 1,3   xy[1] => 5
            this.points[i][0] = Double.valueOf(xy[0]);
            this.points[i][1] = Double.valueOf(xy[1]);
        }
        
        for(int i =0 ;i<points.length;i++)
            System.out.println("x= " + points[i][0] + "\ny= " + points[i][1] + "\n\n");
    }
    
    public static void main(String[] args)
    {
        new testPoints();
    }
}
