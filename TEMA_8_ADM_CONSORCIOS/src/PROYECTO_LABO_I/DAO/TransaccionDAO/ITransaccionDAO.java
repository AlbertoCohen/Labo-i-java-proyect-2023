package PROYECTO_LABO_I.DAO.TransaccionDAO;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.Entidades.Transaccion;

import java.time.LocalDate;
import java.util.List;

public interface ITransaccionDAO {
    void guardarTransaccion(Transaccion transaccion) throws DAOException;
    void eliminarPorCodTransaccion(int codTransaccion) throws DAOException; // Fixed missing method
    void eliminarTransaccionPorUnidadFuncional(int numeroUnidad) throws DAOException;
    void modificarTransaccion(Transaccion transaccion) throws DAOException;
    Transaccion recuperarPorCodTransaccion(int codTransaccion) throws DAOException;
    List<Transaccion> recuperarTransaccionPorUnidadFuncional(int numeroUnidad) throws DAOException;
    List<Transaccion> recuperarTodasTransaccion() throws DAOException;
    List<Transaccion> recuperarPorFechas(LocalDate inicio, LocalDate fin) throws DAOException;
}