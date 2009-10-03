/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * Prova commit
 * Prova commit by carlo
 *
 * @author Cominardi Luca, Ferrari Alessandro, Svanera Carlo
 */


public class Main {     

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
    
    final String TIPOINPUT = "TIPO DI INPUT";
    final String [] SCELTAINPUT  = {"da tastiera","da file xml"};
    final String MESS_FINALE = "CIAO!";

    MyMenu menuInput = new MyMenu( TIPOINPUT , SCELTAINPUT );
    boolean fineProgramma=false;
    
    int selezione = menuInput.scegli();

    Input in;
    switch (selezione)
    {
        case 1: in = new InputTast();
        case 2: in = new InputXML();
        default: in = new Input();
        break;
    }


    Simulazione s = in.leggiSimulazione();   //leggiSimulazione è un metodo della interfaccia Input,
                                            //da cui ereditano le classi InputTast e InputXML
    
    while (!fineProgramma)
        {
        fineProgramma = s.eseguiIterazione();
        }
    

    System.out.println(MESS_FINALE);

    }


}
