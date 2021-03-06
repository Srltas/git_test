package com.github.prgrms.social.api.repository.post;

import com.github.prgrms.social.api.model.commons.Id;
import com.github.prgrms.social.api.model.post.Post;
import com.github.prgrms.social.api.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class JdbcPostLikeRepository implements PostLikeRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcPostLikeRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void insert(Id<User, Long> userId, Id<Post, Long> postId) {
    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO likes(seq,user_seq,post_seq) VALUES (null,?,?)");
      ps.setLong(1, userId.value());
      ps.setLong(2, postId.value());
      return ps;
    });
  }

}