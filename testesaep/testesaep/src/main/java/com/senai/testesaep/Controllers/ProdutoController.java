package com.senai.testesaep.Controllers;

import com.senai.testesaep.Models.ProdutoModel;
import com.senai.testesaep.Services.ProdutoService;
import com.senai.testesaep.Sessao.ControleSessao;
import com.senai.testesaep.dtos.ProdutoDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/listaproduto")
    public String viewLista(@RequestParam(defaultValue = "0")int pagina, Model model, @RequestParam(required = false) String nome){

        Page<ProdutoModel> paginaProdutos;

        if(nome != null && !nome.isBlank()){
            paginaProdutos = produtoService.pesquisarPorNome(nome, pagina);
        } else {
            paginaProdutos = produtoService.listarProdutoPaginado(pagina);
        }
        model.addAttribute("listaproduto", paginaProdutos);
        model.addAttribute("nome", nome);

        return "listaproduto";
    }


    @DeleteMapping("/listaproduto/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id, HttpServletRequest request){
        boolean acessoPermitido = ControleSessao.permitirAcesso(request);

        String resposta = "erro";

        if (acessoPermitido){
            resposta = produtoService.deletarProduto(id);

            if (resposta.equals("ok")){
                return ResponseEntity.ok().body(resposta);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @GetMapping("/cadastroproduto")
    public String viewProduto(Model model){

        ProdutoDto dto = new ProdutoDto();

        model.addAttribute("ProdutoDto", dto);
        return "cadastroproduto";
    }

    @PostMapping("/cadastroproduto")
    public String cadastrarProduto(@ModelAttribute("ProdutoDto")ProdutoDto dto){

        produtoService.cadastrarProduto(dto);

        return "redirect:/listaproduto";
    }

    @GetMapping("/alterarproduto/{id}")
    public String viewAlterarProduto(Model model, @PathVariable Long id){

        ProdutoDto dto = produtoService.encontrarProduto(id);

        if (dto == null){

            return "redirect:/listaproduto?erro";
        }

        model.addAttribute("ProdutoDto", dto);
        return "alterarproduto";
    }

    @PostMapping("/alterarproduto/{id}")
    public String alterarProduto(@ModelAttribute("ProdutoDto") ProdutoDto dto, @PathVariable Long id){

        boolean resposta = produtoService.alterarProduto(dto, id);

        if (resposta){
            return "redirect:/listaproduto";
        }
        return "redirect:/istaproduto?erro";
    }
}
