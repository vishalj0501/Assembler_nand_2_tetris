package com.assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import javax.swing.plaf.synth.SynthSplitPaneUI;

class Assembler_21178 {

    static Map<String, String> dest = new HashMap<>() {
        {

            put("null", "000");
            put("M", "001");
            put("D", "010");
            put("MD", "011");
            put("A", "100");
            put("AM", "101");
            put("AD", "110");
            put("AMD", "111");
            put("", "000");

        }
    };

    static Map<String, String> jump = new HashMap<>() {
        {

            put("null", "000");
            put("JGT", "001");
            put("JEQ", "010");
            put("JGE", "011");
            put("JLT", "100");
            put("JNE", "101");
            put("JLE", "110");
            put("JMP", "111");
            put("", "000");

        }
    };

    static Map<String, String> comp = new HashMap<>() {
        {
            put("0", "0101010");
            put("1", "0111111");
            put("-1", "0111010");
            put("D", "0001100");
            put("A", "0110000");
            put("!D", "0001101");
            put("!A", "0110001");
            put("-D", "0001111");
            put("-A", "0110011");
            put("D+1", "0011111");
            put("A+1", "0110111");
            put("D+A", "0000010");
            put("A+D", "0000010");
            put("D-A", "0010011");
            put("A-D", "0000111");
            put("D&A", "0000000");
            put("A&D", "0000000");
            put("D|A", "0010101");
            put("A|D", "0010101");
            put("M", "1110000");
            put("!M", "1110001");
            put("-M", "1110011");
            put("M+1", "1110111");
            put("M-1", "1110010");
            put("D+M", "1000010");
            put("M+D", "1000010");
            put("D-M", "1010011");
            put("M-D", "1000111");
            put("D&M", "1000000");
            put("M&D", "1000000");
            put("D|M", "1010101");
            put("M|D", "1010101");
            put("D-1", "0001110");
            put("A-1", "0110010");
            put("", "0000000");
            put("null", "0000000");
        }
    };

    static HashMap<String, String> numbers = new HashMap<>() {
        {
            put("R0", bin(0));
            put("R1", bin(1));
            put("R2", bin(2));
            put("R3", bin(3));
            put("R4", bin(4));
            put("R5", bin(5));
            put("R6", bin(6));
            put("R7", bin(7));
            put("R8", bin(8));
            put("R9", bin(9));
            put("R10", bin(10));
            put("R11", bin(11));
            put("R12", bin(12));
            put("R13", bin(13));
            put("R14", bin(14));
            put("R15", bin(15));
            put("SCREEN", bin(16384));
            put("KBD", bin(24576));
            put("SP", bin(0));
            put("LCL", bin(1));
            put("ARG", bin(2));
            put("THIS", bin(3));
            put("THAT", bin(4));
        }
    };

    static HashMap<String, String> labels = new HashMap<>();
    static HashMap<String, String> variables = new HashMap<>();

    public static String bin(int num) {
        return Integer.toBinaryString(0x10000 | num).substring(1);
    }

    public static String whitespace(String line)  {

        line = line.trim();

        line = line.replaceAll(" ", "").trim().concat("\n").replaceAll("(?m)^[ \t]*\r?\n", "");

        if (line.length() > 1) {
            if (line.charAt(0) == '/') {

                // System.out.println(line.length() + "a");
                return "";
            }

            else {
                if (line.contains("//")) {
                    line = line.substring(0, line.indexOf("//"));
                    // System.out.println(line.length() + "b");
                    return line;
                }

                else {
                    // System.out.println(line.length() + "c");
                    return line;
                }
            }
        }

        else {
            // System.out.println(line.length() + "d");
            return "";
        }

    }

    public static String ains(String line) {

        String out = "";

        line = line.substring(1).trim();

        if (Character.isDigit(line.charAt(0))) {
            out = bin(Integer.parseInt(line));
        }

        else if (numbers.containsKey(line)) {

            out = numbers.get(line);
        }

        else if (labels.containsKey(line)) {
            out = labels.get(line);
        }

        else {
            if (variables.containsKey(line)) {
                out = variables.get(line);
            }

            else {
                variables.put(line, bin(variables.size() + 16));
                out = variables.get(line);
            }

        }

        return out;

    }

    public static String cins(String line) {

        line = line.trim();

        String dest_ = "";
        String comp_ = "";
        String jmp_ = "";

        if (line.contains(";") & line.contains("=")) {

            comp_ = line.substring(line.indexOf("=") + 1, line.indexOf(";"));
            dest_ = line.substring(0, line.indexOf("="));
            jmp_ = line.substring(line.indexOf(";") + 1);

        }

        else if (line.contains("=")) {

            dest_ = line.substring(0, line.indexOf("="));
            comp_ = line.substring(line.indexOf("=") + 1);

        }

        else {

            comp_ = line.substring(0, line.indexOf(";"));
            jmp_ = line.substring(line.indexOf(";") + 1);

        }

        return "111" + comp.get(comp_) + dest.get(dest_) + jump.get(jmp_);

    }

    public static void main(String[] args) {

        try {
            File file = new File("test.asm");
            Scanner reader = new Scanner(file);

            int count = 0;

            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                data = whitespace(data);

                if (data.length() > 1) {

                    if (data.charAt(0) == '(') {
                        labels.put(data.substring(1, data.length() - 2), bin(count));
                        continue;
                    }
                }

                else {
                    continue;
                }

                count++;

            }

            reader.close();

        }

        catch (FileNotFoundException e) {
            System.out.println("Error");
        }

        // ----------- Second iteration

        try {
            File file = new File("/Users/vishu/IdeaProjects/EOC/src/com/company/project.asm");
            File file2 = new File("BinaryOut.hack");
            FileOutputStream fileWriter = new FileOutputStream(file2);

            Scanner reader = new Scanner(file);
            PrintWriter writer = new PrintWriter(fileWriter);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                data = whitespace(data);

                if (data.length() > 1) {

                    if (data.charAt(0) == '@') {
                        writer.println(ains(data));
                    }

                    else if (data.charAt(0) == '(') {

                    }

                    else {
                        writer.println(cins(data));
                    }
                }

            }

            writer.close();
            reader.close();

        }

        catch (FileNotFoundException e) {
            System.out.println("Error");
        }

    }
}