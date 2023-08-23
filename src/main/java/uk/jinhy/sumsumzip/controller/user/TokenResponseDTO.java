package uk.jinhy.sumsumzip.controller.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokenResponseDTO {
    private boolean isLogined;
    private String token;
    private String email;
}
