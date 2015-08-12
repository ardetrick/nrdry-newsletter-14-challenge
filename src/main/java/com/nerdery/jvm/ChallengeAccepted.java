package com.nerdery.jvm;

import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.javatuples.Pair;
import rx.Observable;

public class ChallengeAccepted {

	public static void main(String[] args) {
		new ChallengeAccepted().countSundays();
	}

	public void countSundays() {
		Observable<Integer> months = Observable.range(Month.JANUARY.getValue(), 12); 	// emit 12 values starting with January
		Observable<Integer> years = Observable.range(1901, 100); 						// emit 100 values starting with 1901

		Observable<Integer> totalCount = years
				.flatMap(year -> months.map(month -> new Pair<Integer,Integer>(year, month)))	// take Cartesian product of month and year range
				.filter(yearAndMonth -> isSundayFunday(yearAndMonth))							// filter out undesirable dates
				//.doOnNext(System.out::println)												// print them, if you want
				.count();																		// count em up!

		totalCount.subscribe(System.out::println); 		// and the total is...!
	}

	private boolean isSundayFunday(Pair<Integer, Integer> yearAndMonth) {
		// Calendar is the worst :(
		Calendar calendar = new GregorianCalendar(yearAndMonth.getValue0(), yearAndMonth.getValue1(), 1);
		return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}
	
}
