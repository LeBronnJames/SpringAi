package com.ddit.saas.ai.services;

import com.ddit.saas.ai.data.*;
import com.ddit.saas.ai.services.BgdTools.BgdDetails;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BgdService {

	private final BgdData db;

	public BgdService() {
		db = new BgdData();

		initDemoData();
	}

	private void initDemoData() {
		List<String> bgdhs = List.of("111111111111111111", "222222222222222222", "333333333333333333", "444444444444444444", "555555555555555555");
		List<String> ckhths = List.of("CKHTH001", "CKHTH002", "CKHTH003", "CKHTH004");
		Random random = new Random();

		var bgds = new ArrayList<Bgd>();

		for (int i = 0; i < 5; i++) {
			String bgdh = bgdhs.get(i);
			LocalDate ckrq = LocalDate.now().plusDays(2 * (i + 1));
			String ckhth = "";
			if(i != 4){
				ckhth = ckhths.get(i);
			}
			Jgzt jgzt = Jgzt.values()[random.nextInt(Jgzt.values().length)];
			Bgdlx bgdlx = Bgdlx.values()[random.nextInt(Bgdlx.values().length)];

			Bgd bgd = new Bgd(bgdh, ckrq, ckhth, "", jgzt, bgdlx);
			bgds.add(bgd);
		}

		// Reset the database on each start
		db.setBgds(bgds);
	}

	private Bgd findBgd(String bgdh) {
		return db.getBgds()
				.stream()
				.filter(b -> b.getBgdh().equalsIgnoreCase(bgdh))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Bgd not found"));
	}

	private BgdDetails toBgdDetails(Bgd bgd) {
		return new BgdDetails(bgd.getBgdh(), bgd.getCkrq(), bgd.getCkhth(), bgd.getCkfph(), bgd.getJgzt(), bgd.getBgdlx().toString());
	}

	private LocalDate getRandomDateBetween(String startDateStr, String endDateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		long randomDaysToAdd = new Random().nextLong(daysBetween + 1);
		return startDate.plusDays(randomDaysToAdd);
	}

	public List<BgdDetails> getBgds() {
		return db.getBgds().stream().map(this::toBgdDetails).toList();
	}

	public BgdDetails getBgd(String bgdh) {
		var bgd = findBgd(bgdh);
		return toBgdDetails(bgd);
	}

	public void updateBgd(String bgdh, String ckfph) {
		var bgd = findBgd(bgdh);
		bgd.setCkfph(ckfph);
	}

	public void deleteBgd(String bgdh) {
		db.getBgds().remove(findBgd(bgdh));
	}

	public void downloadBgd(String ckrqQ, String ckrqZ, String jgzt, String bgdlx) {

		List<String> bgdhs = List.of("666666666666666666", "777777777777777777", "888888888888888888", "999999999999999999");
		List<String> ckhths = List.of("CKHTH005", "CKHTH006", "CKHTH007", "CKHTH008");
		Random random = new Random();

		var bgds = db.getBgds();

		for (int i = 0; i < 4; i++) {
			String bgdh = bgdhs.get(i);
			String ckhth = ckhths.get(i);
			LocalDate ckrq = getRandomDateBetween(ckrqQ, ckrqZ);
			Jgzt jgzt1 = StringUtils.isNotEmpty(jgzt) ? Jgzt.valueOf(jgzt) : Jgzt.values()[random.nextInt(Jgzt.values().length)];
			Bgdlx bgdlx1 = StringUtils.isNotEmpty(bgdlx) ? Bgdlx.valueOf(bgdlx) : Bgdlx.values()[random.nextInt(Bgdlx.values().length)];

			Bgd bgd = new Bgd(bgdh, ckrq, ckhth, "", jgzt1, bgdlx1);
			bgds.add(bgd);
		}

		db.setBgds(bgds);

	}

}
