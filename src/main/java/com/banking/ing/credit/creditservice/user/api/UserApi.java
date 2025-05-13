package com.banking.ing.credit.creditservice.user.api;

import static com.banking.ing.credit.creditservice.user.constants.UserRouteConstants.USER_BASE_URI;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
    name = "User Management",
    description = "Includes all User Management operations."
)
@RequestMapping(USER_BASE_URI)
public interface UserApi {

  @GetMapping
  @Operation(
      description = "This operation Get all users.",
      summary = "Get all users."
  )
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers();

  @GetMapping("/{id}")
  @Operation(
      description = "This operation Get user by ID.",
      summary = "Get user by ID."
  )
  @PreAuthorize("@userSecurity.isAdminOrSelf(#id)")
  ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable final Long id);

  @GetMapping("/username/{username}")
  @Operation(
      description = "This operation Get user by username.",
      summary = "Get user by username."
  )
  @PreAuthorize("@userSecurity.isAdminOrSelf(#username)")
  ResponseEntity<ApiResponse<UserDto>> getUserByUsername(@PathVariable final String username);

  @PostMapping
  ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody final UserDto user);

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable final Long id, @RequestBody final UserDto user);

  @DeleteMapping("/{id}")
  @PreAuthorize("@userSecurity.isAdminOrSelf(#id)")
  ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable final Long id);

}
