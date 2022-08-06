package com.pdas.blog.playloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be atleast of 4 characters")
	private String name;

	@Email(message = "Invalid Email address!")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "Password length must be 3 and maximum 10!")
//	@Pattern(regexp = )
	private String password;

	@NotEmpty
	private String about;
}
