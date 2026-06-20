package com.senai.testesaep.Repositorys;

import com.senai.testesaep.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmailAndSenha(String email, String senha);

    Optional<UsuarioModel> findByEmail(String email);
}
