package com.example.finalpractice.Daos;

import com.example.finalpractice.Beans.ViajeBean;
import com.example.finalpractice.Daos.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrincipalDao extends BaseDao {

    public ArrayList<ViajeBean> listarViajes(String codigoPUCP){
        ArrayList<ViajeBean> listaViajes = new ArrayList<>();
        String sql = "select idViaje, fechaReserva, fechaViaje, ciudadOrigen, ciudadDestino, seguro, numBoletos, costoTotal \n" +
                "from registroviajesporalumno r \n" +
                "inner join viaje v on (v.idViaje = r.Viaje_idViaje)\n" +
                "where Estudiante_codigoPUCP = ?;";
        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,codigoPUCP);
            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()){
                    ViajeBean viaje = new ViajeBean();
                    viaje.setIdViaje(rs.getString(1));
                    viaje.setFechaReserva(rs.getString(2));
                    viaje.setFechaViaje(rs.getString(3));
                    viaje.setCiudadOrigen(rs.getString(4));
                    viaje.setCiudadDestino(rs.getString(5));
                    viaje.setSeguro(rs.getString(6));
                    viaje.setNumBoletos(rs.getInt(7));
                    viaje.setCostoTotal(rs.getFloat(8));
                    listaViajes.add(viaje);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo ejecutar");
        }
        return listaViajes;
    }

    public ArrayList<ViajeBean> listarViajesFiltrada(String codigoPUCP, String ciudad){
        ArrayList<ViajeBean> listaViajesFiltrada = new ArrayList<>();
        String sql = "select idViaje, fechaReserva, fechaViaje, ciudadOrigen, ciudadDestino, seguro, numBoletos, costoTotal \n" +
                "from registroviajesporalumno r \n" +
                "inner join viaje v on (v.idViaje = r.Viaje_idViaje)\n" +
                "where Estudiante_codigoPUCP = ? and (lower(ciudadOrigen) like ? or lower(ciudadDestino) like ?);";
        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,codigoPUCP);
            pstmt.setString(2,"%"+ciudad+"%");
            pstmt.setString(3,"%"+ciudad+"%");
            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()){
                    ViajeBean viaje = new ViajeBean();
                    viaje.setIdViaje(rs.getString(1));
                    viaje.setFechaReserva(rs.getString(2));
                    viaje.setFechaViaje(rs.getString(3));
                    viaje.setCiudadOrigen(rs.getString(4));
                    viaje.setCiudadDestino(rs.getString(5));
                    viaje.setSeguro(rs.getString(6));
                    viaje.setNumBoletos(rs.getInt(7));
                    viaje.setCostoTotal(rs.getFloat(8));
                    listaViajesFiltrada.add(viaje);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo ejecutar");
        }
        return listaViajesFiltrada;
    }

    public void crearViaje(String idViaje,String fechaViaje,String fechaReserva,String origen,String destino,String seguro,String numBoletos,String costoTotal){
        String sql = "INSERT INTO `lab10`.`viaje` (`idViaje`, `fechaReserva`, `fechaViaje`, `ciudadOrigen`, `ciudadDestino`, `seguro`, `numBoletos`, `costoTotal`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,idViaje);
            pstmt.setString(2,fechaReserva);
            pstmt.setString(3,fechaViaje);
            pstmt.setString(4,origen);
            pstmt.setString(5,destino);
            pstmt.setString(6,seguro);
            pstmt.setInt(7,Integer.parseInt(numBoletos));
            pstmt.setFloat(8,Float.parseFloat(costoTotal));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearViajeEnEstudiante(String codigoPUCP,String idViaje){
        String sql = "INSERT INTO `lab10`.`registroviajesporalumno` (`Estudiante_codigoPUCP`, `Viaje_idViaje`) VALUES (?, ?);";

        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,codigoPUCP);
            pstmt.setString(2,idViaje);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ViajeBean buscarViaje(String idViaje){
        ViajeBean viajeBean = null;

        String sql = "select * from viaje where idViaje = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,idViaje);

            try (ResultSet rs = pstmt.executeQuery();){

                if (rs.next()){
                    viajeBean = new ViajeBean();
                    viajeBean.setIdViaje(rs.getString(1));
                    viajeBean.setFechaReserva(rs.getString(2));
                    viajeBean.setFechaViaje(rs.getString(3));
                    viajeBean.setCiudadOrigen(rs.getString(4));
                    viajeBean.setCiudadDestino(rs.getString(5));
                    viajeBean.setSeguro(rs.getString(6));
                    viajeBean.setNumBoletos(rs.getInt(7));
                    viajeBean.setCostoTotal(rs.getFloat(8));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viajeBean;
    }

    public void editarViaje(String idViaje,String fechaViaje,String fechaReserva,String origen,String destino,String seguro,String numBoletos,String costoTotal){
        String sql = "UPDATE `lab10`.`viaje` SET `fechaReserva` = ?, `fechaViaje` = ?, `ciudadOrigen` = ?, `ciudadDestino` = ?, `seguro` = ?, `numBoletos` = ?, `costoTotal` = ? WHERE (`idViaje` = ?);";

        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(8,idViaje);
            pstmt.setString(1,fechaReserva);
            pstmt.setString(2,fechaViaje);
            pstmt.setString(3,origen);
            pstmt.setString(4,destino);
            pstmt.setString(5,seguro);
            pstmt.setInt(6,Integer.parseInt(numBoletos));
            pstmt.setFloat(7,Float.parseFloat(costoTotal));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrar(String idViaje){
        String sql = "delete from viaje where idViaje = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,idViaje);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrarPorEstudiante(String idViaje){
        String sql = "delete from registroviajesporalumno where Viaje_idViaje = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1,idViaje);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

