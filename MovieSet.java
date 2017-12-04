package csudh.leicao;


import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Lei Cao on 9/13/17.
 */

public class MovieSet {
    /* capacity: give a pre-set capacity 35, so it allows only 5 extra movies to be added by users
     after the 30 movies in the system */
    private int capacity = 35;
    // generatedNo: system generated 30-Movie movieSet as required
    private int generatedNo = 30;
    // size: the number of Movie objects in the movieset. will be increased when new Movie is added by user
    private int size = generatedNo;
    private Movie[] movieSet = new Movie[capacity];
    private String[] genres = {"Comedy", "Drama", "Sci-Fi", "Action", "Documentary"};
    private Random rand = new Random();
    // in order to avoid "magic numbers", all numbers with substantial meanings are assigned to a variable
    private int yearMin = 1920;
    private int yearMax = 2017;
    private int runtimeMin = 60;
    private int runtimeMax = 200;
    // the first index for the user-input Movie objects.
    private int addAtIndex =generatedNo;

    public MovieSet(){

        /* Generating a yearList with all the years added in ascending order;
         will randomly remove and return an element later in order to randomly select a year without duplication. */
        ArrayList<Integer> yearList = new ArrayList<Integer>();
        for(int i=0; i<(yearMax-yearMin+1); i++){
            yearList.add(i+yearMin);//populate the yearList with the years 1920-2017
        }

        //populate the Movie movieSet with 30 Movies.
        int k = 0;
        for(int i=0; i < generatedNo; i++){
            //Assign genres to the movies one by one; if it comes to the end of the genres array, go back to index(0) element.
            //This way ensures that no genres are assigned to more than 10 movies.
            String genre = genres[ k = (k < genres.length) ? k : 0 ]; //simplified if statement
            k++;

            //adding new Movie objects
            movieSet[i]= new Movie("Movie"+(i+1), // assign movie name in the sequence Movie1, Movie2...
                    yearList.remove(rand.nextInt(yearList.size())), // randomly return and remove an element in yearList.
                    rand.nextInt(runtimeMax) + runtimeMin, //randomly generate the runtime
                    genre, //genre is pre-selected above in line 43
                    (rand.nextInt(100) + 1)/10.0 ); /* randomly generate the ratings 0.1 - 10.0
                                                                    rounded to two decimals*/
        }
    }


    protected boolean addMovie(String name, int year, int runtime, String genre, double rating){
        //This method allows a user to add Movies
        if(addAtIndex <35){ //check if the number of Movies is over the limit of 35
            movieSet[addAtIndex]= new Movie(name,year,runtime,genre,rating);
            System.out.println("The new movie has been added successfully");
            printMovie(addAtIndex);
            size++;
            addAtIndex++;
            return true;
        }

        return false;
    }

//    private boolean checkInput(String name, int year, int runtime, String genre, double rating){
//        //This method checks the validity of user's input
//        //Wait until the Scanner is implemented.
//        return true;
//    }

    protected boolean printAll(){
        //print all elements in the movieset
        System.out.println("Name, Year, Genre, Runtime, Rating");//print column names first
        for(int i = 0; i<size; i++){//
            System.out.println(movieSet[i].getName() + ", " + movieSet[i].getYear() + ", " + movieSet[i].getGenre()
                    + ", " + movieSet[i].getRuntime() + " mins, " + movieSet[i].getRating());
        }
        System.out.println();
        return true;
    }

    protected boolean printMovie(int i){
        //print all details of one Movie object
        System.out.println(movieSet[i].getName() + ", " + movieSet[i].getYear() + ", " + movieSet[i].getGenre()
                + ", " + movieSet[i].getRuntime() + " mins, " + movieSet[i].getRating());
        return true;
    }

    /* The original merge sort code is from http://www.geeksforgeeks.org/merge-sort/
       and I revised it to fit my project (I put my name "lei" in a comment at the end of every line I added)
       Because the merge sort code can only sort int[] and we have a Movie[],
       in Menu class's callSortMethod() I create an int[] and copy an array of elements that need to be sorted (e.g. Year or Rating).
       So whenever the elements in the arr[] swaps, the elements in the movieSet[] with the corresponding index are swapped too.
       In this way the Movie[] is being sorted while arr[] is being sorted */

    /*the merge sort algorithm includes two parts: merge() and sort()*/
    private void merge(int arr[], int l, int m, int r)
    {
        // Merges two subarrays of arr[].
        // First subarray is arr[l..m]
        // Second subarray is arr[m+1..r]

        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int [n1];
        Movie Lmovie[] = new Movie [n1];//lei
        int R[] = new int [n2];
        Movie Rmovie[] = new Movie [n2];//lei

        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) {
            L[i] = arr[l + i];
            Lmovie[i] = movieSet[l + i]; //lei
        }
        for (int j=0; j<n2; ++j) {
            R[j] = arr[m + 1 + j];
            Rmovie[j] = movieSet[m + 1 + j]; //lei
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                movieSet[k] = Lmovie[i];//lei
                i++;
            }
            else
            {
                arr[k] = R[j];
                movieSet[k] = Rmovie[j];//lei
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            movieSet[k] = Lmovie[i];//lei
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            movieSet[k] = Rmovie[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    protected void sort(int arr[], int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    protected boolean reverseOrder(){
        /* This method reverse the order of a sorted Movie[] by merge sort for such instances as that an descending order is required */
        Movie temp;
        for(int i = 0; i < size/2; i++){
                //swap elements from both ends to the middle
                temp = movieSet[i]; //make a temp Movie object to perform the swap
                movieSet[i]= movieSet[size-i-1];
                movieSet[size-i-1] = temp;
        }
        return true;
    }
    public Movie[] getMovieSet() {
        //return the Movie[] inside the MovieSet object
        return movieSet;
    }

    public int getSize() {
        return size;
    }

    public String[] getGenres() {
        return genres;
    }

    public int getCapacity() {
        return capacity;
    }
}
