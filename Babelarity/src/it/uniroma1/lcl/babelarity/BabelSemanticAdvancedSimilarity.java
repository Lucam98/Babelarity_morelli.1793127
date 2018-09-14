package it.uniroma1.lcl.babelarity;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Classe che implementa l'algoritmo avanzato per la similarita' semantica.
 */
public class BabelSemanticAdvancedSimilarity implements BabelSemanticSimilarity
{
	/**
	 * Costruttore della classe.
	 */
	public BabelSemanticAdvancedSimilarity()
	{}
	/**
	 * Funzione che confronta due synsets usando l'algoritmo di LCH tra i due nodi dei synsets analizzati.
	 * @param s1 Synset1 da confrontare.
	 * @param s2 Synset2 da confrontare.
     * @return double rappresentante il grado di similarita'.
	 */
	public double computeSimilarity(Synset s1, Synset s2)
	{	
		int dep;
		if(((BabelSynset)s1).getID().equals(((BabelSynset)s2).getID()))
			return 1.0;
		HashMap<String,Integer> mS1=new HashMap<String,Integer>();
		HashMap<String,Integer> mS2=new HashMap<String,Integer>();
		int liv=0;
		mS1=ricAnt((BabelSynset)s1,mS1,liv);
		liv=0;
		mS2=ricAnt((BabelSynset)s2,mS2,liv);
		dep=Math.max(Collections.max(mS1.values()),Collections.max(mS2.values()));
		int len=LCS(mS1,mS2);
		return(BabelSemanticAdvancedSimilarity.rangeMap(0,5,0,1,(double)(Math.abs((Math.log((double)len/((double)dep*2)))))));
	}
	/**
	 * Funzione che crea ricorsivamente la mappa dei figli con la relativa profondita' del synset s.
	 * @param s Synset da analizzare.
	 * @param m Mappa per evitare cicli nel grafo.
	 * @param liv Livello di ricorsione per calcolare la profondita' del figlio.
     * @return mappa dei figli.
	 */
	private HashMap<String,Integer> ricAnt(BabelSynset s,HashMap<String,Integer> m,int liv)
	{
		liv+=1;
		MiniBabelNet miniBabelNet = MiniBabelNet.getInstance();
		for(String r:miniBabelNet.getRelations().keySet())
		{
			if(r.equals(s.getID()))
				for(List<String> l:miniBabelNet.getRelations().get(r)) 		
					if(!m.containsKey(l.get(0)) && l.get(1).equals("is-a"))
					{
						m.put(l.get(0),liv);
						m=ricAnt((BabelSynset)miniBabelNet.getSynset(l.get(0)),m,liv);			
					}				
		}
		if(!m.isEmpty())
			return m;
		else
		{
			for(String r:miniBabelNet.getRelations().keySet())
			{
				for(List<String> l:miniBabelNet.getRelations().get(r)) 		
					if(l.get(0).equals(s.getID()) && !m.containsKey(r) && l.get(1).equals("is-a"))
						{
							m.put(r,liv);
							m=ricAnt((BabelSynset)miniBabelNet.getSynset(r),m,liv);			
						}				
			}
		}
		return m;
	}
	/**
	 * Funzione che trasforma il valore val nell'intervallo a1-a2 nel corrispettivo dell'intervallo b1-b2 .
	 * @param a1 estremo sinistro range di input.
	 * @param a2 estremo destro range di input..
	 * @param b1 estremo sinistro range di output.
	 * @param b2 estremo destro range di output.
     * @return mappa dei figli.
	 */
	private static double rangeMap(int a1,int a2,int b1,int b2,double val)
	{
		return(double)(b1+(((val-a1)*(b2-b1))/(a2-a1)));
	}
	/**
	 * Funzione che crea calcola LCS tra i due nodi dei synset analizzando le loro mappe dei figli.
	 * @param mS1 mappa dei figli del Synset1.
	 * @param mS2 mappa dei figli del Synset2.
     * @return LCS.length(se non presente restituisco 1000 come max length).
	 */
	private int LCS(HashMap<String,Integer> mS1,HashMap<String,Integer> mS2)
	{
		for(String s1:mS1.keySet().stream().sorted((e1,e2)->mS1.get(e1).compareTo(mS1.get(e2))).collect(Collectors.toList()))
				for(String s2:mS2.keySet().stream().sorted((e1,e2)->mS2.get(e1).compareTo(mS2.get(e2))).collect(Collectors.toList()))
				if(s1.equals(s2))
					return (mS1.get(s1)+mS2.get(s2));
		return 1000;			
	}
}
