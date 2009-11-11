/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Alessandro
 */
public class TransizioniAbilitate {

    private Transizione tr1;
    private Transizione tr2;

    public TransizioniAbilitate(Transizione _tr1, Transizione _tr2){
        tr1=_tr1;
        tr2=_tr2;
    }

    public void setTransizioneFSM1(Transizione _tr1){
        tr1 = _tr1;
    }
    public void setTransizioneFSM2(Transizione _tr2){
        tr2 = _tr2;
    }

    public Transizione getTransizioneFSM1(){
        return tr1;
    }

    public Transizione gettransizioneFSM2(){
        return tr2;
    }

    public @Override String toString ()
    {
        //TODO
        return "";
    }
        
}
