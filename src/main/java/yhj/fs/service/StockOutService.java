package yhj.fs.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yhj.fs.dto.StockOutDTO;
import yhj.fs.entity.StockOut;
import yhj.fs.repository.ProductRepository;
import yhj.fs.repository.StockOutRepository;
import yhj.fs.repository.StorageRepository;

@Service
public class StockOutService {

	private final StockOutRepository STOCK_OUT_REPOSITORY;
	private final StorageRepository STORAGE_REPOSITORY;
	private final ProductRepository PRODUCT_REPOSITORY;
	private final ModelMapper modelMapper;
	
	public StockOutService(StockOutRepository SOR, StorageRepository SR,
			ProductRepository PR, ModelMapper MM) {
		this.STOCK_OUT_REPOSITORY = SOR;
		this.STORAGE_REPOSITORY = SR;
		this.PRODUCT_REPOSITORY = PR;
		this.modelMapper = MM;
	}
	
	// 창고 미지정시
	public Page<StockOutDTO> searchByName (int page, String search) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stockOutNo").descending());
		
		return STOCK_OUT_REPOSITORY.searchByName(search, pageable).map(i -> modelMapper.map(i, StockOutDTO.class));
	}
	
	// 창고 지정시
	public Page<StockOutDTO> search (int page, String search, int storageNo) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stockOutNo").descending());
		
		return STOCK_OUT_REPOSITORY.search(search, storageNo, pageable).map(i -> modelMapper.map(i, StockOutDTO.class));
	}
	
	@Transactional
	public void addStockOut (StockOutDTO stockout) {
		
		stockout.setProduct(PRODUCT_REPOSITORY.findById(stockout.getProductNo()).get());
		stockout.setStorage(STORAGE_REPOSITORY.findById(stockout.getStorageNo()).get());
		
		STOCK_OUT_REPOSITORY.save(modelMapper.map(stockout, StockOut.class));
	}
	
	// detail 페이지 (번호로 찾아서 하나 표현)
	public Optional<StockOutDTO> findbyStockOutNo (int stockOutNo) {
		
		return STOCK_OUT_REPOSITORY.findById(stockOutNo).map(i -> modelMapper.map(i, StockOutDTO.class));
	}
}
