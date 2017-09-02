import org.jetbrains.annotations.Contract;
import java.io.*;
import java.util.*;

/** Created by errornosignal on 3/3/2017.
 * Reid_Nolan_HW5_[CSVReader]_[PROG 1403]
 * CSVReader Class
 * Class for reading data stored in a .csv file
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
class CSVReader
{
    //declare and initialize class variables
    private static int lineCount = 0;
    private static int maxLength = 0;

    //create ArrayLists to hold data from input file
    private static ArrayList<List<String>> mainList = new ArrayList<>();
    static List<Integer> rowListSizes = new ArrayList<>();

    /**
     * reads file into memory and parses data
     * @param fileToOpen fileToOpen
     * @throws MaximumColumnsExceededException MCEEx
     * @throws IOException IOEx
     * @throws InterruptedException IEx
     */
    static void readFile(String fileToOpen) throws MaximumColumnsExceededException, IOException, InterruptedException
    {
        //clear ArrayLists
        mainList.clear();
        rowListSizes.clear();
        //initialize counter for rows
        lineCount = 0;

        //try/catch block
        try (Scanner input = new Scanner(new BufferedReader(new FileReader(fileToOpen))))
        {
            //specify delimiter
            input.useDelimiter(",");

            //get input until end of file
            while (input.hasNextLine())
            {
                //create new ArrayList
                List<String> rowList = new ArrayList<>();

                //add next line to string
                String s1 = input.nextLine();

                //split string into separate elements, preserving protected fields, and add to array
                String[] s1_string = s1.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                //remove leading and trailing double quotes from string, preserving internal double quotes and commas
                for (int i = 0; i < s1_string.length; i++)
                {
                    if (s1_string[i].startsWith("\""))
                    {
                        s1_string[i] = s1_string[i].substring(1, s1_string[i].length());
                    }
                    else
                    {
                        //do nothing
                    }
                    if (s1_string[i].endsWith("\""))
                    {
                        s1_string[i] = s1_string[i].substring(0, s1_string[i].length() - 1);
                    }
                    else
                    {
                        //do nothing
                    }
                }

                //throw custom exception for max columns exceeded
                if(s1_string.length > MaximumColumnsExceededException.get_kMAX_COLUMNS())
                {
                    throw new MaximumColumnsExceededException();
                }
                else
                {
                    //do nothing
                }

                //add string elements to list and update maxLength
                for (String element: s1_string) //(int i = 0; i < s1_string.length; i++)
                {
                    //add string elements to list
                    rowList.add(element);

                    //update variable for longest string element length (used for output formatting)
                    int newNumber = element.length();
                    int maxLength = getMaxLength();
                    if(newNumber > maxLength)
                    {
                        //set maxLength
                        setMaxLength(newNumber);
                    }
                }

                //add new row to main list
                mainList.add(new ArrayList<>(rowList));
                //increment line count
                lineCount++;
                //add row size to parallel array
                rowListSizes.add(rowList.size());
                rowList.clear();
            }
            //close input file
            input.close();
            //displays file read successfully status message
            displayFileReadConfirmation();
        }
        catch(MaximumColumnsExceededException MCEEx)
        {
            //display error for thrown exception
            System.err.println(MCEEx.getMessage());
            Thread.sleep(Main.get_kTIMER_DELAY());
            System.out.println();
        }
    }

    /**
     * prints formatted file contents to console
     */
    static void printFile()
    {
        //test for data in memory
        if(lineCount > 0)
        {
            //display file data formatted with header
            displayDataOutputHeader();
            System.out.println("//Beginning of file//");
            for (int i = 0; i <= (lineCount - 1); i++)
            {
                System.out.print("| ");
                for (int j = 0; j <= (rowListSizes.get(i) - 1); j++)
                {
                    //format output
                    String format = "%1$" + (maxLength + 3) + "s";
                    String result = String.format(format, mainList.get(i).get(j) + " | ");
                    System.out.print(result);
                }
                System.out.println();
            }
            System.out.println("//End of file//");
            System.out.println();
        }
        else
        {
            //display message for no data in memory
            displayNoData();
        }
    }

    /**
     * gets number of rows in input file
     * @return lineCount
     */
    @Contract(pure = true)
    static int numberOfRows()
    {
        if (lineCount > 0)
        {
            return lineCount;
        }
        else
        {
            return 0;
        }
    }

    /**
     * gets number of fields in selected row of input file
     * @param row row
     * @return mainList.get(row).size()
     */
    @Contract(pure = true)
    static int numberOfFields(int row)
    {
        if(lineCount > 0)
        {
            return mainList.get(row).size();
        }
        else
        {
            return 0;
        }
    }

    /**
     * gets contents of selected field in input file
     * @param row row
     * @param column column
     * @return mainList.get(row).get(column)
     */
    static String field(int row, int column)
    {
        if(lineCount > 0)
        {
            return mainList.get(row).get(column);
        }
        else
        {
            return "0";
        }
    }

    /**
     * displays no data in memory status message
     */
    static void displayNoData()
    {
        System.out.println("\n" + Main.get_kANSI_RED() + "Error! No data exists in memory." + "\n" +
                "Select a file to load into memory with option 1." + Main.get_kANSI_RESET()+ "\n");
    }

    /**
     * displays file read successfully status message
     */
    private static void displayFileReadConfirmation()
    {
        System.out.println("\n" + "The selected file has been successfully read." + "\n");
    }

    /**
     * displays data output header
     */
    private static void displayDataOutputHeader()
    {
        System.out.println("\n" + "The file contains the following data:");
    }

    /**
     * sets maxLength
     * @param newLength newLength
     */
    static void setMaxLength(int newLength)
    {
        maxLength = newLength;
    }

    /**
     * gets maxLength
     * @return maxLength
     */
    @Contract(pure = true)
    private static int getMaxLength()
    {
        return maxLength;
    }
}