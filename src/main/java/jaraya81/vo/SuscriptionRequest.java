package jaraya81.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class SuscriptionRequest implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4378222056172283843L;

	private String user;

}
