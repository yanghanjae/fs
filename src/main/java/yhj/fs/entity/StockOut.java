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
@Table(name = "stock_out")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockOut {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_out_no", nullable = false)
	private int stockOutNo;
	
	@ManyToOne
	@JoinColumn(name = "product_no", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "storage_no", nullable = false)
	private Storage storage;
	
	@Column(name = "stock_out_quantity", nullable = false)
	private int stockOutQuantity;
	
	@Column(name = "stock_out_date", nullable = false, insertable = false, updatable = false)
	private LocalDate stockOutDate;
	
	@Column(name = "customer_name", length = 50)
	private String customerName;
}
