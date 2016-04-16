
package project2015alg;

import integrationproject.model.Ant;
import integrationproject.model.BlackAnt;
import integrationproject.model.RedAnt;
import java.util.ArrayList;
/**
 * Class to implement tha graph that will be inserted to find mst method.
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr
 */
public class Graph {
    
    private final ArrayList<Edge> graph ;
    private int edgesNum;
    /**
     * Constructor of Graph.
     */
    public Graph(){    
        graph = new ArrayList<>();//Will contain the List of the edges of the graph.
        edgesNum=0;//Counts the numer of edges.
    }
    /**
     * @return  The number of edges. 
     */             
    public int getEdgesNum(){
        return edgesNum;
    }    
    /**
     * @return  Returns the graph. 
     */
    public ArrayList getGraph(){
        return graph;
    }     

    /**
     * Methos to construct the graph.
     * @param population    The ants population.
     * @param redAnts       List of Red Ants.
     * @param blackAnts     List of Black Ants.
     */
    public void implementGraph(int population, ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        double minDistance;
        double distance;  
        ArrayList<Ant> allAnts = new ArrayList<>();//List of all Ants where even slots of array represent Red Ants and prime slots represent Black Ants
        for(int i=0; i<population/2; i++){           
            allAnts.add(redAnts.get(i));   
            allAnts.add(blackAnts.get(i));
        }          

        for(int i=0; i<allAnts.size(); i++){           
            //To avoid the connection of every Ant with all the others, every loop choose a random distance from an Ant to another Ant.
            //That distance is minDistance variable
            minDistance = allAnts.get(i).getDistanceFrom(allAnts.get((i+1)%allAnts.size())); 
            //Every time the loop runs that means that a different colour of Ant is coming.
            //So i%2 can tell the kind of the Ant.
            AntPoint ant1 = new AntPoint(i%2, allAnts.get(i).getID()); 

            for(int j=0; j<allAnts.size(); j++){
                if(i == j)
                    continue;//Do not want the distance from the own Ant. 
                distance = allAnts.get(i).getDistanceFrom(allAnts.get(j));
                AntPoint ant2 = new AntPoint(j%2, allAnts.get(j).getID());
                //Something was wrong with if comparisson. Some minimum edges did not add to the arrayList I do not know why...
                //if(distance <= minDistance ){//Here the minDistance that mentioned above is compered with every distance of all other Ants 
                                            //and if this distance is minor than minDistance the edge will be added to the graph.
                    minDistance = distance;                   
                    graph.add(new Edge(ant1, ant2, distance));
                    edgesNum++;                              
                //}                  
            }
        }
        quickSort(graph, 0, graph.size()-1);
        /*for(int i=0; i<graph.size()-1; i++){            
            if(graph.get(i).getDistance() == graph.get(i+1).getDistance()){
                graph.remove(i+1);//Erase same edges
                edgesNum--;
            }
        }   
        
        int k=graph.size();
        for(int i=0; i<k; i++){
            //Add all edges again but with opposite source and destination so the graph will be undirected.
            graph.add(new Edge(graph.get(i).getAnt2(), graph.get(i).getAnt1(), graph.get(i).getDistance()));
            edgesNum++;
        }
        quickSort(graph, 0, graph.size()-1);// So the edges will be sorted at KruskalUF Class.
        for(int i=0; i<edgesNum; i++){
            System.out.println(graph.get(i).getAnt2().getKind() + " " + graph.get(i).getAnt2().getID() + " " + graph.get(i).getAnt1().getKind() + " " + graph.get(i).getAnt1().getID() + " " + graph.get(i).getDistance());
            
        }*/
    }   
    
    /**
     * Swaps two elements. Useful for partition.
     * @param edges
     * @param ant1  an ant.
     * @param ant2  the other ant.
     */
     public  void swap(ArrayList<Edge> edges, int ant1, int ant2){
        Edge temp = edges.get(ant1);
        edges.set(ant1, edges.get(ant2));
        edges.set(ant2, temp);
    }      
    /**
     * Method useful for QuickSort method.
     * @param edges     the edges to be sorted.
     * @param left      the left position to compare.
     * @param right     the right position to compare.
     * @return          the split position
     */
    public int partition(ArrayList<Edge> edges, int left, int right){
        double pivot = edges.get(left).getDistance();//First element as pinot. 
        
        while(left <= right){//left and right has not crossed yet.           
            while(edges.get(left).getDistance() < pivot){//Keeps searching until it finds an element smaller than pivot
                left++;
            }
            while(edges.get(right).getDistance() > pivot){//Keeps searching until it finds an element bigger than pivot.
                right--;
            }            
            if(left <= right){//That means that a small element is righter that a bigger element.
                swap(edges, left, right);//Sooo swap them.
                left++;
                right--;
            }
        }        
        return left;
    }
    /**
     * Recortional method that sorts edges ArrayList. 
     * Depends on the distances between ants. 
     * It keeps sorting half of the array every time until its done.
     * params are the same as in partition method.
     */    
    public void quickSort(ArrayList<Edge> edges, int left, int right){
        int leftIndex = partition(edges, left, right);
        
        if(left < leftIndex -1){
            quickSort(edges, left, leftIndex - 1);
        }
        if(right > leftIndex){
            quickSort(edges,leftIndex, right);
        }
    }    
}       


