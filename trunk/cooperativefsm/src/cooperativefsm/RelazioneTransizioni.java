/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Renato
 */
public class RelazioneTransizioni {

    public Transizione t1;
    public Transizione t2;

    public RelazioneTransizioni (Transizione tran1, Transizione tran2)
    {
        t1=tran1;
        t2=tran2;
    }
}
