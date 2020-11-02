package com.example.demo.bean;

public class Index {
	private int stuts;
	private int thnums;
	public int getStuts() {
		return stuts;
	}
	@Override
	public String toString() {
		return "Index [stuts=" + stuts + ", thnums=" + thnums + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stuts;
		result = prime * result + thnums;
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
		Index other = (Index) obj;
		if (stuts != other.stuts)
			return false;
		if (thnums != other.thnums)
			return false;
		return true;
	}
	public void setStuts(int stuts) {
		this.stuts = stuts;
	}
	public int getThnums() {
		return thnums;
	}
	public void setThnums(int thnums) {
		this.thnums = thnums;
	}
}
