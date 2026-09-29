package com.michael.learning.documents;

import com.michael.learning.enums.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "usuarios-roles")
public class UsuarioRoles {

    @Id
    private String id;

    private String usuarioId;

    private String roleId;
}
