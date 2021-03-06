package com.github.prgrms.social.controller.post;

import com.github.prgrms.social.configure.support.Pageable;
import com.github.prgrms.social.controller.ApiResult;
import com.github.prgrms.social.error.NotFoundException;
import com.github.prgrms.social.model.commons.Id;
import com.github.prgrms.social.model.post.Post;
import com.github.prgrms.social.model.post.Writer;
import com.github.prgrms.social.model.user.User;
import com.github.prgrms.social.security.JwtAuthentication;
import com.github.prgrms.social.service.post.CommentService;
import com.github.prgrms.social.service.post.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.prgrms.social.controller.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class PostRestController {

  private final PostService postService;

  private final CommentService commentService;

  public PostRestController(PostService postService, CommentService commentService) {
    this.postService = postService;
    this.commentService = commentService;
  }

  @PostMapping(path = "post")
  public ApiResult<PostDto> posting(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody PostingRequest request
  ) {
    return OK(
      new PostDto(
        postService.write(
          request.newPost(authentication.id, new Writer(authentication.email, authentication.name))
        )
      )
    );
  }

  @GetMapping(path = "user/{userId}/post/list")
  @ApiOperation(value = "포스트 목록 조회")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "offset", dataTypeClass = Long.class, paramType = "query", defaultValue = "0", example = "0", value = "페이징 offset"),
    @ApiImplicitParam(name = "limit", dataTypeClass = Integer.class, paramType = "query", defaultValue = "20", example = "20", value = "최대 조회 갯수")
  })
  public ApiResult<List<PostDto>> posts(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 또는 친구)", example = "1") Long userId,
    Pageable pageable
  ) {
    return OK(
      postService.findAll(Id.of(User.class, userId), authentication.id, pageable.offset(), pageable.limit()).stream()
        .map(PostDto::new)
        .collect(toList())
    );
  }

  @PatchMapping(path = "user/{userId}/post/{postId}/like")
  @ApiOperation(value = "포스트 좋아요")
  public ApiResult<PostDto> like(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 또는 친구)", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long postId
  ) {
    return OK(
      postService.like(Id.of(Post.class, postId), Id.of(User.class, userId), authentication.id)
        .map(PostDto::new)
        .orElseThrow(() -> new NotFoundException(Post.class, Id.of(Post.class, postId), Id.of(User.class, userId)))
    );
  }

  @PostMapping(path = "user/{userId}/post/{postId}/comment")
<<<<<<< HEAD
  @ApiOperation(value = "코멘트 작성")
  public ApiResult<CommentDto> comment(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "포스터 작성자 PK", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long postId,
    @RequestBody @ApiParam(value = "코멘트 내용") CommentRequest request
  ) {
    // TODO Comment 작성 API를 구현하세요.
    return OK(
      new CommentDto(
        commentService.write(Id.of(Post.class, postId), Id.of(User.class, userId), authentication.id,
                request.newComment(authentication.id, Id.of(Post.class, postId), new Writer(authentication.email, authentication.name)))
      )
    );
  }

  @GetMapping(path = "user/{userId}/post/{postId}/comment/list")
  @ApiOperation(value = "코멘트 목록 조회")
  public ApiResult<List<CommentDto>> comments(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "포스터 작성자 PK", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long postId
  ) {
    // TODO Comment 목록 조회 API를 구현하세요.
    return OK(
      commentService.findAll(Id.of(Post.class, postId), Id.of(User.class, userId), authentication.id).stream()
        .map(CommentDto::new)
        .collect(toList())
    );
=======
  public ApiResult<CommentDto> comment(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long userId,
    @PathVariable Long postId,
    @RequestBody CommentRequest request
  ) {
    // TODO Comment 작성 API를 구현하세요.
    throw new NotImplementedException("구현이 필요합니다.");
  }

  @GetMapping(path = "user/{userId}/post/{postId}/comment/list")
  public ApiResult<List<CommentDto>> comments(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long userId,
    @PathVariable Long postId
  ) {
    // TODO Comment 목록 조회 API를 구현하세요.
    throw new NotImplementedException("구현이 필요합니다.");
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
  }

}