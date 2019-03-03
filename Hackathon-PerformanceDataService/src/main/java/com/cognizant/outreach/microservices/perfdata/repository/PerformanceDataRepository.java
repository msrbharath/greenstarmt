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

	@Query("SELECT md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.studentSchoolAssoc.clazz.school.schoolName, md.studentSchoolAssoc.clazz.className, md.studentSchoolAssoc.clazz.section, md.studentSchoolAssoc.teamName, md.measurableDate, md.measurableParam.parameterTitle, md.measurableParamValue FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id =:SCHOOL_ID AND md.studentSchoolAssoc.clazz.className=:CLASS_NAME AND md.studentSchoolAssoc.clazz.section=:SECTION_NAME AND md.measurableDate BETWEEN :MEASURABLE_FROM_DATE AND :MEASURABLE_TO_DATE ORDER BY md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.measurableDate, md.measurableParam.parameterTitle")
    public List<Object[]> listOfMeasurableParamDataBySearchParam(@Param("SCHOOL_ID") Long schoolId, @Param("CLASS_NAME") String className, @Param("SECTION_NAME") String sectionName, @Param("MEASURABLE_FROM_DATE") Date fromDate, @Param("MEASURABLE_TO_DATE") Date toDate);
    
    @Query("SELECT md FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id =:SCHOOL_ID AND md.studentSchoolAssoc.clazz.className=:CLASS_NAME AND md.studentSchoolAssoc.clazz.section=:SECTION_NAME AND md.measurableDate BETWEEN :MEASURABLE_FROM_DATE AND :MEASURABLE_TO_DATE ORDER BY md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.measurableDate, md.measurableParam.parameterTitle")
    public List<MeasurableParamData> listOfMeasurableParamObjectBySearchParam(@Param("SCHOOL_ID") Long schoolId, @Param("CLASS_NAME") String className, @Param("SECTION_NAME") String sectionName, @Param("MEASURABLE_FROM_DATE") Date fromDate, @Param("MEASURABLE_TO_DATE") Date toDate);
    
    @Query("SELECT CASE WHEN COUNT(md.id) > 0 THEN 'true' ELSE 'false' END FROM MeasurableParamData md WHERE md.studentSchoolAssoc.clazz.school.id =:SCHOOL_ID AND md.studentSchoolAssoc.clazz.className=:CLASS_NAME AND md.studentSchoolAssoc.clazz.section=:SECTION_NAME AND md.measurableDate BETWEEN :MEASURABLE_FROM_DATE AND :MEASURABLE_TO_DATE ORDER BY md.studentSchoolAssoc.rollId, md.studentSchoolAssoc.student.studentName, md.measurableDate, md.measurableParam.parameterTitle")
    public Boolean isExistOfMeasurableParamObjectBySearchParam(@Param("SCHOOL_ID") Long schoolId, @Param("CLASS_NAME") String className, @Param("SECTION_NAME") String sectionName, @Param("MEASURABLE_FROM_DATE") Date fromDate, @Param("MEASURABLE_TO_DATE") Date toDate);
    
    @Query("SELECT mp FROM MeasurableParam mp WHERE mp.school.id =:SCHOOL_ID ORDER BY mp.parameterTitle")
    public List<MeasurableParam> listOfMeasurableParamBySchoolId(@Param("SCHOOL_ID") Long schoolId);
    
    @Query("SELECT ssa.rollId, ssa.student.studentName, ssa.clazz.school.schoolName, ssa.clazz.className, ssa.clazz.section, ssa.teamName FROM StudentSchoolAssoc ssa WHERE ssa.clazz.school.id=:SCHOOL_ID AND ssa.clazz.className=:CLASS_NAME AND ssa.clazz.section=:SECTION_NAME order by ssa.rollId, ssa.student.studentName")
    public List<Object[]> listOfStudentDetailBySearchParam(@Param("SCHOOL_ID") Long schoolId, @Param("CLASS_NAME") String className, @Param("SECTION_NAME") String sectionName);
    
    @Query("SELECT ssa FROM StudentSchoolAssoc ssa WHERE ssa.clazz.school.id=:SCHOOL_ID AND ssa.clazz.className=:CLASS_NAME AND ssa.clazz.section=:SECTION_NAME order by ssa.rollId, ssa.student.studentName")
    public List<StudentSchoolAssoc> listOfStudentSchoolAssocBySearchParam(@Param("SCHOOL_ID") Long schoolId, @Param("CLASS_NAME") String className, @Param("SECTION_NAME") String sectionName);
}
