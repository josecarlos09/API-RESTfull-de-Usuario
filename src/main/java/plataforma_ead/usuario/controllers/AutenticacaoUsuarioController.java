package plataforma_ead.usuario.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.services.UsuarioService;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoUsuarioController {

    final UsuarioService usuarioService;

    public AutenticacaoUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<Object> registroUsuario(@RequestBody @Validated(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class)
                                                  @JsonView(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class)
                                                  UsuarioRecordDto recordDtoUsuario){
        /* O nome, o E-mail e a senha receberam a restrição UNIQUE no mapeamento da ENTITY UsuarioModel,
        isso significa que antes de salvar o registro na base de dados será realizado uma verificação de
        nome,E-mail e a senha
         */

        if (usuarioService.existByNome(recordDtoUsuario.nome())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, USUARIO JÁ EXISTENTE!");
        }
        if (usuarioService.existByEmail(recordDtoUsuario.email())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE E-MAIL JÁ CADASTRADO!");
        }
        if (usuarioService.findBySenha(recordDtoUsuario.senha())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESTÁ SENHA JÁ ESTÁ EM USO!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(recordDtoUsuario));
    }
}
