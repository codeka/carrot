package au.com.codeka.carrot.expr;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * An {@link Expression} is of the form:
 *
 * <code>expression = ["+"|"-"] term {("+"|"-") term}</code>
 */
public class Expression {
  private final ArrayList<PrefixedTerm> prefixedTerms;

  private Expression(ArrayList<PrefixedTerm> prefixedTerms) {
    this.prefixedTerms = prefixedTerms;
  }

  /** Returns a string representation of this {@link Expression}, useful for debugging. */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (PrefixedTerm prefixedTerm : prefixedTerms) {
      if (sb.length() > 0) {
        sb.append(" ");
      }
      if (prefixedTerm.prefix != null) {
        sb.append(prefixedTerm.prefix);
        sb.append(" ");
      }
      sb.append(prefixedTerm.term);
    }
    return sb.toString();
  }

  public static class Builder {
    private final ArrayList<PrefixedTerm> terms = new ArrayList<>();

    public Builder(@Nullable Token prefix, Term term) {
      this.terms.add(new PrefixedTerm(prefix, term));
    }

    public Builder addTerm(Token prefix, Term term) {
      this.terms.add(new PrefixedTerm(prefix, term));
      return this;
    }

    public Expression build() {
      return new Expression(terms);
    }
  }

  private static class PrefixedTerm {
    @Nullable public Token prefix;
    public Term term;

    PrefixedTerm(@Nullable Token prefix, Term term) {
      this.prefix = prefix;
      this.term = term;
    }
  }
}
