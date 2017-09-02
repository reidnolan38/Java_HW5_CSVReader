import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.*;

/** Created by errornosignal on 3/3/2017.
 * Reid_Nolan_HW5_[CSVReader]_[PROG 1403]
 * Main Class
 * Program for reading data stored in a .csv file
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
public class Main
{
    //declare and initialize class variables
    private final static int kTIMER_DELAY = 250;
    private final static String kANSI_RED = "\u001B[31m";
    private final static String kANSI_RESET = "\u001B[0m";

    /**main method
     * @param args args
     * @throws MaximumColumnsExceededException MCEEx
     * @throws IOException IOEx
     * @throws InterruptedException IEx
     * @throws EmptyFileException EFEx
     */
    public static void main(String[] args) throws MaximumColumnsExceededException, IOException, InterruptedException,
            EmptyFileException
    {
        //display program header
        displayProgramHeader();

        //noinspection InfiniteLoopStatement
        while (true)
        {
            //display main menu
            displayMainMenu();

            //try/catch block 1
            try
            {
                //get main menu selection
                String mainMenuSelection = getUserInput_String(menuSelectionPrompt());

                //main menu switch
                switch (mainMenuSelection)
                {
                    case "1":   //case 1. Specify a file to open and read it's contents into memory.
                    {
                        //prompt for input and assign next string to variable
                        String fileToOpen = getUserInput_String(fileToOpenPrompt());

                        //try/catch block 1.a
                        try (Scanner input = new Scanner(new BufferedReader(new FileReader(fileToOpen))))
                        {
                            //test file for content
                            if (input.hasNext())
                            {
                                //reset maxLength to reset column width formatting
                                CSVReader.setMaxLength(0);
                                //read file into memory
                                CSVReader.readFile(fileToOpen);
                            }
                            else
                            {
                                //close input file
                                input.close();
                                //throw custom exception for empty file
                                throw new EmptyFileException();
                            }
                        }
                        catch (IOException | MaximumColumnsExceededException | EmptyFileException IOEx)
                        {
                            //display error for thrown exception
                            System.err.println(IOEx.getMessage());
                            Thread.sleep(kTIMER_DELAY);
                            System.out.println();
                        }
                        break;
                    }
                    case "2":   //case 2. Display the file's contents.
                    {
                        //display file data formatted with header
                        CSVReader.printFile();
                        break;
                    }
                    case "3":   //case 3. Display the number of rows in the file.
                    {
                        //test for data in memory
                        int numRows = CSVReader.numberOfRows();
                        if (numRows == 0)
                        {
                            //display message for no data in memory
                            CSVReader.displayNoData();
                        }
                        else
                        {
                            //display number of rows in file
                            displayNumberOfRows();
                        }
                        break;
                    }
                    case "4":   //case 4. Display the number of fields in any row[(row)].
                    {
                        //test for data in memory
                        int numFields = CSVReader.numberOfFields(0);
                        if (numFields == 0)
                        {
                            //display message for no data in memory
                            CSVReader.displayNoData();
                        }
                        else
                        {
                            //try/catch block 4.a
                            try
                            {
                                //loop until user input is valid
                                boolean rowIsValid = false;
                                while (!rowIsValid)
                                {
                                    //try/catch block 4.b
                                    try
                                    {
                                        //prompt for input and assign next string to variable
                                        int selectedRow = (getUserInput_int(rowPrompt()));
                                        //validate input is in range
                                        if (selectedRow < CSVReader.numberOfRows() && selectedRow >= 0)
                                        {
                                            //set boolean to true to exit loop
                                            rowIsValid = true;
                                            //display number of fields in selected row
                                            displayFieldsInRow(selectedRow);
                                        }
                                        else
                                        {
                                            //display error message on search out of range
                                            noRowToShow();
                                        }
                                    }
                                    catch (InputMismatchException IMEx)
                                    {
                                        //display error message on invalid search
                                        noRowToShow();
                                        Thread.sleep(kTIMER_DELAY);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException IOBEx)
                            {
                                //display error for thrown exception
                                System.err.println(IOBEx.getMessage());
                                Thread.sleep(kTIMER_DELAY);
                                System.out.println();
                            }
                        }
                        break;
                    }
                    case "5":   //case 5. Get the contents of a specific field[(row),(column)].
                    {
                        //test for data in memory
                        String contField = CSVReader.field(0, 0);
                        if (contField.equals("0"))
                        {
                            //display message for no data in memory
                            CSVReader.displayNoData();
                        }
                        else
                        {
                            //try/catch block 5.a
                            try
                            {
                                //loop until user input is valid
                                boolean rowIsValid = false;
                                while (!rowIsValid)
                                {
                                    //try/catch block 5.b
                                    try
                                    {
                                        //prompt for input and assign next string to variable
                                        int selectedRow = getUserInput_int(rowPrompt());
                                        //validate input is in range
                                        if (selectedRow < CSVReader.numberOfRows() && selectedRow >= 0)
                                        {
                                            //get size of selected row
                                            int selectedRowSize = CSVReader.rowListSizes.get(selectedRow);
                                            //set boolean to true to exit loop
                                            rowIsValid = true;
                                            //loop until user input is valid
                                            boolean columnIsValid = false;
                                            while (!columnIsValid)
                                            {
                                                //try/catch block 5.c
                                                try
                                                {
                                                    //prompt for input and assign next string to variable
                                                    int selectedColumn = getUserInput_int(columnPrompt(selectedRowSize));
                                                    //validate input is in range
                                                    if (selectedColumn >= 0 && selectedColumn < selectedRowSize)
                                                    {
                                                        //set boolean to true to exit loop
                                                        columnIsValid = true;
                                                        //display contents of selected field
                                                        displayFieldContents(selectedRow, selectedColumn);
                                                    }
                                                    else
                                                    {
                                                        //display error message on search out of range
                                                        noColumnToShow();
                                                    }
                                                }
                                                catch (InputMismatchException IMEx)
                                                {
                                                    //display error message on invalid search
                                                    noColumnToShow();
                                                    Thread.sleep(kTIMER_DELAY);
                                                }
                                            }
                                        }
                                        else
                                        {
                                            //display error message on search out of range
                                            noRowToShow();
                                        }
                                    }
                                    catch (InputMismatchException IMEx)
                                    {
                                        //display error message on invalid search
                                        noRowToShow();
                                        Thread.sleep(kTIMER_DELAY);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException IOBEx)
                            {
                                //display error for thrown exception
                                System.err.println(IOBEx.getMessage());
                                Thread.sleep(kTIMER_DELAY);
                                System.out.println();
                            }
                        }
                        break;
                    }
                    case "6":   //case 6. Exit the program.
                    {
                        //display message and terminate program
                        exitProgram();
                    }
                    default:    //default case
                    {
                        //displays error message on invalid selection
                        displayInvalidSelection();
                    }
                }
            } catch (InputMismatchException IMEx)
            {
                //display error message on invalid selection
                displayInvalidSelection();
                //display error for thrown exception
                System.err.println(IMEx.getMessage());
                Thread.sleep(kTIMER_DELAY);
                System.out.println();
            }
        }
    }

    /**
     * displays program header
     */
    private static void displayProgramHeader()
    {
        System.out.println("Reid_Nolan_HW_5_[CSVReader]_[PROG_1403]" + "\n");
    }

    /**
     * displays main menu
     */
    private static void displayMainMenu()
    {
        System.out.println("-----Main Menu-----");
        System.out.println("1. Specify a file to open and read it's contents into memory.");
        System.out.println("2. Display the file's contents.");
        System.out.println("3. Display the number of rows in the file.");
        System.out.println("4. Display the number of fields in any row[(row)].");
        System.out.println("5. Get the contents of a specific field[(row),(column)].");
        System.out.println("6. Exit the program.");
    }

    /**
     * displays error message on row selection does not exist
     */
    private static void noRowToShow()
    {
        System.out.println(get_kANSI_RED() + "Error! Specified row does not exist." + get_kANSI_RESET());
    }

    /**
     * displays error message on column selection does not exist
     */
    private static void noColumnToShow()
    {
        System.out.println(get_kANSI_RED() + "Error! Specified column does not exist." + get_kANSI_RESET());
    }

    /**
     * displays error message on invalid selection
     */
    private static void displayInvalidSelection()
    {
        System.out.println(get_kANSI_RED() + "Error! Invalid selection. Try again." +  get_kANSI_RESET() + "\n");
    }

    /**
     * displays number of rows in input file with formatting
     */
    private static void displayNumberOfRows()
    {
        System.out.println("\n" + "Number of rows in current file: " + CSVReader.numberOfRows() + "\n");
    }

    /**
     * displays number of fields in selected row of input file with formatting
     * @param selectedRow selectedRow
     */
    private static void displayFieldsInRow(int selectedRow)
    {
        System.out.println("\n" + "Number of fields in selected row[" + selectedRow + "]: "
                + CSVReader.numberOfFields(selectedRow) + "\n");
    }

    /**
     * displays contents of selected field in input file with formatting
     * @param selectedRow selectedRow
     * @param selectedColumn selectedColumn
     */
    private static void displayFieldContents(int selectedRow, int selectedColumn)
    {
        System.out.println("\n" + "Contents of selected field[(" + selectedRow + "),(" + selectedColumn + ")]: ["
                + CSVReader.field(selectedRow, selectedColumn) + "]" + "\n");
    }

    /**
     * displays program exiting message and exits program
     */
    private static void exitProgram()
    {
        System.out.println("exiting program...");
        System.exit(1);
    }

    /**
     * gets kTIMER_DELAY value
     * @return kTIMER_DELAY
     */
    @Contract(pure = true)
    static int get_kTIMER_DELAY()
    {
        return kTIMER_DELAY;
    }

    /**
     * gets kANSI_RED value
     * @return ANSI_RED
     */
    @Contract(pure = true)
    static String get_kANSI_RED()
    {
        return kANSI_RED;
    }

    /**
     * gets kANSI_RESET value
     * @return ANSI_RESET
     */
    @Contract(pure = true)
    static String get_kANSI_RESET()
    {
        return kANSI_RESET;
    }

    /**
     * gets make a selection prompt
     * @return "Make a selection"
     */
    @NotNull
    @Contract(pure = true)
    private static String menuSelectionPrompt()
    {
        return "Make a selection> ";
    }

    /**
     * gets file to open prompt
     * @return "Specify file to open"
     */
    @NotNull
    @Contract(pure = true)
    private static String fileToOpenPrompt()
    {
        return "Specify file to open> ";
    }

    /**
     * gets row selection prompt
     * @return "Enter a row number(0-" + (CSVReader.numberOfRows() - 1) + ")"
     */
    @NotNull
    @Contract(pure = true)
    private static String rowPrompt()
    {
        return "Enter a row number(0-" + (CSVReader.numberOfRows() - 1) + ")> ";
    }

    /**
     * gets column selection prompt
     * @param selectedRowSize selectedRowSize
     * @return "Enter a column number(0-" + (selectedRowSize-1) + ")"
     */
    @NotNull
    @Contract(pure = true)
    private static String columnPrompt(int selectedRowSize)
    {
        return "Enter a column number(0-" + (selectedRowSize-1) + ")> ";
    }

    /**
     * gets String user input from scanner
     * @param prompt prompt
     * @return inputString
     */
    private static String getUserInput_String(String prompt)
    {
        //create new scanner
        Scanner sc = new Scanner(System.in);
        String inputString = "";
        //loop until string is valid
        boolean stringIsValid = false;
        while(!stringIsValid)
        {
            //prompt for input
            System.out.print(prompt);
            //assign next scanner input string to variable
            inputString = sc.nextLine();
            //verify input is entered
            if (inputString.isEmpty())  //if no input entered
            {
                //do nothing
            }
            else    //if input entered
            {
                //set boolean to true to exit loop
                stringIsValid = true;
            }
        }
        return inputString;
    }

    /**
     * gets integer user input from scanner
     * @param prompt  prompt
     * @return inputInt
     * @throws InterruptedException NFEx
     */
    private static int getUserInput_int(String prompt) throws InterruptedException
    {
        //create new scanner
        Scanner sc = new Scanner(System.in);
        //declare and initialize local variable
        int inputInt = 0;

        //loop until input string is entered
        boolean stringIsValid = false;
        while(!stringIsValid)
        {
            try
            {
                //prompt for input
                System.out.print(prompt);
                //assign next scanner input string to variable
                String inputString = sc.nextLine();
                //verify input is entered
                if (inputString.isEmpty())  //if no input entered
                {
                    //do nothing
                }
                else    //if input entered
                {
                    //convert string to integer
                    inputInt = Integer.parseInt(inputString);
                    //set boolean to true to exit loop
                    stringIsValid = true;
                }
            }
            catch(NumberFormatException NFEx)
            {
                //display error message on invalid selection
                displayInvalidSelection();
                Thread.sleep(kTIMER_DELAY);
            }
        }
        return inputInt;
    }
}