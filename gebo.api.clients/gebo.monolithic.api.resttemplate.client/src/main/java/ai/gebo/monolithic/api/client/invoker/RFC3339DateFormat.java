package ai.gebo.monolithic.api.client.invoker;

import java.text.FieldPosition;
import java.util.Date;
import tools.jackson.databind.util.StdDateFormat;

public class RFC3339DateFormat extends StdDateFormat {

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    toAppendTo.append(date.toInstant().toString());
    return toAppendTo;
  }

}
