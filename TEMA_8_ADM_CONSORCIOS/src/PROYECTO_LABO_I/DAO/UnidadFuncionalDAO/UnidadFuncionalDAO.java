package PROYECTO_LABO_I.DAO.UnidadFuncionalDAO;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadFuncionalDAO implements IUnidadFuncionalDAO {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public void guardarUF(UnidadFuncional unidadFuncional) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO UNIDAD_FUNCIONAL (NUMERO_UNIDAD_FUNCIONAL, ID_OCUPANTE, NOMBRE_OCUPANTE, TELEFONO, INFO_ADICIONAL, PAGO_MENSUAL) "
                                +
                                "VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setInt(1, unidadFuncional.getNumeroUnidadFuncional());
            statement.setInt(2, unidadFuncional.getIdOcupante());
            statement.setString(3, unidadFuncional.getNombreOcupante());
            statement.setString(4, unidadFuncional.getTelefono());
            statement.setString(5, unidadFuncional.getInfoAdicional());
            statement.setInt(6, unidadFuncional.getPagoMensual());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Error DAO al guardar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public void eliminarUF(int numeroUnidad) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM UNIDAD_FUNCIONAL WHERE NUMERO_UNIDAD_FUNCIONAL=?")) {

            statement.setInt(1, numeroUnidad);

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Error DAO al eliminar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public void modificarUF(UnidadFuncional unidadFuncional) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE UNIDAD_FUNCIONAL SET ID_OCUPANTE=?, NOMBRE_OCUPANTE=?, TELEFONO=?, INFO_ADICIONAL=?, PAGO_MENSUAL=? "
                                +
                                "WHERE NUMERO_UNIDAD_FUNCIONAL=?")) {

            statement.setInt(1, unidadFuncional.getIdOcupante());
            statement.setString(2, unidadFuncional.getNombreOcupante());
            statement.setString(3, unidadFuncional.getTelefono());
            statement.setString(4, unidadFuncional.getInfoAdicional());
            statement.setInt(5, unidadFuncional.getPagoMensual());
            statement.setInt(6, unidadFuncional.getNumeroUnidadFuncional());

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Error DAO al modificar la unidad funcional: " + e.getMessage(), e);
        }
    }

    public UnidadFuncional recuperarUF(int numeroUnidad) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM UNIDAD_FUNCIONAL WHERE NUMERO_UNIDAD_FUNCIONAL=?")) {

            statement.setInt(1, numeroUnidad);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearUnidadFuncionalDesdeResultSet(resultSet);
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Error DAO al recuperar la unidad funcional: " + e.getMessage(), e);
        }
        return null;
    }

    public List<UnidadFuncional> recuperarTodasUF() throws DAOException {
        List<UnidadFuncional> unidadesFuncionales = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM UNIDAD_FUNCIONAL");
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                unidadesFuncionales.add(crearUnidadFuncionalDesdeResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException("Error DAO al recuperar todas las unidades funcionales: " + e.getMessage(), e);
        }
        return unidadesFuncionales;
    }

    private UnidadFuncional crearUnidadFuncionalDesdeResultSet(ResultSet resultSet) throws DAOException {
        try {
            UnidadFuncional unidadFuncional = new UnidadFuncional();
            unidadFuncional.setNumeroUnidadFuncional(resultSet.getInt("NUMERO_UNIDAD_FUNCIONAL"));
            unidadFuncional.setIdOcupante(resultSet.getInt("ID_OCUPANTE"));
            unidadFuncional.setNombreOcupante(resultSet.getString("NOMBRE_OCUPANTE"));
            unidadFuncional.setTelefono(resultSet.getString("TELEFONO"));
            unidadFuncional.setInfoAdicional(resultSet.getString("INFO_ADICIONAL"));
            unidadFuncional.setPagoMensual(resultSet.getInt("PAGO_MENSUAL"));
            return unidadFuncional;
        } catch (SQLException e) {
            throw new DAOException("Error DAO al crear la unidad funcional desde el resultSet: " + e.getMessage(), e);
        }
    }
}
