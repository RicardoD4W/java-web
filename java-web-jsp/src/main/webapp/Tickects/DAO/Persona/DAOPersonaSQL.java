package Tickects.DAO.Persona;

import Tickects.DAO.DAOManager;
import Tickects.Modelo.Admin;
import Tickects.Modelo.Persona;
import Tickects.Modelo.Tecnico;
import Tickects.Modelo.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOPersonaSQL implements DAOPersona{



    public boolean insertU(Usuario persona, DAOManager dao) {
        String sentencia;
        sentencia = "INSERT INTO usuario (id,nombre,apel,clave,email) VALUES(" + persona.getId() +", '" + persona.getNombre() +"', '" +persona.getApel() + "', '" + persona.getClave() +"', '" +persona.getEmail() +"');";
        System.out.println(sentencia);
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    public boolean insertA(Admin persona, DAOManager dao) {
        String sentencia;
        sentencia = "INSERT INTO admin (id,nombre,apel,clave,email) VALUES(" + persona.getId() +", '" + persona.getNombre() +"', '" +persona.getApel() + "', '" + persona.getClave() +"', '" +persona.getEmail() +"');";
        System.out.println(sentencia);
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean insertT(Tecnico persona, DAOManager dao) {
        String sentencia;
        sentencia = "INSERT INTO tecnico (id,nombre,apel,clave,email) VALUES(" + persona.getId() +", '" + persona.getNombre() +"', '" +persona.getApel() + "', '" + persona.getClave() +"', '" +persona.getEmail() + "');";
        System.out.println(sentencia);
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }



    public boolean update(DAOManager dao, String contraseña, int codigo, String tabla) {
        String sentencia;
        sentencia = "UPDATE "+ tabla +" SET clave = " + "'" + contraseña + "' WHERE id = " + codigo + ";";
        System.out.println(sentencia);
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public boolean delete(DAOManager dao, String campo, String valor, String tabla) {
        String sentencia;
        sentencia = "DELETE FROM "+tabla+" WHERE "+ campo +" = '" + valor + "';";
        System.out.println(sentencia);
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public Persona read(DAOManager dao, String tabla, String campo, String valor) {
        Persona persona = null;
        String sentencia;
        sentencia = "SELECT * FROM "+tabla+" WHERE "+campo+" ="+valor+" ";
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            System.out.println("sentencia = " + sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // obtener cada una de la columnas y mapearlas a la clase Alumno
                    persona = new Persona(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }


    public ArrayList<Usuario> readALLU(DAOManager dao, String tabla) {
        ArrayList<Usuario> persona = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM "+tabla+";";
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            System.out.println("sentencia = " + sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // obtener cada una de la columnas y mapearlas a la clase Alumno
                    persona.add(new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }


    public ArrayList<Tecnico> readALLT(DAOManager dao, String tabla) {
        ArrayList<Tecnico> persona = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM "+tabla+";";
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            System.out.println("sentencia = " + sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // obtener cada una de la columnas y mapearlas a la clase Alumno
                    persona.add(new Tecnico(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }


    public ArrayList<Admin> readALLA(DAOManager dao, String tabla) {
        ArrayList<Admin> persona = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM "+tabla+";";
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            System.out.println("sentencia = " + sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // obtener cada una de la columnas y mapearlas a la clase Alumno
                    persona.add(new Admin(
                            rs.getString("nombre"),
                            rs.getString("apel"),
                            rs.getString("clave"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }

}
