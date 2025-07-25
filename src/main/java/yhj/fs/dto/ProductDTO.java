package yhj.fs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private int productNo;
	private String productName;
	private String productImgUrl;
	private String productUnit;
	private int productPrice;
	private CategoryDTO category;
	private int categoryNo;
}
