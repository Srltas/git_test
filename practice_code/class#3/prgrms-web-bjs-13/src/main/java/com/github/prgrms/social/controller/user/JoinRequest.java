package com.github.prgrms.social.controller.user;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JoinRequest {

  @ApiModelProperty(value = "이름", required = true)
  private String name;

  @ApiModelProperty(value = "아이디", required = true)
  private String principal;

  @ApiModelProperty(value = "패스워드", required = true)
  private String credentials;

  protected JoinRequest() {}

  public String getName() {
    return name;
  }

  public String getPrincipal() {
    return principal;
  }

  public String getCredentials() {
    return credentials;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("name", name)
      .append("principal", principal)
      .append("credentials", credentials)
      .toString();
  }

}