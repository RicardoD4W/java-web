package Tickects.DAO.Incidencia;

import Tickects.DAO.DAOManager;
import Tickects.Modelo.Incidencia;
import Tickects.Modelo.Persona;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOIncidenciaSQL implements DAOIncidencia{

    @Override
    public boolean insert(Incidencia incidencia, DAOManager dao) {
        String sentencia;
        sentencia = "INSERT INTO incidencia VALUES(" + incidencia.getId() +", '" + incidencia.getDescripcion() +"', '" +incidencia.getSolucion()+ "', '" + incidencia.getPrioridad() +"', '" +incidencia.isEstaResuelta() + "', '" +incidencia.getFechaInicio() +"', '" +incidencia.getFechaFin()+"', '" +incidencia.getIdUsuario()+"', '" +incidencia.getIdTecnico()+"');";
        System.out.println(sentencia);
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(DAOManager dao, String clave, String valor, int codigo) {
        String sentencia;
        sentencia = "UPDATE incidencia SET "+clave+" = " + "'" + valor + "' WHERE id = " + codigo+ ";";
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

    public boolean litreal(DAOManager dao, String sentencia){
        try (Statement stmt = dao.getConn().createStatement()) {
            // enviar el commando insert
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(DAOManager dao, String campo, String valor, String tabla) {
        return false;
    }

    @Override
    public Incidencia read(DAOManager dao, String sentencia) {
        Incidencia incidencia = null;
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            System.out.println("sentencia = " + sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // obtener cada una de la columnas y mapearlas a la clase Alumno
                    incidencia = new Incidencia(
                            rs.getInt("id"),
                            rs.getString("descripccion"),
                            rs.getString("solucion"),
                            rs.getInt("prioridad"),
                            rs.getInt("estaResuelta"),
                            rs.getString("fechaInicio"),
                            rs.getString("fechaFin"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_tecnico")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidencia;
    }

    @Override
    public ArrayList<Incidencia> readALL(DAOManager dao) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        String sentencia;
        sentencia = "SELECT * FROM incidencia ;";
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            System.out.println("sentencia = " + sentencia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // obtener cada una de la columnas y mapearlas a la clase Alumno
                    incidencias.add(new Incidencia(
                            rs.getInt("id"),
                            rs.getString("descripccion"),
                            rs.getString("solucion"),
                            rs.getInt("prioridad"),
                            rs.getInt("estaResuelta"),
                            rs.getString("fechaInicio"),
                            rs.getString("fechaFin"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_tecnico")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incidencias;
    }
}
