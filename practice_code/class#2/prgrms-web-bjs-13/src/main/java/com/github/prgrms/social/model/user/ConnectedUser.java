package com.github.prgrms.social.model.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;

public class ConnectedUser {

  private final Long seq;

  private final Email email;

  // TODO 이름 프로퍼티 추가
  private final String name;

  private final LocalDateTime grantedAt;

  public ConnectedUser(Long seq, Email email, String name, LocalDateTime grantedAt) {
    checkArgument(seq != null, "seq must be provided.");
    checkArgument(email != null, "email must be provided.");
    checkArgument(name != null, "name must be provided.");
    checkArgument(grantedAt != null, "grantedAt must be provided.");

    this.seq = seq;
    this.email = email;
    this.name = name;
    this.grantedAt = grantedAt;
  }

  public Long getSeq() {
    return seq;
  }

  public Email getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getGrantedAt() {
    return grantedAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("email", email)
      .append("name", name)
      .append("grantedAt", grantedAt)
      .toString();
  }

}