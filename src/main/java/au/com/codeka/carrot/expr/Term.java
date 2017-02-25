package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * A {@link Term} is of the form:
 *
 * <code>term = factor {("*" | "/") factor}</code>
 */
public class Term {
  private final ArrayList<PrefixedFactor> prefixedFactors;

  private Term(ArrayList<PrefixedFactor> prefixedFactors) {
    this.prefixedFactors = prefixedFactors;
  }

  /** Returns a string representation of this {@link Term}, useful for debugging. */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (PrefixedFactor prefixedFactor : prefixedFactors) {
      if (sb.length() > 0) {
        sb.append(" ");
      }
      if (prefixedFactor.prefix != null) {
        sb.append(prefixedFactor.prefix);
        sb.append(" ");
      }
      sb.append(prefixedFactor.factor);
    }
    return sb.toString();
  }

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value = prefixedFactors.get(0).factor.evaluate(config, scope);
    for (int i = 1; i < prefixedFactors.size(); i++) {
      Number lhs = ValueHelper.toNumber(value);
      Number rhs = ValueHelper.toNumber(prefixedFactors.get(i).factor.evaluate(config, scope));
      Token prefix = prefixedFactors.get(i).prefix;
      if (prefix == null) {
        throw new CarrotException("Unexpected null prefix.");
      }
      if (prefix.getType() == TokenType.DIVIDE) {
        value = ValueHelper.divide(lhs, rhs);
      } else {
        value = ValueHelper.multiply(lhs, rhs);
      }
    }
    return value;
  }

  public static class Builder {
    private final ArrayList<PrefixedFactor> factors = new ArrayList<>();

    public Builder(@Nullable Token prefix, Factor factor) {
      this.factors.add(new PrefixedFactor(prefix, factor));
    }

    public Builder addFactor(Token prefix, Factor factor) {
      this.factors.add(new PrefixedFactor(prefix, factor));
      return this;
    }

    public Term build() {
      return new Term(factors);
    }
  }

  private static class PrefixedFactor {
    @Nullable public Token prefix;
    public Factor factor;

    PrefixedFactor(@Nullable Token prefix, Factor factor) {
      this.prefix = prefix;
      this.factor = factor;
    }
  }
}
