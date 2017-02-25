package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.lib.Scope;
import au.com.codeka.carrot.lib.ValueHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * An {@link Comparator} is of the form:
 *
 * <code>expression = ["+"|"-"] term {("+"|"-") term}</code>
 */
public class Comparator {
  private final ArrayList<PrefixedTerm> prefixedTerms;

  private Comparator(ArrayList<PrefixedTerm> prefixedTerms) {
    this.prefixedTerms = prefixedTerms;
  }

  /** Returns a string representation of this {@link Comparator}, useful for debugging. */
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

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value = prefixedTerms.get(0).term.evaluate(config, scope);
    Token prefix = prefixedTerms.get(0).prefix;
    if (prefix != null) {
      if (prefix.getType() == TokenType.MINUS) {
        value = ValueHelper.negate(value);
      }
    }
    for (int i = 1; i < prefixedTerms.size(); i++) {
      Number lhs = ValueHelper.toNumber(value);
      Number rhs = ValueHelper.toNumber(prefixedTerms.get(i).term.evaluate(config, scope));
      prefix = prefixedTerms.get(i).prefix;
      if (prefix == null) {
        throw new CarrotException("Unexpected null prefix.");
      }
      if (prefix.getType() == TokenType.MINUS) {
        rhs = ValueHelper.negate(rhs);
      }
      value = ValueHelper.add(lhs, rhs);
    }
    return value;
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

    public Comparator build() {
      return new Comparator(terms);
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
