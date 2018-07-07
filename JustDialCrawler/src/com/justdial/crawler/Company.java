package com.justdial.crawler;

public class Company implements Comparable<Company> {

	public Company() {
		super();
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Company(String name, String address, String numvotes, String phonenum, String ratings) {
		super();
		this.name = name;
		this.address = address;
		this.numvotes = numvotes;
		this.phonenum = phonenum;
		this.ratings = ratings;
	}

	public String getNumvotes() {
		return numvotes;
	}

	public void setNumvotes(String numvotes) {
		this.numvotes = numvotes;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	private String address;

	private String numvotes;

	private String phonenum;
	private String ratings;

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name + "    " + this.phonenum + "      " + this.ratings + "      " + this.numvotes + "     "
				+ this.address;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof Company) {
			Company company = (Company) o;

			return company.name.equals(this.name) && company.phonenum.equals(this.phonenum)
					&& company.ratings.equals(this.ratings) && company.numvotes.equals(this.numvotes)
					&& company.address.equals(this.address);
		} else {
			return false;
		}

	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + name.hashCode();
		result = 31 * result + phonenum.hashCode();
		result = 31 * result + ratings.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + numvotes.hashCode();
		result = 31 * result + address.hashCode();
		return result;
	}

	public int compareTo(Company p) {
		return ratings.compareTo(p.ratings);
	}
}
