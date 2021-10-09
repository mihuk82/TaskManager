

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args){
        String[][] tasksArray = readTasks("tasks.csv");
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
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }

    }

    public static String selectOption(){
        //System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: ");
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
    public static String[][] addTask(String[][] tasksArray){
        System.out.println("Please add task description:");
        Scanner scanner = new Scanner(System.in);
        tasksArray = Arrays.copyOf(tasksArray, tasksArray.length + 1);
        tasksArray[tasksArray.length-1] = new String[3];
        tasksArray[tasksArray.length-1][0] = scanner.nextLine();
        System.out.println("Please add task due day:");
        tasksArray[tasksArray.length-1][1] = scanner.nextLine();
        System.out.println("Is your task important:");
        tasksArray[tasksArray.length-1][2] = scanner.nextLine();
        return tasksArray;
    }
    public static void list(String[][] tasksArray){
        for(int i=0; i< tasksArray.length; i++){
            System.out.println(i + " : " + tasksArray[i][0] + " " + tasksArray[i][1] + " " + tasksArray[i][2]);
        }
    }
    public static String[][] remove(String[][] tasksArray){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove:");
        int toRemove = scanner.nextInt();
        String[][] tasksAfterRemove = new String[tasksArray.length - 1][];
        for (int i=0, j=0; i<tasksArray.length; i++){
            if(i==toRemove){
                continue;
            }
            else {
                tasksAfterRemove[j]=tasksArray[i];
                j++;
            }
        }
        return tasksAfterRemove;
    }
}
