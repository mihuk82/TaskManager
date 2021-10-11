

import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args){
        String[][] tasksArray = readTasks1("tasks.csv");
//        for (String[] x:tasksArray) {
//            for(String y:x){
//                System.out.println(y);
//            }

        String selectedOption = "";
        while(!"exit".equals(selectedOption)){
            selectedOption = selectOption();
            switch(selectedOption){
                case "add":
                    tasksArray=addTask(tasksArray);
                    break;
                case "remove":
                    tasksArray=remove(tasksArray);
                    break;
                case "list":
                    list(tasksArray);
                    break;
                case "exit":
                    exit(tasksArray,"tasks.csv");
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }

    }

    public static String selectOption(){
        System.out.print(ConsoleColors.BLUE);
        System.out.println("Please select an option: ");
        System.out.print(ConsoleColors.RESET);
        String[] options = {"add", "remove", "list", "exit"};
        for(String x : options){
            System.out.println(x);
        }
        Scanner scanner = new Scanner(System.in);
        String selectedOption = scanner.next();
        return selectedOption;
    }

    public static String[][] readTasks(String fileName){
        File file = new File(fileName);
        String[][] temp = new String[1][1];

        try {
            Scanner scanner = new Scanner(file);

            long lineCount = Files.lines(Paths.get(fileName)).count();
            String[][] fileArr = new String[(int) lineCount][];
            while (scanner.hasNextLine()){
                for(int i=0;i<lineCount;i++){
                    fileArr[i] = scanner.nextLine().split(",");
                }
            }
            scanner.close();
            return fileArr;
        } catch (IOException e) {
            System.out.println("File not found");

        }
        return temp;

    }

    public static String[][] readTasks1(String fileName){
        Path path1 = Paths.get(fileName);
        int i=0;
        String[][] tasksArray = new String[0][];
        try {
            for(String line : Files.readAllLines(path1)){
                tasksArray = Arrays.copyOf(tasksArray, tasksArray.length + 1);
                tasksArray[i] = line.split(",");
                tasksArray[i][0] = tasksArray[i][0].trim();
                tasksArray[i][1] = tasksArray[i][1].trim();
                tasksArray[i][2] = tasksArray[i][2].trim();
                i++;
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return tasksArray;
    }

    public static String[][] addTask(String[][] tasksArray){
        Scanner scanner = new Scanner(System.in);
        tasksArray = Arrays.copyOf(tasksArray, tasksArray.length + 1);
        tasksArray[tasksArray.length-1] = new String[3];
        System.out.println("Please add task description:");
        tasksArray[tasksArray.length-1][0] = scanner.nextLine();
        while ("".equals(tasksArray[tasksArray.length-1][0])){
            System.out.println("Description can not be empty. Please add task description:");
            tasksArray[tasksArray.length-1][0] = scanner.nextLine();
        }
        System.out.println("Please add task due day:");
        tasksArray[tasksArray.length-1][1] = scanner.nextLine();
        while(!isDate(tasksArray[tasksArray.length-1][1]) || "".equals(tasksArray[tasksArray.length-1][1])){
            System.out.println("Incorrect date format. Please add task due day:");
            tasksArray[tasksArray.length-1][1] = scanner.nextLine();
        }
        System.out.println("Is your task important (y/n):");
        if("y".equals(scanner.nextLine())){
            tasksArray[tasksArray.length-1][2] = "true";
        }
        else if("n".equals(scanner.nextLine())){
            tasksArray[tasksArray.length-1][2] = "false";
        }
        return tasksArray;
    }
    public static void list(String[][] tasksArray){
        Scanner scanner = new Scanner(System.in);
        while(!"q".equals(scanner.next())){
            for(int i=0; i< tasksArray.length; i++){
                if("true".equals(tasksArray[i][2])){
                    System.out.print(ConsoleColors.RED);
                    System.out.println(i + " : " + tasksArray[i][0] + " " + tasksArray[i][1] + " " + tasksArray[i][2]);
                    System.out.print(ConsoleColors.RESET);
                }
                else{
                    System.out.print(ConsoleColors.YELLOW);
                    System.out.println(i + " : " + tasksArray[i][0] + " " + tasksArray[i][1] + " " + tasksArray[i][2]);
                    System.out.print(ConsoleColors.RESET);
                }

            }
        }

    }
    public static String[][] remove(String[][] tasksArray){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove or 'q' to quit:");
        String toRemove = scanner.nextLine();
        String[][] tasksAfterRemove = new String[tasksArray.length - 1][];
        while (!"q".equals(toRemove)){
            for (int i=0, j=0; i<tasksArray.length; i++){
                try{
                    if(i==Integer.parseInt(toRemove)){
                        continue;
                    }
                    else {
                        tasksAfterRemove[j]=tasksArray[i];
                        j++;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Incorect argument passed. Please give a number greater or equal 0");
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Index out of bounds. Please give a number greater or equal 0");
                }

            }
            System.out.println("Please select number to remove or 'q' to quit:");
            toRemove = scanner.nextLine();

        }
        return tasksAfterRemove;


    }
    public static void exit(String[][] tasksArray, String fileName){
        Path path1 = Paths.get(fileName);
        List<String> tasksList = new ArrayList<>();
        for(int i=0; i<tasksArray.length; i++){
            tasksList.add(tasksArray[i][0] + ", " + tasksArray[i][1] + ", " + tasksArray[i][2]);
        }
        try {
            Files.write(path1, tasksList);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
    public static boolean isDate(String tasksDate){
        try {
            LocalDate.parse(tasksDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
