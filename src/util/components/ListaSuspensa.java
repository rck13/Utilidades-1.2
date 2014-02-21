package util.components;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

/**
 * Uma imitação da caixa de seleção que permite a informação de valores.<br />
 * Uma lista aparecerá abaixo do menbro local caso exista um item ou conjunto de itens
 * que correspondam à aproximação da consulta.<br />
 * está classe possui ligação direta com o banco de dados atraves do driver JDBC.
 * @author Renan C. Krubniki
 * @deprecated
 */
public class ListaSuspensa extends JScrollPane{
    /**
     * ID do item correspondente ao campo local
     */
    private int id = 0;
    /**
     * lista com os ID's trazidos do banco
     */
    private int idL[];
    /**
     * campo de texto para a entrada de valores
     */
    private JTextComponent local;
    /**
     * lista que exibe valores trazidos do banco que contenham o texto digitado no campo local
     */
    private JList lista = new JList();
    /**
     * @param formulario O formulario no qual se encontra o componente local desta lista.
     * @param local O componente ao qual a lista estará relacionada.
     * @param altura Altura da lista.
     * @param padrao true se a lista for usada de modo padrão - abaixo do objeto, ou false se ela for usada acima do objeto.
     * @param tabela Tabela do banco de dados da qual serão carregados os dados exibidos na lista.
     */
    public ListaSuspensa(Container formulario, JTextComponent local, int altura, boolean padrao, final String tabela) {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.local = local;
        setBounds(padrao == true ? local.getHeight() : -local.getHeight(), altura);
        setViewportView(lista);
        setVisible(false);

        formulario.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setVisible(false);
            }
        });

        lista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    selectLista();
                }
            }
        });
        lista.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectLista();
            }
        });
/*
        local.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                selectLocal(tabela);
                if(e.getKeyCode() == 40 && isVisible()){
                    lista.requestFocus();
                    lista.setSelectedIndex(0);
                }
            }
        });
*/        
/*
        local.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                
            }
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
*/
    }

    /**
     * @return ID do item correspondente ao campo local
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return campo para entrada de texto
     */
    public JTextComponent getLocal() {
        return local;
    }

    /**
     * seta a localização do campo local no formulário e como a lista será exibida
     * @param padrao true - lista abaixo do local, false - lista acima do local
     */
    public void localSetBounds(int x, int y, int width, int height, boolean padrao){
        local.setBounds(x, y, width, height);
        setBounds(padrao == true ? local.getHeight() : -local.getHeight(), getHeight());
    }

    /**
     * adiciona esta lista e seu componente local ao container.<br />
     * deve ser usado para correta adição do componente ao formulário.
     * @param container container ao qual o campo local e a lista devem ser adicionados.
     */
    public void addTo(Container container){
        container.add(this);
        container.add(local);
    }
    
    /**
     * define o local de desenho da lista
     * @param y Valor adicionado ao y de local, para definir o posicionamento da lista quando Instânciada com o Contrutor padrão.
     * @param altura altura da lista
     */
    private void setBounds(int y, int altura){
        setBounds(local.getX(), local.getY()+y, local.getWidth(), altura);
    }

    /**
     * @param tabela tabela na qual a consulta será realizada
     */
/*    
    private void selectLocal(String tabela){
        idL = new Conexao(tabela).buscarDados(lista, local.getText());
        setVisible((local.getText().length() > 0 && idL.length > 0));
        try{
            if(local.getText().equalsIgnoreCase(lista.getModel().getElementAt(0).toString())){
                setVisible(false);
                id = idL[0];
            }else
                id = 0;
        }catch(IndexOutOfBoundsException ev){
            id = 0;
        }
    }
*/    
    
    /**
     * substitui o valor existente no campo local pelo campo selecionado na lista e seta o atributo id com o ID correspondente
     */
    private void selectLista(){
        local.setText(lista.getSelectedValue().toString());
        id = idL[lista.getSelectedIndex()];
        setVisible(false);
    }

}
