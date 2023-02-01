import java.util.ArrayList;

public class Manager {
    ArrayList<Student> students = new ArrayList<Student>();
    ArrayList<Department> departments = new ArrayList<Department>();


    public boolean addStudent(Student student) {
        // Check if same roll no already exists
        for (Student s : students) {
            if (s.roll == student.roll) {
                return false;
            }
        }
        students.add(student);
        return true;
    }

    public Student searchStudent(int roll) {
        for (Student s : students) {
            if (s.roll == roll) {
                return s;
            }
        }
        return null;
    }

    public boolean deleteStudent(int roll) {
        for (Student s : students) {
            if (s.roll == roll) {
                students.remove(s);
                return true;
            }
        }
        return false;
    }

    public boolean updateStudent(int rollno, String name, String address, String phone, String deptCode) {
        for (Student s : students) {
            if (s.roll == rollno) {
                s.name = name;
                s.address = address;
                s.phone = phone;
                s.deptCode = deptCode;
                return true;
            }
        }
        return false;
    }

    public int getNoOfStudents(){
        return students.size();
    }

    public ArrayList<Student> getStudents(int skip, int limit){
        ArrayList<Student> result = new ArrayList<Student>();
        for (int i = skip; i < Math.min(skip+limit, students.size()); i++) {
            result.add(students.get(i));
        }
        return result;
    }

    public ArrayList<Department> getDepartments(){
        return departments;
    }


    public void loadDepartments(){
        // dummy data
        departments.add(new Department("CSE", "Computer Science and Engineering"));
        departments.add(new Department("ECE", "Electronics and Communication Engineering"));
        departments.add(new Department("EEE", "Electrical and Electronics Engineering"));
        departments.add(new Department("ME", "Mechanical Engineering"));
        departments.add(new Department("CE", "Civil Engineering"));
    }

    public void loadStudents() {
        // dummy data
        students.add(new Student(1, "CSE", "John", "Kolkata", "1234567890"));
        students.add(new Student(2, "ECE", "Jane2", "Kolkata", "1234567890"));
        students.add(new Student(3, "ME", "Jane3", "Kolkata", "1234567890"));
        students.add(new Student(4, "CE", "Jane4", "Kolkata", "1234567890"));

        students.add(new Student(5, "CSE", "John5", "Kolkata", "1234567890"));
        students.add(new Student(6, "ECE", "Jane25", "Kolkata", "1234567890"));
        students.add(new Student(7, "ME", "Jane35", "Kolkata", "1234567890"));
        students.add(new Student(8, "CE", "Jane45", "Kolkata", "1234567890"));

        students.add(new Student(9, "CSE", "John6", "Kolkata", "1234567890"));
        students.add(new Student(10, "ECE", "Jane26", "Kolkata", "1234567890"));
        students.add(new Student(11, "ME", "Jane36", "Kolkata", "1234567890"));
        students.add(new Student(12, "CE", "Jane46", "Kolkata", "1234567890"));

        students.add(new Student(13, "CSE", "John7", "Kolkata", "1234567890"));
        students.add(new Student(14, "ECE", "Jane27", "Kolkata", "1234567890"));
        students.add(new Student(15, "ME", "Jane37", "Kolkata", "1234567890"));
        students.add(new Student(16, "CE", "Jane47", "Kolkata", "1234567890"));

        students.add(new Student(17, "CSE", "John8", "Kolkata", "1234567890"));
        students.add(new Student(18, "ECE", "Jane28", "Kolkata", "1234567890"));
        students.add(new Student(19, "ME", "Jane38", "Kolkata", "1234567890"));
        students.add(new Student(20, "CE", "Jane48", "Kolkata", "1234567890"));

    }
}
