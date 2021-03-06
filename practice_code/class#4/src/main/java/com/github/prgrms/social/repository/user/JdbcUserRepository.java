package com.github.prgrms.social.repository.user;

import com.github.prgrms.social.model.commons.Id;
import com.github.prgrms.social.model.user.ConnectedUser;
import com.github.prgrms.social.model.user.Email;
import com.github.prgrms.social.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static com.github.prgrms.social.util.DateTimeUtils.dateTimeOf;
import static com.github.prgrms.social.util.DateTimeUtils.timestampOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcUserRepository implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public User insert(User user) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO users(seq,name,email,passwd,profile_image_url,login_count,last_login_at,create_at) VALUES (null,?,?,?,?,?,?,?)", new String[]{"seq"});
      ps.setString(1, user.getName());
      ps.setString(2, user.getEmail().getAddress());
      ps.setString(3, user.getPassword());
<<<<<<< HEAD
      ps.setString(4, user.getProfileImageUrl().orElse(null));
=======
      ps.setString(4, user.getProfileImageUrl());
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
      ps.setInt(5, user.getLoginCount());
      ps.setTimestamp(6, timestampOf(user.getLastLoginAt().orElse(null)));
      ps.setTimestamp(7, timestampOf(user.getCreateAt()));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    long generatedSeq = key != null ? key.longValue() : -1;
    return new User.Builder(user)
      .seq(generatedSeq)
      .build();
  }

  @Override
  public void update(User user) {
    jdbcTemplate.update(
      "UPDATE users SET name=?,passwd=?,profile_image_url=?,login_count=?,last_login_at=? WHERE seq=?",
      user.getName(),
      user.getPassword(),
<<<<<<< HEAD
      user.getProfileImageUrl().orElse(null),
=======
      user.getProfileImageUrl(),
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
      user.getLoginCount(),
      user.getLastLoginAt().orElse(null),
      user.getSeq()
    );
  }

  @Override
  public Optional<User> findById(Id<User, Long> userId) {
    List<User> results = jdbcTemplate.query(
      "SELECT * FROM users WHERE seq=?",
      mapper,
      userId.value()
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public Optional<User> findByEmail(Email email) {
    List<User> results = jdbcTemplate.query(
      "SELECT * FROM users WHERE email=?",
      mapper,
      email.getAddress()
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public List<ConnectedUser> findAllConnectedUser(Id<User, Long> userId) {
    return jdbcTemplate.query(
<<<<<<< HEAD
      "SELECT u.seq,u.name,u.email,u.profile_image_url,c.granted_at FROM connections c JOIN users u ON c.target_seq=u.seq WHERE c.user_seq=? AND c.granted_at is not null ORDER BY seq DESC",
=======
      "SELECT u.seq,u.name,u.email,c.granted_at FROM connections c JOIN users u ON c.target_seq=u.seq WHERE c.user_seq=? AND c.granted_at is not null ORDER BY seq DESC",
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
      (rs, rowNum) -> new ConnectedUser(
        rs.getLong("seq"),
        rs.getString("name"),
        new Email(rs.getString("email")),
<<<<<<< HEAD
        rs.getString("profile_image_url"),
=======
        rs.getString("profileImageUrl"),
>>>>>>> 08efee8c4c42d8f21fbf98a0f4014c784ecd4685
        dateTimeOf(rs.getTimestamp("granted_at"))
      ),
      userId.value()
    );
  }

  @Override
  public List<Id<User, Long>> findConnectedIds(Id<User, Long> userId) {
    return jdbcTemplate.query(
      "SELECT target_seq FROM connections WHERE user_seq=? AND granted_at is not null ORDER BY target_seq",
      (rs, rowNum) -> Id.of(User.class, rs.getLong("target_seq")),
      userId.value()
    );
  }

  static RowMapper<User> mapper = (rs, rowNum) -> new User.Builder()
    .seq(rs.getLong("seq"))
    .name(rs.getString("name"))
    .email(new Email(rs.getString("email")))
    .password(rs.getString("passwd"))
    .profileImageUrl(rs.getString("profile_image_url"))
    .loginCount(rs.getInt("login_count"))
    .lastLoginAt(dateTimeOf(rs.getTimestamp("last_login_at")))
    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
    .build();

}