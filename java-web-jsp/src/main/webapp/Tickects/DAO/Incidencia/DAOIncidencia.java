package Tickects.DAO.Incidencia;

import Tickects.DAO.DAOManager;
import Tickects.Modelo.Incidencia;
import Tickects.Modelo.Persona;

import java.util.ArrayList;

public interface DAOIncidencia {

    boolean insert(Incidencia incidencia, DAOManager dao);
    boolean update(DAOManager dao, String clave, String valor, int codigo);
    boolean delete(DAOManager dao, String campo, String valor, String tabla);

    Incidencia read(DAOManager dao, String sentencia);
    ArrayList<Incidencia> readALL(DAOManager dao);
}
