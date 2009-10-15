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

public class InputXML extends Input {

     private Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
     private Vector<Fsm> listaFsm;
     private StatoCorrente statoIniziale;


     public InputXML()
    {

    }

    public @Override Simulazione leggiSimulazione()
    {  
        return new Simulazione(listaFsm, relazioniTransizioni, statoIniziale);
    }

    public @Override StatoCorrente leggiStatoIniziale(Vector<Fsm> list) {return new StatoCorrente();}

    
   
}
