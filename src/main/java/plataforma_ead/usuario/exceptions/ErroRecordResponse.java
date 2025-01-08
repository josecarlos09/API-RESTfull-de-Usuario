package plataforma_ead.usuario.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroRecordResponse(int codigoErro,
                                 String mensagemErro,
                                 Map<String, String> detalhesErro
){}
