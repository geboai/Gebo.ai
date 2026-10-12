package ai.gebo.llms.abstraction.layer.model;

import lombok.Data;

/** A currency of the ISO 4217 list bundled with Gebo.ai. */
@Data
public class GCurrency {
	/** ISO 4217 alphabetic code, e.g. EUR. */
	private String code = null;
	private String name = null;
	/** ISO 4217 numeric code, e.g. 978. */
	private String numericCode = null;
	/** Digits after the decimal separator, e.g. 2. */
	private Integer minorUnits = null;
}
