/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.model;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI generated comments
 * The number of expensive operations executed while evaluating a Jira expression.
 * This class extends JiraExpressionsComplexityValueBean to specifically track
 * expensive operations which are operations that load additional data such as
 * entity properties, comments, or custom fields.
 */
@Schema(description = "The number of expensive operations executed while evaluating the expression. Expensive operations are those that load additional data, such as entity properties, comments, or custom fields.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class AllOfJiraExpressionsComplexityBeanExpensiveOperations extends JiraExpressionsComplexityValueBean {

  /**
   * Compares this instance with another object for equality.
   * Two instances are considered equal if they are the same instance or
   * if they are of the same class and their superclass equals method returns true.
   *
   * @param o the object to compare with
   * @return true if this object is equal to the specified object
   */
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return super.equals(o);
  }

  /**
   * Computes the hash code for this instance.
   * The hash code is based on the superclass's hash code.
   *
   * @return the hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  /**
   * Returns a string representation of this object.
   * Includes the class name and the superclass's string representation.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllOfJiraExpressionsComplexityBeanExpensiveOperations {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}