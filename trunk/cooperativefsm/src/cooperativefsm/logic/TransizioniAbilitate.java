/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm.logic;

/**
 * Classe che contiene una singola transizione o una coppia di queste abilitate
 * a scattare in un iterazione della simulazione.
 *
 * @author Alessandro
 * @see Transizione, Stato
 */
public class TransizioniAbilitate {

    private Transizione tr1;
    private Transizione tr2;

    public TransizioniAbilitate(Transizione _tr1, Transizione _tr2){
        tr1=_tr1;
        tr2=_tr2;
    }

    public Transizione getTransizioneFSM1(){
        return tr1;
    }

    public Transizione getTransizioneFSM2(){
        return tr2;
    }

    @Override 
    public String toString ()
    {
        //TODO
        return (tr1.ToString()+"\t"+tr1.ToString());
    }
        
}
