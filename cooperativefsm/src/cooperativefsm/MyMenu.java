package cooperativefsm;

/**
 *
 * @author Renato chi?
 */
/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0
e sia presentata in fondo al menu

*/
public class MyMenu
{
  final private static String CORNICE = "--------------------------------";
  final private static String VOCE_USCITA = "0\tEsci";
  final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

  private String titolo;
  private String [] voci;


  public MyMenu (String titolo, String [] voci)
  {
	this.titolo = titolo;
	this.voci = voci;
  }

  /**
   *
   * @return
   */
  public int scegli ()          //NB: non è stato fatto nessun controllo che il carattere inserito sia un intero
  {
	stampaMenu();
	return Servizio.leggiInt(RICHIESTA_INSERIMENTO, 0 ,voci.length);
  }

  public void stampaMenu ()
  {
	System.out.println(CORNICE);
	System.out.println(titolo);
	System.out.println(CORNICE);
    for (int i=0; i<voci.length; i++)
	 {
	  System.out.println( (i+1) + "\t" + voci[i]);
	 }
    System.out.println();
	System.out.println(VOCE_USCITA);
    System.out.println();
  }

}

