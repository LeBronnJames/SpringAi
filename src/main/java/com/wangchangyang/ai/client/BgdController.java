package com.wangchangyang.ai.client;

import com.wangchangyang.ai.services.BgdTools.BgdDetails;
import com.wangchangyang.ai.services.BgdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class BgdController {

	private final BgdService bgdService;

	public BgdController(BgdService bgdService) {
		this.bgdService = bgdService;
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/api/bgds")
	@ResponseBody
	public List<BgdDetails> getBgds() {
		return bgdService.getBgds();
	}

}
