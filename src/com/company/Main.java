package com.company;

public class Main {
    public static void main(String[] args) {
        Application app = new Application(args, "C:\\Users\\Anastasiya\\Desktop\\prop.txt");
        System.out.println(app.integerValue("b"));
    }
}
