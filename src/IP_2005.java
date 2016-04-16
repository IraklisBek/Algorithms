package project2015alg;

import integrationproject.algorithms.Algorithms;
import integrationproject.model.BlackAnt;
import integrationproject.model.RedAnt;
import integrationproject.utils.InputHandler;
import integrationproject.utils.Visualize;
import java.util.ArrayList;

/**
 *
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr
 */
public class IP_2005 extends Algorithms{

    public static void main(String[] args) {
        
        checkParameters(args);
        
        //create Lists of Red and Black Ants
        int flag = Integer.parseInt(args[1]);
        ArrayList<RedAnt> redAnts = new ArrayList<>();
        ArrayList<BlackAnt> blackAnts = new ArrayList<>();
        if (flag == 0) {
            InputHandler.createRandomInput(args[0], Integer.parseInt(args[2]));
        }
        InputHandler.readInput("output.txt", redAnts, blackAnts);
        
        IP_2005 algs = new IP_2005();
        
        //debugging options
        boolean visualizeMST = true;
        boolean visualizeSM = true;
        boolean printCC = true;
        boolean evaluateResults = true;

        if(visualizeMST){
            int[][] mst = algs.findMST(redAnts, blackAnts);
            if (mst != null) {
                Visualize sd = new Visualize(redAnts, blackAnts, mst, null, "Minimum Spanning Tree");
                sd.drawInitialPoints();
            }
        }

        if(visualizeSM){
            int[][] matchings = algs.findStableMarriage(redAnts, blackAnts);
            if (matchings != null) {
                Visualize sd = new Visualize(redAnts, blackAnts, null, matchings, "Stable Marriage");
                sd.drawInitialPoints();
            }
        }

        if(printCC){
            int[] coinChange = algs.coinChange(redAnts.get(0), blackAnts.get(0)); 
            System.out.println("Capacity: " + redAnts.get(0).getCapacity());
            for(int i = 0; i < blackAnts.get(0).getObjects().length; i++){
                System.out.println(blackAnts.get(0).getObjects()[i] + ": " + coinChange[i]);
            }
        }
        
        if(evaluateResults){
            System.out.println("\nEvaluation Results");
            algs.evaluateAll(redAnts, blackAnts);
        }
    }

    @Override
    public int[][] findMST(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        int antsPopulation = 2*redAnts.size();
        Graph graph = new Graph();
        graph.implementGraph(antsPopulation, redAnts, blackAnts); 
        KruskalUF uf = new KruskalUF(antsPopulation-1, graph.getGraph(), graph.getEdgesNum());
        return uf.mst(graph.getGraph());

    }
    
    @Override
    public int[][] findStableMarriage(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        StableMerriage s = new StableMerriage(redAnts, blackAnts);
        s.match(redAnts, blackAnts);
        return s.match(redAnts, blackAnts);
    }

    @Override
    public int[] coinChange(RedAnt redAnt, BlackAnt blackAnt) {
        Dynamic d = new Dynamic(redAnt, blackAnt);
        d.makeTable();
        d.getSeeds();
        return d.toEvaluate();
    }
  
    private static void checkParameters(String[] args) {
        if (args.length == 0 || args.length < 2 || (args[1].equals("0") && args.length < 3)) {
            if (args.length > 0 && args[1].equals("0") && args.length < 3) {
                System.out.println("3rd argument is mandatory. Represents the population of the Ants");
            }
            System.out.println("Usage:");
            System.out.println("1st argument: name of filename");
            System.out.println("2nd argument: 0 create random file, 1 input file is given as input");
            System.out.println("3rd argument: number of ants to create (optional if 1 is given in the 2nd argument)");
            System.exit(-1);
        }
    }
        
}
