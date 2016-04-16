package project2015alg;

import integrationproject.model.BlackAnt;
import integrationproject.model.RedAnt;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to implement the coin change problem.
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr
 */
public class Dynamic {
    
    private final int weight;                   //The weight to fill.
    private final int weights[];                //The weights of the seeds.
    private final int weights2[];               //Weights of seeds with one slot more than weights and first slot 0.
    private int seeds;                          //Number of seeds.
    private final int[][] table ;               //The dynamin table.
    private final ArrayList<Integer> finals;    //List of seeds that will be used.
    private final int[] toEvaluate;             //Array where its slots will represent the quantity of the seed that is needed.
   /**
    * Constructor
    * @param redAnt     a Red Ant to get the capacity.
    * @param blackAnt   a Black Ant to get the seeds.
    */
    public Dynamic(RedAnt redAnt, BlackAnt blackAnt){
        finals = new ArrayList<>();
        toEvaluate = new int[blackAnt.getObjects().length];
        for(int i=0; i< toEvaluate.length; i++){
            toEvaluate[i]=0;
        }
        weight = redAnt.getCapacity();
        weights = new int[blackAnt.getObjects().length];
        weights2 = new int[blackAnt.getObjects().length + 1];
        weights2[0]=0;
        for(int i=0; i<weights.length; i++){
            weights[i]=blackAnt.getObjects()[i];
            weights2[i+1]=weights[i];
        }
        Arrays.sort(weights2);//Sort the array so when we calculate the dynamic table the last line will represent the bigger element 
                              // the second last the second bigger etc.. In this way we will keep track of coins.      
        seeds=weights.length;
        table = new int[seeds + 1][weight +1]; 
        for(int i=0; i<seeds+1; i++){
            table[i][0] = 0;
        }        
        for(int j=0; j<weight+1; j++){
            table[0][j]=j;
        }          
    }
    /**
     * The dynamic table will have 0...seeds rows and 0...weight columns.
     * That means that for 5 seeds and 100 weight the table will hava 6 rows and 101 columns.
     * The first row represent the possiple weights that we can have 
     *  and it is been initialized at the constructor with 0, 1, 2, ..., 100.
     *  Because first row represents the possible weights thats why we need weights2 array. 
     *  So the slots 1,2,3,4,5 of weight2 that has the values of weight 0,1,2,3,4
     *  will represent a row[0] (1,2,3,4,5) of the dynamic table !0 where each slot of the row has a number that shows 
     *  the minimum number of seeds that we need for a j (0..100) weight that exists in the first row.
     * The first column has as (0,0) 0 so it is been initialized at the contrusctor with zeros 
     *  because there is no number or variety of seeds that can fill 0 weight.
     * So because i=0 and j=0 had been initialized the loops begin from 1.
     * In each loop we check if j (a weight <= 100) is greater or equal than weights2[i] (a weight of a seed).
     *  If yes then table[i][j] will be equal with the minimum between the number above the slot i,j that is the number
     *  that represents the minimum quanity of coins that calculated for the above line for j weights, and 
     *  the number of weights2[i] slots before the slot on the same row + 1  (+1 because a new seed had been added)
     *  Else table[i][j] will be equal with the number above the slot i,j because j<weights2[i] and there is no meaning 
     *  calculating the same things
     * @return  the dynamic table.
     */
    public int[][] makeTable(){
        for(int i=1; i<seeds+1; i++){
            for(int j=1; j<weight+1; j++){
                if(j>=weights2[i]){
                    table[i][j] = min(table[i-1][j], 1 + table[i][j-weights2[i]]);
                }else{
                    table[i][j]=table[i-1][j];
                }
            }
        }
        for (int[] rows : table) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }       
        return table;
    }
    /**
     * Keeps track of seeds.
     * Each time a number is different from the number above him that means that we need the seed the row represents  to fill our weight 
     *  because the seed is part of the j weight that represetns at this time. Then we move the cursor weights2[i] back
     *  to check the seed that represents j - weights2[i] etc..
     * If a number is equal with his above that means that we do not need the seed the row represents
     *  because both [i][j] needs the same number of seeds and we move on to the number (and row) above it.
     * @return  the seeds needed.
     */
    public ArrayList<Integer> getSeeds(){
        int[][] table = makeTable();
        for(int j=weight; j>0; j--){               
            if(table[seeds][j] != table[seeds-1][j]){
                finals.add(weights2[seeds]);
                if(1+j-weights2[seeds]>=0)
                    j=1+j-weights2[seeds];
            }else{
                seeds=seeds-1;
                j++;
            }
            if(seeds==1)
                break;
        }
        return finals;
    }
    /**
     * In each loop if a weight belongs to finals list we plus one to the corresponding line.
     * @return  the array needed for the evaluate resutls.
     */
    public int[] toEvaluate(){
        for (Integer weight : finals) {
            for (int j = 0; j<toEvaluate.length; j++) {
                if (weight == weights[j]) {
                    toEvaluate[j] += 1;
                }
            }
        }    
        return toEvaluate;
    }
    /**
     * Finds minimum of two ints.
     * @param a 
     * @param b
     * @return  the minimum.
     */
    private int min(int a, int b){
        return a < b ? a : b;
    }     
    
}
