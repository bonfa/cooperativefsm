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
    
    public static void main(String[] args)
    {
        final String TIPOINPUT = "TIPO DI INPUT";
        final String [] SCELTAINPUT  = {"da tastiera","da file xml"};
        final String MESS_FINALE = "CIAO!";

        MyMenu menuInput = new MyMenu( TIPOINPUT , SCELTAINPUT );
        boolean fineProgramma = false;

        int selezione = menuInput.scegli();

        Input in = null;

            switch (selezione)
            {
                case 1: {in = new InputTast();
                        break;
                        }
                case 2: {in = new InputXML();
                        break;
                        }
                case 0: {
                        System.out.println(MESS_FINALE);
                        System.exit(0);
                        }
            }

        Simulazione s = in.leggiSimulazione();  //leggiSimulazione è un metodo della classe Input,
                                                //da cui ereditano le classi InputTast e InputXML
        //while (!fineProgramma)
            {
            //fineProgramma = s.eseguiIterazione();
            }

        //System.out.println(s.getListaFsm().get(0).ToString());
        //System.out.println(s.getListaFsm().get(1).ToString());

        System.out.println(MESS_FINALE);
    }
    
}