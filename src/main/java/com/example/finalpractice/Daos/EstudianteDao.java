package com.example.finalpractice.Daos;


import com.example.finalpractice.Daos.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstudianteDao extends BaseDao {
    public void crearEstudiante(String nombre, String apellido, int edad, String codigo, String especialidad, String correo, String contrasenia){
        String sql = "INSERT INTO estudiante (codigoPUCP, nombre, apellido, edad, correoPUCP, especialidad, contrasenia)\n" +
                " values (?, ?, ?, ?, ? , ? ,sha2(?,256));";

        try(Connection connection = this.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setString(1, codigo);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setInt(4, edad);
            pstmt.setString(5, correo);
            pstmt.setString(6, especialidad);
            pstmt.setString(7, contrasenia);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean existeUsuario(String correo){
        boolean usuarioExistente=false;
        String sql = "SELECT * from estudiante where correoPUCP = ?;";
        try(Connection connection = this.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    usuarioExistente = true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usuarioExistente;
    }
}
