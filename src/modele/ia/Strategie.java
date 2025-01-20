package modele.ia;

/**
 * Interface représentant une stratégie de génération.
 */
public interface Strategie {

    /**
     * Génère un coup selon la stratégie implémentée.
     * 
     * @param <T> Le type du coup généré.
     * @return Un coup généré selon la stratégie.
     */
    public <T> T generer();
}
