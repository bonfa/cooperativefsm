/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author bonfa
 */
public interface Messaggi {


    /**Messaggi utilizzati dalla classe Interazione*/
    public final static String STATO_CORRENTE="STATO CORRENTE:";
    public final static String TRANSIZIONI_ABILITATE="TRANSIZIONI ABILITATE:";
    public final static String SCEGLI_TRANSIZIONE="Scegliere la transizione da fare scattare:";
    public final static String LA_ACCENDIAMO="Sei sicuro?:";
    public final static String NO_TRANSIZIONI_ABILITATE="Non ci sono transizioni abilitate allo scatto!";
    public final static String STATO_FINALE="STATO FINALE:";
    public final static String SIMULAZIONE_TERMINATA="Simulazione Terminata";
    public final static String ERRORE="ERRORE!";
    public final static String ERR_TRANSIZIONI_USCENTI="Le transizioni uscenti non sono state correttamente settate!\n";
    public final static String ERR_TRANSIZIONI_SINCRONE="Le relazioni sincrone non sono state settate!\n";

}
