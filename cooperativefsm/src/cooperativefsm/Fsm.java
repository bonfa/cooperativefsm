package cooperativefsm;

/**
 *
 * @author Renato
 */

import java.util.Vector;

public class Fsm {

    String id;
    int numStati;
    Stato sCorrente;    //serve davvero???
    Vector listaStati;
    Vector listaTrans;



    public Fsm () {    // costruttore di una macchina a stati finiti

        listaStati = new Vector ();
        listaTrans = new Vector ();

     }

    

    public void addStato(Stato s) {         //aggiunge uno stato al vector listaStati
        listaStati.add(s);
    }

    public void setStatoCorrente(Stato s) {         //imposta il valore dello stato corrente
        sCorrente = s;
    }

    public Stato getStatoCorrente ()  {
        return sCorrente;
    }


    public Stato getStato (int indice)  {
        
        Stato s = (Stato) listaStati.get(indice);
        return s;
    }
    
     //settaTransizioniUscentiStati();


  }
