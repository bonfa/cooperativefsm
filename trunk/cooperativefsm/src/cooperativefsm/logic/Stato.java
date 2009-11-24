package cooperativefsm.logic;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

import java.util.*;

public class Stato {

    private int idStato;    //è il numero progressivo assegnato allo stato, riferito alla fsm cui appartiene
    private Vector<Transizione> transizioniUscenti;

    private boolean transizioniUscentiIsSetted;

    //@ requires num_stato>0
    public Stato (int num_stato) {   //costruttore di uno stato

        transizioniUscentiIsSetted = false;

        idStato = num_stato;
        transizioniUscenti = new Vector<Transizione>();
    }

    //@requires t!=null && t.getStato1()==this
    public void addTransUscente(Transizione t){
        transizioniUscenti.add(t);
    }
    
    public void setTransizioniUscentiIsSetted(){
        transizioniUscentiIsSetted = true;
    }

    public /*@ pure @*/  boolean getTransizioniUscentiIsSetted(){
        return transizioniUscentiIsSetted;
    }

    //@ensures /return!=null
    public Vector<Transizione> getTransizioniUscenti(){

        if(transizioniUscentiIsSetted){
            return transizioniUscenti;
        }else{
            return null;
        }
    }

    public int getId()
    {
        return idStato;
    }


    public String ToString()
    {
        String s = ("Stato numero: " + idStato);
        return s;
    }

    public boolean equals(Object o){
        Stato toCompare = (Stato) o;
        if(idStato == toCompare.getId()){
            return true;
        }else{
            return false;
        }

    }

}
