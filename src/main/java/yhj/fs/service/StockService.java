package yhj.fs.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yhj.fs.dto.StockDTO;
import yhj.fs.entity.Stock;
import yhj.fs.repository.ProductRepository;
import yhj.fs.repository.StockRepository;
import yhj.fs.repository.StorageRepository;

@Service
public class StockService {

	private final StockRepository STOCK_REPOSITORY;
	private final StorageRepository STORAGE_REPOSITORY;
	private final ProductRepository PRODUCT_REPOSITORY;
	private final ModelMapper modelMapper;
	
	public StockService(StockRepository SR, StorageRepository StorageR, 
			ProductRepository PR, ModelMapper MM) {
		this.STOCK_REPOSITORY = SR;
		this.STORAGE_REPOSITORY = StorageR;
		this.PRODUCT_REPOSITORY = PR;
		this.modelMapper = MM;
	}
	
	// 창고 미지정시
	public Page<StockDTO> searchStockByName (int page, String search) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stockNo").descending());
		
		return STOCK_REPOSITORY.searchByName(search, pageable).map(i -> modelMapper.map(i, StockDTO.class));
		
	}
	
	// 창고 지정시
	public Page<StockDTO> searchStock (int page, String search, int storageNo) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stockNo").descending());
		
		return STOCK_REPOSITORY.search(search, storageNo, pageable).map(i -> modelMapper.map(i, StockDTO.class));
	}
	
	// 특정 재고 찾기
	public Optional<StockDTO> findByStockNo(int stockNo) {
		
		return STOCK_REPOSITORY.findById(stockNo).map(i -> modelMapper.map(i, StockDTO.class));
	}
	
	// 상품과 창고번호로 재고의 여부 확인 (입고, 출고 관리 기능에서 사용)
	public Optional<StockDTO> checkStockExistence (int productNo, int storageNo) {
		
		return STOCK_REPOSITORY.checkStockExistence(productNo, storageNo).map(i -> modelMapper.map(i, StockDTO.class));
	}
	
	@Transactional
	public void addStock (StockDTO stock) {
		
		stock.setStorage(STORAGE_REPOSITORY.findById(stock.getStorageNo()).get());
		stock.setProduct(PRODUCT_REPOSITORY.findById(stock.getProductNo()).get());
		STOCK_REPOSITORY.save(modelMapper.map(stock, Stock.class));
	}
	
	// 재고 수량 변경
	@Transactional
	public void changeStockQuantity (StockDTO stock, int quantity) {
		
		Stock foundStock = STOCK_REPOSITORY.findById(stock.getStockNo())
				.orElseThrow(()-> new IllegalArgumentException("해당 재고가 존재하지 않습니다."));
		
		foundStock.setStockQuantity(foundStock.getStockQuantity() + quantity);
		
		
		// 수량이 0일시, 목록에서 제거.
		if(foundStock.getStockQuantity() == 0) {
			STOCK_REPOSITORY.delete(foundStock);
		}
	}
}
