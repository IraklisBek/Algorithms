package project2015alg;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class to find Minimum Spanning Tree using Kruskal + Union-Find.
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr
 */
public class KruskalUF {
    private final HashMap<AntPoint, AntPoint> parents;//Key is an Ant that has as Parent the Value.
    private final HashMap<AntPoint, Integer> rank;  //Counts the number of elements in the tree rooted at Ant Key.
                                                  //Useful to avoid further comparisons and make the algorithm more optimize.
    private final int mst[][];
    private int mstSlots;
    
    public KruskalUF(int mstSize, ArrayList<Edge> edges, int edgeNum){
        parents = new HashMap<>();   
        rank = new HashMap<>();
        mst = new int[mstSize][4];
        mstSlots=0;
        for(int i=0; i<edgeNum; i++){      
            //At first, each Ant has as Parent him self
            parents.put(edges.get(i).getAnt1(), edges.get(i).getAnt1());
            parents.put(edges.get(i).getAnt2(), edges.get(i).getAnt2());
            //and every Ant has 0 elements rooted at it.
            rank.put(edges.get(i).getAnt1(), 0);
            rank.put(edges.get(i).getAnt2(), 0);
        }     
    }
    /**
     * Method that finds the root of an Ant.
     * @param ant   the ant that its root will be found.
     * @return      the root of the ant.
     */
    public AntPoint find(AntPoint ant) {
        while (!ant.equals(parents.get(ant))) {
            ant = parents.get(parents.get(ant));
        }
        return ant;
    }    
    /**
     * Decleres the root of ant11 or ant22.
     * @param ant11     Ant that will be declered as Parent or Child.
     * @param ant22     Ant that will be declered as Parent or Child.
     */
    public void union(AntPoint ant11, AntPoint ant22) {
        AntPoint ant1 = find(ant11);
        AntPoint ant2 = find(ant22);
        if (ant1.equals(ant2)) return;
        //rank comparisons are useful to help find method run more quickly by changing as more values of HashMap as it can.
        if (rank.get(ant1) < rank.get(ant2)) {//If ant1 has less childs than ant2.
            parents.put(ant1, ant2);//Make root ant2       
        }
        else if (rank.get(ant1) > rank.get(ant2)) {
            parents.put(ant2, ant1);
        }
        else {
            //Random choice could be otherwise.
            parents.put(ant2, ant1);
            rank.put(ant1, rank.get(ant1) + 1);
        }
    }
    /**
     * Implemets mst.
     * @param edges the edges of the graph.
     * @return      the mst.
     */
    public int[][] mst(ArrayList<Edge> edges) {
     
        for (Edge edge : edges) {
            //Find roots of source and destination.
            AntPoint ant1 = find(edge.getAnt1());
            AntPoint ant2 = find(edge.getAnt2());
            if(!ant1.equals(ant2)){//If roots are different unite the ants. If roots are the same that means that there is a circle.  
                uniteMininumEdge(edge.getAnt1(), edge.getAnt2());
                union(ant1, ant2); 
            } 
        }  
        return mst;
    }      
    /**
     * Method to unite two ants for mst.
     * @param ant1  an ant.
     * @param ant2  the other ant.
     */
    public void uniteMininumEdge(AntPoint ant1, AntPoint ant2){
        mst[mstSlots][0] = ant1.getID();
        mst[mstSlots][1] = ant1.getKind();
        mst[mstSlots][2] = ant2.getID();
        mst[mstSlots][3] = ant2.getKind();
        mstSlots++;
    }       

}
