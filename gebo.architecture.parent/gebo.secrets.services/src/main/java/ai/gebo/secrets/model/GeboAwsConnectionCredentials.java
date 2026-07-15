package ai.gebo.secrets.model;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GeboAwsConnectionCredentials extends AbstractGeboSecretContent {
	@AllArgsConstructor
	public static enum AwsRegion {

		ME_CENTRAL_1("me-central-1"), AWS_CN_GLOBAL("aws-cn-global"), US_ISOF_SOUTH_1("us-isof-south-1"),
		AP_EAST_2("ap-east-2"), US_WEST_1("us-west-1"), US_WEST_2("us-west-2"), AF_SOUTH_1("af-south-1"),
		AP_NORTHEAST_3("ap-northeast-3"), AP_NORTHEAST_2("ap-northeast-2"), AP_NORTHEAST_1("ap-northeast-1"),
		ME_SOUTH_1("me-south-1"), SA_EAST_1("sa-east-1"), CN_NORTH_1("cn-north-1"), AP_SOUTHEAST_1("ap-southeast-1"),
		AP_SOUTHEAST_2("ap-southeast-2"), AP_SOUTHEAST_3("ap-southeast-3"), AP_SOUTHEAST_4("ap-southeast-4"),
		AP_SOUTHEAST_5("ap-southeast-5"), US_EAST_1("us-east-1"), AP_SOUTHEAST_6("ap-southeast-6"),
		US_EAST_2("us-east-2"), AP_SOUTHEAST_7("ap-southeast-7"), CN_NORTHWEST_1("cn-northwest-1"),
		AWS_ISO_E_GLOBAL("aws-iso-e-global"), AP_SOUTH_2("ap-south-2"), AP_SOUTH_1("ap-south-1"),
		EU_SOUTH_1("eu-south-1"), EU_SOUTH_2("eu-south-2"), US_GOV_EAST_1("us-gov-east-1"),
		IL_CENTRAL_1("il-central-1"), CA_CENTRAL_1("ca-central-1"), MX_CENTRAL_1("mx-central-1"),
		EU_CENTRAL_1("eu-central-1"), EUSC_DE_EAST_1("eusc-de-east-1"), US_ISO_WEST_1("us-iso-west-1"),
		EU_CENTRAL_2("eu-central-2"), EU_ISOE_WEST_1("eu-isoe-west-1"), AWS_GLOBAL("aws-global"),
		US_ISOB_WEST_1("us-isob-west-1"), EU_NORTH_1("eu-north-1"), EU_WEST_3("eu-west-3"), EU_WEST_2("eu-west-2"),
		EU_WEST_1("eu-west-1"), AWS_ISO_GLOBAL("aws-iso-global"), AP_EAST_1("ap-east-1"), CA_WEST_1("ca-west-1"),
		US_GOV_WEST_1("us-gov-west-1"), US_ISO_EAST_1("us-iso-east-1"), AWS_ISO_B_GLOBAL("aws-iso-b-global"),
		AWS_ISO_F_GLOBAL("aws-iso-f-global"), US_ISOB_EAST_1("us-isob-east-1"), AWS_US_GOV_GLOBAL("aws-us-gov-global"),
		US_ISOF_EAST_1("us-isof-east-1");

		private final String code;

		/**
		 * The AWS region id (e.g. {@code us-east-1}). Annotated with
		 * {@link JsonValue} so the region is serialized/deserialized by its AWS
		 * region code on the wire (and in the encrypted secret vault) instead of the
		 * Java enum constant name, keeping the REST contract aligned with the AWS
		 * region identifiers.
		 *
		 * @return the AWS region code
		 */
		@JsonValue
		public String getCode() {
			return code;
		}

		@Override
		public String toString() {
			return code;
		}
	}
	@NotNull
	private String accessKeyId;// The AWS access key, used to identify the user interacting with AWS.
	@NotNull
	private String secretAccessKey;// The AWS secret access key, used to authenticate the user interacting with
									// AWS.
	@NotNull
	private AwsRegion region;

	@Override
	public GeboSecretType type() {

		return GeboSecretType.AWS_CONNECTION;
	}

}
