package yhj.fs.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yhj.fs.common.Pagenation;
import yhj.fs.common.PagingButtonInfo;
import yhj.fs.dto.StockDTO;
import yhj.fs.dto.StockInDTO;
import yhj.fs.service.CategoryService;
import yhj.fs.service.ProductService;
import yhj.fs.service.StockInService;
import yhj.fs.service.StockService;
import yhj.fs.service.StorageService;

@Controller
@RequestMapping("/stockIn")
public class StockInController {
	
	private final StockInService STOCK_IN_SERVICE;
	private final StorageService STORAGE_SERVICE;
	private final StockService STOCK_SERVICE;
	
	public StockInController(StockInService SIS, StorageService SS, 
			ProductService PS, CategoryService CS, 
			StockService StockS) {
		this.STOCK_IN_SERVICE = SIS;
		this.STORAGE_SERVICE = SS;
		this.STOCK_SERVICE = StockS;
	}

	
	@GetMapping("")
	public String stockIn(Model model, @RequestParam (defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String search, @RequestParam(required = false) Integer storageNo) {
		
		Page<StockInDTO> stockIn = STOCK_IN_SERVICE.searchByName(page, search);
		
		if (storageNo != null) {
			model.addAttribute("selectedStorage", storageNo);
			stockIn = STOCK_IN_SERVICE.search(page, search, storageNo);
		}
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(stockIn);
		
		model.addAttribute("stockIn", stockIn);
		model.addAttribute("paging", paging);
		model.addAttribute("storage", STORAGE_SERVICE.findAllStorage());
		
		model.addAttribute("search", search);
		
		return "stockIn/stockIn";
	}
	
	@ResponseBody
	@GetMapping("/{stockInNo}")
	public StockInDTO stockInDetail (@PathVariable int stockInNo) {
		
		return STOCK_IN_SERVICE.findbyStockInNo(stockInNo).orElse(new StockInDTO());
	}
	
	@GetMapping("/add")
	public String addStockIn (Model model) {
		
		model.addAttribute("storage", STORAGE_SERVICE.findAllStorage());
		
		return "stockIn/addStockIn";
	}
	
	
	@PostMapping("/add")
	public String addNewStockIn(StockInDTO stockIn) {
		
		STOCK_IN_SERVICE.addStockIn(stockIn);
		
		Optional<StockDTO> stock = STOCK_SERVICE.checkStockExistence(stockIn.getProductNo(), stockIn.getStorageNo());
		
		// 입고 하려는 상품이 해당 창고에 있을경우, 수량만 증가. 없을 시, 새 재고를 추가.
		if(stock.isPresent()) {
			STOCK_SERVICE.changeStockQuantity(stock.get(), stockIn.getStockInQuantity());
		} else {
			StockDTO newStock = new StockDTO();
			newStock.setProductNo(stockIn.getProductNo());
			newStock.setStorageNo(stockIn.getStorageNo());
			newStock.setStockQuantity(stockIn.getStockInQuantity());
			STOCK_SERVICE.addStock(newStock);
		}
		
		return "redirect:/stockIn";
	}
	
	
}
