package yhj.fs.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import yhj.fs.entity.Product;
import yhj.fs.entity.Storage;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockInDTO {

	private int stockInNo;
	private int productNo;
	private Product product;
	private int storageNo;
	private Storage storage;
	private int stockInQuantity;
	private LocalDate stockInDate;
	private String supplierName;
}
