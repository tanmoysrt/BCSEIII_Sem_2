public class Department {
    final String deptCode;
    final String name;

    public Department(String deptCode, String name) {
        this.deptCode = deptCode;
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
