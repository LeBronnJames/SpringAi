package com.ddit.saas.ai.data;

import java.time.LocalDate;

public class Bgd {

	private String bgdh;

	private LocalDate ckrq;

	private String ckhth;

	private String ckfph;

	private Jgzt jgzt;

	private Bgdlx bgdlx;

	public Bgd(String bgdh, LocalDate ckrq, String ckhth, String ckfph, Jgzt jgzt, Bgdlx bgdlx) {
		this.bgdh = bgdh;
		this.ckrq = ckrq;
		this.ckhth = ckhth;
		this.ckfph = ckfph;
		this.jgzt = jgzt;
		this.bgdlx = bgdlx;
	}

	public String getBgdh() {
		return bgdh;
	}

	public void setBgdh(String bgdh) {
		this.bgdh = bgdh;
	}

	public LocalDate getCkrq() {
		return ckrq;
	}

	public void setCkrq(LocalDate ckrq) {
		this.ckrq = ckrq;
	}

	public String getCkhth() {
		return ckhth;
	}

	public void setCkhth(String ckhth) {
		this.ckhth = ckhth;
	}

	public String getCkfph() {
		return ckfph;
	}

	public void setCkfph(String ckfph) {
		this.ckfph = ckfph;
	}

	public Jgzt getJgzt() {
		return jgzt;
	}

	public void setJgzt(Jgzt jgzt) {
		this.jgzt = jgzt;
	}

	public Bgdlx getBgdlx() {
		return bgdlx;
	}

	public void setBgdlx(Bgdlx bgdlx) {
		this.bgdlx = bgdlx;
	}
}