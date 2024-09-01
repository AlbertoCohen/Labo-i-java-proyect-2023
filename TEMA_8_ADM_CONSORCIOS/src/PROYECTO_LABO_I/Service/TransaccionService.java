package PROYECTO_LABO_I.Service;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.DAO.TransaccionDAO.TransaccionDAO;
import PROYECTO_LABO_I.Entidades.Transaccion;

import java.util.List;

public class TransaccionService {
    private final TransaccionDAO transaccionDAO;

    public TransaccionService() {
        this.transaccionDAO = new TransaccionDAO();
    }

    public void guardar(Transaccion transaccion) throws ServiceException {
        try {
            transaccionDAO.guardarTransaccion(transaccion);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al guardar la transacción: " + e.getMessage(), e);
        }
    }

    public void eliminarPorCodTransaccion(int codTransaccion) throws ServiceException {
        try {
            transaccionDAO.eliminarPorCodTransaccion(codTransaccion);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar la transacción por codTransaccion: " + e.getMessage(), e);
        }
    }

    public void eliminarPorUnidadFuncional(int numeroUnidad) throws ServiceException {
        try {
            List<Transaccion> transacciones = recuperarPorNumeroUnidad(numeroUnidad);
            for (Transaccion transaccion : transacciones) {
                transaccionDAO.eliminarPorCodTransaccion(transaccion.getCodTransaccion());
            }
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al eliminar las transacciones para la unidad funcional: " + e.getMessage(), e);
        }
    }

    public void modificar(Transaccion transaccion) throws ServiceException {
        try {
            transaccionDAO.modificarTransaccion(transaccion);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al modificar la transacción: " + e.getMessage(), e);
        }
    }

    public Transaccion recuperarPorCodTransaccion(int codTransaccion) throws ServiceException {
        try {
            return transaccionDAO.recuperarPorCodTransaccion(codTransaccion);
        } catch (DAOException e) {
            throw new ServiceException("Error al recuperar la transacción por codTransaccion: " + e.getMessage(), e);
        }
    }

    public List<Transaccion> recuperarPorNumeroUnidad(int numeroUnidad) throws ServiceException {
        try {
            return transaccionDAO.recuperarTransaccionPorUnidadFuncional(numeroUnidad);
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al recuperar las transacciones para la unidad funcional: " + e.getMessage(), e);
        }
    }

    public List<Transaccion> recuperarTodas() throws ServiceException {
        try {
            return transaccionDAO.recuperarTodasTransaccion();
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al recuperar todas las transacciones: " + e.getMessage(), e);
        }
    }
}
