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
    public final static String STATO_CORRENTE="STATO CORRENTE SIMULAZIONE:";
    public final static String TRANSIZIONI_ABILITATE="TRANSIZIONI ABILITATE:";
    public final static String MENU_TRANSIZIONI_ABILITATE="ID\tFSM_1(INIZ - FIN)\tNOME_TR_1\tFSM_2(INIZ - FIN)\tNOME:TR_2";
    public final static String SCEGLI_TRANSIZIONE="Scegliere la transizione da fare scattare:[Selezione per ID; -1 PER USCIRE]";
    public final static String LA_ACCENDIAMO="Sei sicuro?:";
    public final static String NO_TRANSIZIONI_ABILITATE="Non ci sono transizioni abilitate allo scatto!";
    public final static String STATO_FINALE="STATO FINALE:";
    public final static String SIMULAZIONE_TERMINATA="SIMULAZIONE TERMINATA";
    public final static String ERRORE="ERRORE!";
    public final static String ERR_TRANSIZIONI_USCENTI="Le transizioni uscenti non sono state correttamente settate!";
    public final static String ERR_TRANSIZIONI_SINCRONE="Le relazioni sincrone non sono state settate!";
    public final static String NIENTE=" NULL ";
    public final static String MENU_STATO_CORRENTE="SC_FSM_1\tSC_FSM_2";
    public final static String ABILITAZIONE="Vuoi far scattare questa transizione?";
    public final static int ESCI=-1;
}
