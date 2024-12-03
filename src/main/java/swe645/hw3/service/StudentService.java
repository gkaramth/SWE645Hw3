package swe645.hw3.service;

import java.util.List;

import swe645.hw3.model.Student;

public interface StudentService {
	Student createStudent(Student student);
	List<Student> fetchAllStudents();
	Student fetchStudentById(long id);
	Student modifyStudent(Student student, long id);
	void removeStudent(long id);
}