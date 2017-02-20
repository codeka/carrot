package au.com.codeka.carrot.tmpl.parse;

/**
 * An enumeration which describes the different types of tokens we can parse.
 */
public enum TokenType {
  /** Unknown token usually represents the end of the file. */
  UNKNOWN,

  /** A fixed token is anything outside of a {{foo}} or {%foo%} etc. */
  FIXED,

  /** A tag token is of the form {% foo %}, where "foo" is the content. */
  TAG,

  /** An echo token is of the form {{ foo }}, where "foo" is the content. */
  ECHO,

  /** A comment token is of the form {# foo #}, where "foo" is the content. */
  COMMENT
}
