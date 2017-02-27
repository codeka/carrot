package au.com.codeka.carrot;

import au.com.codeka.carrot.CarrotException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Various helpers for working with {@link Object}s.
 */
public class ValueHelper {
  /** Does the given value represent "true". For example, it's a Boolean that's true, a non-zero integer, etc. */
  public static boolean isTrue(Object value) throws CarrotException {
    if (value == null) {
      return false;
    } else if (value instanceof Boolean) {
      return (Boolean) value;
    } else if (value instanceof Number) {
      return ((Number) value).intValue() > 0;
    } else if (value instanceof String) {
      return ((String) value).isEmpty();
    } else {
      throw new CarrotException("Value '" + value + "' cannot be converted to boolean.");
    }
  }

  public static Number negate(Object value) throws CarrotException {
    if (value == null) {
      throw new CarrotException("Value is null");
    } else if (value instanceof Integer) {
      return -((Integer) value);
    } else if (value instanceof Long) {
      return -((Long) value);
    } else if (value instanceof Float) {
      return -((Float) value);
    } else if (value instanceof Double) {
      return -((Double) value);
    } else {
      throw new CarrotException("Value '" + value + "' cannot be negated.");
    }
  }

  public static Number toNumber(Object value) throws CarrotException {
    if (value == null) {
      throw new CarrotException("Value is null.");
    } else if (value instanceof Number) {
      return (Number) value;
    } else if (value instanceof String) {
      String str = (String) value;
      if (str.contains(".")) {
        return Double.parseDouble(str);
      } else {
        return Long.parseLong(str);
      }
    } else {
      throw new CarrotException("Cannot convert '" + value + "' to a number.");
    }
  }

  public static Number add(Object lhs, Object rhs) throws CarrotException {
    if (lhs == null || rhs == null) {
      throw new CarrotException("Left hand side or right hand side is null.");
    }

    Number lhsNumber = toNumber(lhs);
    Number rhsNumber = toNumber(rhs);
    if (lhsNumber instanceof Double || rhsNumber instanceof Double) {
      return lhsNumber.doubleValue() + rhsNumber.doubleValue();
    } else if (lhsNumber instanceof Float || rhsNumber instanceof Float) {
      return lhsNumber.floatValue() + rhsNumber.floatValue();
    } else if (lhsNumber instanceof Long || rhsNumber instanceof Long) {
      return lhsNumber.longValue() + rhsNumber.longValue();
    } else if (lhsNumber instanceof Integer || rhsNumber instanceof Integer) {
      return lhsNumber.longValue() + rhsNumber.longValue();
    }

    throw new CarrotException("Unknown number type '" + lhs + "' or '" + rhs + "'.");
  }

  /** Return a number that's the inverse of the given value (that is, 1 / value). */
  public static Number divide(Object lhs, Object rhs) throws CarrotException {
    if (lhs == null || rhs == null) {
      throw new CarrotException("Left hand side or right hand side is null.");
    }

    Number lhsNumber = toNumber(lhs);
    Number rhsNumber = toNumber(rhs);

    if (lhsNumber instanceof Double || rhsNumber instanceof Double) {
      return lhsNumber.doubleValue() / rhsNumber.doubleValue();
    } else if (lhsNumber instanceof Float || rhsNumber instanceof Float) {
      return lhsNumber.floatValue() / rhsNumber.floatValue();
    } else if (lhsNumber instanceof Long || rhsNumber instanceof Long) {
      return lhsNumber.longValue() / rhsNumber.longValue();
    } else if (lhsNumber instanceof Integer || rhsNumber instanceof Integer) {
      return lhsNumber.longValue() / rhsNumber.longValue();
    }

    throw new CarrotException("Unknown number type '" + lhs + "' or '" + rhs + "'.");
  }

  /** Return a number that's the inverse of the given value (that is, 1 / value). */
  public static Number multiply(Object lhs, Object rhs) throws CarrotException {
    if (lhs == null || rhs == null) {
      throw new CarrotException("Left hand side or right hand side is null.");
    }

    Number lhsNumber = toNumber(lhs);
    Number rhsNumber = toNumber(rhs);

    if (lhsNumber instanceof Double || rhsNumber instanceof Double) {
      return lhsNumber.doubleValue() * rhsNumber.doubleValue();
    } else if (lhsNumber instanceof Float || rhsNumber instanceof Float) {
      return lhsNumber.floatValue() * rhsNumber.floatValue();
    } else if (lhsNumber instanceof Long || rhsNumber instanceof Long) {
      return lhsNumber.longValue() * rhsNumber.longValue();
    } else if (lhsNumber instanceof Integer || rhsNumber instanceof Integer) {
      return lhsNumber.longValue() * rhsNumber.longValue();
    }

    throw new CarrotException("Unknown number type '" + lhs + "' or '" + rhs + "'.");
  }

  @SuppressWarnings("unchecked")
  public static List<Object> iterate(Object iterable) throws CarrotException {
    if (iterable == null) {
      // Just iterate an empty list.
      return new ArrayList<>();
    } else if (iterable instanceof List) {
      return (List) iterable;
    } else if (iterable instanceof Collection) {
      return new ArrayList<>((Collection) iterable);
    } else if (iterable.getClass().isArray()) {
      int length = Array.getLength(iterable);
      ArrayList<Object> objects = new ArrayList<>(length);
      for (int i = 0; i < length; i++) {
        objects.add(Array.get(iterable, i));
      }
      return objects;
    }

    throw new CarrotException("Unable to iterate '" + iterable + "'");
  }
}
