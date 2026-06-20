package com.senai.testesaep.Controllers;

import com.senai.testesaep.Services.UsuarioService;
import com.senai.testesaep.Sessao.ControleSessao;
import com.senai.testesaep.dtos.UsuarioCadastroDto;
import com.senai.testesaep.dtos.UsuarioDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String viewLogin(Model model){

        UsuarioDto dto = new UsuarioDto();
        model.addAttribute("UsuarioDto", dto);
        return "login";
    }
    @PostMapping("/login")
    public String autenticarUsuario(@ModelAttribute("UsuarioDto") UsuarioDto dto, HttpServletRequest request){

        boolean resposta = usuarioService.autenticacaoUsuario(dto);

        if (resposta){

            ControleSessao.registrar(request, usuarioService.retornarLoginSessao(request, dto));
            return "redirect:/listaproduto";
        }
        return "redirect:/login?erro";
    }

    @GetMapping("cadastrarusuario")
    public String viewUsuario(Model model){
        UsuarioCadastroDto dto = new UsuarioCadastroDto();

        model.addAttribute("UsuarioDto", dto);

        return "cadastrarusuario";
    }

    @PostMapping("cadastrarusuario")
    public String cadastrarUsuario(@ModelAttribute("UsuarioDto") UsuarioCadastroDto dto){

        boolean resposta = usuarioService.cadastrarUsuario(dto);

        if (resposta){
            return "redirect:/login";
        }
        return "redirect:/login?erro";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        ControleSessao.encerrar(request);
        return "redirect:/login";
    }
}
