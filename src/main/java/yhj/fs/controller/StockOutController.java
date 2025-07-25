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
import yhj.fs.dto.StockOutDTO;
import yhj.fs.service.CategoryService;
import yhj.fs.service.ProductService;
import yhj.fs.service.StockInService;
import yhj.fs.service.StockOutService;
import yhj.fs.service.StockService;
import yhj.fs.service.StorageService;

@Controller
@RequestMapping("/stockOut")
public class StockOutController {
	
	private final StockOutService STOCK_OUT_SERVICE;
	private final StockInService STOCK_IN_SERVICE;
	private final StorageService STORAGE_SERVICE;
	private final StockService STOCK_SERVICE;
	
	public StockOutController(StockOutService SOS, StockInService SIS,
			StorageService SS, ProductService PS, 
			CategoryService CS, StockService StockS) {
		this.STOCK_OUT_SERVICE = SOS;
		this.STOCK_IN_SERVICE = SIS;
		this.STORAGE_SERVICE = SS;
		this.STOCK_SERVICE = StockS;
	}

	
	@GetMapping("")
	public String stockOut(Model model, @RequestParam (defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String search, @RequestParam(required = false) Integer storageNo) {
		
		Page<StockOutDTO> stockOut = STOCK_OUT_SERVICE.searchByName(page, search);
		
		if (storageNo != null) {
			model.addAttribute("selectedStorage", storageNo);
			stockOut = STOCK_OUT_SERVICE.search(page, search, storageNo);
		}
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(stockOut);
		
		model.addAttribute("stockOut", stockOut);
		model.addAttribute("paging", paging);
		model.addAttribute("storage", STORAGE_SERVICE.findAllStorage());
		
		model.addAttribute("search", search);
		
		return "stockOut/stockOut";
	}
	
	@ResponseBody
	@GetMapping("/{stockOutNo}")
	public StockOutDTO stockOutDetail (@PathVariable int stockOutNo) {
		
		return STOCK_OUT_SERVICE.findbyStockOutNo(stockOutNo).orElse(new StockOutDTO());
	}
	
	@GetMapping("/add")
	public String addStockOut (Model model) {
		
		return "stockOut/addStockOut";
	}
	
		
	@PostMapping("/add")
	public String addNewStockOut(StockOutDTO stockOut) {
		
		STOCK_OUT_SERVICE.addStockOut(stockOut);
		STOCK_SERVICE.changeStockQuantity(STOCK_SERVICE.checkStockExistence(stockOut.getProductNo(), stockOut.getStorageNo()).get(), - stockOut.getStockOutQuantity());
		
		
		return "redirect:/stockOut";
	}
	
	@GetMapping("/move")
	public String moveStock (Model model) {
		
		model.addAttribute("storage", STORAGE_SERVICE.findAllStorage());
		
		return "stockOut/moveStock";
	}
	
	//재고 이동
	// stockOut + stockIn을 각각 실행합니다.
	@PostMapping("move")
	public String moveStocks (StockOutDTO stockOut) {
		
		STOCK_OUT_SERVICE.addStockOut(stockOut);
		STOCK_SERVICE.changeStockQuantity(STOCK_SERVICE.checkStockExistence(stockOut.getProductNo(), stockOut.getStorageNo()).get(), - stockOut.getStockOutQuantity());
		
		StockInDTO stockIn = new StockInDTO();
		stockIn.setProductNo(stockOut.getProductNo());
		stockIn.setStorageNo(STORAGE_SERVICE.findByStorageName(stockOut.getCustomerName()).get().getStorageNo());
		stockIn.setStockInQuantity(stockOut.getStockOutQuantity());
		stockIn.setSupplierName(STORAGE_SERVICE.findByStorageNo(stockOut.getStorageNo()).get().getStorageName());
		
		STOCK_IN_SERVICE.addStockIn(stockIn);
		
		// 입고 하려는 상품이 해당 창고에 있을경우, 수량만 증가. 없을 시, 새 재고를 추가.
		
		Optional<StockDTO> stock = STOCK_SERVICE.checkStockExistence(stockIn.getProductNo(), stockIn.getStorageNo());
		
		if(stock.isPresent()) {
			STOCK_SERVICE.changeStockQuantity(stock.get(), stockIn.getStockInQuantity());
		} else {
			StockDTO newStock = new StockDTO();
			newStock.setProductNo(stockIn.getProductNo());
			newStock.setStorageNo(stockIn.getStorageNo());
			newStock.setStockQuantity(stockIn.getStockInQuantity());
			STOCK_SERVICE.addStock(newStock);
		}
		
		return "redirect:/stockOut";
	}
}
