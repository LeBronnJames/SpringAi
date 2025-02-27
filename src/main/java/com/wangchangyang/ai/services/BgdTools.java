package com.wangchangyang.ai.services;

import java.time.LocalDate;
import java.util.function.Function;

import com.wangchangyang.ai.data.Jgzt;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

@Configuration
public class BgdTools {

	private static final Logger logger = LoggerFactory.getLogger(BgdTools.class);

	@Autowired
	private BgdService bgdService;

	public record SearchBgdRequest(String bgdh) {
	}

	public record UpdateBgdCkfphRequest(String bgdh, String ckfph) {
	}

	public record DeleteBgdRequest(String bgdh) {
	}

	public record DownloadBgdRequest(String ckrqQ, String ckrqZ, String jgzt, String bgdlx) {
	}

	@JsonInclude(Include.NON_NULL)
	public record BgdDetails(String bgdh, LocalDate ckrq, String ckhth, String ckfph, Jgzt jgzt, String bgdlx) {
	}

	@Bean
	@Description("报关单信息查询")
	public Function<SearchBgdRequest, BgdDetails> getBgdDetails() {
		return request -> {
			try {
				return bgdService.getBgd(request.bgdh());
			}
			catch (Exception e) {
				logger.warn("Bgd details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
				return new BgdDetails(request.bgdh(), null, null, null, null, null);
			}
		};
	}

	@Bean
	@Description("报关单出口发票号补填")
	public Function<UpdateBgdCkfphRequest, String> updateBgd() {
		return request -> {
			bgdService.updateBgd(request.bgdh(), request.ckfph());
			return "";
		};
	}

	@Bean
	@Description("报关单信息删除")
	public Function<DeleteBgdRequest, String> deleteBgd() {
		return request -> {
			bgdService.deleteBgd(request.bgdh());
			return "";
		};
	}

	@Bean
	@Description("报关单数据下载")
	public Function<DownloadBgdRequest, String> downloadBgd() {
		return request -> {
			bgdService.downloadBgd(request.ckrqQ(), request.ckrqZ(), request.jgzt(), request.bgdlx());
			return "";
		};
	}
}
