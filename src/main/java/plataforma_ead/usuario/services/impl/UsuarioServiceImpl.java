package plataforma_ead.usuario.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.enums.StatusUsuario;
import plataforma_ead.usuario.enums.TipoUsuario;
import plataforma_ead.usuario.exceptions.NotFoundException;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.repositorys.UsuarioRepository;
import plataforma_ead.usuario.services.UsuarioService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<UsuarioModel> findById(UUID usuarioId) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(usuarioId);

        if (usuarioModelOptional.isEmpty()){
            // Exception NOT FALD (USUARIO NÃO ENCONTRADO)
            throw new NotFoundException("ERRO, USUARIO NÃO ENCONTRADO!");
        }
        return usuarioModelOptional;
    }

    @Override
    public UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel) {
        usuarioRepository.delete(usuarioModel);
        return usuarioModel;
    }

    @Override
    public UsuarioModel saveUsuario(UsuarioRecordDto recordDtoUsuario) {
        var usuarioModel = new UsuarioModel(); //Instanciando o UsuarioModel
        BeanUtils.copyProperties(recordDtoUsuario, usuarioModel); // Convertendo de DTO para Model
        // Populando status, tipo de usuario data de criaçao e data de atualização
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);
        usuarioModel.setTipoUsuario(TipoUsuario.USUARIO);
        usuarioModel.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        // Retornando os dados salvos
        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public boolean existByNome(String nome) {
        return usuarioRepository.existsByNome(nome);
    }

    @Override
    public boolean existByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean findBySenha(String senha) {
        return usuarioRepository.existsBySenha(senha);
    }

    @Override
    public UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto recordDtoUsuario) {

        // Populando status, tipo de usuario data de criaçao e data de atualização
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);
        usuarioModel.setTipoUsuario(TipoUsuario.USUARIO);
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        usuarioModel.setNomeCompleto(recordDtoUsuario.nomeCompleto());
        usuarioModel.setTelefoneCelular(recordDtoUsuario.telefoneCelular());

        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public UsuarioModel updateSenha(UsuarioRecordDto usuarioRecordDto, UsuarioModel usuarioModel) {
        usuarioModel.setSenha(usuarioRecordDto.senha());
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public UsuarioModel updateImagem(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {

        usuarioModel.setImagemUrl(usuarioRecordDto.imagemUrl());
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public List<UsuarioModel> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable) {
        return usuarioRepository.findAll(spec, pageable);
    }
}