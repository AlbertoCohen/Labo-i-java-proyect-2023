package PROYECTO_LABO_I.DAO.UnidadFuncionalDAO;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;

import java.util.List;

public interface IUnidadFuncionalDAO {
    public void guardarUF(UnidadFuncional unidadFuncional) throws DAOException;
    public void eliminarUF(int numeroUnidad) throws DAOException;
    public void modificarUF(UnidadFuncional unidadFuncional) throws DAOException;
    public UnidadFuncional recuperarUF(int numeroUnidad) throws DAOException;
    public List<UnidadFuncional> recuperarTodasUF() throws DAOException;
}