package com.example.finalpractice.Daos;

import com.example.finalpractice.Beans.EstudianteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao extends BaseDao{
    public EstudianteBean validarContrasenia(String user, String contrasenia){
        EstudianteBean estudiante = null;
        String sql = "Select * from estudiante where  (correoPUCP= ? and contrasenia=sha2(?, 256));";
        try(Connection connection = this.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setString(1, user);
            pstmt.setString(2, contrasenia);
            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    estudiante = new EstudianteBean();
                    estudiante.setCodigoPUCP(rs.getString(1));
                    estudiante.setNombre(rs.getString(2));
                    estudiante.setApellido(rs.getString(3));
                    estudiante.setEdad(rs.getInt(4));
                    estudiante.setCorreoPUCP(rs.getString(5));
                    estudiante.setEspecialidad(rs.getString(6));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return estudiante;
    }
    public boolean validarTeleco(String user){
        boolean isTeleco = false;
        String sql = "Select * from estudiante where  (correoPUCP= ? and lower(especialidad) like '%telecom%');";
        try(Connection connection = this.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setString(1, user);
            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    isTeleco=true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return isTeleco;
    }
    public String obtenerStatus(EstudianteBean estudiante){
        String status = null;
        String sql = "select sum(v.costoTotal) from registroviajesporalumno r\n" +
                "inner join viaje v on r.Viaje_idViaje = v.idViaje\n" +
                "where r.Estudiante_codigoPUCP=?";
        try(Connection connection = this.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setString(1, estudiante.getCodigoPUCP());
            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    float totalGastado = rs.getInt(1);
                    if(totalGastado<100){
                        status = "Normal";
                    }else if(totalGastado<1000){
                        status = "Silver";
                    }else if(totalGastado<10000){
                        status = "Gold";
                    }else{
                        status = "Platinum";
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return status;
    }
}
