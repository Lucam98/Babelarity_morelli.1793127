package it.uniroma1.lcl.babelarity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
/**
 *  Classe che implementa l'algoritmo avanzato per la similarita' lessicale.
 */
public class BabelLexicalAdvancedSimilarity implements BabelLexicalSimilarity
{
	/**
	 * Mappa che gestisce le mappe pmi già create per le parole analizzate in precedenza così da velocizzare l'algoritmo.
	 */
	private static HashMap<String,TreeMap<String,Double>> vMap=new HashMap<String,TreeMap<String,Double>>();
	/**
	 * Attributo per salvare il dizionario una volta creato così da non ricrearlo per ogni compute.
	 */
	private static HashSet<String> diz=new HashSet<String>();
	/**
	 * Costruttore della classe.
	 */
	public BabelLexicalAdvancedSimilarity()
	{}
	/**
	 * Funzione che confronta due parole usando la cosine similarity sulle mappe pmi delle parole analizzate.
	 * @param w1 Parola1 da confrontare.
	 * @param w2 Parola2 da confrontare.
     * @return double rappresentante il grado di similarita'.
	 */
	public double computeSimilarity(Word w1, Word w2)
	{
		TreeMap<String,Double> vW1,vW2;
		HashSet<String> D;
		if(BabelLexicalAdvancedSimilarity.diz.isEmpty())
			D=creaDiz();
		else
			D=BabelLexicalAdvancedSimilarity.diz;
		if(BabelLexicalAdvancedSimilarity.vMap.containsKey(w1.getName()))
			vW1=BabelLexicalAdvancedSimilarity.vMap.get(w1.getName());
		else
		{
			vW1=pmi(w1,w2,D);
			BabelLexicalAdvancedSimilarity.vMap.put(w1.getName(),vW1);
		}
		if(BabelLexicalAdvancedSimilarity.vMap.containsKey(w2.getName()))
			vW2=BabelLexicalAdvancedSimilarity.vMap.get(w2.getName());
		else
		{
			vW2=pmi(w2,w1,D);
			BabelLexicalAdvancedSimilarity.vMap.put(w2.getName(),vW2);
		}
		double som1=0.0,som2=0.0,som3=0.0;
		for(String st:vW1.keySet())
		{
			if(vW2.containsKey(st))
				som1+=(vW1.get(st)*vW2.get(st));
			som2+=Math.pow(vW1.get(st),2.0);
		}
		for(String st:vW2.keySet())
			som3+=Math.pow(vW2.get(st),2.0);
		if(som2*som3!=0) 
			return (som1/(Math.sqrt(som2)*Math.sqrt(som3)));
		else
			return 0.0;
	}
	/**
	 * Funzione che crea la mappa pmi di w andando ad analizzare la presenza di quella parola in confronto a tutte le altre nel corpus.
	 * @param w Parola su cui creare la mappa.
	 * @param n Parola con la quale confrontare w e quindi non va messa nella mappa pmi.
	 * @param D Dizionario delle parole del corpus.
	 * @return mappa pmi.
	 */
	private TreeMap<String,Double> pmi(Word w,Word n,HashSet<String> D)
	{
		TreeMap<String,Double> m=new TreeMap<String,Double>();
		double freqAllW=frequencyAll(w);
		for(String s:D) 
		{	
			if(!s.equals(w.getName()) && !s.equals(n.getName()))
			{
				double freqAllS=frequencyAll(Word.fromString(s));
				double freqWS=frequencyCombinated(Word.fromString(s),w);
				double pmi=0.0;
				if(freqAllS*freqAllW!=0.0 && freqWS!=0.0) 
					pmi=Math.log(freqWS/(freqAllS*freqAllW));
				else
					pmi=0.0;
				m.put(s,pmi);	
			}
		}
		return m;
	}
	/**
	 * Funzione che restituisce il numero di volte che la parola w appare nel corpus.
	 * @param w Parola da analizzare.
	 * @return double rappresentante il numero di apparizioni della parola.
	 */
	private double frequencyAll(Word w)
	{
		double freq=0;
		CorpusManager documentManager = CorpusManager.getInstance();
		for(HashSet<String> a:documentManager.getCorpus())
				if(a.contains(w.getName()))
					freq++;
		return freq;
	}
	/**
	 * Funzione che restituisce il numero di volte che la parola w1 e la parola w2 appaiono insieme in un documento del corpus.
	 * @param w1 Parola1 da analizzare.
	 * @param w2 Parola2 da analizzare.
	 * @return double rappresentante il numero di apparizioni combinate delle parole.
	 */
	private double frequencyCombinated(Word w1,Word w2)
	{
		double freq=0;
		CorpusManager documentManager = CorpusManager.getInstance();
		for(HashSet<String> a:documentManager.getCorpus())
			if(a.contains(w1.getName()) && a.contains(w2.getName()))
					freq++;
		return freq;
	}
	/**
	 * Funzione che crea il dizionario delle parole presenti nel corpus.
	 * @return insieme rappresentante il dizionario delle parole del corpus.
	 */
	private HashSet<String> creaDiz()
	{
		HashSet<String> D=new HashSet<String>();
		HashSet<String> ripetute=new HashSet<String>();
		HashSet<String> ripetute2=new HashSet<String>();
		List<String> stopwords= Arrays.asList("and","the","with","more","only","are","under","over","for","from","was","his","were","her","where","when","who","again","she","had","after","before");
		CorpusManager documentManager = CorpusManager.getInstance();
		for(HashSet<String> a:documentManager.getCorpus())
			for(String s:a) 
			{
				if(s.length()>2 && s.length()<8 && !stopwords.contains(s) && ripetute.contains(s)) 
					if(ripetute2.contains(s)) 
						D.add(s);
					ripetute2.add(s);
				ripetute.add(s);
			}
		BabelLexicalAdvancedSimilarity.diz=D;
		return D;	
	}
}
