package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tmpl.TagNode;

/**
 * StatementParser is used to parse expressions. Expressions are used to refer to everything that appears after the
 * {@link Tag} in a {@link TagNode}, and has the following pseudo-EBNF grammar:
 * <code>
 *   statement =
 *     expression
 *     | condition
 *     | identifier "(" expression {"," expression} ")"
 *
 *   condition =
 *     expression ("=="|"!="|"<"|"<="|">="|">") expression
 *
 *   expression = ["+"|"-"] term {("+"|"-") term}
 *
 *   term = factor {("*" | "/") factor}
 *
 *   factor =
 *     variable
 *     | number
 *     | literal
 *     | "(" expression ")"
 *
 *   variable = identifier {"." variable} {"[" variable "]"}
 *
 *   identifier = "any valid Java identifier"
 *   number = "and valid Java number"
 *   literal = """ anything """
 * </code>
 * <p>The statement parser allows you to extract any sub-element from a string as well (for example, the {@link ForTag}
 * wants to pull off it's arguments an identifier followed by the identifier "in" followed by a statement.
 */
public class StatementParser {

}
