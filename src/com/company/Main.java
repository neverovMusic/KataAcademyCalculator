package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        try{
            System.out.println("Введите математическое выражение:");
            String s = userInput.nextLine();
            System.out.print(calc(s));
        }
        catch(CustomException e){
            System.out.println("Выполнение метода было прервано");
            e.printStackTrace();
        }
    }

    public static String calc(String input) throws CustomException{
        int answer = 0;
        boolean isRoman = false;
        for(int i = 0; i < input.length(); i++){
            if(!((int)input.charAt(i)>47)&&!((int)input.charAt(i)<58)){
                if(input.charAt(i) != 'I' || input.charAt(i) != 'V' || input.charAt(i) != 'X') {
                    if (input.charAt(i) != '+' && input.charAt(i) != '-' && input.charAt(i) != '*' && input.charAt(i) != '/') {
                        throw new CustomException("throws Exception //т.к. в выражении есть неподдерживаемые символы");
                    }
                }
            }
        }
        if(!input.matches(".*[1234567890].*") && !input.contains("I") && !input.contains("V") && !input.contains("X")){
            throw new CustomException("throws Exception //т.к. в выражении нет поддерживаемых символов");
        }
        if(input.contains("I") || input.contains("V") || input.contains("X")){
            isRoman = true;
            for(int i = 0; i < input.length(); i++){
                if(Character.isDigit(input.charAt(i))){
                    throw new CustomException("throws Exception //т.к. используются одновременно разные системы счисления");
                }
            }
            if(input.contains("-")){
                throw new CustomException("throws Exception //т.к. в римской системе нет отрицательных чисел");
            }
        }
        input = input.replaceAll("\\s+","");
        input = input.replaceAll("[+]" ," + ");
        input = input.replaceAll("[-]" ," + -");
        input = input.replaceAll("[/]" ," / ");
        input = input.replaceAll("[*]" ," * ");

        String [] expression = input.split(" ");
        if(isRoman){
            expression[0] = String.valueOf(romanToDecimal(expression[0]));
            expression[2] = String.valueOf(romanToDecimal(expression[2]));
        }

        if(Integer.parseInt(expression[0])>10 || Integer.parseInt(expression[2]) > 10){
            throw new CustomException("throws Exception //т.к. поддерживаются только числа до 10 включительно");
        }

        if(!expression[0].matches(".*[1234567890].*") || !expression[2].matches(".*[1234567890].*")){
            throw new CustomException("throws Exception //т.к. строка не является математической операцией");
        }
        if(!expression[1].equals("+") && !expression[1].equals("*") && !expression[1].equals("/") && !expression[1].equals("-")){
            throw new CustomException("throws Exception //т.к. строка не является математической операцией");
        }

        if(expression.length > 3){
            throw new CustomException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

       // System.out.println(expression[0] + " " + expression[1] + " " + expression[2] + " = ");

        if(expression[1].equals("+")){ answer = Integer.parseInt(expression[0]) + Integer.parseInt(expression[2]); }
        if(expression[1].equals("*")){ answer = Integer.parseInt(expression[0]) * Integer.parseInt(expression[2]); }
        if(expression[1].equals("/")){ answer = Integer.parseInt(expression[0]) / Integer.parseInt(expression[2]); }

        if(isRoman){
            int[] values = {100,90,50,40,10,9,5,4,1};
            String[] romanLiterals = {"C","XC","L","XL","X","IX","V","IV","I"};

            StringBuilder romanAnswer = new StringBuilder();

            for(int i=0;i<values.length;i++) {
                while(answer >= values[i]) {
                    answer -= values[i];
                    romanAnswer.append(romanLiterals[i]);
                }
            }
            return romanAnswer.toString();
        }
        return  String.valueOf(answer);
    }

    static int value(char r) {
        if (r == 'I') return 1;     if (r == 'V') return 5;
        if (r == 'X') return 10;    if (r == 'L') return 50;
        if (r == 'C') return 100;   if (r == 'D') return 500;
        if (r == 'M') return 1000;  return -1;
    }

    static int romanToDecimal(String str) {
        int result = 0;
        for (int i = 0; i < str.length(); i++) {
            int s1 = value(str.charAt(i));
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));
                if (s1 >= s2) { result = result + s1; }
                else {
                    result = result + s2 - s1;
                    i++;
                }
            }
            else { result = result + s1; }
        }
        return result;
    }
}

