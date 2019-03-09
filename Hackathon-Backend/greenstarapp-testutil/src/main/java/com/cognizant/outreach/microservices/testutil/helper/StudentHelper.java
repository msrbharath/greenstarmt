package com.cognizant.outreach.microservices.testutil.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.outreach.entity.ClassDetail;
import com.cognizant.outreach.entity.Student;
import com.cognizant.outreach.entity.StudentSchoolAssoc;
import com.cognizant.outreach.microservices.testutil.dao.StudentRepository;
import com.cognizant.outreach.microservices.testutil.dao.StudentSchoolAssocRepository;
import com.cognizant.outreach.util.DateUtil;

@Component
public class StudentHelper {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentSchoolAssocRepository studentSchoolAssocRepository;

	public List<Student> createStudents() throws ParseException {
		List<Student> students= new ArrayList<Student>();
		
		//Students for section A
		students.add(createStudent("22-MAY-2012", "Akil", "12,Nethaji Nagar"));
		students.add(createStudent("09-FEB-2012", "Jishnuprasad", "30,Kalaam Nagar"));
		students.add(createStudent("02-DEC-2011", "Aadhi", "12,kurunji Nagar"));
		students.add(createStudent("02-NOV-2011", "Neethi", "4/2,mullai Nagar"));
		students.add(createStudent("10-MAR-2012", "Deepthi", "5,marutham Nagar"));
		students.add(createStudent("11-APR-2012", "Aarav", "7,kandhan Nagar"));
		students.add(createStudent("12-JAN-2012", "Prathiksha", "5,village Nagar"));
		students.add(createStudent("23-MAY-2012", "Aaradhanaa", "12,Ragavi garden Nagar"));
		students.add(createStudent("23-MAR-2012", "Paul", "6/7,Ram Nagar"));
		students.add(createStudent("01-JAN-2012", "Musthafa", "4,Kamaraj Nagar"));
		
		//Students for section B
		students.add(createStudent("22-MAY-2012", "Aadhav", "12,Nethaji Nagar"));
		students.add(createStudent("09-FEB-2012", "Akilesh", "30,Kalaam Nagar"));
		students.add(createStudent("02-DEC-2011", "Preethi", "12,kurunji Nagar"));
		students.add(createStudent("02-NOV-2011", "Guptha", "4/2,mullai Nagar"));
		students.add(createStudent("10-MAR-2012", "Ragavi", "5,marutham Nagar"));
		students.add(createStudent("11-APR-2012", "Ranjani", "7,kandhan Nagar"));
		students.add(createStudent("12-JAN-2012", "Kumaran", "5,village Nagar"));
		students.add(createStudent("23-MAY-2012", "Dheeran", "12,Ragavi garden Nagar"));
		students.add(createStudent("23-MAR-2012", "Judwin", "6/7,Ram Nagar"));
		students.add(createStudent("01-JAN-2012", "Moulish", "4,Kamaraj Nagar"));
		
		return students;
	}

	public List<StudentSchoolAssoc> createStudentAssociaton(ClassDetail classDetail,List<Student> students) throws ParseException {
		List<StudentSchoolAssoc> studentClassAssociations= new ArrayList<>();
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Kalaam",students.get(0)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Kalaam",students.get(1)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Kalaam",students.get(2)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Kalaam",students.get(3)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Kalaam",students.get(4)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Teresa",students.get(5)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Teresa",students.get(6)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Teresa",students.get(7)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Teresa",students.get(8)));
		studentClassAssociations.add(createStudentSchoolAssoc(classDetail,"Teresa",students.get(9)));
		return studentClassAssociations;
	}
	
	private StudentSchoolAssoc createStudentSchoolAssoc(ClassDetail classDetail,String teamName,Student student) {
		StudentSchoolAssoc studentSchoolAssoc = new StudentSchoolAssoc();
		studentSchoolAssoc.setClazz(classDetail);
		studentSchoolAssoc.setRollId(classDetail.getClassName()+classDetail.getSection()+student.getId());
		studentSchoolAssoc.setTeamName(teamName);
		studentSchoolAssoc.setStudent(student);
		studentSchoolAssoc.setStatus("ACTIVE");
		CommonHelper.setAuditTrailInfo(studentSchoolAssoc);
		studentSchoolAssocRepository.save(studentSchoolAssoc);
		return studentSchoolAssoc;
	}
	
	public void deleteStudentAssociations(List<StudentSchoolAssoc> studentSchoolAssocs) {
		studentSchoolAssocRepository.deleteAll(studentSchoolAssocs);
	}
	
	private Student createStudent(String dob, String name, String address) throws ParseException {
		Student student = new Student();
		student.setDob(DateUtil.getParseDateObject(dob));
		student.setStudentName(name);
		student.setAddress(address);
		CommonHelper.setAuditTrailInfo(student);
		studentRepository.save(student);
		return student;
	}

	public void deleteStudents(List<Student> students) {
		studentRepository.deleteAll(students);
	}
}
