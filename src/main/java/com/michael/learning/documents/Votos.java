package com.michael.learning.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "votos")
public class Votos {

    @Id
    private String id;

    private String usuarioId;
    private String enqueteId;
    private String opcaoVotoId;

    private LocalDateTime dtCriacao;

}
