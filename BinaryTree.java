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
        size = 0;
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
        for (Object n : this) {
            size++;
        }
    }

    public boolean contains(Object obj) {
        //returns true iff the tree contains an object equivalent to obj
    	for (Object o : this) {
    		if (o.equals(obj)) {
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
    	Iterator it1 = this.nodeIterator();
    	Iterator it2 = bt.nodeIterator();
    	
        if (this.size() != bt.size()) {
    		return false;
    	}
    	
    	while (it1.hasNext()) {
            Node n1 = (Node) it1.next();
            Node n2 = (Node) it2.next();
    		
    		if ((n1.left() == null && n2.left() != null) || (n1.left() != null && n2.left() == null)) {
    			return false;
    		}
    		if ((n1.right() == null && n2.right() != null) || (n1.right() != null && n2.right() == null)) {
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
            if (!itThis.next().equals(itOther.next())) {
                return false;
            }
        }

        itThis = inOIterator();
        itOther = bT.inOIterator();

        // Checks inorder
        while(itThis.hasNext() && itOther.hasNext()) {
            if (!itThis.next().equals(itOther.next())) {
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

    public Iterator nodeIterator() {
        return new nodeIterator();
    }

    public Iterator iterator() {
        return new myIterator();
    }

    public Iterator inOIterator() {
        return new myInOIterator();
    }

    public class nodeIterator implements Iterator {
        //iterator only returning the nodes, not the data
        Node node;
        int count = 0;
        Stack<Object> stack = new Stack<Object>();

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Object next() {
            if (node == null) {
                node = owner.root;
                count++;
                stack.push(node);
                return node;
            }
            while (true) {
                if (stack.peek() == node) {
                    if (node.left() != null) {
                        node = node.left();
                        count++;
                        stack.push(node);
                        return node;
                    }
                    if (node.right() != null) {
                        node = node.right();
                        count++;
                        stack.push(node);
                        return node;
                    }
                    node = node.parent();
                    continue;
                }
                if (stack.peek() == node.left()) {
                    if (node.right() != null) {
                        node = node.right();
                        count++;
                        stack.pop();
                        stack.push(node);
                        return node;
                    }
                    node = node.parent();
                    continue;
                }
                if (stack.peek() == node.right()) {
                    stack.pop();
                    node = node.parent();
                    continue;
                }
            }
        }

        public boolean hasNext() {
            return owner.size() != count;
        }
    }

    public class myIterator implements Iterator {
        //preorder
        Node node;
        int count = 0;
        Stack<Object> stack = new Stack<Object>();

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Object next() {
            if (node == null) {
                node = owner.root;
                count++;
                stack.push(node);
                return node.data;
            }
            while (true) {
                if (stack.peek() == node) {
                    if (node.left() != null) {
                        node = node.left();
                        count++;
                        stack.push(node);
                        return node.data();
                    }
                    if (node.right() != null) {
                        node = node.right();
                        count++;
                        stack.push(node);
                        return node.data();
                    }
                    node = node.parent();
                    continue;
                }
                if (stack.peek() == node.left()) {
                    if (node.right() != null) {
                        node = node.right();
                        count++;
                        stack.pop();
                        stack.push(node);
                        return node.data();
                    }
                    node = node.parent();
                    continue;
                }
                if (stack.peek() == node.right()) {
                    stack.pop();
                    node = node.parent();
                    continue;
                }
            }
        }

        public boolean hasNext() {
            return owner.size() != count;
        }
    }

    // private Iterator preOrder() {
    //     //should return a preorder iterator over the tree
    //     return new Iterator() {
    //         Iterator it = owner.preOrderNode();

    //         public void remove() {
    //             throw new UnsupportedOperationException();
    //         }

    //         public Object next() {
    //             return ((Node) it.next()).data();
    //         }

    //         public boolean hasNext() {
    //             return it.hasNext();
    //         }
    //     };
    // }

    // private Iterator preOrderNode() {
    //     return new Iterator() {
            
    //         public void remove() {
    //         	throw new UnsupportedOperationException();
    //         }

    //         Node node;
    //         int count = 0;
    //         Stack<Object> stack = new Stack<Object>();

    //         public Object next() {
    //             if (node == null) {
    //                 node = owner.root;
    //                 count++;
    //                 stack.push(node);
    //                 return node;
    //             }
    //             while (true) {
    //                 if (stack.peek() == node) {
    //                     if (node.left() != null) {
    //                         node = node.left();
    //                         count++;
    //                         stack.push(node);
    //                         return node;
    //                     }
    //                     if (node.right() != null) {
    //                         node = node.right();
    //                         count++;
    //                         stack.push(node);
    //                         return node;
    //                     }
    //                     node = node.parent();
    //                     continue;
    //                 }
    //                 if (stack.peek() == node.left()) {
    //                     if (node.right() != null) {
    //                         node = node.right();
    //                         count++;
    //                         stack.pop();
    //                         stack.push(node);
    //                         return node;
    //                     }
    //                     node = node.parent();
    //                     continue;
    //                 }
    //                 if (stack.peek() == node.right()) {
    //                     stack.pop();
    //                     node = node.parent();
    //                     continue;
    //                 }
    //             }
    //         }

    //         public boolean hasNext() {
    //             return owner.size() != count;
    //         }
    //     };
    // }

    public class myInOIterator implements Iterator {
        Node node;
        int count = 0;
        Stack<Object> stack = new Stack<Object>();

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Object next() {
            if (node == null) {
                node = owner.root;
            }
            while (true) {
                if (stack.size() > 0 && stack.peek() == node.left()) { //just finished the left side of a node
                    count++;
                    stack.pop();
                    stack.push(node);
                    return node.data();
                }
                if (stack.size() > 0 && stack.peek() == node) { //retured it last time so do the right side.
                    if (node.right() != null) {
                        node = node.right();
                        continue;
                    }
                    node = node.parent();
                    continue;
                }
                if (stack.size() > 0 && stack.peek() == node.right()) { // we just finished the right side so go up
                    stack.pop();
                    node = node.parent();
                    continue;
                }
                if (node.left() != null) { // keep going left
                    node = node.left();
                    continue;
                }
                count++;
                stack.push(node);
                return node.data();
            }
        }

        public boolean hasNext() {
            return owner.size() != count;
        }        
    }

    // public Iterator inOrder() {
    //     //should return an inorder iterator over the tree
    //     return new Iterator() {
    //         Iterator it = owner.inOrderNode();

    //         public void remove() {
    //             throw new UnsupportedOperationException();
    //         }

    //         public Object next() {
    //             return ((Node) it.next()).data();
    //         }

    //         public boolean hasNext() {
    //             return it.hasNext();
    //         }
    //     };
    // }

    // private Iterator inOrderNode() {
    //     return new Iterator() {
    //         Node node;
    //         int count = 0;
    //         Stack<Object> stack = new Stack<Object>();

    //         public void remove() {
    //         	throw new UnsupportedOperationException();
    //         }

    //         public Object next() {
    //             if (node == null) {
    //                 node = owner.root;
    //             }
    //             while (true) {
    //                 if (stack.size() > 0 && stack.peek() == node.left()) {
    //                     count++;
    //                     stack.pop();
    //                     stack.push(node);
    //                     return node;
    //                 }
    //                 if (stack.size() > 0 && stack.peek() == node) {
    //                     if (node.right() != null) {
    //                         node = node.right();
    //                         continue;
    //                     }
    //                     node = node.parent();
    //                     continue;
    //                 }
    //                 if (stack.size() > 0 && stack.peek() == node.right()) {
    //                     stack.pop();
    //                     node = node.parent();
    //                     continue;
    //                 }
    //                 if (node.left() != null) {
    //                     node = node.left();
    //                     continue;
    //                 }
    //                 count++;
    //                 stack.push(node);
    //                 return node;
    //             }
    //         }

    //         public boolean hasNext() {
    //             return owner.size() != count;
    //         }
    //     };
    // }

    public boolean putCursorAtRoot() {
        //returns false if this is an empty tree
        if (this.isEmpty()) {
            return false;
        }
        cursor = root;
        return true;
    }

    public boolean putCursorAtLeftSon() {
        if (this.isEmpty() && cursor.left() == null) {
            return false;
        }
        cursor = cursor.left();
        return true;
    }

    public boolean putCursorAtRightSon() {
        if (this.isEmpty() && cursor.right() == null) {
            return false;
        }
        cursor = cursor.right();
        return true;
    }

    public boolean putCursorAtFather() {
        if (this.isEmpty() && cursor == root) {
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
        test_Equals();

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

    private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;
        System.out.println(value ? "success" : "failure");
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
        
        Iterator it = bt.inOIterator();
        
        //preorder
        // System.out.println(it.next()); // a
        // System.out.println(it.next()); // b
        // System.out.println(it.next()); // c
        // System.out.println(it.next()); // d
        // System.out.println(it.next()); // e
        // System.out.println(it.next()); // f
        // System.out.println(it.next()); // g

        String s = "badcfeg";
        String t = "";
        Object o = null;
        while (it.hasNext()) {
            o = it.next();
            t += o.toString();
        }

        try {
            displaySuccessIfTrue(s.equals(t));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }        
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
            displaySuccessIfTrue(bt2.contains("f"));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }
    
    private static void test_Equals() {
        System.out.println("Testing equals...");

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

        BinaryTree bt2 = new BinaryTree("a");
        bt2.putCursorAtRoot();
        bt2.attachLeftSonAtCursor("b");
        bt2.attachRightSonAtCursor("c");
        bt2.putCursorAtRightSon();
        bt2.attachLeftSonAtCursor("d");
        bt2.attachRightSonAtCursor("e");
        bt2.putCursorAtRightSon();
        bt2.attachLeftSonAtCursor("f");
        bt2.attachRightSonAtCursor("g");

        try {
            displaySuccessIfTrue(bt.equals(bt2));
        } catch (Exception e) {
            displaySuccessIfTrue(false);
        }
    }
}
