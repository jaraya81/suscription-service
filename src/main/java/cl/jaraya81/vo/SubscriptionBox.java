package cl.jaraya81.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Builder
@Data
@ToString
public class SubscriptionBox implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4378222056172283843L;

	private String secret;

	@Tolerate
	private SubscriptionBox() {
		super();
	}
	

}
