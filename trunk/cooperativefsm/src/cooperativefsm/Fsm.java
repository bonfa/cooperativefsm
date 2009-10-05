package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

import java.util.Vector;

public class Fsm {

    private String id;
    private int numStati;
    private Vector<Stato> stati;
    private Vector<Transizione> transizioni;
    private int numRelazioniSincroneTransizioniUscenti;



    public Fsm (String _id, Vector<Stato> _stati,Vector<Transizione> _transizioni) {    // costruttore di una macchina a stati finiti

        id=_id;
        stati = _stati;
        transizioni = _transizioni;

     }

    public void addStato(Stato s) {         //aggiunge uno stato al vector listaStati
        stati.add(s);
    }

    public Vector<Stato> getStati(){
        return stati;
    }

    public Stato getStato (int indice)  {
        
        Stato s = (Stato) stati.get(indice);
        return s;
    }

    public Vector<Transizione> getTransizioni()
    {
        return transizioni;
    }

    
     public void setTransizioniUscentiStati(){
         //TODO
     }

     public int getNumRelazioniSincroneTransizioniUscenti(){
         return numRelazioniSincroneTransizioniUscenti;
     }

     public void setNumRelazioniSincroneTransizioniUscenti(Simulazione.Relazione relazioni[][]){
         //TODO
     }


  }
