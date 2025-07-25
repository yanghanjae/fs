package yhj.fs.dto;

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
public class StockDTO {

	private int stockNo;
	private int productNo;
	private Product product;
	private Storage storage;
	private int stockQuantity;
	private int storageNo;
}
