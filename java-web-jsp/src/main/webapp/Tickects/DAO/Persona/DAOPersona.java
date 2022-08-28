package Tickects.DAO.Persona;

import Tickects.DAO.DAOManager;
import Tickects.Modelo.Admin;
import Tickects.Modelo.Persona;
import Tickects.Modelo.Tecnico;
import Tickects.Modelo.Usuario;

import java.util.ArrayList;

public interface DAOPersona {

    boolean insertU(Usuario persona, DAOManager dao);
    boolean insertT(Tecnico persona, DAOManager dao);
    boolean update(DAOManager dao, String contraseña, int codigo, String tabla);
    boolean delete(DAOManager dao, String campo, String valor, String tabla);
    Persona read(DAOManager dao, String tabla, String campo, String valor);

     ArrayList<Usuario> readALLU(DAOManager dao, String tabla);
    ArrayList<Tecnico> readALLT(DAOManager dao, String tabla);
    ArrayList<Admin> readALLA(DAOManager dao, String tabla);
}
