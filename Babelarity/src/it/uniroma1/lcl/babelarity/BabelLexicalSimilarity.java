package it.uniroma1.lcl.babelarity;
/**
 * Interfaccia per gli algoritmi di similarità lessicale.
 */
public interface BabelLexicalSimilarity
{
	public double computeSimilarity(Word w1, Word w2);
}
