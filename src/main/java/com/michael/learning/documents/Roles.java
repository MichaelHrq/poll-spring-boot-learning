package com.michael.learning.documents;

import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document("roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements GrantedAuthority {

    @Id
    private String id;

    private String role;

    @Override
    public @Nullable String getAuthority() {
        return role;
    }
}
