/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm.logic;

/**
 * Classe che contiene una coppia di queste abilitate (una transizione può essere a null per indicare
 * "nessuna transizione" a scattare in un iterazione della simulazione.
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

    /**
     * Transizione abilitata a scattare della prima Fsm. Può essere un riferimento
     * a null per indicare lo scatto di nessuna transizione.
     * @return Transizione abilitata allo scatto nella prima Fsm
     */
    public Transizione getTransizioneFSM1(){
        return tr1;
    }

    /**
     * Transizione abilitata a scattare della seconda Fsm. Può essere un riferimento
     * a null per indicare lo scatto di nessuna transizione.
     * @return Transizione abilitata allo scatto nella seconda Fsm
     */
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
