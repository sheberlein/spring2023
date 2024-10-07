// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: N/A
// Lecturer: Gary Dahl
// Notes to Grader: none
import java.util.LinkedList;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T>
{

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> 
    {
      public int blackHeight; // tracks the black height only for the current node: 0 = red,
      //1 = black, and 2 = double-black. Every newly instantiated Node object has a blackHeight of
      //0 (aka red) by default
      public T data;
      // The context array stores the context of the node in the tree:
      // - context[0] is the parent reference of the node,
      // - context[1] is the left child reference of the node,
      // - context[2] is the right child reference of the node.
      // The @SupressWarning("unchecked") annotation is used to supress an unchecked
      // cast warning. Java only allows us to instantiate arrays without generic
      // type parameters, so we use this cast here to avoid future casts of the
      // node type's data field.
      @SuppressWarnings("unchecked")
      public Node<T>[] context = (Node<T>[])new Node[3];
      public Node(T data) { this.data = data; }
      
      /**
       * @return true when this node has a parent and is the right child of
       * that parent, otherwise return false
       */
      public boolean isRightChild() 
        {
          return context[0] != null && context[0].context[2] == this;
        }
    }
    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree
    
    /**
     * This method resolves any red-black tree property violations that are introduced by inserting
     * each new node into the red-black tree.
     * @param redNode   a reference to a newly added red node
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> newRedNode)
    {
   // if we are testing adding the root node, we need to change it to black.
      if (newRedNode.equals(root))
      {
        newRedNode.blackHeight = 1;
      }
      else if (newRedNode.context[0].blackHeight != 1 && !newRedNode.context[0].equals(root))
      {
        // we need a reference to the parent's sibling
        Node<T> pSibling; // this will be updated in the if-else statement below.
        if (newRedNode.context[0].isRightChild())
        {
          pSibling = newRedNode.context[0].context[0].context[1];
        }
        else
        {
          pSibling = newRedNode.context[0].context[0].context[2];
        }
        
        // We can only apply these properties normally if the parent's sibling exists (is not null)
        if (pSibling != null)
        {
          Node<T> parent = newRedNode.context[0]; // the parent of the new node
          Node<T> grandparent = newRedNode.context[0].context[0]; // the grandparent 
          // of the new node
          
          // case 3: parent's sibling is red
          if (pSibling.blackHeight == 0)
          {
            // color swap the parent, grandparent, and parent's sibling
            parent.blackHeight = 1;
            pSibling.blackHeight = 1;
            grandparent.blackHeight = 0;
            // then we need to check for violations further up the tree.
            enforceRBTreePropertiesAfterInsert(grandparent);
          }
          
          // case 1: parent's sibling is black and both parent and child are left or right nodes
          else if (pSibling.blackHeight == 1)
          {
            if ((!newRedNode.isRightChild() && !parent.isRightChild()) 
                || (newRedNode.isRightChild() && parent.isRightChild()))
            {
              // rotate and color swap the parent and grandparent.
              parent.blackHeight = 1;
              grandparent.blackHeight = 0;
              rotate(parent, grandparent);
            }
            
            // case 2: parent's sibling is black and parent and child are on opposite sides of tree
            else if ((!newRedNode.isRightChild() && parent.isRightChild()) 
                || (newRedNode.isRightChild() && !parent.isRightChild()))
            {
              // rotate first, then rotate and color swap
              rotate(newRedNode, parent);
              rotate(newRedNode, grandparent);
              grandparent.blackHeight = 0;
              newRedNode.blackHeight = 1;
            }
            
          }
          
        }
        
        // if the parent's sibling was null (did not exist), we need to do some updating
        else
        {
          if (newRedNode.blackHeight == newRedNode.context[0].blackHeight)
          {
            // color swap the parent and grandparent of the newly inserted node
            newRedNode.context[0].context[0].blackHeight = 0;
            newRedNode.context[0].blackHeight = 1;
            // rotate the parent and grandparent of the newly inserted node
            rotate(newRedNode.context[0], newRedNode.context[0].context[0]);
          }
        }
        
      }
      
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is already contained in the tree
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException 
    {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (this.root == null) 
        {
            // add first node to an empty tree
            root = newNode; size++; return true;
        } 
        else 
        {
          // insert into subtree
          Node<T> current = this.root;
          while (true) 
          {
            int compare = newNode.data.compareTo(current.data);
            if (compare == 0) 
            {
              throw new IllegalArgumentException("This RedBlackTree already contains value " + data.toString());
            } 
            else if (compare < 0)
            {
            // insert in left subtree
            if (current.context[1] == null) 
            {
                // empty space to insert into
                current.context[1] = newNode;
                newNode.context[0] = current;
                this.size++;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            }
            else 
            {
              // no empty space, keep moving down the tree
              current = current.context[1];
            }
                } else {
                    // insert in right subtree
                    if (current.context[2] == null) {
                        // empty space to insert into
                        current.context[2] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[2]; 
                    }
                }
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException
    {
      // if the child or parent are null, throw an exception
      if (parent == null)
        throw new IllegalArgumentException("The given parent is null.");
      if (child == null)
        throw new IllegalArgumentException("The given child is null.");
      // if the child and parent are not initially related that way, throw an exception
      if (child.context[0] != parent)
      {
        throw new IllegalArgumentException("The given child and parent are not related in the correct way.");
      }
      
      // if the provided child is a right child of the provided parent, perform a left rotation
      if (child.isRightChild())
      {
        Node<T> grandparent = parent.context[0]; // a reference to the parent's parent
        // set node P's right pointer to node G's left child
        parent.context[2] = child.context[1];
        
        // set node P's parent to node G
        parent.context[0] = child;
        
        // set node G's left pointer to node P
        child.context[1] = parent;
        
        // set node P's right child's parent pointer to node P
        if (parent.context[2] != null)
          parent.context[2].context[0] = parent;
        
        // set node G's parent to node P's parent
        child.context[0] = parent.context[0];
        
        // if the parent did have a parent, we need to update its context array
        if (grandparent != null)
        {
          // if parent is a right child, set the right child of parent's parent to node G
          if (parent.isRightChild())
          {
            grandparent.context[2] = child;
          }
          else
          {
            grandparent.context[1] = child;
          }
        }
        else
        {
          this.root = child;
        }
      }
      
      // if the provided child is a left child of the provided parent, perform a right rotation
      else
      {
        Node<T> grandparent = parent.context[0]; // a reference to the parent's parent (node G's parent)
        // set node G's left child to node P's right child
        parent.context[1] = child.context[2];
        
        // set node G's left child's parent pointer to G
        if (parent.context[1] != null)
          parent.context[1].context[0] = parent;
        
        // set node P's right child to node G
        child.context[2] = parent;
        
        // set node G's parent to node P
        parent.context[0] = child;
        
        // set node P's parent to node G's parent
        child.context[0] = parent.context[0];
        
        // if the parent did have a parent, we need to update its context array
        if (grandparent != null)
        {
          // if parent is a right child, set the right child of parent's parent to node P
          if (parent.isRightChild())
          {
            grandparent.context[2] = child;
          }
          // if parent is a left child, set the left child of parent's parent to node P
          else
          {
            grandparent.context[1] = child;
          }
        }
        else
        {
          this.root = child;
        }
      } 
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size()
    {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    /**
     * Removes the value data from the tree if the tree contains the value.
     * This method will not attempt to rebalance the tree after the removal and
     * should be updated once the tree uses Red-Black Tree insertion.
     * @return true if the value was remove, false if it didn't exist
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is not stored in the tree
     */
    public boolean remove(T data) throws NullPointerException, IllegalArgumentException
    {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // throw exception if node with data does not exist
            if (nodeWithData == null) {
                throw new IllegalArgumentException("The following value is not in the tree and cannot be deleted: " + data.toString());
            }  
            boolean hasRightChild = (nodeWithData.context[2] != null);
            boolean hasLeftChild = (nodeWithData.context[1] != null);
            if (hasRightChild && hasLeftChild) {
                // has 2 children
                Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
                // replace value of node with value of successor node
                nodeWithData.data = successorNode.data;
                // remove successor node
                if (successorNode.context[2] == null) {
                    // successor has no children, replace with null
                    this.replaceNode(successorNode, null);
                } else {
                    // successor has a right child, replace successor with its child
                    this.replaceNode(successorNode, successorNode.context[2]);
                }
            } else if (hasRightChild) {
                // only right child, replace with right child
                this.replaceNode(nodeWithData, nodeWithData.context[2]);
            } else if (hasLeftChild) {
                // only left child, replace with left child
                this.replaceNode(nodeWithData, nodeWithData.context[1]);
            } else {
                // no children, replace node with a null node
                this.replaceNode(nodeWithData, null);
            }
            this.size--;
            return true;
        } 
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(T data)
    {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Helper method that will replace a node with a replacement node. The replacement
     * node may be null to remove the node from the tree.
     * @param nodeToReplace the node to replace
     * @param replacementNode the replacement for the node (may be null)
     */
    protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode)
    {
        if (nodeToReplace == null) {
            throw new NullPointerException("Cannot replace null node.");
        }
        if (nodeToReplace.context[0] == null) {
            // we are replacing the root
            if (replacementNode != null)
                replacementNode.context[0] = null;
            this.root = replacementNode;
        } else {
            // set the parent of the replacement node
            if (replacementNode != null)
                replacementNode.context[0] = nodeToReplace.context[0];
            // do we have to attach a new left or right child to our parent?
            if (nodeToReplace.isRightChild()) {
                nodeToReplace.context[0].context[2] = replacementNode;
            } else {
                nodeToReplace.context[0].context[1] = replacementNode;
            }
        }
    }

    /**
     * Helper method that will return the inorder successor of a node with two children.
     * @param node the node to find the successor for
     * @return the node that is the inorder successor of node
     */
    protected Node<T> findMinOfRightSubtree(Node<T> node)
    {
        if (node.context[1] == null && node.context[2] == null) {
            throw new IllegalArgumentException("Node must have two children");
        }
        // take a step to the right
        Node<T> current = node.context[2];
        while (true) {
            // then go left as often as possible to find the successor
            if (current.context[1] == null) {
                // we found the successor
                return current;
            } else {
                current = current.context[1];
            }
        }
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * value. Returns null if there is no node that contains the value.
     * @return the node that contains the data, or null of no such node exists
     */
    protected Node<T> findNodeWithData(T data)
    {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                // keep looking in the left subtree
                current = current.context[1];
            } else {
                // keep looking in the right subtree
                current = current.context[2];
            }
        }
        // we're at a null node and did not find data, so it's not in the tree
        return null; 
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString()
    {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.context[2] != null) sb.append(", ");
                    current = popped.context[2];
                } else {
                    nodeStack.add(current);
                    current = current.context[1];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.context[1] != null) q.add(next.context[1]);
                if(next.context[2] != null) q.add(next.context[2]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString()
    {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }
    
    // three jUnit tests


      /**
       * Tests the correctness of the enforceRBTreePropertiesAfterInsert method after inserting a new
       * red node whose parent's sibling is black and the parent and child are either both left or 
       * both right nodes
       */
      @Test
      public void test()
      {
        {
          RedBlackTree<Integer> testTree = new RedBlackTree<Integer>();
          testTree.insert(5);
          testTree.insert(4);
          testTree.insert(3);
          System.out.println(testTree.toLevelOrderString());
        }
        
        
        
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
        tree.insert(4); // this will be the root of the tree, and it will be black
        tree.insert(2); // this will be the left child of the root of the tree. Will become black.
        tree.insert(8); // this will become the right child of the root node. Will become black.
        tree.insert(7); // this will become the left child of node 8. It will be red.
        tree.insert(6); // this will be inserted as the left child of node 7. It will be inserted 
        // as a red node.
        String actual = tree.toInOrderString(); // the actual in order string of the tree.
        String expected = "[ 2, 4, 6, 7, 8 ]"; // the expected in order string of the tree.
        assertEquals(actual, expected, "the in order String of the RBT did not match the expected"
            + " String in test 1.");
        assertEquals(tree.root.blackHeight, 1, "your root node was not set to black after the "
            + "insert in test 1."); // the root of the tree should be black after the insertion
        
        // the left and right children of the root of the tree should be black after the insertion.
        assertEquals(tree.root.context[1].blackHeight, 1, "incorrect black height of the root's left "
            + "child in test 1.");
        assertEquals(tree.root.context[2].blackHeight, 1, "incorrect black height of the root's "
            + "right child in test 1.");
        
        // the left and right children of the root's right child should be red after the insertion.
        assertEquals(tree.root.context[2].context[1].blackHeight, 0, "incorrect black height of the"
            + " root's right child's left child in test 1.");
        assertEquals(tree.root.context[2].context[2].blackHeight, 0, "incorrect black height of the"
            + " root's right child's right child in test 1.");
      }
      
      
      /**
       * Tests the correctness of the enforceRBTreePropertiesAfterInsert method after inserting a new
       * red node whose parent's sibling is black and the parent and child are not both left or both 
       * right nodes
       */
      @Test
      public void test2()
      {
        RedBlackTree<Integer> tree2 = new RedBlackTree<Integer>();
        tree2.insert(4); // this will be the root of the tree, and it will be black
        tree2.insert(2); // this will be the left child of the root of the tree. Will become black.
        tree2.insert(8); // this will be the right child of the root of the tree. Will become black.
        tree2.insert(6); // this will be the left child of node 8. It will become red.
        tree2.insert(7); // this will be inserted as the right child of node 6. Will be inserted as red.
        String actual = tree2.toInOrderString(); // the actual in order string of the tree.
        String expected = "[ 2, 4, 6, 7, 8 ]"; // the expected in order string of the tree.
        assertEquals(actual, expected, "the in order String of the RBT did not match the expected"
            + " String in test 2.");
        assertEquals(tree2.root.blackHeight, 1, "your root node was not set to black after the "
            + "insert in test 2."); // the root of the tree should be black after the insertion
        // the left child of the root of the tree should be black after the insertion.
        assertEquals(tree2.root.context[1].blackHeight, 1, "incorrect black height of the root's left "
            + "child in test 2.");
        
       // the left and right children of the root of the tree should be black after the insertion.
        assertEquals(tree2.root.context[1].blackHeight, 1, "incorrect black height of the root's left "
            + "child in test 2.");
        assertEquals(tree2.root.context[2].blackHeight, 1, "incorrect black height of the root's "
            + "right child in test 2.");
      }
      
      
      /**
       * Tests the correctness of the enforceRBTreePropertiesAfterInsert method after inserting a new
       * red node whose parent's sibling is red
       */
      @Test
      void test3() 
      {
        RedBlackTree<Integer> tree3 = new RedBlackTree<Integer>();
        tree3.insert(2); // this will be the root of the tree, and it will be black.
        tree3.insert(1); // this will be the root's left child, and it will be red.
        tree3.insert(4); // this will be the root's right child, and it will be red
        tree3.insert(3); // this will be inserted as node 4's left child
        String actual = tree3.toInOrderString(); // the actual in order string of the tree.
        String expected = "[ 1, 2, 3, 4 ]";
        assertEquals(actual, expected, "the in order String of the RBT did not match the expected "
            + "String in test 3.");
        assertEquals(tree3.root.blackHeight, 1, "your root node was not set to black after the "
            + "insert in test 3."); // the root of the tree should be black after the insertion
        // the root's left and right children should both be black after the insertion
        assertEquals(tree3.root.context[1].blackHeight, 1);
        assertEquals(tree3.root.context[2].blackHeight, 1);
        // the newly inserted node 3 should be red after the insertion
        assertEquals(tree3.root.context[2].context[1].blackHeight, 0);
      }
}
