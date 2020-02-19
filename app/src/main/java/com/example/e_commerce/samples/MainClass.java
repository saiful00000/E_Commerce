package com.example.e_commerce.samples;

public class MainClass {

    public static class Test<T> {
        T obj;

        public Test(T obj){
            this.obj = obj;
        }

        public T getObject(){
            return this.obj;
        }
    }

    public static class Test2<T, U>{
        T obj1;
        U obj2;

        public Test2(T obj1, U obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }

        public void print() {
            System.out.println(obj1);
            System.out.println(obj2);
        }
    }


    public static void main(String[] args) {
        // string type
        Test<String> str = new Test<String>("Hello Generics");
        System.out.println(str.getObject());

        // integer type
        Test<Integer> inte = new Test<>(100);
        System.out.println(inte.getObject());

        Test2<String, Integer> obj3 = new Test2<>("Saiful", 161003);
        obj3.print();
    }
}
