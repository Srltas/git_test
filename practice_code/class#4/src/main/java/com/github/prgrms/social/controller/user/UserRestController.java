package com.github.prgrms.social.controller.user;

import com.github.prgrms.social.aws.S3Client;
import com.github.prgrms.social.controller.ApiResult;
import com.github.prgrms.social.error.NotFoundException;
import com.github.prgrms.social.model.commons.AttachedFile;
<<<<<<< HEAD
import com.github.prgrms.social.model.commons.Id;
=======
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
import com.github.prgrms.social.model.user.Email;
import com.github.prgrms.social.model.user.Role;
import com.github.prgrms.social.model.user.User;
import com.github.prgrms.social.security.Jwt;
import com.github.prgrms.social.security.JwtAuthentication;
import com.github.prgrms.social.service.user.UserService;
<<<<<<< HEAD
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.github.prgrms.social.controller.ApiResult.OK;
import static com.github.prgrms.social.model.commons.AttachedFile.toAttachedFile;
import static java.util.Optional.of;
=======
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.prgrms.social.controller.ApiResult.OK;
import static com.github.prgrms.social.model.commons.AttachedFile.toAttachedFile;
import static java.util.Optional.empty;
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
@Api(tags = "????????? APIs")
public class UserRestController {

<<<<<<< HEAD
  private final Logger log = LoggerFactory.getLogger(getClass());

=======
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
  private final Jwt jwt;

  private final S3Client s3Client;

  private final UserService userService;

  public UserRestController(Jwt jwt, S3Client s3Client, UserService userService) {
    this.jwt = jwt;
    this.s3Client = s3Client;
    this.userService = userService;
  }

  @PostMapping(path = "user/exists")
  @ApiOperation(value = "????????? ???????????? (API ?????? ????????????)")
  public ApiResult<Boolean> checkEmail(
    @RequestBody @ApiParam(value = "example: {\"address\": \"test00@gmail.com\"}") Map<String, String> request
  ) {
    Email email = new Email(request.get("address"));
    return OK(
      userService.findByEmail(email).isPresent()
    );
  }

  /**
   * TODO S3Client??? ????????? ???????????? S3??? ???????????????.
   */
  public Optional<String> uploadProfileImage(AttachedFile profileFile) {
<<<<<<< HEAD
    return of(s3Client.upload(
            profileFile.inputStream(),
            profileFile.length(),
            profileFile.randomName(LocalDateTime.now().toString()),
            profileFile.getContentType(),
            ImmutableMap.of()
    ));
=======
    return empty();
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
  }

  @PostMapping(path = "user/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "???????????? (API ?????? ????????????)")
  public ApiResult<JoinResult> join(
    @ModelAttribute JoinRequest joinRequest,
    @RequestPart(required = false) MultipartFile file
  ) {
    User user = userService.join(
      joinRequest.getName(),
      new Email(joinRequest.getPrincipal()),
      joinRequest.getCredentials()
    );

    toAttachedFile(file).ifPresent(attachedFile -> {
      // TODO User ????????? ???????????? ?????? (??????????????? CompletableFuture??? ????????? ???????????? ????????? ????????? ??????)
<<<<<<< HEAD
      CompletableFuture.supplyAsync(() -> {
        return uploadProfileImage(attachedFile);
      }).exceptionally(e -> {
        log.warn("failed to upload profile image: {}", e.getMessage(), e);
        return Optional.empty();
      }).thenAccept(url -> {
        userService.updateProfileImage(Id.of(User.class, user.getSeq()), url.get());
      });
=======
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
    });

    String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
    return OK(
      new JoinResult(apiToken, new UserDto(user))
    );
  }

  @GetMapping(path = "user/me")
  @ApiOperation(value = "??? ??????")
  public ApiResult<UserDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
    return OK(
      userService.findById(authentication.id)
        .map(UserDto::new)
        .orElseThrow(() -> new NotFoundException(User.class, authentication.id))
    );
  }

  @GetMapping(path = "user/connections")
  @ApiOperation(value = "??? ?????? ??????")
  public ApiResult<List<ConnectedUserDto>> connections(@AuthenticationPrincipal JwtAuthentication authentication) {
    return OK(
      userService.findAllConnectedUser(authentication.id).stream()
        .map(ConnectedUserDto::new)
        .collect(toList())
    );
  }

}