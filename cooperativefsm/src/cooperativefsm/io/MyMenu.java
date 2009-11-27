package cooperativefsm.io;

/**
 * Questa classe rappresenta un menu testuale generico a piu' voci
 * @author Carlo
/*


*/
public class MyMenu
{
  final private static String CORNICE = "--------------------------------";
  final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

  private String titolo;
  private String [] voci;


  public MyMenu (String titolo, String [] voci)
  {
	this.titolo = titolo;
	this.voci = voci;
  }

  /**
   * Restituisce un intero relativo alla scelta dell'utente
   * @return
   */
  public int scegli ()          //NB: non è stato fatto nessun controllo che il carattere inserito sia un intero
  {
	stampaMenu();
	return Servizio.leggiInt(RICHIESTA_INSERIMENTO, 1 ,voci.length);
  }

  /**
   * Stampa a video il menu
   */
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
  }

}

