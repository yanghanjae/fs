package yhj.fs.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yhj.fs.common.Pagenation;
import yhj.fs.common.PagingButtonInfo;
import yhj.fs.dto.StorageDTO;
import yhj.fs.service.StorageService;

@Controller
@RequestMapping("/storage")
public class StorageController {

	private final StorageService STORAGE_SERVICE;
	
	public StorageController(StorageService SS) {
		this.STORAGE_SERVICE = SS;
	}
	
	@GetMapping("")
	public String storage(Model model, @RequestParam (defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String search ) {
		
		Page<StorageDTO> storage = STORAGE_SERVICE.searchStorageByName(page, search);
		
		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(storage);
		
		model.addAttribute("storage", storage);
		model.addAttribute("paging", paging);
		model.addAttribute("search", search);
		
		return "storage/storage";
	}
	
	@GetMapping("/add")
	public String addStorage () {
		
		return "storage/addStorage";
	}
	
	@PostMapping("/add")
	public String addNewStorage (StorageDTO storage, Model model) {
		
		// 창고 이름 중복검사
		if(STORAGE_SERVICE.findByStorageName(storage.getStorageName()).isPresent()) {
			model.addAttribute("error", "이미 존재하는 창고명입니다.");
			return "storage/addStorage";
		} else {
			
			STORAGE_SERVICE.addStorage(storage);
			return "redirect:/storage";
		}
	}
	
	@PostMapping("/delete")
	public String deleteStorage (@RequestParam int storageNo) {
		
		STORAGE_SERVICE.deleteStorage(storageNo);
		return "redirect:/storage";
	}
}
