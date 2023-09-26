package in.pr.Modal;

import java.time.LocalDate;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PaymentInformations")
public class PaymentInformation {

	private String cardHolderName;
	private String cardNumber;
	private LocalDate expirationDate;
	private String cvv;
}
