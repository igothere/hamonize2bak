package com.center.hamonize.pcmngr;

import java.util.Arrays;

public class PcMangrVo {

	private int sgb_seq;
	private String sgb_pc_status;
	private String sgb_pc_cpu;
	private String sgb_pc_cpu_id;
	private String sgb_pc_memory;
	private String sgb_pc_disk;
	private String sgb_pc_disk_id;
	private String sgb_pc_macaddress;
	private String sgb_pc_ip;
	private String sgb_pc_vpnip;
	private String sgb_pc_hostname;
	private String sgb_pc_guid;
	private String sgb_pc_uuid;
	private String sgb_pc_os;
	private String pc_change;
	private String cpu_info;
	private String ram_info;
	private String hdd_info;
	private String mboard_info;
	private String etc_info;
	private String status;
	private String maxSgbPcCntByorgSeq;

	private String old_pc_ip;
	private String old_pc_vpnip;
	private String old_pc_macaddr;
	
	private int seq;
	private String sttus;
	private String org_sgb_room_name;
	private String sgb_public_ip;

	@Override
	public String toString() {
		return "PcMangrVo [sgb_seq=" + sgb_seq + ", sgb_pc_status=" + sgb_pc_status + ", sgb_pc_cpu=" + sgb_pc_cpu
				+ ", sgb_pc_cpu_id=" + sgb_pc_cpu_id + ", sgb_pc_memory=" + sgb_pc_memory + ", sgb_pc_disk="
				+ sgb_pc_disk + ", sgb_pc_disk_id=" + sgb_pc_disk_id + ", sgb_pc_macaddress=" + sgb_pc_macaddress
				+ ", sgb_pc_ip=" + sgb_pc_ip + ", sgb_pc_vpnip=" + sgb_pc_vpnip + ", sgb_pc_hostname=" + sgb_pc_hostname
				+ ", sgb_pc_guid=" + sgb_pc_guid + ", sgb_pc_uuid=" + sgb_pc_uuid + ", sgb_pc_os=" + sgb_pc_os
				+ ", pc_change=" + pc_change + ", cpu_info=" + cpu_info + ", ram_info=" + ram_info + ", hdd_info="
				+ hdd_info + ", mboard_info=" + mboard_info + ", etc_info=" + etc_info + ", status=" + status
				+ ", maxSgbPcCntByorgSeq=" + maxSgbPcCntByorgSeq + ", old_pc_ip=" + old_pc_ip + ", old_pc_vpnip="
				+ old_pc_vpnip + ", old_pc_macaddr=" + old_pc_macaddr + ", seq=" + seq + ", sttus=" + sttus
				+ ", org_sgb_room_name=" + org_sgb_room_name + ", sgb_public_ip=" + sgb_public_ip + ", first_date="
				+ first_date + ", last_date=" + last_date + ", sgbname=" + sgbname + ", allsgbname=" + allsgbname
				+ ", sgbsido=" + sgbsido + ", sgbpcname=" + sgbpcname + ", org_seq=" + org_seq + ", move_org_seq="
				+ move_org_seq + ", org_nm=" + org_nm + ", move_org_nm=" + move_org_nm + ", updateBlockList="
				+ Arrays.toString(updateBlockList) + ", updateUnblockList=" + Arrays.toString(updateUnblockList)
				+ ", txtSearch=" + txtSearch + ", keyWord=" + keyWord + ", selectOrgSeq=" + selectOrgSeq
				+ ", pcListInfoCurrentPage=" + pcListInfoCurrentPage + ", date_fr=" + date_fr + ", date_to=" + date_to
				+ "]";
	}

	public int getSgb_seq() {
		return sgb_seq;
	}

	public void setSgb_seq(int sgb_seq) {
		this.sgb_seq = sgb_seq;
	}

	public String getSgb_pc_status() {
		return sgb_pc_status;
	}

	public void setSgb_pc_status(String sgb_pc_status) {
		this.sgb_pc_status = sgb_pc_status;
	}

	public String getSgb_pc_cpu() {
		return sgb_pc_cpu;
	}

	public void setSgb_pc_cpu(String sgb_pc_cpu) {
		this.sgb_pc_cpu = sgb_pc_cpu;
	}

	public String getSgb_pc_cpu_id() {
		return sgb_pc_cpu_id;
	}

	public void setSgb_pc_cpu_id(String sgb_pc_cpu_id) {
		this.sgb_pc_cpu_id = sgb_pc_cpu_id;
	}

	public String getSgb_pc_memory() {
		return sgb_pc_memory;
	}

	public void setSgb_pc_memory(String sgb_pc_memory) {
		this.sgb_pc_memory = sgb_pc_memory;
	}

	public String getSgb_pc_disk() {
		return sgb_pc_disk;
	}

	public void setSgb_pc_disk(String sgb_pc_disk) {
		this.sgb_pc_disk = sgb_pc_disk;
	}

	public String getSgb_pc_disk_id() {
		return sgb_pc_disk_id;
	}

	public void setSgb_pc_disk_id(String sgb_pc_disk_id) {
		this.sgb_pc_disk_id = sgb_pc_disk_id;
	}

	public String getSgb_pc_macaddress() {
		return sgb_pc_macaddress;
	}

	public void setSgb_pc_macaddress(String sgb_pc_macaddress) {
		this.sgb_pc_macaddress = sgb_pc_macaddress;
	}

	public String getSgb_pc_ip() {
		return sgb_pc_ip;
	}

	public void setSgb_pc_ip(String sgb_pc_ip) {
		this.sgb_pc_ip = sgb_pc_ip;
	}

	public String getSgb_pc_hostname() {
		return sgb_pc_hostname;
	}

	public void setSgb_pc_hostname(String sgb_pc_hostname) {
		this.sgb_pc_hostname = sgb_pc_hostname;
	}

	public String getSgb_pc_guid() {
		return sgb_pc_guid;
	}

	public void setSgb_pc_guid(String sgb_pc_guid) {
		this.sgb_pc_guid = sgb_pc_guid;
	}

	public String getSgb_pc_uuid() {
		return sgb_pc_uuid;
	}

	public void setSgb_pc_uuid(String sgb_pc_uuid) {
		this.sgb_pc_uuid = sgb_pc_uuid;
	}

	public String getFirst_date() {
		return first_date;
	}

	public void setFirst_date(String first_date) {
		this.first_date = first_date;
	}

	public String getLast_date() {
		return last_date;
	}

	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}

	public String getSgbname() {
		return sgbname;
	}

	public void setSgbname(String sgbname) {
		this.sgbname = sgbname;
	}
	
	public String getSgbsido() {
		return sgbsido;
	}

	public void setSgbsido(String sgbsido) {
		this.sgbsido = sgbsido;
	}

	public Integer getOrg_seq() {
		return org_seq;
	}

	public void setOrg_seq(Integer org_seq) {
		this.org_seq = org_seq;
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

	public Integer getSelectOrgSeq() {
		return selectOrgSeq;
	}

	public void setSelectOrgSeq(Integer selectOrgSeq) {
		this.selectOrgSeq = selectOrgSeq;
	}

	public int getPcListInfoCurrentPage() {
		return pcListInfoCurrentPage;
	}

	public void setPcListInfoCurrentPage(int pcListInfoCurrentPage) {
		this.pcListInfoCurrentPage = pcListInfoCurrentPage;
	}

	public String getPc_change() {
		return pc_change;
	}

	public void setPc_change(String pc_change) {
		this.pc_change = pc_change;
	}

	public String getCpu_info() {
		return cpu_info;
	}

	public void setCpu_info(String cpu_info) {
		this.cpu_info = cpu_info;
	}

	public String getRam_info() {
		return ram_info;
	}

	public void setRam_info(String ram_info) {
		this.ram_info = ram_info;
	}

	public String getHdd_info() {
		return hdd_info;
	}

	public void setHdd_info(String hdd_info) {
		this.hdd_info = hdd_info;
	}

	public String getMboard_info() {
		return mboard_info;
	}

	public void setMboard_info(String mboard_info) {
		this.mboard_info = mboard_info;
	}

	public String getEtc_info() {
		return etc_info;
	}

	public void setEtc_info(String etc_info) {
		this.etc_info = etc_info;
	}

	public String getSgb_pc_vpnip() {
		return sgb_pc_vpnip;
	}

	public void setSgb_pc_vpnip(String sgb_pc_vpnip) {
		this.sgb_pc_vpnip = sgb_pc_vpnip;
	}

	public String getOld_pc_ip() {
		return old_pc_ip;
	}

	public void setOld_pc_ip(String old_pc_ip) {
		this.old_pc_ip = old_pc_ip;
	}

	public String getOld_pc_vpnip() {
		return old_pc_vpnip;
	}

	public void setOld_pc_vpnip(String old_pc_vpnip) {
		this.old_pc_vpnip = old_pc_vpnip;
	}

	public String getOld_pc_macaddr() {
		return old_pc_macaddr;
	}

	public void setOld_pc_macaddr(String old_pc_macaddr) {
		this.old_pc_macaddr = old_pc_macaddr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxSgbPcCntByorgSeq() {
		return maxSgbPcCntByorgSeq;
	}

	public void setMaxSgbPcCntByorgSeq(String maxSgbPcCntByorgSeq) {
		this.maxSgbPcCntByorgSeq = maxSgbPcCntByorgSeq;
	}
	
	public String getSgb_pc_os() {
		return sgb_pc_os;
	}

	public void setSgb_pc_os(String sgb_pc_os) {
		this.sgb_pc_os = sgb_pc_os;
	}



	private String first_date;
	private String last_date;
	private String sgbname;
	private String allsgbname;
	private String sgbsido;
	private String sgbpcname;
	private Integer org_seq;
	private Integer move_org_seq;
	private String org_nm;
	private String move_org_nm;
	private String[] updateBlockList;
	private String[] updateUnblockList;
	public String getOrg_nm() {
		return org_nm;
	}

	public void setOrg_nm(String org_nm) {
		this.org_nm = org_nm;
	}

	public String getMove_org_nm() {
		return move_org_nm;
	}

	public void setMove_org_nm(String move_org_nm) {
		this.move_org_nm = move_org_nm;
	}

	public Integer getMove_org_seq() {
		return move_org_seq;
	}

	public void setMove_org_seq(Integer move_org_seq) {
		this.move_org_seq = move_org_seq;
	}
	
	public String getSgbpcname() {
		return sgbpcname;
	}

	public void setSgbpcname(String sgbpcname) {
		this.sgbpcname = sgbpcname;
	}
	
	

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSttus() {
		return sttus;
	}

	public void setSttus(String sttus) {
		this.sttus = sttus;
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
	

	public String[] getUpdateBlockList() {
		return updateBlockList;
	}

	public void setUpdateBlockList(String[] updateBlockList) {
		this.updateBlockList = updateBlockList;
	}
	



	public String[] getUpdateUnblockList() {
		return updateUnblockList;
	}
	public void setUpdateUnblockList(String[] updateUnblockList) {
		this.updateUnblockList = updateUnblockList;
	}
	public String getAllsgbname() {
		return allsgbname;
	}

	public void setAllsgbname(String allsgbname) {
		this.allsgbname = allsgbname;
	}



	public String getOrg_sgb_room_name() {
		return org_sgb_room_name;
	}

	public void setOrg_sgb_room_name(String org_sgb_room_name) {
		this.org_sgb_room_name = org_sgb_room_name;
	}

	


	public String getSgb_public_ip() {
		return sgb_public_ip;
	}

	public void setSgb_public_ip(String sgb_public_ip) {
		this.sgb_public_ip = sgb_public_ip;
	}



	// search ====
	private String txtSearch;

	private String keyWord;

	private Integer selectOrgSeq;

	private int pcListInfoCurrentPage;
	
	private String date_fr;
	private String date_to;

}