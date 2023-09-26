package in.pr.Modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADRESS")
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String firtName;
	private String lastName;
	private String streeetAddress;
	private String city;
	private String state;
	private String zipcode;
	private String mobile;
	
	
	@ManyToOne
	@JoinColumn(name = "user_Id")
	@JsonIgnore
	private User user;
	
	
	
}
