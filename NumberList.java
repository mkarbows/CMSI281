/** An object of this class represents a number list, i.e., an ordered collection
    of integers, each of Java class <a href="http://docs.oracle.com/javase/7/docs/api/java/lang/Long.html">Long</a>, 
    with duplicates permitted. Be sure to read the Java documentation on
    <a href="http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html">interface java.util.Collection</a>.
*/

public class NumberList implements java.util.Collection {

    private static int attempts = 0;
    private static int successes = 0;

    private Long[] elements = new Long[16]; // don't need to initialize this here
    private int currentSize = 0;
    private Object[] cArray = new Object[0];

    /** Constructs an empty number list. */
    //asymptotic performance = bigO(1)
    public NumberList(){ //        
        this.elements = new Long[16]; //originally had it starting at size 0 so i couldn't add anything into it, so if i start it at anything other than 0 then there room to put the items in
    }


    /** Constructs a number list from an array of Longs. */
    //bigO(n)
    public NumberList( Long[] l ){
        this.elements = new Long[l.length];
        for (int i = 0; i < l.length; i++) {
            this.elements[i] = l[i];
            currentSize++;          
        }
    }
    
    /** Increases by one the number of instances of the given element in this collection. */
    //bigO(n)
    public boolean add ( Object obj ) { 
        if (!(obj instanceof Long)) {
            return false;
        }
        if (currentSize == elements.length) {
            Long[] newArray = new Long[elements.length * 2];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
        elements[currentSize++] = (Long) obj;
        return true;
    }
    

    /** Adds all of the elements of the given number list to this one. */
    //bigO(n^2)
    public boolean addAll ( java.util.Collection c ) {        
        if (c instanceof NumberList) {
            cArray = c.toArray();
        }
        for (int i = 0; i < cArray.length; i++) {
            if (!this.add(cArray[i])) {
                return false;
            }
        }
        return true;
    }
 
    /** Removes all of the elements from this collection. */
    //bigO(1)
    public void clear () {
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] = null;
        }
    }
 

    /** Returns true iff this number list contains at least one instance of the specified element. */
    //bigO(2n) = bigO(n)
    public boolean contains ( Object obj ) {
        if (!(obj instanceof Long)) {
            return false;
        }
        for (int i = 0; i < elements.length; i++) {
            if (obj.equals(elements[i])) {
                return true;
            }          
        }
        return false;
    }
 
    /** Returns true iff this number list contains at least one instance of each element 
        in the specified list. Multiple copies of some element in the argument do not
        require multiple copies in this number list. */
    //n + n * n^2 = n^3 = bigO(n^3)
    public boolean containsAll ( java.util.Collection c ) {
        for (int i = 0; i < c.size(); i++) {
            if (!this.contains(c.toArray()[i])) {
                return false;
            }
        }
        return true;
    }

    /** Compares the specified object with this collection for equality. */
    //bigO(n)
    public boolean equals ( Object obj ) {
        if (!(obj instanceof NumberList)) {
            return false;
        }
        Long[] objArray = new Long[0];
        objArray = ((NumberList) obj).elements;

        if (this.elements.length != objArray.length) {
            return false;
        } else {
            for (int i = 0; i < elements.length; i++) {
                if (this.elements[i] != objArray[i]) {
                    return false;
                } 
            }              
        }
        return true;
    }
 
    /** Returns the hashcode value for this collection. */
    //bigO(1)
    public int hashCode () {
        return super.hashCode();
    }


    /** Returns true if this collection contains no elements. */
    //bigO(n)
    public boolean isEmpty () {
        int nulls = 0;
        if (!(this instanceof NumberList)) {
            return false;
        }
        if (this.elements.length == 0) {
            return true;
        } else {
            for (int i = 0; i < this.elements.length; i++) {
                if (this.elements[i] == null) {
                    nulls++;
                }
                if (nulls == this.elements.length) {
                    return true;
                }
            }
        }
        return false;
    }


    /** Returns an iterator over the elements in this collection. Replicated elements should
        be "iterated over" just once. */
    public java.util.Iterator iterator () {
        throw new UnsupportedOperationException();
    }


    /** Removes a single instance of the specified element from 
        this collection, if it is present. */
    //n * n = n^2 = bigO(n^2)
    public boolean remove ( Object obj ) {
        if (!(obj instanceof Long)) {
            return false;
        }
        for (int i = 0; i < this.elements.length; i++) {
            if (this.elements[i].equals(obj)) {
                System.arraycopy(elements, i + 1, elements, i, currentSize - 1 - i);
                currentSize--;
                return true;
            }
        }
        return false;
    }



    /** Removes all of this collection's elements that are also contained 
        in the specified collection. */
    //n^3 + n^2 + n^3 = bigO(n^3)
    public boolean removeAll ( java.util.Collection c ) {
        if (this.containsAll(c)) {
            Object[] cArray = c.toArray();
            for (int i = 0; i < cArray.length; i++) {
                this.remove(cArray[i]);
            }
            return true;
        }
        return false;
    }



	/** Retains only the elements in this collection that are contained in the specified collection. 
		 In other words, removes from this collection all of its elements that are not contained in the 
		 specified collection. */
    //n(n + n^2) = n^2 + n^3 = bigO(n^3)
	public boolean retainAll ( java.util.Collection c ) {
        if (c instanceof java.util.Collection) {
            for (int i = 0; i < currentSize; i++) {
                if (!c.contains(elements[i])) {
                    remove(elements[i]);
                    i--;
                }
            }
            return true;
        }
        return false;
	}


    /** Returns the number of elements in this number list, including duplicates. */
    //bigO(1)
    public int sizeIncludingDuplicates () {
        return currentSize;
    }
    
    

    /** Returns a NumberList[] containing all of the elements in this collection, not including duplicates. */
    //n^2 + n = 2n^2 = bigO(n^2)
    public Long[] toArray () {
        Long[] newArray = new Long[elements.length];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        for (int i = 0; i < newArray.length; i++) {
            for (int j = i + 1; j < newArray.length; j++) {
                if (newArray[i] == newArray[j]) {
                    newArray[j] = null;
                }
            }
        }
        int items = 0;
        for (int i = 0; i < newArray.length; i++) {
            if (newArray[i] != null) {
                items++;
            }
        }
        Long[] finalArray = new Long[items];
        int finalPos = 0;
        for (int i = 0; finalPos < items; i++) {
            if (newArray[i] != null) {
                finalArray[finalPos] = newArray[i];
                finalPos++;
            }         
        }
        return finalArray;
    }


    /** Not supported for this class. */
    public Object[] toArray ( Object[] obj ) {
        throw new UnsupportedOperationException();
    }



    /** Returns the number of elements in this number list, not including duplicates. */
    //bigO(n^2)
    public int size () {
        return this.toArray().length;
    }

    /** Returns the number of instances of the given element in this number list. */
    //bigO(n)
    public int count ( Object obj ) {
        int counter = 0;
        for (int i = 0; i < this.elements.length; i++) {
            if (this.elements[i] == obj) {
                counter++;
            }
        }
        return counter;
    }
    
  
    /** This returns a stringy version of this number list. */
    //bigO(n)
    public String toString () { // overrides Object.toString()
        String endString = "";
        for (int i = 0; i < currentSize; i++) {
            endString += elements[i] + ", ";
        }
        endString = "[" + endString.substring(0, endString.length() - 2) + "]";
        return endString;
    }

    
    
    /** This so-called "static factory" returns a new number list comprised of the numbers in the specified array.
        Note that the given array is long[], not Long[]. */
    //bigO(n)
    public static NumberList fromArray ( long[] l ) {
        int i = 0;
        Long[] transform = new Long[l.length];
        for (Long x : l) {
            transform[i++] = x;
        }
        NumberList n = new NumberList(transform);
        return n;
    }

    
    /** This main method is just a comprehensive test program for the class. */
    public static void main ( String[] args ) {

        attempts = 0;
        successes = 0;

        NumberList nl = new NumberList();
        nl.add(5L);

        test_Constructors();
        test_add();
        test_addAll();
        test_clear();
        test_contains();
        test_containsAll();
        test_equals();
        test_isEmpty();
        test_remove();
        test_removeAll();
        test_retainAll();
        test_toArray();
        test_fromArray();
        test_count();

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

     private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;

        System.out.println(value ? "success" : "failure");
    }

    private static void test_Constructors() {
        System.out.println("Testing constructors...");        
        Long[] testArray = new Long[]{5L, 6L, 5L};
        NumberList input1 = new NumberList(testArray);

        try {
            displaySuccessIfTrue(input1.elements[0] == 5L && input1.elements[1] == 6L && input1.elements[2] == 5L);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_add() {
        System.out.println("testing add...");
        Long[] testArray2 = new Long[]{7L};
        NumberList input2 = new NumberList(testArray2);
        input2.add(7L);

        try {
            displaySuccessIfTrue(input2.elements[0] == 7L && input2.elements[1] == 7L);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_addAll() {
        System.out.println("testing addAll...");
        Long[] testArray3 = new Long[]{6L, 7L};
        NumberList input3 = new NumberList(testArray3);
        input3.addAll(input3);

        try {
            displaySuccessIfTrue(input3.elements[0] == 6L && input3.elements[1] == 7L
                && input3.elements[2] == 6L && input3.elements[3] == 7L);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_clear() {
        System.out.println("testing clear...");
        Long[] testArray4 = new Long[]{4L, 5L};
        NumberList input4 = new NumberList(testArray4);
        input4.clear();

        try{
            displaySuccessIfTrue(input4.elements[0] == null && input4.elements[1] == null);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_contains() {
        System.out.println("testing contains...");
        Long[] testArray5 = new Long[]{3L, 2L, 3L};
        NumberList input5 = new NumberList(testArray5);
        Long[] testArray6 = new Long[]{9L};
        NumberList input6 = new NumberList(testArray6);
        input5.contains(input6);

        try {
            displaySuccessIfTrue(!input5.contains(input6));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_containsAll() {
        System.out.println("testing containsAll...");
        Long[] testArray9 = new Long[]{3L, 2L, 3L};
        NumberList input9 = new NumberList(testArray9);
        Long[] testArray10 = new Long[]{3L, 2L, 2L, 3L, 6L};
        NumberList input10 = new NumberList(testArray10);
        

        try {
            displaySuccessIfTrue(!input9.containsAll(input10));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_equals() {
        System.out.println("testing equals...");
        Long[] testArray7 = new Long[]{8L, 7L, 9L};
        NumberList input7 = new NumberList(testArray7);
        Long[] testArray8 = new Long[]{7L, 9L, 2L};
        NumberList input8 = new NumberList(testArray8);
        
        try {
            displaySuccessIfTrue(!input7.equals(input8));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_isEmpty() {
        System.out.println("testing isEmpty...");
        Long[] testArray11 = new Long[]{null, 5L};
        NumberList input11 = new NumberList(testArray11);
        
        try {
            displaySuccessIfTrue(!input11.isEmpty());
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_remove() {
        System.out.println("testing remove...");
        Long[] testArray12 = new Long[]{6L, 7L, 8L};
        NumberList input12 = new NumberList(testArray12);

        input12.remove(new Long(6L));

        try {
            displaySuccessIfTrue(input12.elements[0] == 7L && input12.elements[1] == 8L);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_removeAll() {
        System.out.println("testing removeAll...");
        Long[] testArray13 = new Long[]{6L, 7L, 8L, 9L, 10L};
        NumberList input13 = new NumberList(testArray13);
        input13.removeAll(new java.util.ArrayList<Long>(java.util.Arrays.asList(new Long[]{6L, 10L, 8L})));

        try {
            displaySuccessIfTrue(input13.elements[0] == 7L && input13.elements[1] == 9L);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_retainAll() {
        System.out.println("testing retainAll...");
        Long[] testArray14 = new Long[]{6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L};
        NumberList input14 = new NumberList(testArray14);
        Long[] testArray15 = new Long[]{6L, 10L, 8L};
        NumberList input15 = new NumberList(testArray15);
        input14.retainAll(new java.util.ArrayList<Long>(java.util.Arrays.asList(new Long[]{6L, 10L, 8L})));

        try {
            displaySuccessIfTrue(input14.containsAll(input15));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_toArray() {
        System.out.println("testing toArray...");
        Long[] testArray16 = new Long[]{10L, 8L, 10L, 13L, 25L, 10L};
        NumberList input16 = new NumberList(testArray16);
        Long[] testArray17 = new Long[]{10L, 8L, 13L, 25L};
        NumberList input17 = new NumberList(testArray17);

        NumberList finalArray = new NumberList(input16.toArray());

        try {
            displaySuccessIfTrue(finalArray.equals(input17));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_fromArray() {
        System.out.println("testing toArray...");
        long[] testArray18 = new long[]{8L, 10L, 13L, 25L, 10L};
        //NumberList input18 = new NumberList(testArray18);
        Long[] testArray19 = new Long[]{8L, 10L, 13L, 25L, 10L};
        NumberList input19 = new NumberList(testArray19);


        try {
            displaySuccessIfTrue(fromArray(testArray18).equals(input19));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_count() {
        System.out.println("testing count...");
        Long[] testArray20 = new Long[]{5L, 5L, 6L, 7L, 5L, 5L, 6L};
        NumberList input20 = new NumberList(testArray20);

        try {
            displaySuccessIfTrue(input20.count(5L) == 4);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(input20.count(6L) == 2);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(input20.count(7L) == 1);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

}
