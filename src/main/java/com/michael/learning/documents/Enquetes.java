package com.michael.learning.documents;

import com.michael.learning.enums.StatusEnquete;
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
@Document(collection = "enquetes")
public class Enquetes {

    @Id
    private String id;

    private String titulo;
    private String descricao;
    private StatusEnquete status;

    private LocalDateTime dtEncerramento;

    private String usuario;

}
