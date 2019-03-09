package com.cognizant.outreach.microservices.perfdata.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.SchoolWeekendWorkingDay;

@RestResource(exported = false)
public interface SchoolWeekendWorkingDayResposiotry extends CrudRepository<SchoolWeekendWorkingDay, Long> {

	@Query("SELECT day(swd.workingDate) FROM SchoolWeekendWorkingDay swd WHERE swd.school.id=:SCHOOL_ID AND  month(swd.workingDate)=:MONTH_NUMBER ORDER BY swd.workingDate")
	public Optional<List<Integer>> listWorkingDaysBySchoolIdAndDate(@Param("SCHOOL_ID") long schoolId,@Param("MONTH_NUMBER") int monthNumber);
	
	@Query("SELECT swd.workingDate FROM SchoolWeekendWorkingDay swd WHERE swd.school.id=:SCHOOL_ID AND month(swd.workingDate)=:MONTH_NUMBER ORDER BY swd.workingDate")
	public Optional<List<Date>> listWorkingDatesBySchoolIdAndDate(@Param("SCHOOL_ID") long schoolId, @Param("MONTH_NUMBER") int monthNumber);
	
}
