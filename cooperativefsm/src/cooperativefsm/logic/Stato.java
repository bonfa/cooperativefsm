package cooperativefsm.logic;

import java.util.*;

/**
 * Classe che rappresenta uno stato di una fsm. Possiede un id assegnato in modo
 * autoincrementale coincidente con l'ordine di immissione della fsm.
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

public class Stato {

    //@ public invariant \old(idStato)==idStato

    private int idStato;    //è il numero progressivo assegnato allo stato, deve essere settato in modo autoincrementale
    private Vector<Transizione> transizioniUscenti;

    private boolean transizioniUscentiIsSetted;

    /**
     * Costruttore a cui bisogna passare l'id dello stato che deve essere assegnato
     * in modo autoincrementale e non deve essere duplicato all'interno della stessa fsm.
     * @param num_stato
     */

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

    /**
     *  Serve per verificare se le Transizioni uscenti dallo Stato this sono già state settate.
     * @return Valore true se sono già state settate.
     */
    public /*@ pure @*/  boolean getTransizioniUscentiIsSetted(){
        return transizioniUscentiIsSetted;
    }

    /**
     * Ritorna un Vector contenente le Transizioni che hanno come stato sorgente lo stato
     * this.
     * @return Vector di Transizioni uscenti dallo stato corrente.
     */

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
