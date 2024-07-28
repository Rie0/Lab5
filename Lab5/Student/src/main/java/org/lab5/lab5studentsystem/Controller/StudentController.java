package org.lab5.lab5studentsystem.Controller;

import org.lab5.lab5studentsystem.Api.ApiResponse;
import org.lab5.lab5studentsystem.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/student_system")
public class StudentController {

    ArrayList<Student> students = new ArrayList<>();

    //GET--------------------------------------------------
    //General gets
    @GetMapping("/get/students")
    public ApiResponse getAllStudents() {
        if (students.isEmpty()) {
            return new ApiResponse("404","No students found");
        }else {
            return new ApiResponse("200","List of students: "+students);
        }
    }
    @GetMapping("/get/search_student/{name}")
    public ApiResponse getStudentByName(@PathVariable String name) { //return type
        ArrayList<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().contains(name)) {
                foundStudents.add(student);
            }
        }
        if (foundStudents.isEmpty()) {
            return new ApiResponse("404","No students with name "+name+" found");
        }else {
            return new ApiResponse("200","List of students: "+foundStudents);
        }
    }
    //student-specific gets
    @GetMapping("/get/student_age/{index}")
    public ApiResponse getStudentByIndex(@PathVariable int index) {

        if (index >= 0 && index < students.size()) {
            return new ApiResponse("200","Student of index "+ index + " is found. "+
                    "Age is: " + students.get(index).getAge());
        }
        return new ApiResponse("404","Student not found");
    }
    @GetMapping("get/college/degree/{index}")
    public ApiResponse getStudentCollegeDegree(@PathVariable int index ) {
        if (index >= 0 && index < students.size()) {
            return new ApiResponse("200","Student of index "+ index + " is found. "+
                    "Degree is: " + students.get(index).getDegree());
        }
        return new ApiResponse("404","Student not found");
    }
    @GetMapping("get/study/status/{index}")
    public ApiResponse getStudentStatus(@PathVariable int index) {
        boolean isGraduated = students.get(index).getStatus().equalsIgnoreCase("Graduated");

        if (index >= 0 && index < students.size()) {
             return new ApiResponse("200","Student of index "+index+" is found. "+
                     "Is graduated: "+isGraduated);
        }
        return new ApiResponse("404","Student not found");
    }
    //POST------------------------------------------------
    @PostMapping("/post")
    public ApiResponse postStudent(@RequestBody Student student) {
        students.add(student);
        return new ApiResponse("200","Added");
    }
    //PUT--------------------------------------------------
    @PutMapping("update/{index}")
    public ApiResponse updateStudent(@PathVariable int index, @RequestBody Student student) {
        if (index >= 0 && index < students.size()) {
            students.set(index, student);
            return new ApiResponse("200","Student info updated");
        }else {
            return new ApiResponse("404","Student not found");
        }
    }
    //DELETE-----------------------------------------------
    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteStudent(@PathVariable int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            return new ApiResponse("200","Student deleted");
        } else {
            return new ApiResponse("404","Student not found");
        }
    }
}
