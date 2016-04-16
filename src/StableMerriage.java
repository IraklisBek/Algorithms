package project2015alg;

import integrationproject.model.BlackAnt;
import integrationproject.model.RedAnt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/**
 * Class to implement Stable Merriage algorithm.
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr 
 */
public class StableMerriage {
    private final HashMap<RedAnt, ArrayList<BlackAnt>> redAntsPrefers;    //Map to show Red Ants prefers.
    private final HashMap<BlackAnt, ArrayList<RedAnt>> blackAntsPrefers;  //Map to show Black Ants prefers.
    private final int[][] engages;//The final array with index the pairs of red and black ants.
    private int engagesMade;//Counter for number of engages. At the end it should be equal with the number of red or black ants.
    /**
     * Constructor of StableMerriage. 
     * @param redAnts       List of Red Ants.
     * @param blackAnts     List of Black Ants.
     */
    public StableMerriage(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts){
        redAntsPrefers = new HashMap<>();
        blackAntsPrefers = new HashMap<>();
        engages = new int[redAnts.size()][2];
        engagesMade=0;
    }
    /**
     * Method to construct the prefers of the each Red and Black Ant.
     * @param redAnts       List of Red Ants.
     * @param blackAnts     List of Black Ants.
     */
    public void makeRedPrefers(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts){
        for (RedAnt redAnt : redAnts) {
            ArrayList<BlackAnt> redPrefers = new ArrayList<>();
            double[] distances = new double[redAnts.size()];//Array to keep distances bettwen Red and Black Ants. Useful for QuickSort.
            for (int j = 0; j<blackAnts.size(); j++) {
                redPrefers.add(blackAnts.get(j));
                distances[j] = redAnt.getDistanceFrom(blackAnts.get(j));
            }
            quickSort(redPrefers, distances, 0, distances.length-1);//Check in QiuckSort comments.  
            redAntsPrefers.put(redAnt, redPrefers);           
        }
    }
    /**
     * Same philosophy with makeRedPrefers.
     */
    public void makeBlackPrefers(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts){
        for (BlackAnt blackAnt : blackAnts) {
            ArrayList<RedAnt> blackPrefers = new ArrayList<>();
            double[] distances = new double[blackAnts.size()];
            for (int j = 0; j<blackAnts.size(); j++) {
                blackPrefers.add(redAnts.get(j));
                distances[j] = blackAnt.getDistanceFrom(redAnts.get(j));
            }
            quickSort(blackPrefers, distances, 0, distances.length-1);
            blackAntsPrefers.put(blackAnt, blackPrefers);
        }       
    }

    /**
     * Method to implements the matches between Red and Black Ants.
     * @param redAnts       List of Red Ants.
     * @param blackAnts     List of Black Ants.
     * @return              The matches.
     */
    public int[][] match(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts){
        makeBlackPrefers(redAnts,  blackAnts);
        makeRedPrefers(redAnts,  blackAnts);
        HashMap<RedAnt, BlackAnt> engagesRed = new HashMap<>();
        HashMap<BlackAnt, RedAnt> engagesBlack = new HashMap<>();
        ArrayList<RedAnt> freeReds = new ArrayList<>();//List with all free Red Ants.
        freeReds.addAll(redAnts);
        
        while(engagesMade < redAnts.size()){//Check line 15.
            RedAnt red = freeReds.get(0);//A red ant. Each time gets another.
            ArrayList<BlackAnt> blacks = redAntsPrefers.get(red);//The red ant preferences
            for(BlackAnt black : blacks){//For every red ant preferences
                if(engagesBlack.get(black) == null){//take the highest black ant available
                    engagesRed.put(red, black);//If highest is free engage them.
                    engagesBlack.put(black, red);
                    engagesMade++;//Engagement made.
                    freeReds.remove(0);//remove man
                    break;
                }else{//else
                    ArrayList<RedAnt> blackPref = blackAntsPrefers.get(black);//The black ant preferences.
                    
                    if(blackPref.indexOf(red) < blackPref.indexOf(engagesBlack.get(black))){//If black ant preferes the red ant from its fionce
                        engagesRed.put(red, black);//engage them
                        freeReds.remove(0);//remove it
                        freeReds.add(engagesBlack.get(black));//free fionce.
                        engagesBlack.put(black, red);                       
                        break;
                    }
                }
            }
        }

        int c=0;
        for(RedAnt ra : engagesRed.keySet()){
            engages[c][0] = ra.getID();
            engages[c][1] = engagesRed.get(ra).getID();
            c++;
        }
        return engages;
    }
    /**
     * Method useful for QuickSort method.
     * @param prefers       List of Black or Red Ant prefers as it been made in makeBlackPrefers and makeRedPrefers methods.
     * @param distances     Array with distances as it been made in makeBlackPrefers and makeRedPrefers methods.
     * prefers and distances implemented in parallel way. That means tha slot distance[i] refers to slot prefers.get(i). 
     * @param left          First of elements to check.
     * @param right         Last of elements to check.
     * @return              The position where left passed right. Useful for QuickSort to select the right pivot every time.
     */
    public int partition(ArrayList prefers, double[] distances, int left, int right){
        double pivot = distances[left];
        
        while(left <= right){           
            while(distances[left] < pivot){
                left++;
            }
            while(distances[right] > pivot){
                right--;
            }            
            if(left <= right){
                swap(prefers, distances, left, right);//Every time distances change the same slots change in prefers List.
                                                      //In that way we have the right prefers of ants acording to how much is the distance between Red and Black Ants.
                left++;
                right--;
            }
        }        
        return left;
    }
    /**
     * Recortional method that sorts distances array. It keeps sorting half of the array every time until its done.
     * params are the same as in partition method.
     */
    public void quickSort(ArrayList prefers, double[] distances, int left, int right){
        int leftIndex = partition(prefers, distances, left, right);//Check comment in line 23.
        
        if(left < leftIndex -1){
            quickSort(prefers, distances, left, leftIndex - 1);
        }
        if(right > leftIndex){
            quickSort(prefers, distances,leftIndex, right);
        }
    } 
    //Check comment at line 126.
    public  void swap(ArrayList prefers, double[] distances, int i, int j){
        double temp0 = distances[i];               
        distances[i] = distances[j];
        distances[j] = temp0;  
        Collections.swap(prefers, i, j);
    } 
    
}