package project2015alg;

/**
 * Class that will be used to represent Edges.
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr
 */
public class Edge {
    private AntPoint ant1;
    private AntPoint ant2;
    private double distance;
    /**
     * Constructor.
     * @param ant1      the source.
     * @param ant2      the destination.    
     * @param distance  the <<weight>> of the edge. In our case is the distance of one Ant to another.
     */
    public Edge(AntPoint ant1, AntPoint ant2, double distance){   
        this.ant1 = ant1;
        this.ant2 = ant2;
        this.distance = distance;
    }     

    //Getters and Setters.
    public AntPoint getAnt1(){
        return ant1;
    }
    
    public AntPoint getAnt2(){
        return ant2;
    }
    
    public double getDistance(){
        return distance;
    }
    
    public void setAnt1(AntPoint ant1){
        this.ant1=ant1;
    }
    
    public void setAnt2(AntPoint ant2){
        this.ant2=ant2;
    }
    
    public void setDistance(double distance){
        this.distance =distance;
    } 
   
}
