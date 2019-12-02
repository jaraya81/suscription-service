package cl.jaraya81.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class SubscriptionResponse implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2814913714292522661L;

	private String user;
	private Boolean valid;
	private LocalDateTime expiration;
	private String message;
	private String secret;

}
