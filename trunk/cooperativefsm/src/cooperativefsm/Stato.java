package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

import java.util.*;

public class Stato {

    private int idStato;    //è il numero progressivo assegnato allo stato, riferito alla fsm cui appartiene
    private String nome;    //identifica un certo stato (attributo opzionale)
    private Vector<Transizione> transizioniUscenti;

    private boolean transizioniUscentiIsSetted;


    public Stato (int num_stato) {   //costruttore di uno stato

        transizioniUscentiIsSetted = false;

        idStato = num_stato;
        transizioniUscenti = new Vector<Transizione>();
    }

    public void addTransUscente(Transizione t){
        transizioniUscenti.add(t);
    }
    
    public void transizioniUscentiIsSetted(){
        transizioniUscentiIsSetted = true;
    }

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

    public void setId(int id)
    {
        idStato = id;
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
