package it.uniroma1.lcl.babelarity;
/**
 * Interfaccia per gli algoritmi di similarit� dei documenti.
 */
public interface BabelDocumentSimilarity
{
	public double computeSimilarity(Document d1,Document d2);
}
