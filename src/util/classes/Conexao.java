package util.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Renan C. Krubniki
*/
public class Conexao {
    private Connection con;
    private String usuario, senha, url, driver;

    /**
     * Cria um objeto para se conectar e desconectar do banco de dados.
     * @param usuario o usuario para acesso a base de dados
     * @param senha senha do usu�rio
     * @param url url de comunica��o com a base de dados
     * @param driver driver de conex�o como o SGBD
     */
    public Conexao(String usuario, String senha, String url, String driver){
        this.usuario=usuario;
        this.senha=senha;
        this.url=url;
        this.driver=driver;
    }

    /**
     * @return o objeto Connection desta conex�o
     * @see Connection
     */
    public Connection getCon() {
        return con;
    }
    
    /**
     * Realiza a conex�o com o banco de dados.
     * @see Class
     * @see DriverManager
     * @throws ClassNotFoundException
     * @throws SQLException
     */    
    public void conectar(){
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, usuario, senha);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * realiza a desconex�o do banco de dados
     * @exception SQLException
     */
    public void desconectar(){
        try{
            if(!con.isClosed())
                con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
