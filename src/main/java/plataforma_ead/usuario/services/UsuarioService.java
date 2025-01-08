package plataforma_ead.usuario.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.specifications.SpecificationsTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService{

    Optional<UsuarioModel> findById(UUID usuarioId);

    UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel);

    UsuarioModel saveUsuario(UsuarioRecordDto recordDtoUsuario);

    boolean existByNome(String nome);

    boolean existByEmail(String email);

    boolean findBySenha(String senha);

    UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto recordDtoUsuario);

    UsuarioModel updateSenha(UsuarioRecordDto usuarioRecordDto, UsuarioModel usuarioModel);

    UsuarioModel updateImagem(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto);

    List<UsuarioModel> findAll();

    Page<UsuarioModel> findAll(Pageable pageable);

    Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable);

}

