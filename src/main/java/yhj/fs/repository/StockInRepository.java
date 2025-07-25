package yhj.fs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yhj.fs.entity.StockIn;

@Repository
public interface StockInRepository extends JpaRepository<StockIn, Integer> {

	
	// 창고 미지정시 상품명으로 검색
	@Query(value = "SELECT s "
			+ "FROM StockIn s "
			+ "JOIN s.product p "
			+ "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) ")
	Page<StockIn> searchByName (@Param(value = "search") String search, Pageable pageable);
	
	// 창고 지정시 상품명으로 검색
	@Query(value = "SELECT s "
			+ "FROM StockIn s "
			+ "JOIN s.product p "
			+ "JOIN s.storage st "
			+ "WHERE (LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))) "
			+ "AND (st.storageNo = :storageNo) ")
	Page<StockIn> search (@Param(value = "search") String search, @Param(value = "storageNo") Integer storageNo, Pageable pageable);
		
}
