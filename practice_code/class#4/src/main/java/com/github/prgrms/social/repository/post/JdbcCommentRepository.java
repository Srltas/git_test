package com.github.prgrms.social.repository.post;

import com.github.prgrms.social.model.commons.Id;
import com.github.prgrms.social.model.post.Comment;
import com.github.prgrms.social.model.post.Post;
import com.github.prgrms.social.model.post.Writer;
import com.github.prgrms.social.model.user.Email;
import com.github.prgrms.social.model.user.User;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
<<<<<<< HEAD
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
=======
import org.springframework.stereotype.Repository;

>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
import java.util.List;
import java.util.Optional;

import static com.github.prgrms.social.util.DateTimeUtils.dateTimeOf;
<<<<<<< HEAD
import static com.github.prgrms.social.util.DateTimeUtils.timestampOf;
=======
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
import static java.util.Optional.ofNullable;

@Repository
public class JdbcCommentRepository implements CommentRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Comment insert(Comment comment) {
    // TODO comment 추가
<<<<<<< HEAD
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO comments(seq, user_seq, post_seq, contents, create_at) VALUES (null,?,?,?,?)", new String[]{"seq"});
      ps.setLong(1, comment.getUserId().value());
      ps.setLong(2, comment.getPostId().value());
      ps.setString(3, comment.getContents());
      ps.setTimestamp(4, timestampOf(comment.getCreateAt()));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    long generatedSeq = key != null ? key.longValue() : -1;
    return new Comment.Builder(comment)
            .seq(generatedSeq)
            .build();
=======
    throw new NotImplementedException("구현이 필요합니다.");
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
  }

  @Override
  public void update(Comment comment) {
    jdbcTemplate.update(
      "UPDATE comments SET contents=? WHERE seq=?",
      comment.getContents(),
      comment.getSeq()
    );
  }

  @Override
  public Optional<Comment> findById(Id<Comment, Long> commentId) {
    List<Comment> results = jdbcTemplate.query(
      "SELECT c.*,u.email,u.name FROM comments c JOIN users u ON c.user_seq=u.seq WHERE c.seq=?",
      mapper,
      commentId.value()
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public List<Comment> findAll(Id<Post, Long> postId) {
    // TODO comment 목록 조회
<<<<<<< HEAD
    return jdbcTemplate.query(
      "SELECT " +
        "c.seq, c.user_seq, c.post_seq, c.contents, u.email, u.name, c.create_at " +
      "FROM " +
        "comments c JOIN users u ON c.user_seq=u.seq " +
      "WHERE " +
        "c.post_seq=?",
      mapper,
      postId.value()
    );
=======
    throw new NotImplementedException("구현이 필요합니다.");
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
  }

  static RowMapper<Comment> mapper = (rs, rowNum) -> new Comment.Builder()
    .seq(rs.getLong("seq"))
    .userId(Id.of(User.class, rs.getLong("user_seq")))
    .postId(Id.of(Post.class, rs.getLong("post_seq")))
    .contents(rs.getString("contents"))
    .writer(new Writer(new Email(rs.getString("email")), rs.getString("name")))
    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
    .build();

}