package util.metodos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

/**
 * @author Renan C. Krubniki
 * esta classe contem metodos est√°ticos com a finalidade de serem usados em formul√°rios swing
 */
public class UtilGUIDesktop{
    /**
     * Titulo para mensagens de erro
     * @see mensagemErro(String mensagem)
     */
    public static final String TITULOERRO = "ERRO!";
    /**
     * Titulo para mensagens de aten√ß√£o
     * @see mensagemAtencao(String mensagem)
     */
    public static final String TITULOATENCAO = "ATEN«√O!";
    private static String txt="";
    
    /**
     * @param data uma data no formato dd/mm/yyyy
     * @return o valor numerico de uma data para ser usada em calculos
     * retorna 0 caso erro de convers√£o
     */
    public static long StrToDateD_M_Y(String data){
        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(data).getTime();
        }catch(ParseException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }
    }

    /**
     * @param data uma data no formato yyyy/mm/dd
     * @return o valor numerico de uma data para ser usada em calculos<br/>
     * retorna 0 caso erro de convers√£o
     */
    public static long StrToDateY_M_D(String data){
        try{
            return new SimpleDateFormat("yyyy-MM-dd").parse(data).getTime();
        }catch(ParseException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }
    }
    
    /**
     * soma os valores contidos na coluna de uma tabela
     * @param tabela tabela com os valores
     * @param coluna coluna com os valores a serem somados
     * @return a soma dos valores na coluna
     */
    public static float valorTotal(JTable tabela, int coluna){
        float valor = 0;
        for(int x=0; x<tabela.getRowCount(); x++)
            valor+=Utilitarios.StrToFloat(tabela.getValueAt(x, coluna).toString());
        return valor;
    }

    /**
     * @param mascara a mascara usada no campo
     * @param caracter o caracter que representa um espaÁo vazio na mascara
     * @see MaskFormatter
     * @return DefaultFormatterFactory configurado com a mascara desejada
     */
    public static DefaultFormatterFactory getFormatterFactory(String mascara, char caracter){
        return new DefaultFormatterFactory(inputMask(mascara, caracter));
    }
    
    /**
     * @param mascara a mascara usada no campo
     * @param caracter o caracter que representa um espaÁo vazio na mascara
     * @see MaskFormatter
     * @return MaskFormatter configurado com a mascara desejada
     */
    public static MaskFormatter inputMask(String mascara, char caracter){
        try{
            MaskFormatter mask = new MaskFormatter(mascara);
            mask.setPlaceholderCharacter(caracter);
            return mask;
        }catch(ParseException e){
            return null;
        }
    }
    
    /**
     * subsitui a mascara de entrada do compoenten por uma nova mascara
     * @param campo o campo a ser atualizado
     * @param mascara a mascara usada no campo
     * @param caracter o caracter que representa um espa√ßo vazio na mascara
     * @see MaskFormatter
     */
    public static void AtualizarMascara(JFormattedTextField campo, String mascara, char caracter){
        campo.setFormatterFactory(null);
        campo.setFormatterFactory(new DefaultFormatterFactory(inputMask(mascara, caracter)));
        campo.setValue(null);
    }

    /**
     * @param data
     * @param nasc
     * @return
     * @deprecated n„o lembro como explicar o funcionamento, apenas por isso essa marcaÁ„o.
     */
    public static boolean validaIdade(String data, String nasc){
        try{
            Date Data = new SimpleDateFormat().parse(data);
            Date dtNasc = new SimpleDateFormat().parse(nasc);
            for(int y=0; y<270; y++)
                dtNasc.setTime(dtNasc.getTime()+2074620000);
            if(Data.getTime() > dtNasc.getTime())
                return true;
            else
                return false;
        }catch(ParseException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    /**
     * atualiza um rotulo com a quantidade de caracteres em um campo de texto<br />
     * e impede que o campo de texto tenha mais caracteres do que o<br />
     * limite permitido
     * @param edt o campo de entrada que ter√° a quantidade de caracteres controlada
     * @param lbl o rotulo que exibir√° a quantidade de caracteres atual
     * @param lmt o limite de caracteres permitido no campo
     */
    public static void contador(JTextComponent edt, JLabel lbl, int lmt){
        if(edt.getText().length()>lmt)
            edt.setText(txt);
        else
            txt=edt.getText();
        lbl.setText(edt.getText().length()+"/"+lmt);
    }

    /**
     * @param titulo titulo do painel
     * @return borda padr√£o na cor preta, com o titulo especificado.
     */
    public static TitledBorder bordaTitulo(String titulo){
        return BorderFactory.createTitledBorder(bordaLinha(), titulo);
    }

    /**
     * @return borda padr√£o na cor preta.
     */
    public static LineBorder bordaLinha(){
        return new LineBorder(Color.black);
    }

    /**
     * Verifica se ja existe uma instancia adicionada de determinada classe de tela
     * @param frames um vetor com todos os filhos de JInternalFrame no JDesktopPane
     * @param classe A classe filha de JInternalFrame que est· sendo procurada
     * @return o formulario encontrado ou null
     * @throws PropertyVetoException caso ocorra algum problema para des-iconificar o formul·rio interno encontrado
     */
    public static JInternalFrame frameExists(JInternalFrame[] frames, Class classe) throws PropertyVetoException{
        for(JInternalFrame frame : frames){
            if(frame.getClass() == classe) {
                if(frame.isIcon())
                    frame.setIcon(false);
                frame.toFront();
                return frame;
            }
        }
        return null;
    }
    
    /**
     * Realiza uma busca na area de trabalho e retorna a primeira correspondencia
     * @param frames um vetor com todos os filhos de JInternalFrame no JDesktopPane
     * @param busca A classe ou interface que est· sendo procurada
     * @return o formul·rio encontrado ou null
     */
    public static JInternalFrame getFrame(JInternalFrame[] frames, Class busca){
        for(JInternalFrame frame : frames){
            if(frame.getClass() != busca){
                for(Class classe : frame.getClass().getInterfaces()){
                    if(classe == busca){
                        return frame;
                    }
                }
            }else
                return frame;
        }
        return null;
    }
    
    /*
     * Busca na area de trabalho uma classe filha de JInternalFrame e retorna todas as correspondencias
     * @param frames um vetor com todos os filhos de JInternalFrame no JDesktopPane
     * @param busca A classe filha de JInternalFrame que est· sendo procurada
     * @return um vetor com todos os formularios que correspondem com a busca
     *
    public static Object[] getFrames(JInternalFrame[] frames, Class busca){
        List<JInternalFrame> formularios = new ArrayList(0);
        for(JInternalFrame frame : frames)
            if(frame.getClass() == busca)
                formularios.add(frame);
        return formularios.toArray();
    }
*/    
    /**
     * adiciona um novo formul·rio na ·rea de trabalho caso ele n„o exista
     * @param desktop area de trabalho que receber· o novo formul·rio
     * @param form formul·rio a ser adicionado
     * @param classe classe do formul·rio, usada para verificar se o formul·rio ja existe.
     * @return o formul·rio que ja estava na area de trabalho ou null
     * @throws PropertyVetoException caso ocorra algum problema para des-iconificar o formul·rio interno encontrado
     * @see frameExists(JInternalFrame[] frames, Class classe)
     */
    public static JInternalFrame addForm(JDesktopPane desktop, JInternalFrame form) throws PropertyVetoException{
        JInternalFrame frame = frameExists(desktop.getAllFrames(), form.getClass());
        if(frame == null){
            desktop.add(form);
            form.setVisible(true);
            form.toFront();
        }
        return frame;
    }

    /**
     * Mostra uma mensagem de erro para o usu√°rio
     * @param mensagem mensagem a ser exibida
     */
    public static void mensagemErro(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, TITULOERRO, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Mostra uma mensagem de alerta para o usu√°rio
     * @param mensagem mensagem a ser exibida
     */
    public static void mensagemAtencao(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, TITULOATENCAO, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * retorna a posi√ß√£o X para um componente com referencia em outro
     * @param referencia componente de referencia
     * @return a posi√ß√£o X para o novo componente
     */
    public static int getX(Component referencia){
        return referencia.getX()+referencia.getWidth()+10;
    }
    
    /**
     * retorna a posi√ß√£o Y para um componente com referencia em outro
     * @param referencia componente de referencia
     * @return a posi√ß√£o Y para o novo componente
     */
    public static int getY(Component referencia){
        return referencia.getY()+referencia.getHeight()+10;
    }

    /**
     * retorna a largura e altura para o componente com base em dois componentes de referencia
     * @param refLargura o componente mais proximo da borda direita do componente
     * @param refAltura o componente mais proximo da borda de baixo do componente
     * @param addLargura distancia entre este componente e a borda de seu container
     * @param addAltura distancia entre este componente e a borda de seu container
     * @return um objeto Dimension com o tamanho do componente
     */
    public static Dimension getSize(Component refLargura, Component refAltura, int addLargura, int addAltura){
        return new Dimension(getX(refLargura)+addLargura, getY(refAltura)+addAltura);
    }
    
    /**
     * retorna a largura e altura para o formulario com base em dois componentes de referencia
     * @param refLargura o componente mais proximo da borda direita do componente
     * @param refAltura o componente mais proximo da borda de baixo do componente
     * @return um objeto Dimension com o tamanho do componente
     */
    public static Dimension getSizeFormulario(Component refLargura, Component refAltura){
        return getSize(refLargura, refAltura, 15, 35);
    }

    /**
     * retorna a largura e altura para o painel com base em dois componentes de referencia
     * @param refLargura o componente mais proximo da borda direita do componente
     * @param refAltura o componente mais proximo da borda de baixo do componente
     * @return um objeto Dimension com o tamanho do componente
     */
    public static Dimension getSizePainel(Component refLargura, Component refAltura){
        return getSize(refLargura, refAltura, 5, 5);
    }

    /**
     * retorna a largura e altura equivalentes a toda a area na qual a referencia est√° adicionada.
     * @param referencia  o componente que ter√° seu tamanho setado, preferencialmente um formul√°rio
     * @return um objeto Dimension com o tamanho do componente
     */
    public static Dimension getSize(Component referencia){
        return new Dimension((int)referencia.getGraphicsConfiguration().getBounds().getWidth(),
                (int)referencia.getGraphicsConfiguration().getBounds().getHeight()-30);
    }

    /**
     * cria um Rectangle na posi√ß√£ 0, 0 com largura e altura equivalentes a toda a area na qual a referencia est√° adicionada.
     * @param referencia compoente de referencia
     * @return as medidas para o componente.
     */
    public static Rectangle getBounds(Component referencia){
        return new Rectangle(new Point(0, 0), getSize(referencia));
    }

    /**
     * procura por um componente de entrada com texto em branco e faz com que ele requisite o foco
     * @param componentes vetor de componetens a serem verificados
     */
    public static void procurar(Component[] componentes){
        for(Component c : componentes){
            if(c.getClass() == JFormattedTextField.class){
                JFormattedTextField campo = (JFormattedTextField)c;
                if(campo.getValue() == null){
                    campo.requestFocus();
                    return;
                }
            }else if(c.getClass() == JTextField.class || c.getClass() == JTextArea.class){
                JTextComponent campo = (JTextComponent)c;
                if(campo.getText().trim().isEmpty()){
                    campo.requestFocus();
                    return;
                }
            }
        }
    }
}
