package plataforma_ead.usuario.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import plataforma_ead.usuario.validates.SenhaConstraint;

public record UsuarioRecordDto(@NotBlank(groups = UsuarioView.RegistroUsuarioPost.class, message = "O campo nome é obrigatorio!")
                               @Size(groups = UsuarioView.RegistroUsuarioPost.class, min = 5, max = 50, message = "O número máximo de caracteres é 50, e o mínimo é 5.")
                               @JsonView(UsuarioView.RegistroUsuarioPost.class)
                               String nome,

                               @NotBlank(groups = UsuarioView.RegistroUsuarioPost.class, message = "Campo E-mail é obrigatorio")
                               @Email(groups = UsuarioView.RegistroUsuarioPost.class, message = "Formato de E-mail invalido. Verifique o campo!")
                               @JsonView(UsuarioView.RegistroUsuarioPost.class)
                               String email,

                               @NotBlank(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class}, message = "O campo senha é obrigatorio")
                               @Size(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class}, min = 5, max = 20, message = "Informe a senha com no minimo 5 caracteres e no maximo 20")
                               @SenhaConstraint(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class})
                               @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class})
                               String senha,

                               @NotBlank(groups = UsuarioView.SenhaPut.class, message = "Informe a senha antiga")
                               @Size(groups = UsuarioView.SenhaPut.class, min = 5, max = 20, message = "Informe a senha com no minimo 5 caracteres e no maximo 20")
                               @SenhaConstraint(groups = UsuarioView.SenhaPut.class)
                               @JsonView(UsuarioView.SenhaPut.class)
                               String senhaAntiga,

                               @NotBlank(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class}, message = "informe o nome completo")
                               @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class})
                               String nomeCompleto,

                               @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class})
                               String telefoneCelular,

                               @NotBlank(groups = UsuarioView.ImagemPut.class, message = "Informe a URL da imagem")
                               @JsonView(UsuarioView.ImagemPut.class)
                               String imagemUrl){

    public interface UsuarioView{
        interface RegistroUsuarioPost{}
        interface UsuarioPut {}
        interface SenhaPut{}
        interface ImagemPut{}
    }
}