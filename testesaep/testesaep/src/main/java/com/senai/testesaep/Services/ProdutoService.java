package com.senai.testesaep.Services;

import com.senai.testesaep.Models.ProdutoModel;
import com.senai.testesaep.Repositorys.EstoqueRepository;
import com.senai.testesaep.Repositorys.ProdutoRepository;
import com.senai.testesaep.dtos.ProdutoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoService(ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public void cadastrarProduto(ProdutoDto dto){

        ProdutoModel model = new ProdutoModel();

        model.setNome(dto.getNome());
        model.setEstoque(dto.getEstoque());
        produtoRepository.save(model);
    }

    public Page<ProdutoModel> listarProdutoPaginado(int pagina){

        Pageable pageable = PageRequest.of(pagina, 5);

        return produtoRepository.findAll(pageable);
    }

    public List<ProdutoDto> listarProduto(){

        List<ProdutoModel> listaOP = produtoRepository.findAll();
        List<ProdutoDto> listaDto = new ArrayList<>();

        for (int i = 0; i < listaOP.size(); i++) {

            ProdutoDto dto = new ProdutoDto();

            dto.setId(listaOP.get(i).getId());
            dto.setNome(listaOP.get(i).getNome());
            dto.setEstoque(listaOP.get(i).getEstoque());

            listaDto.add(dto);
        }

        return listaDto;
    }

    public ProdutoDto encontrarProduto(Long id){

        Optional<ProdutoModel> produtoOp = produtoRepository.findById(id);

        ProdutoDto dto = new ProdutoDto();

        if (produtoOp.isPresent()){

            dto.setId(produtoOp.get().getId());
            dto.setEstoque(produtoOp.get().getEstoque());
            dto.setNome(produtoOp.get().getNome());

            return dto;
        }

        return dto;
    }

    public boolean verificarExistenciaProduto(Long id) {

        Optional<ProdutoModel> produtoOp = produtoRepository.findById(id);

        if (produtoOp.isPresent()) {

            return true;
        }
        return false;
    }

    public boolean alterarProduto(ProdutoDto dto, Long id){
        Optional<ProdutoModel> produtoOP = produtoRepository.findById(id);

        if (produtoOP.isPresent()){
            ProdutoModel model = new ProdutoModel();

            model.setId(id);
            model.setNome(dto.getNome());
            model.setEstoque(produtoOP.get().getEstoque());

            produtoRepository.save(model);

            return true;
        }

        return false;
    }

    public ProdutoModel retornarProdutoModel(Long id){

        Optional<ProdutoModel> produtoOP = produtoRepository.findById(id);
        ProdutoModel model = new ProdutoModel();
        if (produtoOP.isPresent()){

            model.setId(produtoOP.get().getId());
            model.setNome(produtoOP.get().getNome());
            model.setEstoque(produtoOP.get().getEstoque());
        }

        return  model;
    }

    public boolean adicionarEstoque(int valor, Long idProduto){

        Optional<ProdutoModel> produtoOp = produtoRepository.findById(idProduto);

        if (produtoOp.isPresent() && valor>0){

            ProdutoModel model = new ProdutoModel();
            model.setId(produtoOp.get().getId());
            model.setNome(produtoOp.get().getNome());
            model.setEstoque(produtoOp.get().getEstoque()+valor);
            produtoRepository.save(model);

            return true;
        }
        return false;
    }

    public boolean retirarProduto(int valorSaida, Long idProduto){
        Optional<ProdutoModel> produtoOp = produtoRepository.findById(idProduto);

        if (produtoOp.isPresent() && produtoOp.get().getEstoque()>0){
            if (valorSaida>0 && valorSaida<=produtoOp.get().getEstoque()){

                ProdutoModel model = new ProdutoModel();
                model.setId(idProduto);
                model.setNome(produtoOp.get().getNome());
                model.setEstoque(produtoOp.get().getEstoque() - valorSaida);

                produtoRepository.save(model);
                return true;
            }
        }
        return false;
    }

    public String deletarProduto(Long id){
         Optional<ProdutoModel> produtoOP = produtoRepository.findById(id);

         if (produtoOP.isPresent()){

             produtoRepository.delete(produtoOP.get());
             return "ok";
         }
         return "erro";
    }

    public Page<ProdutoModel> pesquisarPorNome(String nome, int pagina){

        Pageable pageable = PageRequest.of(pagina, 5);

        Page<ProdutoModel> paginaProdutos = produtoRepository.findByNomeContainingIgnoreCase(nome, pageable);

        return paginaProdutos;
    }
}
