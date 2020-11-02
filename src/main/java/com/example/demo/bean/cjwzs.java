package com.example.demo.bean;

public class cjwzs {
	private int id;
	private String wzdz;
	private String cjgz;
	private String cjlb;
	private String wjbt;
	public String getSechword() {
		return sechword;
	}
	public void setSechword(String sechword) {
		this.sechword = sechword;
	}
	public String getSechwordxpth() {
		return sechwordxpth;
	}
	public void setSechwordxpth(String sechwordxpth) {
		this.sechwordxpth = sechwordxpth;
	}
	private String wjnr;
	private String nextPage;
	private String jsNextPage;
	private String fjlb;
	private int xqsleepTime;
	private int nums;
	private int pageNum;
	private String sechword;
	private String sechwordxpth;
	private String insertTablename;
	private String iframe;
	private String lbiframe;
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	public String getIframe() {
		return iframe;
	}
	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	public String getLbiframe() {
		return lbiframe;
	}
	public void setLbiframe(String lbiframe) {
		this.lbiframe = lbiframe;
	}
	public String getFjlb() {
		return fjlb;
	}
	public void setFjlb(String fjlb) {
		this.fjlb = fjlb;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWzdz() {
		return wzdz;
	}
	public void setWzdz(String wzdz) {
		this.wzdz = wzdz;
	}
	public String getCjgz() {
		return cjgz;
	}
	public void setCjgz(String cjgz) {
		this.cjgz = cjgz;
	}
	public String getCjlb() {
		return cjlb;
	}
	public void setCjlb(String cjlb) {
		this.cjlb = cjlb;
	}
	public String getWjbt() {
		return wjbt;
	}
	public void setWjbt(String wjbt) {
		this.wjbt = wjbt;
	}
	public String getWjnr() {
		return wjnr;
	}
	public void setWjnr(String wjnr) {
		this.wjnr = wjnr;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
	public String getJsNextPage() {
		return jsNextPage;
	}
	public void setJsNextPage(String jsNextPage) {
		this.jsNextPage = jsNextPage;
	}
	public int getXqsleepTime() {
		return xqsleepTime;
	}
	public void setXqsleepTime(int xqsleepTime) {
		this.xqsleepTime = xqsleepTime;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cjgz == null) ? 0 : cjgz.hashCode());
		result = prime * result + ((cjlb == null) ? 0 : cjlb.hashCode());
		result = prime * result + id;
		result = prime * result + ((jsNextPage == null) ? 0 : jsNextPage.hashCode());
		result = prime * result + ((nextPage == null) ? 0 : nextPage.hashCode());
		result = prime * result + nums;
		result = prime * result + pageNum;
		result = prime * result + ((wjbt == null) ? 0 : wjbt.hashCode());
		result = prime * result + ((wjnr == null) ? 0 : wjnr.hashCode());
		result = prime * result + ((wzdz == null) ? 0 : wzdz.hashCode());
		result = prime * result + xqsleepTime;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		cjwzs other = (cjwzs) obj;
		if (cjgz == null) {
			if (other.cjgz != null)
				return false;
		} else if (!cjgz.equals(other.cjgz))
			return false;
		if (cjlb == null) {
			if (other.cjlb != null)
				return false;
		} else if (!cjlb.equals(other.cjlb))
			return false;
		if (id != other.id)
			return false;
		if (jsNextPage == null) {
			if (other.jsNextPage != null)
				return false;
		} else if (!jsNextPage.equals(other.jsNextPage))
			return false;
		if (nextPage == null) {
			if (other.nextPage != null)
				return false;
		} else if (!nextPage.equals(other.nextPage))
			return false;
		if (nums != other.nums)
			return false;
		if (pageNum != other.pageNum)
			return false;
		if (wjbt == null) {
			if (other.wjbt != null)
				return false;
		} else if (!wjbt.equals(other.wjbt))
			return false;
		if (wjnr == null) {
			if (other.wjnr != null)
				return false;
		} else if (!wjnr.equals(other.wjnr))
			return false;
		if (wzdz == null) {
			if (other.wzdz != null)
				return false;
		} else if (!wzdz.equals(other.wzdz))
			return false;
		if (xqsleepTime != other.xqsleepTime)
			return false;
		return true;
	}
	@Override
	public String toString() {
		String [] gzarr=cjgz.split(",");
		boolean ok=true;
		String gz="";
		for(int i=0;i<gzarr.length;i++) {
			if(ok) {
				gz+="\""+(gzarr[i]!=null ? gzarr[i].replaceAll("\"", "'"):gzarr[i])+"\"";
				ok=false;
			}else {
				gz+=",\""+(gzarr[i]!=null ? gzarr[i].replaceAll("\"", "'"):gzarr[i])+"\"";
			}
		}
		return "{\"id\":" + id + ",\"wzdz\":\""+ wzdz +"\",\"cjgz\":[" + gz + "],\"cjlb\":\"" + (cjlb!=null ? cjlb.replaceAll("\"", "'"):cjlb) + "\",\"fjlb\":\"" + (fjlb!=null ? fjlb.replaceAll("\"", "'"):fjlb) + "\",\"wjbt\":\"" + (wjbt!=null ? wjbt.replaceAll("\"", "'"):wjbt) + "\",\"wjnr\":\""
				+ (wjnr!=null ? wjnr.replaceAll("\"", "'"):wjnr) + "\",\"nextPage\":\"" +(nextPage!=null ? nextPage.replaceAll("\"", "'"):nextPage) + "\",\"jsNextPage\":\"" + jsNextPage + "\",\"xqsleepTime\":" + xqsleepTime
				+ ",\"nums\":" + nums + ",\"pageNum\":" + pageNum + ",\"sechword\":\"" + sechword + "\",\"sechwordxpth\":\"" + (sechwordxpth!=null ? sechwordxpth.replaceAll("\"", "'"):sechwordxpth )+ "\",\"insertTablename\":\"" + insertTablename + "\",\"iframe\":\"" + (iframe!=null ? iframe.replaceAll("\"", "'"): "null" )+ "\",\"lbiframe\":\"" + (lbiframe!=null ? lbiframe.replaceAll("\"", "'"): "null" )+ "\"}";
	}
	public String getInsertTablename() {
		return insertTablename;
	}
	public void setInsertTablename(String insertTablename) {
		this.insertTablename = insertTablename;
	}
	
}
