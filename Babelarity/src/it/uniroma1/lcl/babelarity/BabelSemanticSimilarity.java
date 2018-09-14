package it.uniroma1.lcl.babelarity;
/**
 * Interfaccia per gli algoritmi di similarit� semantica.
 */
public interface BabelSemanticSimilarity
{
	public double computeSimilarity(Synset s1, Synset s2);
}
