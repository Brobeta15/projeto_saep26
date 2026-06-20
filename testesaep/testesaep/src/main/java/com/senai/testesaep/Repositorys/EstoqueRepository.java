package com.senai.testesaep.Repositorys;

import com.senai.testesaep.Models.EstoqueModel;
import com.senai.testesaep.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueModel, Long> {

    List<EstoqueModel> findByProdutoId(Long idProduto);
}
