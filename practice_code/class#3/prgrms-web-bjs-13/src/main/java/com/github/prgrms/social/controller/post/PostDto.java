package com.github.prgrms.social.controller.post;

import com.github.prgrms.social.model.post.Post;
import com.github.prgrms.social.model.post.Writer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class PostDto {

  @ApiModelProperty(value = "PK", required = true)
  private Long seq;

  @ApiModelProperty(value = "내용", required = true)
  private String contents;

  @ApiModelProperty(value = "좋아요")
  private int likes;

  @ApiModelProperty(value = "해당 Post에 대한 나의 좋아요 확인", required = true)
  private boolean likesOfMe;

  @ApiModelProperty(value= "댓글 수")
  private int comments;

  @ApiModelProperty(value = "작성자", required = true)
  private Writer writer;

  @ApiModelProperty(value = "생성일자", required = true)
  private LocalDateTime createAt;

  public PostDto(Post source) {
    copyProperties(source, this);

    this.writer = source.getWriter().orElse(null);
  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public boolean isLikesOfMe() {
    return likesOfMe;
  }

  public void setLikesOfMe(boolean likesOfMe) {
    this.likesOfMe = likesOfMe;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public Writer getWriter() {
    return writer;
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("contents", contents)
      .append("likes", likes)
      .append("likesOfMe", likesOfMe)
      .append("comments", comments)
      .append("writer", writer)
      .append("createAt", createAt)
      .toString();
  }

}