package com.cognizant.outreach.microservices.perfdata.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.entity.MeasurableParamData;
import com.cognizant.outreach.entity.StudentSchoolAssoc;

@RestResource(exported = false)
public interface PerformanceDataRepository extends CrudRepository<MeasurableParamData, Long> {

	@Query("SELECT md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.studentSchoolAssoc.clazz.school.schoolName, md.studentSchoolAssoc.clazz.className, md.studentSchoolAssoc.teamName, md.measurableDate, md.measurableParam.parameterTitle, md.measurableParamValue FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id =:SCHOOL_ID AND md.studentSchoolAssoc.clazz.id=:CLASS_ID AND md.measurableDate BETWEEN :MEASURABLE_FROM_DATE AND :MEASURABLE_TO_DATE ORDER BY md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.measurableDate, md.measurableParam.parameterTitle")
	public List<Object[]> listOfMeasurableParamDataBySearchParam(@Param("SCHOOL_ID") Long schoolId,
			@Param("CLASS_ID") Long classId, @Param("MEASURABLE_FROM_DATE") Date fromDate,
			@Param("MEASURABLE_TO_DATE") Date toDate);

	@Query("SELECT md FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id =:SCHOOL_ID AND md.studentSchoolAssoc.clazz.id=:CLASS_ID AND md.measurableDate BETWEEN :MEASURABLE_FROM_DATE AND :MEASURABLE_TO_DATE ORDER BY md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.measurableDate, md.measurableParam.parameterTitle")
	public List<MeasurableParamData> listOfMeasurableParamObjectBySearchParam(@Param("SCHOOL_ID") Long schoolId,
			@Param("CLASS_ID") Long classId, @Param("MEASURABLE_FROM_DATE") Date fromDate,
			@Param("MEASURABLE_TO_DATE") Date toDate);

	@Query("SELECT CASE WHEN COUNT(md.id) > 0 THEN 'true' ELSE 'false' END FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id =:SCHOOL_ID AND md.studentSchoolAssoc.clazz.id=:CLASS_ID AND md.measurableDate BETWEEN :MEASURABLE_FROM_DATE AND :MEASURABLE_TO_DATE ORDER BY md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.measurableDate, md.measurableParam.parameterTitle")
	public Boolean isExistOfMeasurableParamObjectBySearchParam(@Param("SCHOOL_ID") Long schoolId,
			@Param("CLASS_ID") Long classId, @Param("MEASURABLE_FROM_DATE") Date fromDate,
			@Param("MEASURABLE_TO_DATE") Date toDate);

	@Query("SELECT mp FROM MeasurableParam mp WHERE mp.school.id =:SCHOOL_ID ORDER BY mp.parameterTitle")
	public List<MeasurableParam> listOfMeasurableParamBySchoolId(@Param("SCHOOL_ID") Long schoolId);

	@Query("SELECT ssa.rollId, ssa.student.studentName, ssa.clazz.school.schoolName, ssa.clazz.className, ssa.clazz.section, ssa.teamName FROM StudentSchoolAssoc ssa WHERE ssa.clazz.school.id=:SCHOOL_ID AND ssa.clazz.id=:CLASS_ID order by ssa.rollId, ssa.student.studentName")
	public List<Object[]> listOfStudentDetailBySearchParam(@Param("SCHOOL_ID") Long schoolId,
			@Param("CLASS_ID") Long classId);

	@Query("SELECT ssa FROM StudentSchoolAssoc ssa WHERE ssa.clazz.school.id=:SCHOOL_ID AND ssa.clazz.id=:CLASS_ID order by ssa.rollId, ssa.student.studentName")
	public List<StudentSchoolAssoc> listOfStudentSchoolAssocBySearchParam(@Param("SCHOOL_ID") Long schoolId,
			@Param("CLASS_ID") Long classId);

	@Query("SELECT md FROM MeasurableParamData md WHERE md.studentSchoolAssoc.student.id=:STUDENT_ID AND md.measurableParam.id=:MEASURABLE_PARAM_ID AND month(md.measurableDate)=:MONTH_NUMBER ORDER BY md.measurableDate")
	public List<MeasurableParamData> listOfMeasurableParamDataByStudent(@Param("STUDENT_ID") long studentId,
			@Param("MEASURABLE_PARAM_ID") long measurableParamId, @Param("MONTH_NUMBER") int monthNumber);

	@Query("SELECT s.schoolName FROM School s WHERE s.id =:STUDENT_ID")
	public String findSchoolNameBySchoolId(@Param("STUDENT_ID") long studentId);

	@Query("SELECT concat(cd.className,' - ',cd.section) FROM ClassDetail cd WHERE cd.id =:CLASS_ID")
	public String findClassNameByClassId(@Param("CLASS_ID") Long classId);

	@Query( "select (SUM(md.measurableParamValue)/COUNT(md.measurableParamValue))*100,DAY(md.measurableDate)"
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.id=:CLASS_ID AND"
			+ " md.studentSchoolAssoc.teamName=:TEAM_NAME AND md.measurableParam.id=:MEASURABLE_PARAM_ID"
			+ " AND month(md.measurableDate)=:MONTH_NUMBER GROUP BY md.measurableDate ORDER BY md.measurableDate asc")
	public List<Object[]> listOfMeasurableParamDataByTeam(@Param("CLASS_ID") long classId,
			@Param("TEAM_NAME") String teamName, @Param("MEASURABLE_PARAM_ID") long measurableParamId,
			@Param("MONTH_NUMBER") int monthNumber);
	
	@Query( "select (SUM(md.measurableParamValue)/COUNT(md.measurableParamValue))*100,DAY(md.measurableDate)"
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.id=:CLASS_ID AND"
			+ " md.measurableParam.id=:MEASURABLE_PARAM_ID"
			+ " AND month(md.measurableDate)=:MONTH_NUMBER GROUP BY md.measurableDate ORDER BY md.measurableDate asc")
	public List<Object[]> listOfMeasurableParamDataByClass(@Param("CLASS_ID") long classId,
			@Param("MEASURABLE_PARAM_ID") long measurableParamId,
			@Param("MONTH_NUMBER") int monthNumber);
	
	@Query( "select (SUM(md.measurableParamValue)/COUNT(md.measurableParamValue))*100,DAY(md.measurableDate)"
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id=:SCHOOL_ID AND"
			+ " md.measurableParam.id=:MEASURABLE_PARAM_ID"
			+ " AND month(md.measurableDate)=:MONTH_NUMBER GROUP BY md.measurableDate ORDER BY md.measurableDate asc")
	public List<Object[]> listOfMeasurableParamDataBySchool(@Param("SCHOOL_ID") long schoolId,
			@Param("MEASURABLE_PARAM_ID") long measurableParamId,
			@Param("MONTH_NUMBER") int monthNumber);
	
	@Query( "select SUM(md.measurableParamValue), md.studentSchoolAssoc.clazz.className, md.studentSchoolAssoc.clazz.section"
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id=:SCHOOL_ID AND"
			+ " md.studentSchoolAssoc.clazz.className=:CLASS_NAME AND"
			+ " md.measurableParam.id=:MEASURABLE_PARAM_ID  GROUP BY md.studentSchoolAssoc.clazz.section ORDER BY md.studentSchoolAssoc.clazz.section asc")
	public List<Object[]> listOfMeasurableParamDataBySection(@Param("SCHOOL_ID") long schoolId, @Param("CLASS_NAME") String className,
			@Param("MEASURABLE_PARAM_ID") long measurableParamId);

	@Query( "select SUM(md.measurableParamValue), md.studentSchoolAssoc.teamName"
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id=:SCHOOL_ID AND"
			+ " md.studentSchoolAssoc.clazz.className=:CLASS_NAME AND"
			+ " md.measurableParam.id=:MEASURABLE_PARAM_ID  GROUP BY md.studentSchoolAssoc.teamName ORDER BY md.studentSchoolAssoc.teamName asc")
	public List<Object[]> listOfMeasurableParamDataByTeam(@Param("SCHOOL_ID") long schoolId, @Param("CLASS_NAME") String className,
			@Param("MEASURABLE_PARAM_ID") long measurableParamId);

	@Query( "select (SUM(md.measurableParamValue)/COUNT(md.measurableParamValue))*100, md.studentSchoolAssoc.clazz.className, md.studentSchoolAssoc.clazz.section"
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id=:SCHOOL_ID AND"
			+ " md.studentSchoolAssoc.clazz.className=:CLASS_NAME AND"
			+ " md.measurableParam.id=:MEASURABLE_PARAM_ID"
			+ " AND month(md.measurableDate)=:MONTH_NUMBER GROUP BY md.studentSchoolAssoc.clazz.section ORDER BY md.studentSchoolAssoc.clazz.section asc")
	public List<Object[]> listOfMeasurableParamDataByMonth(@Param("SCHOOL_ID") long schoolId, @Param("CLASS_NAME") String className,
			@Param("MEASURABLE_PARAM_ID") long measurableParamId,
			@Param("MONTH_NUMBER") int monthNumber);

	@Query( "select COUNT(DISTINCT md.studentSchoolAssoc.clazz.school.id ),month(md.measurableDate) "
			+ " FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id=md.studentSchoolAssoc.clazz.school.id AND month(md.measurableDate)=month(md.measurableDate) "
			+ "GROUP BY month(md.measurableDate) having COUNT(md.studentSchoolAssoc.clazz.school.id)>0 ORDER BY month(md.measurableDate) asc")
	public List<Object[]> listOfMeasurableParamDataNumSchoolsByMonth();
	
	@Query(nativeQuery=true,  value="select sum(measurable0_.measurable_param_value) as col_0_0_, school3_.school_name as col_1_0_ from measurable_param_data measurable0_ cross join student_school_assoc studentsch1_ cross join class classdetai2_ cross join school school3_ where measurable0_.stud_schl_assoc_id=studentsch1_.id and studentsch1_.class_id=classdetai2_.id and classdetai2_.school_id=school3_.id group by classdetai2_.school_id order by col_0_0_ desc limit 5")
	public List<Object[]> listOfMeasurableParamDataOfTopSchool();
	
	@Query( "select COUNT(md.lastUpdatedUserId), lastUpdatedUserId"
			+ " FROM MeasurableParamData md "
			+ " GROUP BY md.lastUpdatedUserId ORDER BY md.lastUpdatedUserId asc")
	public List<Object[]> listOfMeasurableParamDataOfTopPerformer();
}
