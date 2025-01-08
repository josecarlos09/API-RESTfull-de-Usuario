package plataforma_ead.usuario.validates;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SenhaConstraintImpl implements ConstraintValidator<SenhaConstraint, String> {
    // constraint: padrão de verificação de senha
    private static final String SENHA_PATTER = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{5,20}$";

    // Inicialização dos métodos defaul (initialize, e isValid)
    @Override
    public void initialize(SenhaConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(SENHA_PATTER); // Uso do padrão PATTERN

        // Verificação se a senha é: nula, ou vazia ou com espassos
        if (senha == null || senha.trim().isEmpty() || senha.contains(" ")){
             return false;
        } else if (!pattern.matcher(senha).matches()){
            return false;
        }
        return true;
    }
}