package util.components;

import util.metodos.UtilGUIDesktop;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
/**
 * Esta classe representa um painel para o cadastro de N telefones.<br /><br />
 * Objetos dessa classe possuem conexão direta com o banco através da conexão JDBC, por tanto, para seu correto funcionamento,
 * a seguinte tabela é necessária no banco de dados:<br /><br />
 * tipotelefone(
 * <p style="margin-left: 16">
 * id int not null primary key auto_increment,<br />
 * nome varchar(15) unique key
 * </p>
 * )<br /><br />
 * nesta tabela serão buscados os valores ja existentes para o preenchimento do campo tipo.
 * @author Renan C. Krubniki
 */
public class PainelTelefone extends JPanel{
    /**
     * lista com os ids dos tipos relacionados aos telefones
     */
    private ArrayList<Integer> idTipo = new ArrayList(0);
    
    /**
     * @param formulario formulário ao qual o painel será adicionado
     * @param p localização na qual o painel será desenhado
     * @param tamanhoPadrao especifica as dimensões do painel:<br />
     * se true<br />
     * <p style="margin-left: 16">
     * 444, 100
     * </p>
     * senão
     * <p style="margin-left: 16">
     * 244, 170
     * </p><br /><br />
     * dimensões diferentes dessas devem ser setadas manualmente através da manipulação do objeto da classe.
     */
    public PainelTelefone(Container formulario, Point p, boolean tamanhoPadrao) {
        init(formulario, p);
        if(tamanhoPadrao){
            setSize(444, 100);
            painelTblTelefones.setLocation(lblTipoTel.getX()+30, jchb0800.getY());
            tipo.setSize(tipo.getWidth(), 35);
        }else{
            setSize(244, 170);
            painelTblTelefones.setLocation(10, btnAddTel.getY()+btnAddTel.getHeight()+5);
        }

        jchb0800.setToolTipText("Marque para adicionar um telefone com pre-fixo 0800.");
        tblTelefones.setToolTipText("Telefones a serem cadastrados.");
        btnAddTel.setToolTipText("Adiciona um telefone na lista de telefones a serem cadastrados.");
        jftfArea.setToolTipText("C�digo de �rea do telefone.");
        jftfTelefone.setToolTipText("Numero do telefone.");
    
        jchb0800.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jftfArea.setEnabled(!jchb0800.isSelected());
                jftfArea.setEditable(!jchb0800.isSelected());
                if(jchb0800.isSelected())
                    UtilGUIDesktop.AtualizarMascara(jftfTelefone, "###-####", '_');
                else
                    UtilGUIDesktop.AtualizarMascara(jftfTelefone, "####-####", '_');
            }
        });
        
        jftfArea.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                if(jftfArea.getText().charAt(2) != '_')
                    jftfTelefone.requestFocus();
            }

        });
        
        tipo.getLocal().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                UtilGUIDesktop.contador(tipo.getLocal(), lblTipoTel, 15);
            }
        });

        btnAddTel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTelefone();
            }
        });
        btnAddTel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10)
                    addTelefone();
            }
        });

        tblTelefones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 127 && tblTelefones.getSelectedRow() >= 0){
                    DefaultTableModel modelo = (DefaultTableModel)tblTelefones.getModel();
                    idTipo.remove(tblTelefones.getSelectedRow());
                    modelo.removeRow(tblTelefones.getSelectedRow());
                    tblTelefones.setModel(modelo);
                }
            }
        });

        tipo.addTo(this);
        add(jftfArea);
        add(jftfTelefone);
        add(jchb0800);
        add(lblTipoTel);
        add(btnAddTel);
        add(painelTblTelefones);
    }
    
    /**
     * inicia os compoentes basicos do formulário.
     * @param formulario formulário ao qual o painel será adicionado
     * @param p localização na qual o painel será desenhado
     */
    private void init(Container formulario, Point p){
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "TELEFONE"));
        setLocation(p);

        tipo = new ListaSuspensa(formulario, new JTextField(), 60, true, "tipotelefone");
        
        jchb0800.setBounds(40, 20, 60, 20);
        jftfArea = new JFormattedTextField(UtilGUIDesktop.inputMask("(##)", '_'));
        jftfArea.setBounds(jchb0800.getX()+jchb0800.getWidth(), 20, 26, 20);
        jftfTelefone = new JFormattedTextField(UtilGUIDesktop.inputMask("####-####", '_'));
        jftfTelefone.setBounds(jftfArea.getX()+jftfArea.getWidth()+5, jftfArea.getY(), 64, 20);
        
        tipo.localSetBounds(jchb0800.getX()+20, jchb0800.getY()+jchb0800.getHeight()+5, 105, 20, true);
        lblTipoTel.setSize(45, 20);
        lblTipoTel.setLocation(tipo.getLocal().getX()+tipo.getLocal().getWidth()+10, tipo.getLocal().getY());
        btnAddTel.setBounds(tipo.getLocal().getX()+10, tipo.getLocal().getY()+tipo.getLocal().getHeight()+5, 88, 20);

        painelTblTelefones.setSize(224, 65);
        tblTelefones.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblTelefones.getColumnModel().getColumn(1).setPreferredWidth(100);

    }

    /**
     * @return lista com os ID's dos tipos relacionados aos telefones
     * @see ArrayList
     */
    public ArrayList<Integer> getIdTipo() {
        return idTipo;
    }

    /**
     * @return retorna o botão que adiciona um telefone na listagem de telefones
     * @see JButton
     */
    public JButton getBtnAddTel() {
        return btnAddTel;
    }

    /**
     * @return caixa de marcação 0800
     * @see JCheckBox
     */
    public JCheckBox getJchb0800() {
        return jchb0800;
    }

    /**
     * @return caixa de entrada de área
     * @see JFormattedTextField
     */
    public JFormattedTextField getJftfArea() {
        return jftfArea;
    }

    /**
     * @return caixa de entrada de telefone
     * @see JFormattedTextField
     */
    public JFormattedTextField getJftfTelefone() {
        return jftfTelefone;
    }

    /**
     * @return rótulo com a contegem de caracteres do campo de tipo
     * @see JLabel
     */
    public JLabel getLblTipoTel() {
        return lblTipoTel;
    }

    /**
     * @return o componente com a listagem dos tipos
     * @see ListaSuspensa
     */
    public ListaSuspensa getTipo() {
        return tipo;
    }

    /**
     * @return painel de rolagem que contem a tabela de telefones
     * @see JScrollPane
     */
    public JScrollPane getPainelTblTelefones() {
        return painelTblTelefones;
    }

    /**
     * @return a tabela contendo os telefones
     */
    public JTable getTblTelefones() {
        return tblTelefones;
    }

    /**
     * limpa o painel deixando-o pronto para um novo cadastro
     */
    public void limparPainel(){
        limparCampos();
        idTipo = new ArrayList(0);
        tblTelefones.setModel(new DefaultTableModel(new String[] {"Área", "Número", "Tipo"}, 0));
    }
    
    /**
     * limpa os campos do painel
     */
    private void limparCampos(){
        tipo.setId(1);
        if(jchb0800.isSelected()){
            jchb0800.setSelected(false);
            jftfArea.setEnabled(true);
            jftfArea.setEditable(true);
            UtilGUIDesktop.AtualizarMascara(jftfTelefone, "####-####", '_');
        }
        jftfArea.setText("");
        jftfArea.setValue(null);
        jftfTelefone.setText("");
        jftfTelefone.setValue(null);
        tipo.getLocal().setText("");
        UtilGUIDesktop.contador(tipo.getLocal(), lblTipoTel, 15);
    }
    
    /**
     * adiciona o telefone na tabela de telefones
     */
    private void addTelefone(){
        boolean condicao = !jchb0800.isSelected() ?
            jftfArea.getValue() != null && jftfTelefone.getValue() != null : jftfTelefone.getValue() != null;
        if(condicao){
            tblTelefoneUpdate();
            limparCampos();
        }else
            JOptionPane.showMessageDialog(null, "Informe o número do telefone!");
    }
    
    /**
     * atualiza a tabela de telefones e a listagem de tipos
     */
    private void tblTelefoneUpdate(){
        if(tipo.getLocal().getText().isEmpty())
            idTipo.add(1);
        else
            idTipo.add(tipo.getId());
        DefaultTableModel modelo = (DefaultTableModel)tblTelefones.getModel();
        if(jchb0800.isSelected())
            modelo.addRow(new String[] {
                jchb0800.getText(),
                jftfTelefone.getText().substring(0, 3)+jftfTelefone.getText().substring(4, jftfTelefone.getText().length()),
                tipo.getLocal().getText()
            });
        else
            modelo.addRow(new String[] {
                jftfArea.getText().substring(1, jftfArea.getText().length()-1),
                jftfTelefone.getText().substring(0, 4)+jftfTelefone.getText().substring(5, jftfTelefone.getText().length()),
                tipo.getLocal().getText()
            });
        tblTelefones.setModel(modelo);
    }

    private JCheckBox jchb0800 = new JCheckBox("0800");
    private JTable tblTelefones = new JTable(new DefaultTableModel(new String[] {"Área", "Número", "Tipo"}, 0)){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JScrollPane painelTblTelefones = new JScrollPane(tblTelefones, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JButton btnAddTel = new JButton("Adicionar");
    private JFormattedTextField jftfArea;
    private JFormattedTextField jftfTelefone;
    private JLabel lblTipoTel = new JLabel("0/15");
    private ListaSuspensa tipo;
    
}
