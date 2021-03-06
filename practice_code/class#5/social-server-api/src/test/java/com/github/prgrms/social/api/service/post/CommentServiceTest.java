package com.github.prgrms.social.api.service.post;

import com.github.prgrms.social.api.error.NotFoundException;
import com.github.prgrms.social.api.model.commons.Id;
import com.github.prgrms.social.api.model.post.Comment;
import com.github.prgrms.social.api.model.post.Post;
import com.github.prgrms.social.api.model.post.Writer;
import com.github.prgrms.social.api.model.user.Email;
import com.github.prgrms.social.api.model.user.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private PostService postService;

  @Autowired private CommentService commentService;

  private Id<Post, Long> postId;

  private Id<User, Long> postWriterId;

  private Id<User, Long> userId;

  @BeforeAll
  void setUp() {
    postId = Id.of(Post.class, 1L);
    postWriterId = Id.of(User.class, 1L);
    userId = Id.of(User.class, 2L);
  }

  @Test
  @Order(1)
  void 코멘트를_작성한다() {
    String contents = randomAlphabetic(40);
    Post beforePost = postService.findById(postId, postWriterId, userId).orElseThrow(() -> new NotFoundException(Post.class, postId));
    Comment comment = commentService.write(
      postId,
      postWriterId,
      userId,
      new Comment(userId, postId, new Writer(new Email("test00@gmail.com"), "test00"), contents)
    );
    Post afterPost = postService.findById(postId, postWriterId, userId).orElseThrow(() -> new NotFoundException(Post.class, postId));
    assertThat(afterPost.getComments() - beforePost.getComments(), is(1));
    assertThat(comment, is(notNullValue()));
    assertThat(comment.getSeq(), is(notNullValue()));
    assertThat(comment.getContents(), is(contents));
    log.info("Written comment: {}", comment);
  }

  @Test
  @Order(2)
  void 댓글_목록을_조회한다() {
    List<Comment> comments = commentService.findAll(postId, postWriterId, userId);
    assertThat(comments, is(notNullValue()));
    assertThat(comments.size(), is(2));
  }

}