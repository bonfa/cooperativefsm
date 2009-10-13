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

    public InputXML()
    {
    }

    public @Override int leggiNumStati(){return 0;}

    public @Override boolean ciSonoTrans(){ return true;}

    public @Override boolean ciSonoRelaz() {return true;}

    public @Override void imposta (Simulazione.Relazione [][] relaz) {}

    public @Override Stato leggiStato(String a){ return new Stato(0);}

    public @Override StatoCorrente leggiStatoIniziale(Vector<Fsm> list) {return new StatoCorrente();}

}
