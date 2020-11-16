package com.leferti.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private String email;
	private String name;
	private String pass;
}
