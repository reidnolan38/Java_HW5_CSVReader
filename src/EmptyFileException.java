/** Created by errornosignal on 3/3/2017.
 * Reid_Nolan_HW5_[CSVReader]_[PROG 1403]
 * EmptyFileException Class
 * Custom exception class for empty input file
 * @author Reid Nolan
 * @since 04/07/2017
 * @version 1.0
 */
class EmptyFileException extends Exception
{
    /**
     * returns EmptyFileException error message
     */
    EmptyFileException()
    {
        super("\n" + "Error! The selected input file is empty. Choose another file.");
    }
}