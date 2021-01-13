package lesson01.error;

/**
 * @author Webb Dong
 * @description: 模拟因类循环引用导致的 StackOverflowError 的情况
 * @date 2021-01-13 11:12
 */
public class ClassCircularityCauseStackOverflowErrorCase {

    private static class Student {

        private Teacher teacher;

        public Student() {
            this.teacher = new Teacher();
        }

    }

    private static class Teacher {

        private Student student;

        public Teacher() {
            this.student = new Student();
        }

    }

    public static void main(String[] args) {
        // 类循环引用导致的 StackOverflowError
        Student s1 = new Student();
        Teacher t1 = new Teacher();
    }

}
