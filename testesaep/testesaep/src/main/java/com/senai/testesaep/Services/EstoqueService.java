package com.senai.testesaep.Services;

import com.senai.testesaep.Models.EstoqueModel;
import com.senai.testesaep.Models.ProdutoModel;
import com.senai.testesaep.Models.UsuarioModel;
import com.senai.testesaep.Repositorys.EstoqueRepository;
import com.senai.testesaep.Sessao.ControleSessao;
import com.senai.testesaep.dtos.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoService produtoService, UsuarioService usuarioService) {
        this.estoqueRepository = estoqueRepository;
        this.produtoService = produtoService;
        this.usuarioService = usuarioService;
    }

    public List<EstoqueDto> retornarLista(Long idProduto){

        List<EstoqueModel> estoqueOP = estoqueRepository.findByProdutoId(idProduto);

        List<EstoqueDto> lista = new ArrayList<>();

        for (int i = 0; i < estoqueOP.size(); i++) {

            EstoqueDto dto = new EstoqueDto();

            dto.setId(estoqueOP.get(i).getId());
            dto.setData(estoqueOP.get(i).getData());
            dto.setHora(estoqueOP.get(i).getHora());
            dto.setQtdEntrada(estoqueOP.get(i).getQtdEntrada());
            dto.setQtdSaida(estoqueOP.get(i).getQtdSaida());
            dto.setUsuario(estoqueOP.get(i).getUsuario().getNome());

            lista.add(dto);
        }
        return lista;
    }

    public EstoqueEntradaDto retornarEstoqueEntradaDto(Long idProduto){

        ProdutoDto produto = produtoService.encontrarProduto(idProduto);

        EstoqueEntradaDto dto = new EstoqueEntradaDto();
        dto.setProduto(produto.getNome());
        dto.setProdutoId(produto.getId());

        return dto;
    }

    public boolean darEntradaEstoque(EstoqueEntradaDto dto, Long produtoId, HttpServletRequest request){

        ProdutoModel produto = produtoService.retornarProdutoModel(produtoId);
        EstoqueModel model = new EstoqueModel();

        if (produtoService.adicionarEstoque(dto.getQtdEntrada(), produtoId)){
            if (produto.getId().equals(produtoId)){

                UsuarioModel infoUsuario = usuarioService.retornarUsuarioLogado(ControleSessao.obter(request));

                model.setQtdEntrada(dto.getQtdEntrada());
                model.setHora(LocalTime.now());
                model.setData(LocalDate.now());
                model.setProduto(produto);
                model.setUsuario(infoUsuario);

                estoqueRepository.save(model);
                return true;
            }
        }
        return false;
    }

    public boolean retirarEstoque(EstoqueSaidaDto dto, Long produtoId, HttpServletRequest request){

        if (produtoService.verificarExistenciaProduto(produtoId)){

            if (produtoService.retirarProduto(dto.getQtdSaida(), produtoId)){
                ProdutoModel produtoModel = produtoService.retornarProdutoModel(produtoId);
                UsuarioModel usuarioModel = usuarioService.retornarUsuarioLogado(ControleSessao.obter(request));

                EstoqueModel model = new EstoqueModel();
                model.setHora(LocalTime.now());
                model.setData(LocalDate.now());
                model.setQtdSaida(dto.getQtdSaida());
                model.setProduto(produtoModel);
                model.setUsuario(usuarioModel);

                estoqueRepository.save(model);

                return true;
            }
        }
        return false;
    }
}
