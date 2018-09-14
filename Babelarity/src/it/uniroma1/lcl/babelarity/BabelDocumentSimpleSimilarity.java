package it.uniroma1.lcl.babelarity;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
/**
 * Classe che implementa l'algoritmo base per la similarita' dei documenti.
 */
public class BabelDocumentSimpleSimilarity implements BabelDocumentSimilarity
{
	/**
	 * Funzione ereditata dall'interfaccia che confronta i documenti applicando la cosine similarity tra le mappe delle occcorrenze di parole nei documenti.
	 * @param d1 Documento1 da confrontare.
	 * @param d2 Documento2 da confrontare.
     * @return double rappresentante il grado di similarita'.
	 */
	public double computeSimilarity(Document d1,Document d2)
	{
		TreeMap<String,Double> vD1,vD2;
		vD1=bagOfWords(d1);
		vD2=bagOfWords(d2);
		double som1=0.0,som2=0.0,som3=0.0;
		for(String st:vD1.keySet())
		{
			if(vD2.containsKey(st))
				som1+=(vD1.get(st)*vD2.get(st));
			som2+=Math.pow(vD1.get(st),2.0);
		}
		for(String st:vD2.keySet())
			som3+=Math.pow(vD2.get(st),2.0);
		if(som2*som3!=0) 
			return (som1/(Math.sqrt(som2)*Math.sqrt(som3)));
		else
			return 0.0;
	}
	/**
	 * Funzione che crea la mappa delle occorrenze delle parole in un documento.
	 * @param d Documento su cui creare la mappa.
     * @return mappa delle occorrenze delle parole.
	 */
	private TreeMap<String,Double> bagOfWords(Document d)
	{
		List<String> stopwords= Arrays.asList("and","the","with","more","only","are","under","over","for","from","was","his","were","her","where","when","who","again","she","had","after","before");
		TreeMap<String,Double> m=new TreeMap<String,Double>();
		for(String s:d.getContent().replaceAll("//M"," ").split(" ")) 
				if(!stopwords.contains(s) && s.length()>2)	
					if(m.putIfAbsent(s,1.0)!=null) 
						m.put(s,m.get(s)+1.0);	
		return m;
	}
}
