import org.jetbrains.annotations.Contract;

/** Created by errornosignal on 3/3/2017.
 * Reid_Nolan_HW5_[CSVReader]_[PROG 1403]
 * MaximumColumnsExceededException Class
 * Custom exception class for input file exceeding maximum allowable columns
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
class MaximumColumnsExceededException extends Exception
{
    private static final int kMAX_COLUMNS = 10;

    /**
     * returns MaximumColumnsExceededException error message
     */
    MaximumColumnsExceededException()
    {
        super("Error! The maximum number of columns [" + kMAX_COLUMNS +"] has been exceeded" + "\n"
                + "Reformat the file and re-try.");
    }

    /**
     * gets kMAX_COLUMNS value
     * @return kMAX_COLUMNS
     */
    @Contract(pure = true)
    static int get_kMAX_COLUMNS()
    {
        return kMAX_COLUMNS;
    }
}