/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Renato
 */

import java.util.Vector;

public class Fsm {

    Vector listaStati;
    Vector listaTrans;

    int numStati;
    Stato sCorrente;

    public Fsm () {    // costruttore di una macchina a stati finiti

        listaStati = new Vector ();
        listaTrans = new Vector ();

     }

    public void addStato(Stato s) {
        //aggiunge uno stato al vector listaStati
    }

    public void setStatoCorrente(Stato s) {
        //imposta il valore dello stato corrente
        sCorrente = s;
    }

    public Stato getStatoCorrente ()  {
        return sCorrente;
    }


     //settaTransizioniUscentiStati();


  }
