package com.justdial.crawler;

import java.util.Comparator;

public class CompanyRatingsComparator implements Comparator<Company> {

	public int compare(Company p1, Company p2) {
		return p2.getRatings().compareTo(p1.getRatings());
	}
}
