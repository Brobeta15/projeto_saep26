package com.senai.testesaep.Repositorys;

import com.senai.testesaep.Models.ProdutoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    Page<ProdutoModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
