package in.pr.Modal;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SIZE")
public class Size {

	private String name;
	private Integer quantity;
}


// there is no @Entity and Id because its a @embeded