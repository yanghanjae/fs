package yhj.fs.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "stock_in")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_in_no", nullable = false)
	private int stockInNo;
	
	@ManyToOne
	@JoinColumn(name = "product_no", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "storage_no", nullable = false)
	private Storage storage;
	
	@Column(name = "stock_in_quantity", nullable = false)
	private int stockInQuantity;
	
	@Column(name = "stock_in_date" ,nullable = false, insertable = false, updatable = false)
	private LocalDate stockInDate;
	
	@Column(name = "supplier_name", length = 50)
	private String supplierName;
}
