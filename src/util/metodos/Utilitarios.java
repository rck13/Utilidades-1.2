package util.metodos;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Renan C. Krubniki
 */
public class Utilitarios{
    /**
     * o valor correspondente as 24H de um DIA inteiro
     */
    public static final long DIA = 86400000;
    /**
     * Usado para salvar uma data no banco de dados
     * @param data a data no formato dd/mm/yyyy a ser convertida
     * @return a data no formato yyyy/mm/dd
     */
    public static String ArrumaDataY_M_D(String data){
        int x;
        String aux = "";
        for(x=6;x<data.length();x++)
            aux+=data.charAt(x);
        for(x=2;x<6;x++)
            aux+=data.charAt(x);
        for(x=0;x<2;x++)
            aux+=data.charAt(x);
        return aux;
    }

    /**
     * Usado para exibir uma data ao usu√°rio
     * @param data a data no formato yyyy/mm/dd a ser convertida
     * @return a data no formato dd/mm/yyyy
     */
    public static String ArrumaDataD_M_Y(String data){
        String aux = "";
        if(data!=null){
            int x;
            for(x=8;x<data.length();x++)
                aux+=data.charAt(x);
            aux+="/";
            for(x=5;x<7;x++)
                aux+=data.charAt(x);
            aux+="/";
            for(x=0;x<4;x++)
                aux+=data.charAt(x);
        }
        return aux;
    }

    /**
     * Verifica a validade do CPF<br/>
     * o CPF ser valido n„o significa que ele ja exista na receita federal, apenas que a numeraÁ„o do documento È valida
     * @param doc o CPF a ser verificado
     * @return TRUE se o CPF for valido
     */
    public static boolean verificaCpf(String doc){
        if(!doc.isEmpty()){
            int x,y,cpf=0;
            String aux="";
            //verifica a validade do CPF
            if(doc.charAt(0)!='_'){
                for(x=0;x<doc.length();x++){
                    for(y=0;y<=9;y++){
                        if(String.valueOf(doc.charAt(x)).equals(String.valueOf(y)))
                            aux+=doc.charAt(x);
                    }
                }
                if(aux.equals("00000000000") || aux.equals("11111111111") || aux.equals("22222222222") ||
                aux.equals("33333333333") || aux.equals("44444444444") || aux.equals("55555555555") ||
                aux.equals("66666666666") || aux.equals("77777777777") || aux.equals("88888888888") ||
                aux.equals("99999999999"))
                    return false;
                else{
                    for(x=0;x<aux.length()-2;x++)
                        cpf+=Integer.parseInt(String.valueOf(aux.charAt(x)))*(10-x);
                    //verificando validade do 1¬∫ digito
                    if(cpf % 11 < 2){
                        if(aux.charAt(9)=='0'){
                            //verficando validade do 2¬∫ digito
                            cpf=0;
                            for(x=0;x<aux.length()-1;x++)
                                cpf+=Integer.parseInt(String.valueOf(aux.charAt(x)))*(11-x);
                            if(cpf % 11 == 0 && cpf % 11 == 1){
                                if(aux.charAt(10)!='0')
                                    return false;
                                else
                                    return true;
                            }
                            else if(cpf % 11 >= 2 && cpf % 11 <= 10){
                                if(!String.valueOf(aux.charAt(10)).equals(String.valueOf(11-(cpf % 11))))
                                    return false;
                                else
                                    return true;
                            }else
                                return false;
                        }else
                            return false;
                    }else if(cpf % 11 >= 2 && cpf % 11 <= 10){
                        if(String.valueOf(aux.charAt(9)).equals(String.valueOf(11-(cpf % 11)))){
                            //verficando validade do 2∫ digito
                            cpf=0;
                            for(x=0;x<aux.length()-1;x++)
                                cpf+=Integer.parseInt(String.valueOf(aux.charAt(x)))*(11-x);
                            if(cpf % 11 < 2){
                                if(aux.charAt(10)!='0')
                                    return false;
                                else
                                    return true;
                            }else if(cpf % 11 >= 2 && cpf % 11 <= 10){
                                if(!String.valueOf(aux.charAt(10)).equals(String.valueOf(11-(cpf % 11))))
                                    return false;
                                else
                                    return true;
                            }else
                                return false;
                        }else
                            return false;
                    }else
                        return false;
                }
            }else
                return false;
        }else
            return false;
    }

    /**
     * Verifica a validade do CNPJ<br/>
     * o CNPJ ser valido n„o significa que ele ja exista na receita federal, apenas que a numeraÁ„o do documento È valida
     * @param doc o CNPJ a ser verificado
     * @return TRUE se o CNPJ for valido
     */
    public static boolean VerificaCnpj(String doc){
        if(doc.isEmpty()){
            int x,y,cnpj=0;
            String aux="";
            //verifica a validade do CNPJ
            if(doc.charAt(0)!='_'){
                for(x=0;x<doc.length();x++){
                    for(y=0;y<=9;y++){
                        if(String.valueOf(doc.charAt(x)).equals(String.valueOf(y)))
                            aux+=doc.charAt(x);
                    }
                }
                if(aux.equals("00000000000000") || aux.equals("11111111111111") || aux.equals("22222222222222") ||
                aux.equals("33333333333333") || aux.equals("44444444444444") || aux.equals("55555555555555") ||
                aux.equals("66666666666666") || aux.equals("77777777777777") || aux.equals("88888888888888") ||
                aux.equals("99999999999999"))
                    return false;
                else{
                    for(x=0;x<aux.length()-2;x++){
                        int v=12-(x+7);
                        if(v<=1)
                            cnpj+=Integer.parseInt(String.valueOf(aux.charAt(x)))*(v+8);
                        else
                            cnpj+=Integer.parseInt(String.valueOf(aux.charAt(x)))*v;
                    }
                    //verificando validade do 1∫ digito
                    if(cnpj % 11 < 2){
                        if(aux.charAt(12)=='0'){
                            //verficando validade do 2∫ digito
                            cnpj=0;
                            for(x=0;x<aux.length()-1;x++){
                                int v=12-(x+6);
                                if(v<=1)
                                    cnpj+=Integer.parseInt(String.valueOf(aux.charAt(x)))*(v+8);
                                else
                                    cnpj+=Integer.parseInt(String.valueOf(aux.charAt(x)))*v;
                            }
                            if(cnpj % 11 < 2){
                                if(aux.charAt(13)!='0')
                                    return false;
                                else
                                    return true;
                            }else if(cnpj % 11 >= 2){
                                if(!String.valueOf(aux.charAt(13)).equals(String.valueOf(11-(cnpj % 11))))
                                    return false;
                                else
                                    return true;
                            }else
                                return false;
                        }else
                            return false;
                    }else if(cnpj % 11 >= 2){
                        if(String.valueOf(aux.charAt(12)).equals(String.valueOf(11-(cnpj % 11)))){
                            //verficando validade do 2∫ digito
                            cnpj=0;
                            for(x=0;x<aux.length()-1;x++){
                                int v=12-(x+6);
                                    if(v<=1)
                                        cnpj+=Integer.parseInt(String.valueOf(aux.charAt(x)))*(v+8);
                                    else
                                        cnpj+=Integer.parseInt(String.valueOf(aux.charAt(x)))*v;
                            }
                            if(cnpj % 11 < 2){
                                if(aux.charAt(10)!='0')
                                    return false;
                                else
                                    return true;
                            }else if(cnpj % 11 >= 2){
                                if(!String.valueOf(aux.charAt(13)).equals(String.valueOf(11-(cnpj % 11))))
                                    return false;
                                else
                                    return true;
                            }else
                                return false;
                        }else
                            return false;
                    }else
                        return false;
                }
            }else
                return false;
        }else
            return false;
    }
    
    /**
     * @param data data a ser validada
     * @return TRUE se a data for valida
     */
    public static boolean verificaData(String data){
        int x;
        boolean t=true;
        for(x=0; x<data.length(); x++){
            if(data.charAt(x)=='_'){
                t=false;
                break;
            }
        }
        if(t){
            String aux="";
            for(x=3;x<5;x++)
                aux+=data.charAt(x);
            if(aux.equals("01") || aux.equals("03") || aux.equals("05") ||
            aux.equals("07") || aux.equals("08") || aux.equals("10")|| aux.equals("12")){
                aux="";
                for(x=0;x<2;x++)
                    aux+=data.charAt(x);
                if(Integer.parseInt(aux)>0 && Integer.parseInt(aux)<=31)
                    return true;
                else
                    return false;
            }
            else if(aux.equals("04") || aux.equals("06") || aux.equals("09") || aux.equals("11")){
                aux="";
                for(x=0;x<2;x++)
                    aux+=data.charAt(x);
                if(Integer.parseInt(aux)>0 && Integer.parseInt(aux)<=30)
                    return true;
                else
                    return false;
            }
            else if (aux.equals("02")) {
                aux="";
                for(x=6;x<data.length();x++)
                    aux+=data.charAt(x);
                if(Integer.parseInt(aux)% 4 == 0 && (Integer.parseInt(aux)% 400 == 0 || Integer.parseInt(aux)% 100 != 0)){
                    aux="";
                    for(x=0;x<2;x++)
                        aux+=data.charAt(x);
                    if(Integer.parseInt(aux)>0 && Integer.parseInt(aux)<=29)
                        return true;
                    else
                        return false;
                }
                else{
                    aux="";
                    for(x=0;x<2;x++)
                        aux+=data.charAt(x);
                    if(Integer.parseInt(aux)>0 && Integer.parseInt(aux)<=28)
                        return true;
                    else
                        return false;
                }
            }
            else
                return false;
        }
        return false;
    }

    /**
     * retorna a data do DIA seguinte com base no DIA de hoje no sistema, formata para exibi√ß√£o ao usu√°rio
     */
    public static String diaSeguinte(){
        Date Data = new Date();
        Data.setTime(Data.getTime()+DIA);
        return new SimpleDateFormat("dd/MM/yyyy").format(Data);
    }

    /**
     * transforma um numero String em um numero double
     * as casas decimais do campo no formato String podem estar separados tanto por . quanto por ,
     * @param valor valor a ser convertido
     * @return o valor no formato double
     */
    public static double StrToDouble(String valor){
        return Double.parseDouble(arrumaNumero(valor));
    }

    /**
     * transforma um numero String em um numero float
     * as casas decimais do campo no formato String podem estar separados tanto por . quanto por ,
     * @param valor valor a ser convertido
     * @return o valor no formato float
     */
    public static float StrToFloat(String valor){
        return Float.parseFloat(arrumaNumero(valor));
    }

    /**
     * substitui o separador de casa decimal , por .
     * @param valor valor a ser ajustado
     * @return o valor a ser ajustado ou uma String contendo o valor 0
     */
    private static String arrumaNumero(String valor){
        int x;
        String aux="0";
        if(valor!=null)
            for(x=0;x<valor.length();x++){
                if(valor.charAt(x)==',')
                    aux+='.';
                else
                    aux+=valor.charAt(x);
            }
        return aux;
    }

    /**
     * separa um ID do texto no qual est√° embutido<br />
     * o ID deve estar no come√ßo do texto e o caractere de separa√ß√£o deve ser um dos seguinte:
     * <p style="margin-left: 16">
     * ' ' - espa√ßo em branco<br />
     * '_' - underline<br />
     * '-' - tra√ßo
     * </p>
     * @param txt o texto no qual o ID est√° embutido
     * @return o ID removido do texto ou 0
     * @deprecated
     */
    public static int getId(String txt){
        String aux="0";
        if(!txt.equals("N/A")){
            for(int x=0;x<txt.length();x++)
                if(txt.charAt(x)!=' ' || txt.charAt(x)!='_' || txt.charAt(x)!='-')
                    aux+=txt.charAt(x);
                else
                    break;
        }
        return Integer.parseInt(aux);
    }

    /**
     * gera um arquivo de log na pasta log
     * @param classe classe que gerou o log
     * @param e exceÁ„o lanÁada pela classe
     */
    public static void log(Class classe, Exception e){
        try{
            Logger log = Logger.getLogger(classe.getSimpleName());
            FileHandler fh = new FileHandler(logFile(classe), true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
            log.log(Level.WARNING, "", e);
            Toolkit.getDefaultToolkit().beep();
        }catch(Exception ex){
            e.printStackTrace();
        }
    }
    
    /**
     * gera o caminho com o nome do arquivo para o log
     * @param classe classe que gerou o log
     * @return uma String contendo o caminho para do arquivo de log
     * @throws IOException 
     */
    private static String logFile(Class classe) throws IOException{
        String diretorio = "log/";
        File f = new File(diretorio);
        if(!f.exists())
            f.mkdir();
        return diretorio+new SimpleDateFormat("dd-MM-yyyy - hh-mm-ss").format(new Date())+" - "+classe.getSimpleName()+".txt";
    }
}
