package plataforma_ead.usuario.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import plataforma_ead.usuario.models.UsuarioModel;

public class SpecificationsTemplate {
    // Mapeamento dos filtros dinamicos em uma interface que herda o Specification na entidade UsuarioModel
    @And({
            @Spec(path = "tipoUsuario", spec = Equal.class),
            @Spec(path = "statusUsuario", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "nome", spec = Like.class),
            @Spec(path = "nomeCompleto", spec = LikeIgnoreCase.class)
    })
    public interface UsuarioSpec extends Specification<UsuarioModel>{}
}