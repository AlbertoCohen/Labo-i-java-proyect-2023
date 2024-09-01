package PROYECTO_LABO_I.DAO.TransaccionDAO;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.DAO.UnidadFuncionalDAO.UnidadFuncionalDAO;
import PROYECTO_LABO_I.Entidades.Entrada;
import PROYECTO_LABO_I.Entidades.Gasto;
import PROYECTO_LABO_I.Entidades.Transaccion;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO implements ITransaccionDAO {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    @Override
    public void guardarTransaccion(Transaccion transaccion) throws DAOException {
        String sql = "INSERT INTO TRANSACCIONES (TIPO, DESCRIPCION, FECHA, MONTO) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, transaccion.getTipo());
            statement.setString(2, transaccion.getDescripcion());
            statement.setTimestamp(3, Timestamp.valueOf(transaccion.getFecha()));
            statement.setFloat(4, transaccion.getMonto());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaccion.setCodTransaccion(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Failed to retrieve the generated key.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error DAO al guardar la transacción: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarPorCodTransaccion(int codTransaccion) throws DAOException {
        String sql = "DELETE FROM TRANSACCIONES WHERE COD_TRANSACCION=?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codTransaccion);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar la transacción por codTransaccion: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarTransaccionPorUnidadFuncional(int numeroUnidad) throws DAOException {
        String sql = "DELETE FROM TRANSACCIONES WHERE DESCRIPCION=? AND TIPO='ENTRADA'";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "Pagada por la unidad numero: " + numeroUnidad);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error DAO al eliminar las transacciones para la unidad funcional: " + e.getMessage(), e);
        }
    }

    @Override
    public void modificarTransaccion(Transaccion transaccion) throws DAOException {
        String sql = "UPDATE TRANSACCIONES SET MONTO=?, FECHA=?, DESCRIPCION=? WHERE COD_TRANSACCION=?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setFloat(1, transaccion.getMonto());
            statement.setTimestamp(2, Timestamp.valueOf(transaccion.getFecha()));
            statement.setString(3, transaccion.getDescripcion());
            statement.setInt(4, transaccion.getCodTransaccion());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error DAO al modificar la transacción: " + e.getMessage(), e);
        }
    }

    @Override
    public Transaccion recuperarPorCodTransaccion(int codTransaccion) throws DAOException {
        String sql = "SELECT * FROM TRANSACCIONES WHERE COD_TRANSACCION=?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codTransaccion);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearTransaccionDesdeResultSet(resultSet);
                } else {
                    throw new DAOException("Transacción no encontrada");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error DAO al recuperar la transacción por codTransaccion: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaccion> recuperarTransaccionPorUnidadFuncional(int numeroUnidad) throws DAOException {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM TRANSACCIONES WHERE DESCRIPCION=? AND TIPO='ENTRADA'";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "Pagada por la unidad numero: " + numeroUnidad);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transacciones.add(crearTransaccionDesdeResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error DAO al recuperar las transacciones para la unidad funcional: " + e.getMessage(), e);
        }
        return transacciones;
    }

    @Override
    public List<Transaccion> recuperarPorFechas(LocalDate inicio, LocalDate fin) throws DAOException {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM TRANSACCIONES WHERE FECHA BETWEEN ? AND ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(inicio));
            statement.setDate(2, Date.valueOf(fin));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transacciones.add(crearTransaccionDesdeResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error DAO al recuperar las transacciones por fechas: " + e.getMessage(), e);
        }
        return transacciones;
    }

    @Override
    public List<Transaccion> recuperarTodasTransaccion() throws DAOException {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM TRANSACCIONES";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                transacciones.add(crearTransaccionDesdeResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Error DAO al recuperar todas las transacciones: " + e.getMessage(), e);
        }
        return transacciones;
    }

    private Transaccion crearTransaccionDesdeResultSet(ResultSet resultSet) throws DAOException {
        try {
            int codTransaccion = resultSet.getInt("COD_TRANSACCION");
            String tipo = resultSet.getString("TIPO");
            LocalDateTime fecha = resultSet.getTimestamp("FECHA").toLocalDateTime();
            String descripcion = resultSet.getString("DESCRIPCION");
            float monto = resultSet.getFloat("MONTO");

            if ("ENTRADA".equals(tipo)) {
                String numeroUnidadStr = null;
                if (descripcion.startsWith("Pagada por la unidad numero: ")) {
                    numeroUnidadStr = descripcion.substring("Pagada por la unidad numero: ".length()).trim();
                }

                UnidadFuncional unidadFuncional = null;
                if (numeroUnidadStr != null) {
                    try {
                        int numeroUnidad = Integer.parseInt(numeroUnidadStr);
                        unidadFuncional = new UnidadFuncionalDAO().recuperarUF(numeroUnidad);
                    } catch (NumberFormatException e) {
                        // No hacer nada si la entrada pertenece a una unidad eliminada
                    }
                }

                return new Entrada(codTransaccion, monto, fecha, descripcion, unidadFuncional);
            } else if ("GASTO".equals(tipo)) {
                return new Gasto(codTransaccion, monto, fecha, descripcion);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new DAOException("Error DAO al crear transacción desde ResultSet: " + e.getMessage(), e);
        }
        return null;
    }
}
