import java.lang.Iterable;
import java.util.*;

public class BinaryTree implements Iterable {
    private static int attempts = 0;
    private static int successes = 0;
    
    private Node root;
    private int size;
    private Node cursor;
    private BinaryTree owner;

    public BinaryTree() {
        //constructs an empty tree
        owner = this;
    }

    public BinaryTree(Object data) {
        //constructs a tree with just a root node decorated with (i.e., referring to) obj
        this.root = new Node(data);
        this.cursor = root;
        this.size = 1;
        owner = this;
    }

    private BinaryTree(Node r) {
        this.root = r;
        this.cursor = r;
        for (Object n: this) {//*****************************************
            size++;
        }
    }

    public boolean contains(Object obj) {
        //returns true iff the tree contains an object equivalent to obj
    	for (Object o : this) {
    		if (((Node) o).data().equals(obj)) {
    			return true;
    		}
    	}
        return false;
    }

    public boolean similar(Object obj) {
        //returns true iff obj is a similar binary tree- i.e., obj must have identical structure (only)
    	if (!(obj instanceof BinaryTree)) {
    		return false;
    	}
    	BinaryTree bt = (BinaryTree) obj;
    	Iterator it1 = this.iterator();
    	Iterator it2 = bt.iterator();
    	
    	if (this.size() != bt.size()) {
    		System.out.println("---0");
    		return false;
    	}
    	
    	while (it1.hasNext()) {
    		Node n1 = (Node) it1.next();
    		Node n2 = (Node) it2.next();
    		if ((n1.left() == null && n2.left() != null) || (n1.left() != null && n2.left() == null)) {
    			System.out.println("---1");
    			return false;
    		}
    		if ((n1.right() == null && n2.right() != null) || (n1.right() != null && n2.right() == null)) {
    			System.out.println("---2");
    			return false;
    		}
    	}  	
        return true;
    }

    public boolean equals(Object obj) {
        //returns true iff obj is an equivalent binary tree- i.e., obj must have identical structure and equivalent objects
        if (!(obj instanceof BinaryTree)) {
            return false;
        }
        BinaryTree bT = (BinaryTree) obj;

        Iterator itThis = this.iterator();
        Iterator itOther = bT.iterator();

        Node nThis = null; 
        Node nOther = null; 

        if (size() != bT.size()) {
            return false;
        }

        // Checks preorder
        while(itThis.hasNext() && itOther.hasNext()) {
            nThis = (Node) itThis.next();
            nOther = (Node) itOther.next();
            
            if (!nThis.data().equals(nOther.data())) {
                return false;
            }
        }

        itThis = inOrder();
        itOther = bT.inOrder();

        // Checks inorder
        while(itThis.hasNext() && itOther.hasNext()) {
            nThis = (Node) itThis.next();
            nOther = (Node) itOther.next();
            
            if (!nThis.data().equals(nOther.data())) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        //should do the obvious thing- and the same for public int size() and public int hashCode()
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public Iterator iterator() {
        return preOrder();
    }

    private Iterator preOrder() {
        //should return a preorder iterator over the tree
        return new Iterator() {
            Node node;
            int count = 0;

            public void remove() {
            	throw new UnsupportedOperationException();
            }

            public Object next() {

                if(node == null) {
                    node = owner.root;
                    count++;
                    return node;
                }
                if(node.left() !=  null) {
                    node = node.left();
                    count++;
                    return node;
                }
                if(node.right() != null) {
                    node = node.right();
                    count++;
                    return node;
                }

                Object prevNode = node;
                node = node.parent();
                while(true) {
                    if(node.right() == prevNode) {
                        prevNode = node;
                        node = node.parent();
                        continue;
                    }
                    if(node.left() == prevNode) {
                        if(node.right() != null) {
                            node = node.right();
                            count++;
                            return node;
                        }
                        prevNode = node;
                        node = node.parent();
                        continue;
                    }
                }
            }

            public boolean hasNext() {
                // this isnt really the "right way", TODO: make suck less
                //System.out.println("hi!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + owner.size() + " " + (count + 1));
                return owner.size() != count;
            }
        };
    }

    public Iterator inOrder() {
        return new Iterator() {
            Node node;
            int count = 0;
            Stack<Object> stack = new Stack<Object>();

            public void remove() {
            	throw new UnsupportedOperationException();
            }

            
            public Object next() {
                //should return an inorder iterator over the tree

                if(node == null) {
                    node = owner.root;
                }

                Object prevNode = node;
                while (true) {
                    if (prevNode == node.right()) {
                        prevNode = node;
                        node = node.parent();
                        continue;
                    }
                    
                    if (prevNode == node && node.left() == null && node.right() == null) {
                    	node = node.parent();
                    	continue;
                    }

                    if ((stack.isEmpty() || node != stack.peek()) && node.left() != null) {
                    	prevNode = node;
                        stack.push(prevNode);
                        node = node.left();
                        continue;
                    }
                    if (node.left() == null && prevNode == node.parent()) {
                    	count++;
                        return node;
                    }

                    if (prevNode == node.left()) {
                    	count++;
                        return node;
                    }

                    if (node.right() != null) {
                    	if((stack.isEmpty() || node == stack.peek())) {
                            stack.pop();
                    	}
                        prevNode = node;
                        node = node.right();
                        continue;
                    }
                }
            }

            public boolean hasNext() {
                // this isnt really the "right way", TODO: make suck less
                return owner.size() != count;
            }
        };
    }

    public boolean putCursorAtRoot() {
        //returns false if this is an empty tree
        if (this.isEmpty()) {
            return false;
        }
        cursor = root;
        return true;
    }

    public boolean putCursorAtLeftSon() {
        if (cursor.left() == null) {
            return false;
        }
        cursor = cursor.left();
        return true;
    }

    public boolean putCursorAtRightSon() {
        if (cursor.right() == null) {
            return false;
        }
        cursor = cursor.right();
        return true;
    }

    public boolean putCursorAtFather() {
        if (cursor == root) {
            return false;
        }
        cursor = cursor.parent();
        return true;
    }

    public boolean attachLeftSonAtCursor(Object data) {
        //returns false if a left son already exists
        if (cursor.left() == null) {
            cursor.left(new Node(data));
            cursor.left().parent(cursor);
            size++;
            return true;
        } 
        return false;
    }
    
    public boolean attachRightSonAtCursor(Object data) {
        //returns false if a right son already exists
        if (cursor.right() == null) {
            cursor.right(new Node(data));
            cursor.right().parent(cursor);
            size++;
            return true;
        }
        return false;
    }

    public boolean pruneFromCursor() {
        //removes everything that descends from the cursor, including the node to which the cursor refers, 
        //then relocates the cursor to the root node, returning true iff something (including the cursor) changed
        if (size == 0) {
            return false;
        }
        Node oldCursor = cursor;
        if (cursor == root) {
            cursor = null;
            root = null;
            size = 0;
        } else {
            cursor = cursor.parent();
            if (cursor.left() == oldCursor) {
                cursor.left(null);
            } else {
                cursor.right(null);
            }       
            size -= (new BinaryTree(oldCursor)).size();
        }
        return true;
    }

    private class Node {
        public Object data;
        private Node parent;
        private Node lSon;
        private Node rSon;


        public Node(Object data) {
            this.data = data;
        }

        public Node left(){
            return lSon;
        }

        public void left(Node newLeft) {
            this.lSon = newLeft;
        }

        public Node right() {
            return rSon;
        }

        public void right(Node newRight) {
            this.rSon = newRight;
        }

        public Object data() {
            return data;
        }

        public void data(Object obj) {
            this.data = obj;
        }

        public Node parent() {
            return parent;
        }

        public void parent(Node newParent) {
            this.parent = newParent;
        }

    }


    public static void main(String[] args) {
        attempts = 0;
        successes = 0;

        test_Iterator();
        test_Similar();
        test_Contains();

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

    private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;
        System.out.println(value ? "success" : "failure");
    }

    private static void test_Similar() {
    	System.out.println("Testing similar...");
    	
    	BinaryTree bt = new BinaryTree("a");
        bt.putCursorAtRoot();
        bt.attachLeftSonAtCursor("b");
        bt.attachRightSonAtCursor("c");
        
        BinaryTree bt2 = new BinaryTree("e");
        bt2.putCursorAtRoot();
        bt2.attachLeftSonAtCursor("f");
        bt2.attachRightSonAtCursor("g");
        
        try {
            displaySuccessIfTrue(bt.similar(bt2));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    	
    }
    
    private static void test_Contains() {
    	System.out.println("Testing contains...");
    	
    	BinaryTree bt = new BinaryTree("a");
        bt.putCursorAtRoot();
        bt.attachLeftSonAtCursor("b");
        bt.attachRightSonAtCursor("c");
        
        BinaryTree bt2 = new BinaryTree("e");
        bt2.putCursorAtRoot();
        bt2.attachLeftSonAtCursor("f");
        bt2.attachRightSonAtCursor("a");
        
        try {
            displaySuccessIfTrue(bt2.contains("n"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }
    
    private static void test_Iterator() {
        System.out.println("Testing iterator...");        

        BinaryTree bt = new BinaryTree("a");
        bt.putCursorAtRoot();
        bt.attachLeftSonAtCursor("b");
        bt.attachRightSonAtCursor("c");
        bt.putCursorAtRightSon();
        bt.attachLeftSonAtCursor("d");
        bt.attachRightSonAtCursor("e");
        bt.putCursorAtRightSon();
        bt.attachLeftSonAtCursor("f");
        bt.attachRightSonAtCursor("g");
        
        Iterator it = bt.iterator();
        
        System.out.println(((Node)it.next()).data()); // a
        System.out.println(((Node)it.next()).data()); // b
        System.out.println(((Node)it.next()).data()); // c
        System.out.println(((Node)it.next()).data()); // d
        
        it.remove();
        System.out.println("--------");
      //System.out.println(((Node)it.next()).data()); // e
        System.out.println(((Node)it.next()).data()); // e
        System.out.println(((Node)it.next()).data()); // f
        System.out.println(((Node)it.next()).data()); // g
        
        Iterator it2 = bt.iterator();
        System.out.println(((Node)it2.next()).data()); // a
        System.out.println(((Node)it2.next()).data()); // b
        System.out.println(((Node)it2.next()).data()); // c
        System.out.println(((Node)it2.next()).data()); // e
        System.out.println(((Node)it2.next()).data()); // f
        System.out.println(((Node)it2.next()).data()); // g
        //System.out.print(((Node)it.next()).data() + ", ");
        /*try {
            displaySuccessIfTrue(deque.size() == 0);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
        try {
            displaySuccessIfTrue(deque.leftMost == null && deque.rightMost == null);
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }*/
    }

}
