package plataforma_ead.usuario.repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import plataforma_ead.usuario.models.UsuarioModel;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID>, JpaSpecificationExecutor<UsuarioModel> {
    // o JpaSpecificationExecutor<UsuarioModel> está sendo herdado para usarmos especifications na entidade usuarioModel
    boolean existsByNome(String nome);
    boolean existsByEmail(String email);
    boolean existsBySenha(String senha);

}

