package src;
// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number

public class IndexTree {

	// This is your root 
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;
	
	// Make your constructor
	// It doesn't need to do anything
	public IndexTree(){
		this.root = null;
	}
	// complete the methods below
	
	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(String word, int lineNumber){
		this.root = add(root, word, lineNumber);
	}

	
	
	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists, 
	// you want to  add it to the IndexNode that already exists
	// otherwise make a new indexNode

	//List to use for add method
	//Empty list
	// Base case
	//Increment occurences of word
	// Cases for if its the left or righ subtree
	private IndexNode add(IndexNode root, String word, int lineNumber){
		if (root == null) {
            return new IndexNode(word, lineNumber);
        }
        int comparison = word.compareTo(root.word);
        if (comparison == 0) {
            root.occurences++;
            root.list.add(lineNumber);
        } else if (comparison < 0) {
            root.left = add(root.left, word, lineNumber);
        } else {
            root.right = add(root.right, word, lineNumber);
        }
        return root;
    }
	
	
	
	
	// returns true if the word is in the index
	public boolean contains(String word){
		return contains(root, word);
	}
	private boolean contains(IndexNode root, String word){
		if(root == null){//Empty tree so return false
			return false;
		}
		//If the current word matches its true
		//If its smaler or large check the right tree
		int comparison = word.compareTo(root.word);
		if(comparison ==0){ 
			return true;
		}
		else if (comparison < 0){
			return contains(root.left, word);
		}else{
			return contains(root.right, word);
		}
	}
	
	// call your recursive method
	// use book as guide
	public void delete(String word){
		root = delete(root, word);
	}
	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word){
		if (root == null) {
            return null;
        }

        int comparison = word.compareTo(root.word);
        if (comparison < 0) {
            root.left = delete(root.left, word);
        } else if (comparison > 0) {
            root.right = delete(root.right, word);
        } else { //If the word matches chek if the node has no left child
            if (root.left == null) {
                return root.right;

            } else if (root.right == null) { //check if it has no right child
                return root.left;

            } else { //If the node has both children
				IndexNode parent = root;
				//Find the successor in the right subtree
                IndexNode successor = root.right;
                while (successor.left != null) {
                    parent = successor;
                    successor = successor.left;
                }

                root.word = successor.word;//Replace current nodes data with the succesors data
                root.occurences = successor.occurences;
                root.list = successor.list;
				//Remove it from the tree
                if (parent.left == successor) {
                    parent.left = successor.right;
                } else {
                    parent.right = successor.right;
                }
            }
        }
        return root;
    }
	
	
	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the list of all occurrences
	// each word and its data gets its own line
	public void printIndex() {
        printIndex(root);
    }

    private void printIndex(IndexNode root) {
        if (root == null) {
            return;
        }
        printIndex(root.left);
        System.out.println(root.word + " (" + root.occurences + ") ");
        printIndex(root.right);
    }
	
	public static void main(String[] args){
		//IndexTree index = new IndexTree();
		
		// add all the words to the tree
		// index.add("word1", 1);
        // index.add("word", 2);
        // index.add("word2", 3);
        // index.add("word2", 5);
        // index.add("iguess", 6);
        // index.add("imword", 7);
		// print out the index
		//System.out.println("Initial Index:");
        //index.printIndex();

		// test removing a word from the index
		//index.delete("banana");
        //System.out.println("Index after deleting 'banana':");
        //index.printIndex();
		
	}
}