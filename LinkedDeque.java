public class LinkedDeque {

    private static int attempts = 0;
    private static int successes = 0;

    private static int size;
    protected static Node leftMost;
    private static Node rightMost;
    private static Node value;
       
    public LinkedDeque () {
    //default constructor
        size = 0;
        leftMost = null;
        rightMost = null;
    }

    public void insertLeft ( Object o ) {
        leftMost = new Node(o, null, leftMost);
        rightMost = rightMost == null ? leftMost : rightMost;
        size++;
    }

    public void insertRight ( Object o ) {
        rightMost = new Node(o, rightMost, null);
        leftMost = leftMost == null ? rightMost : leftMost;
        size++;
    }

    public void deleteLeft () {
        if (leftMost == null) {
            return;
        }
        leftMost = leftMost.right();
        if (leftMost != null) {
            leftMost.left(null);
        }       
        size--;
    }

    public void deleteRight () {
        if (rightMost == null) {
            return;
        }
        rightMost = rightMost.left();
        if (rightMost != null) {
            rightMost.right(null);
        }        
        size--;
    }

    public Object left () { 
    //returns the left element without modifiying the deque
        return leftMost.data;
    }

    public Object right () {
    // return the right element without modifying the deque
        return rightMost.data;
    }

    public int size () {
        return size;
    }

    public String toString () {
        return leftMost.toStringExt("");
    }

    public boolean equals(Object o) {
        if (!(o instanceof LinkedDeque)) {
            return false;
        }
        LinkedDeque d = (LinkedDeque) o;
        if (size() != d.size()) {
            return false;
        }
        return leftMost.equalsExt(d.leftMost);
    }


    private class Node {
        public Object data;
        private Node leftNode;
        private Node rightNode;

        public Node(Object data) {
            this.data = data;
        }

        public Node(Object data, Node leftNode, Node rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;

            if (leftNode != null) {
                leftNode.rightNode = this;
            }
            if (rightNode != null) {
                rightNode.leftNode = this;
            }
        }

        public Node left(){
            return leftNode;
        }

        public void left(Node newLeft) {
            this.leftNode = newLeft;
            if (newLeft != null) {
                newLeft.rightNode = this;
            }
        }

        public Node right() {
            return rightNode;
        }

        public void right(Node newRight) {
            this.rightNode = newRight;
            if (newRight != null) {
                newRight.leftNode = this;
            }
        }

        public String toStringExt(String n) {
            n += "[" + data + "]";
            return (rightNode != null) ? rightNode.toStringExt(n) : n;
        }

        public boolean equalsExt(Node n) {
            return this.data.equals(n.data) && ((rightNode != null) ? this.rightNode.equalsExt(n.rightNode) : true);
        }
    }


    public static void main ( String[] args ) {
    //runs a comprehensive set of unit tests
        attempts = 0;
        successes = 0;

        test_Constructor();
        test_insertLeft();
        test_insertRight();
        test_deleteLeft();
        test_deleteRight();
        test_left();
        test_right();
        test_size();
        test_toString();
        test_equals();

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

    private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;
        System.out.println(value ? "success" : "failure");
    }

    private static void test_Constructor() {
        System.out.println("Testing constructors...");        
        LinkedDeque deque = new LinkedDeque();

        try {
            displaySuccessIfTrue(deque.size() == 0);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
        try {
            displaySuccessIfTrue(deque.leftMost == null && deque.rightMost == null);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_insertLeft() {
        System.out.println("Testing insertLeft...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertLeft("cat");
        deque.insertLeft("dog");
        deque.insertLeft("cow");
        deque.insertRight("bat");
        deque.insertRight("rat");

        try {
            displaySuccessIfTrue(deque.leftMost.data.equals("cow") && deque.rightMost.data.equals("rat"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_insertRight() {
        System.out.println("Testing insertRight...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("dog");
        deque.insertRight("cow");
        deque.insertRight("goat");
        deque.insertLeft("pug");
        deque.deleteRight();
        LinkedDeque ans1 = new LinkedDeque();
        ans1.insertRight("cat");
        ans1.insertRight("dog");
        ans1.insertRight("cow");
        ans1.insertRight("goat");
        ans1.insertLeft("pug");
        ans1.deleteRight();

        try {
            displaySuccessIfTrue(deque.equals(ans1));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
        try {
            displaySuccessIfTrue(deque.leftMost.data.equals("pug") && deque.rightMost.data.equals("cow"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_deleteLeft() {
        System.out.println("Testing deleteLeft...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("cow");
        deque.insertRight("goat");
        deque.deleteLeft();
        deque.deleteLeft();
        deque.insertLeft("pug");
        
        try {
            displaySuccessIfTrue(deque.leftMost.data.equals("pug") && deque.rightMost.data.equals("goat"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_deleteRight() {
        System.out.println("Testing deleteRight...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("cow");
        deque.insertRight("goat");
        deque.deleteRight();
        deque.deleteRight();
        deque.insertLeft("pug");

        try {
            displaySuccessIfTrue(deque.leftMost.data.equals("pug") && deque.rightMost.data.equals("cat"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_left() {
        System.out.println("Testing left...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("cow");
        deque.insertRight("goat");

        try {
            displaySuccessIfTrue(deque.left().equals("cat"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }        
    }

    private static void test_right() {
        System.out.println("Test right...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("cow");
        deque.insertRight("goat");

        try {
            displaySuccessIfTrue(deque.right().equals("goat"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_size() {
        System.out.println("Test size...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("cow");
        deque.insertRight("goat");

        try {
            displaySuccessIfTrue(deque.size() == 3);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_toString() {
        System.out.println("Test toString...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertRight("cow");
        deque.insertRight("goat");

        try {
            displaySuccessIfTrue(deque.toString().equals("[cat][cow][goat]"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }

    private static void test_equals() {
        System.out.println("Test equals...");
        LinkedDeque deque = new LinkedDeque();
        deque.insertRight("cat");
        deque.insertLeft("cow");
        deque.insertRight("goat");
        deque.deleteLeft();
        LinkedDeque ans = new LinkedDeque();
        ans.insertRight("cat");
        ans.insertRight("goat");

        try {
            displaySuccessIfTrue(deque.equals(ans));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }    
}
