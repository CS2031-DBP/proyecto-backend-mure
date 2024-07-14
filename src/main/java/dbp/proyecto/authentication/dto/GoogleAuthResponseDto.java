package dbp.proyecto.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GoogleAuthResponseDto {
    private boolean isValid;
}
