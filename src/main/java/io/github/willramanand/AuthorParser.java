package io.github.willramanand;

public class AuthorParser {
  private String author_name;
  private String author_email;
  private String author_url;

  public void setName(String name) {
    this.author_name = name;
  }

  public void setEmail(String email) {
    this.author_email = email;
  }

  public void setURL(String url) {
    this.author_url = url;
  }

  public String getName() {
    return author_name;
  }

  public String getEmail() {
    return author_email;
  }

  public String getURL() {
    return author_url;
  }
}
