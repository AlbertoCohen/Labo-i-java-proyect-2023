// UnidadFuncionalService.java
package PROYECTO_LABO_I.Service;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.DAO.UnidadFuncionalDAO.UnidadFuncionalDAO;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;

import java.util.List;

public class UnidadFuncionalService {
    private UnidadFuncionalDAO unidadFuncionalDAO;

    public UnidadFuncionalService() {
        this.unidadFuncionalDAO = new UnidadFuncionalDAO();
    }

    public void guardar(UnidadFuncional unidadFuncional) throws ServiceException {
        try {
            unidadFuncionalDAO.guardarUF(unidadFuncional);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al guardar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public void eliminar(int numeroUnidad) throws ServiceException {
        try {
            unidadFuncionalDAO.eliminarUF(numeroUnidad);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al eliminar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public void modificar(UnidadFuncional unidadFuncional) throws ServiceException {
        try {
            unidadFuncionalDAO.modificarUF(unidadFuncional);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al modificar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public UnidadFuncional recuperarUF(int numeroUnidad) throws ServiceException {
        try {
            return unidadFuncionalDAO.recuperarUF(numeroUnidad);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al recuperar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public List<UnidadFuncional> recuperarTodas() throws ServiceException {
        try {
            return unidadFuncionalDAO.recuperarTodasUF();
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al recuperar todas las unidades funcionales: " + e.getMessage(), e);
        }
    }
}
