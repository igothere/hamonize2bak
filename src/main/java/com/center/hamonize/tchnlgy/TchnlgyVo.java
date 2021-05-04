package com.model;

public class TchnlgyVo {
	
	private int tcng_seq;
	private String tcng_org_seq;
	private int tcng_sgb_seq;
	private int tcng_sol_seq;
	private String tcng_title;
	private String tcng_content;
	private String tcng_insert_dt;
	private String tcng_update_dt;
	private String tcng_type;
	private String tcng_type_detail;
	private String tcng_status;
	private String org_nm;
	private String user_name;
	private String user_id;
	private String tbl_cnt;
	private String complete;
	private String progress;
	private String receipt;
	private String tel_num;
	private String state;
	private String date_fr;
	private String date_to;
	
	private String admin_info;
	private String as_content;
	
	// search ====
	private String txtSearch;
	private String keyWord;

	private int mngeListInfoCurrentPage;
	
	// insert
	private String sportOrg;
	private String sportSgb;
	private String sportSoldier;
	private String sportTitle;
	private String sportContent;
	private String sportType;
	private String sportType_detail;
	private String sportStatus;
	public int getTcng_seq() {
		return tcng_seq;
	}
	public void setTcng_seq(int tcng_seq) {
		this.tcng_seq = tcng_seq;
	}
	public String getTcng_org_seq() {
		return tcng_org_seq;
	}
	public void setTcng_org_seq(String tcng_org_seq) {
		this.tcng_org_seq = tcng_org_seq;
	}
	public int getTcng_sgb_seq() {
		return tcng_sgb_seq;
	}
	public void setTcng_sgb_seq(int tcng_sgb_seq) {
		this.tcng_sgb_seq = tcng_sgb_seq;
	}
	public int getTcng_sol_seq() {
		return tcng_sol_seq;
	}
	public void setTcng_sol_seq(int tcng_sol_seq) {
		this.tcng_sol_seq = tcng_sol_seq;
	}
	public String getTcng_title() {
		return tcng_title;
	}
	public void setTcng_title(String tcng_title) {
		this.tcng_title = tcng_title;
	}
	public String getTcng_content() {
		return tcng_content;
	}
	public void setTcng_content(String tcng_content) {
		this.tcng_content = tcng_content;
	}
	public String getTcng_insert_dt() {
		return tcng_insert_dt;
	}
	public void setTcng_insert_dt(String tcng_insert_dt) {
		this.tcng_insert_dt = tcng_insert_dt;
	}
	public String getTcng_update_dt() {
		return tcng_update_dt;
	}
	public void setTcng_update_dt(String tcng_update_dt) {
		this.tcng_update_dt = tcng_update_dt;
	}
	public String getTcng_type() {
		return tcng_type;
	}
	public void setTcng_type(String tcng_type) {
		this.tcng_type = tcng_type;
	}
	public String getTcng_status() {
		return tcng_status;
	}
	public void setTcng_status(String tcng_status) {
		this.tcng_status = tcng_status;
	}
	public String getTxtSearch() {
		return txtSearch;
	}
	public void setTxtSearch(String txtSearch) {
		this.txtSearch = txtSearch;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getMngeListInfoCurrentPage() {
		return mngeListInfoCurrentPage;
	}
	public void setMngeListInfoCurrentPage(int mngeListInfoCurrentPage) {
		this.mngeListInfoCurrentPage = mngeListInfoCurrentPage;
	}
	public String getSportOrg() {
		return sportOrg;
	}
	public void setSportOrg(String sportOrg) {
		this.sportOrg = sportOrg;
	}
	public String getSportSgb() {
		return sportSgb;
	}
	public void setSportSgb(String sportSgb) {
		this.sportSgb = sportSgb;
	}
	public String getSportSoldier() {
		return sportSoldier;
	}
	public void setSportSoldier(String sportSoldier) {
		this.sportSoldier = sportSoldier;
	}
	public String getSportTitle() {
		return sportTitle;
	}
	public void setSportTitle(String sportTitle) {
		this.sportTitle = sportTitle;
	}
	public String getSportContent() {
		return sportContent;
	}
	public void setSportContent(String sportContent) {
		this.sportContent = sportContent;
	}
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public String getSportStatus() {
		return sportStatus;
	}
	public void setSportStatus(String sportStatus) {
		this.sportStatus = sportStatus;
	}
	public String getOrg_nm() {
		return org_nm;
	}
	public void setOrg_nm(String org_nm) {
		this.org_nm = org_nm;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTcng_type_detail() {
		return tcng_type_detail;
	}
	public void setTcng_type_detail(String tcng_type_detail) {
		this.tcng_type_detail = tcng_type_detail;
	}
	public String getSportType_detail() {
		return sportType_detail;
	}
	public void setSportType_detail(String sportType_detail) {
		this.sportType_detail = sportType_detail;
	}
	public String getTbl_cnt() {
		return tbl_cnt;
	}
	public void setTbl_cnt(String tbl_cnt) {
		this.tbl_cnt = tbl_cnt;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDate_fr() {
		return date_fr;
	}
	public void setDate_fr(String date_fr) {
		this.date_fr = date_fr;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public String getAdmin_info() {
		return admin_info;
	}
	public void setAdmin_info(String admin_info) {
		this.admin_info = admin_info;
	}
	public String getAs_content() {
		return as_content;
	}
	public void setAs_content(String as_content) {
		this.as_content = as_content;
	}
	
}
