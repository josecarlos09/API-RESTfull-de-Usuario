package plataforma_ead.usuario.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.services.UsuarioService;
import plataforma_ead.usuario.specifications.SpecificationsTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuario")
//@CrossOrigin(origins = "http://usuario.com", maxAge = 3600) // Configuração de CROS a nivel de método.
public class UsuarioController {

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity <Page<UsuarioModel>> getAllUsuarios(//@PageableDefault(page = 0, size = 10, sort = "usuarioId", direction = Sort.Direction.ASC)
                                                              SpecificationsTemplate.UsuarioSpec spec,
                                                              Pageable pageable){
        Page<UsuarioModel> usuarioModelPage = usuarioService.findAll(pageable); // Uso da paginação (Tipo do retorno Page)
        Page<UsuarioModel> usuarioModelPageSpec = usuarioService.findAll(spec, pageable); // Uso da paginação com specifications (Tipo do retorno Page)
        List<UsuarioModel> usuarioModels = usuarioService.findAll(); // uso de listas (Tipo do retorno List)

        if (!usuarioModelPageSpec.isEmpty()){
            for (UsuarioModel usuario: usuarioModelPageSpec.toList()){
                usuario.add(linkTo(methodOn(UsuarioController.class).getOnUsuario(usuario.getUsuarioId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioModelPageSpec);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> getOnUsuario(@PathVariable(value = "usuarioId")UUID usuarioId){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(usuarioId));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "usuarioId")UUID usuarioId){
        usuarioService.deleteUsuarioId(usuarioService.findById(usuarioId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com suceso!");
    }

    @PutMapping("/{usuarioId}/usuario")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "usuarioId")UUID usuarioId,
                                                @RequestBody @Validated(UsuarioRecordDto.UsuarioView.UsuarioPut.class)
                                                @JsonView(UsuarioRecordDto.UsuarioView.UsuarioPut.class)
                                                UsuarioRecordDto usuarioRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateUsuario(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }

    @PutMapping("/{usuarioId}/senha")
    public ResponseEntity<Object> updateSenha(@PathVariable(value = "usuarioId") UUID usuarioId,
                                              @RequestBody @Validated(UsuarioRecordDto.UsuarioView.SenhaPut.class)
                                              @JsonView(UsuarioRecordDto.UsuarioView.SenhaPut.class)
                                              UsuarioRecordDto dadosUsuarioRecordDto){
        Optional<UsuarioModel> optionalModelUsuario = usuarioService.findById(usuarioId);
        // Se o optionalModelUsuario.get().getSenha() não coresponder a dadosUsuarioRecordDto.senhaAntiga() será retornado Senha inexistente!
        if (!optionalModelUsuario.get().getSenha().equals(dadosUsuarioRecordDto.senhaAntiga())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Senha inexistente!");
        }
        usuarioService.updateSenha(dadosUsuarioRecordDto, optionalModelUsuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
    }

    @PutMapping("/{usuarioId}/imagem")
    public ResponseEntity<Object> updateImagem(@PathVariable(value = "usuarioId")UUID usuarioId,
                                               @RequestBody @Validated(UsuarioRecordDto.UsuarioView.ImagemPut.class)
                                               @JsonView(UsuarioRecordDto.UsuarioView.ImagemPut.class)
                                               UsuarioRecordDto usuarioRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateImagem(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }
}