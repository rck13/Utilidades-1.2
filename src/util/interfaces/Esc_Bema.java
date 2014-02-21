package util.interfaces;

/**
 * Comandos para impressoras Bematech usando o conjunto de comandos ESC/Bema
 * @author Renan
 */
public interface Esc_Bema {
    public static final String OPERACAO_ESC_BEMA = ""+(char)29+(char)249 + (char)32 + (char)0;//seta temporariamente o conjunto de comandos esc/bema como modo de operação
    public static final String CORTE_PAPEL_PARCIAL = ""+(char)27+(char)109; // partial paper cut
    public static final String CORTE_PAPEL_TOTAL = ""+(char)27+(char)119;// full paper cut
    public static final String SET_CODE_PAGE_850 = (char)27+""+(char)116+""+(char)2;
    
}
