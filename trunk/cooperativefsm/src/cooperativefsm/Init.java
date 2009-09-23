/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Renato
 */

/*E' una classe che ha il compito di inizializzare una fsm a seconda del tipo di input (lettura di un file xml, 
 input dell'utente ecc.). Contiene un metodo init (che è invocato nel main) che legge i valori passati dal parser
 e li passa a sua volta al costruttore della classe fsm*/


public class Init {

    public static Fsm init () {
        
        //legge da parser o da input utente
        
        Fsm macch1= new Fsm ();
        
        int numStati;
        //ottiene dal parser il numero di stati della fsm
        /*ciclo for che per tot volte:
                crea un nuovo stato
                aggiunge lo stato alla fsm   */
             
        for (int i=0; i<numStati; i++)  {
            Stato s = new Stato (i);
            macch1.addStato(s);
        }
        
        //ottiene dal parser lo stato iniziale che deve € al vettore macch1.listaStati --->controllare!
        //imposta lo stato corrente: macch1.setStatoCorrente(statoIniziale);
        
        
        //ottiene dal parser le transizioni ....
        
        
        return macch1;
        
    }
    
}
