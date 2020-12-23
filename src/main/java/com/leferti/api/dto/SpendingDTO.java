package com.leferti.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpendingDTO {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String dateRegister;
    private Integer amount;

}
