package com.systechafrica.service;

import com.systechafrica.model.Student;

public interface StudentService {
    boolean saveStudent(Student student);

    Student getStudentByRegNo(String regNo);
}
