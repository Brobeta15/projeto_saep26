package com.senai.testesaep.Services;
import com.senai.testesaep.Models.UsuarioModel;
import com.senai.testesaep.Repositorys.UsuarioRepository;
import com.senai.testesaep.Sessao.ControleSessao;
import com.senai.testesaep.dtos.LoginSessaoDto;
import com.senai.testesaep.dtos.UsuarioCadastroDto;
import com.senai.testesaep.dtos.UsuarioDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.senai.testesaep.Models.CargoEnum.OPERADOR;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean autenticacaoUsuario(UsuarioDto dto){

        Optional<UsuarioModel> usuarioOP = repository.findByEmailAndSenha(dto.getEmail(), dto.getSenha());

        if (usuarioOP.isPresent()){
            return true;
        }
        return false;
    }

    public boolean cadastrarUsuario(UsuarioCadastroDto dto){

        Optional<UsuarioModel> usuarioOP = repository.findByEmail(dto.getEmail());

        if (usuarioOP.isEmpty()){
            UsuarioModel model = new UsuarioModel();

            model.setNome(dto.getNome());
            model.setEmail(dto.getEmail());
            model.setSenha(dto.getSenha());
            model.setCargo(OPERADOR);

            repository.save(model);

            return true;
        }
        return false;
    }

    public UsuarioModel retornarUsuarioLogado(LoginSessaoDto sessao){

        Optional<UsuarioModel> usuarioOp = repository.findById(sessao.getId());

        UsuarioModel dto = new UsuarioModel();
        if (usuarioOp.isPresent()){

            dto.setNome(usuarioOp.get().getNome());
            dto.setCargo(usuarioOp.get().getCargo());
            dto.setId(usuarioOp.get().getId());
            dto.setEmail(usuarioOp.get().getEmail());
            dto.setSenha(usuarioOp.get().getSenha());

            return dto;
        }
        return dto;
    }

    public LoginSessaoDto retornarLoginSessao(HttpServletRequest request, UsuarioDto dto){

        Optional<UsuarioModel> usuarioOP = repository.findByEmailAndSenha(dto.getEmail(), dto.getSenha());
        LoginSessaoDto sessao = new LoginSessaoDto();

        if (usuarioOP.isPresent()){
            sessao.setId(usuarioOP.get().getId());
            sessao.setNome(usuarioOP.get().getNome());
            sessao.setCargo(usuarioOP.get().getCargo().name());

            return sessao;
        }
        return sessao;
    }
}
