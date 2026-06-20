package com.senai.testesaep.Controllers;

import com.senai.testesaep.Services.EstoqueService;
import com.senai.testesaep.Services.ProdutoService;
import com.senai.testesaep.Sessao.ControleSessao;
import com.senai.testesaep.dtos.EstoqueDto;
import com.senai.testesaep.dtos.EstoqueEntradaDto;
import com.senai.testesaep.dtos.EstoqueSaidaDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final ProdutoService produtoService;

    public EstoqueController(EstoqueService estoqueService, ProdutoService produtoService) {
        this.estoqueService = estoqueService;
        this.produtoService = produtoService;
    }

    @GetMapping("/listaestoque/{id}")
    public String viewListaEstoque(Model model, @PathVariable Long id){

        boolean resposta = produtoService.verificarExistenciaProduto(id);

        if (resposta){
            List<EstoqueDto> lista = estoqueService.retornarLista(id);
            model.addAttribute("listaestoque", lista);
            model.addAttribute("produtoId", id);

            return "/listaestoque";
        }
        return "redirect:listaproduto?erro";
    }

    @GetMapping("/entradaestoque/{id}")
    public String viewEntradaEstoque(Model model, @PathVariable Long id){

        EstoqueEntradaDto dto = estoqueService.retornarEstoqueEntradaDto(id);

        model.addAttribute("EntradaEstoqueDto", dto);
        model.addAttribute("produtoId", id);

        return "/entradaestoque";
    }

    @PostMapping("/entradaestoque/{id}")
    public String entrarComEstoque(@ModelAttribute("EntradaEstoqueDto") EstoqueEntradaDto dto, @PathVariable Long id, Model model, HttpServletRequest request){

        boolean resposta = estoqueService.darEntradaEstoque(dto, id, request);
        model.addAttribute("produtoId", id);

        if (resposta){
            return "redirect:/listaproduto";
        }
       return "redirect:/listaproduto?erro";
    }

    @GetMapping("/saidaestoque/{id}")
    public String viewSaidaEstoque(Model model,@PathVariable Long id){

        if (produtoService.verificarExistenciaProduto(id)){
            EstoqueSaidaDto dto = new EstoqueSaidaDto();

            model.addAttribute("EstoqueSaidaDto", dto);
            model.addAttribute("produtoId", id);

            return "saidaestoque";
        }
        return "redirect:listaproduto?erro";
    }

    @PostMapping("/saidaestoque/{id}")
    public String retirarEstoque(@ModelAttribute("EstoqueSaidaDto") EstoqueSaidaDto dto, @PathVariable Long id, HttpServletRequest request){

        boolean resposta = estoqueService.retirarEstoque(dto,id, request);

        if (resposta){
            return "redirect:/listaproduto";
        }
        return "redirect:/listaproduto?erro";
    }
}
