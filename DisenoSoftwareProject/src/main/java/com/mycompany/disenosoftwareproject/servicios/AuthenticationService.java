/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.servicios;

import com.mycompany.disenosoftwareproject.persistencia.DAOUsuario;

/**
 *
 * @author yournm
 */
public class AuthenticationService {
    private final DAOUsuario usuarioDAO;

    public AuthenticationService(DAOUsuario usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    public boolean autenticarUsuario(String login, String password) {
        try {
            return usuarioDAO.verificarCredenciales(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}