package com.senai.testesaep.Sessao;

import com.senai.testesaep.dtos.LoginSessaoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ControleSessao {
    public static void registrar(HttpServletRequest request, LoginSessaoDto usuarioSessao) {

        //--Obter a sessão da requisição ativa do momento
        HttpSession session = request.getSession(true); // cria se não existir

        //--armazenar os dados do usuário logado!
        session.setAttribute("codigoUsuario", usuarioSessao.getId());
        session.setAttribute("nomeUsuario", usuarioSessao.getNome());
        session.setAttribute("cargoUsuario", usuarioSessao.getCargo());
    }

    public static LoginSessaoDto obter(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginSessaoDto usuarioSessao = new LoginSessaoDto();
        if (session != null && session.getAttribute("codigoUsuario") != null) {
            usuarioSessao.setId((long) session.getAttribute("codigoUsuario"));
            usuarioSessao.setNome((String) session.getAttribute("nomeUsuario"));
        } else {
            usuarioSessao = null;
        }
        return usuarioSessao;
    }

    public static void encerrar(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean permitirAcesso(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("cargoUsuario").toString().equals("ADMINISTRADOR")){
            return true;
        }
        return false;
    }
}
