package project2015alg;

import java.util.Objects;
/**
 * Class that will be used to represent an Ant Point.
 * @author  Iraklis Bekiaris
 * @aem     2005
 * @email   irakmpek@csd.auth.gr
 */
public class AntPoint {   
    
    private int kind;
    private int id;
    /**
     * Constructor.
     * @param kind  the kind of an Ant.
     * @param id    the id of an Ant.
     */
    public AntPoint(int kind, int id){
        this.kind=kind;
        this.id=id;
    }
    //Getters and Setters.
    public int getKind(){
        return kind;
    }
    
    public int getID(){
        return id;
    }
    
    public void setKind(int kind){
        this.kind=kind;
    }
    
    public void setID(int id){
        this.id=id;
    }

    /**
     * To implement Union - Find algorithm I use HashMaps where key is an AntPoint and value an AntPoint
     * So because of the act that Ant Point is an object it is neccesary to override equals function so comparisons can be done.
     * @param obj   the object to compare.
     * @return      if its equal with the other object or not
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        if (Objects.equals(this.getClass(), obj.getClass()))
        {
            AntPoint myValueObject = (AntPoint) obj;
            if ((myValueObject.kind == this.kind) &&
                    (myValueObject.id == this.id))
            {
                isEqual = true;
            }
        }       
        return isEqual;
    }
    /**
     * If two objects are equal they should have the same hash code.
     * Here we initialize the hash code of every object of Ant Point class.
     * For example if a.equals(b) then a.hashCode should be equal with b.hashCode.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.kind;
        hash = 17 * hash + this.id;
        return hash;
    }

}
