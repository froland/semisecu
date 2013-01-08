package com.hermes.owasphotel.service.impl;

import java.util.Comparator;

import com.hermes.owasphotel.domain.Hotel;

public class HotelAverageNoteComparator implements Comparator<Hotel> {

	final private double EPSILON = 0.00001;
	@Override
	public int compare(Hotel o1, Hotel o2) {
		if(o1.getAverageNote() - o2.getAverageNote() > EPSILON)
			return -1;
		if(o1.getAverageNote() - o2.getAverageNote() < -EPSILON)
			return 1;
		return 0;
	}

}
