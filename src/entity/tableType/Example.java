package entity.tableType;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import entity.IEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Example implements IEntity {

	private String table;
	@JsonProperty("no")
	private String id;
	private String effectiveDate;
	private String tradingDate;
	private List<Rate> rates = null;
}
