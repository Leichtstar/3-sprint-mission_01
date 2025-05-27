package com.sprint.mission.discodeit.Controller.api;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "UserAPI")
public interface UserApi {

  @Operation(summary = "User 등록")   // 유저 생성 (POST /api/users)
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201", description = "User가 성공적으로 생성됨",
          content = @Content(schema = @Schema(implementation = User.class))
      ),
      @ApiResponse(
          responseCode = "400", description = "같은 email 또는 username을 사용하는 User가 이미 존재함.",
          content = @Content(examples = @ExampleObject(value = "User with email{email} already exists."))
      ),
  })
  ResponseEntity<UserDto> create(
      @Parameter(
          description = "User 생성 정보",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ) UserCreateRequest userCreateRequest,
      @Parameter(
          description = "User 프로필 이미지",
          content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
      ) MultipartFile profile
  );

  @Operation(summary = "User 정보 수정") // 유저 수정 (PUT /api/users/{userId})
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200", description = "User 정보가 성공적으로 수정됨",
          content = @Content(schema = @Schema(implementation = User.class))
      ),
      @ApiResponse(
          responseCode = "404", description = "User 를 찾을 수 없음.",
          content = @Content(examples = @ExampleObject("User with id {userId} not found"))
      ),
      @ApiResponse(
          responseCode = "400", description = "같은 email 또는 username을 사용하는 User가 이미 존재함.",
          content = @Content(examples = @ExampleObject("User with email {newEmail} or username {newUsername} already exists"))
      )
  })
  ResponseEntity<UserDto> update(
      @Parameter(description = "수정할 User Id") UUID userId,
      @Parameter(description = "수정할 User 정보") UserUpdateRequest userUpdateRequest,
      @Parameter(description = "수정할 User 프로필 이미지") MultipartFile profile
  );

  @Operation(summary = "User 정보 삭제")   // 유저 삭제 (DELETE /api/users/{userId}
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "204", description = "User 정보가 성공적으로 삭제됨"
      ),
      @ApiResponse(
          responseCode = "404", description = "User 를 찾을 수 없음.",
          content = @Content(examples = @ExampleObject("User with id {userId} not found"))
      )
  })
  ResponseEntity<Void> delete(
      @Parameter(description = "삭제할 UserId ") UUID userId
  );

  @Operation(summary = "전체 User 목록 조회")   // 전체 유저 조회 (Get /api/users)
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200", description = "User 목록 조회 성공",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
      )
  })
  ResponseEntity<List<UserDto>> findAll();

  @Operation(summary = "User Online 상태 업데이트")    //유저 상태 업데이트 (PATCH /api/users/{uerId}/status)
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200", description = "User 온라인 상태 성공적으로 업데이트됨",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
      ),
      @ApiResponse(
          responseCode = "404", description = "해당 User의 UserStatus를 찾을 수 없음",
          content = @Content(examples = @ExampleObject(value = "UserStatus with userId {userId} not found."))
      )
  })
  ResponseEntity<UserStatus> updateUserStatusByUserId(
      @Parameter(description = "상태를 변경할 UserId") UUID userId,
      @Parameter(description = "변경할 User 온라인 상태 정보") UserStatusUpdateRequest request);

}
