package MainPackage;
import java.util.ArrayList;


public class Tree<T> {

	private Node<T> root;

	public Tree(Node<T> root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return (root == null) ? true : false;
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	public int getNumberOfNodes() {
		return getNumberOfDescendants(root) + 1;
	}

	public int getNumberOfDescendants(Node<T> node) {
		int n = node.getChildren().size();
		for (Node<T> child : node.getChildren())
			n += getNumberOfDescendants(child);

		return n;
	}
	
	
	public ArrayList<Node<T>> getNodesByLevel(){
		ArrayList<Node<T>> nodelist = new ArrayList<Node<T>>();
		nodelist.add(this.getRoot());
		getRecursive(this.getRoot(),nodelist);
	return nodelist;
	} 	
	
	private ArrayList<Node<T>> getRecursive(Node<T> node, ArrayList<Node<T>> nodelist){
		for(int i=0; i<node.getChildren().size(); i++)
			nodelist.add(node.getChildren().get(i));
		
		for(int i=0; i<node.getChildren().size(); i++)
			getRecursive(node.getChildren().get(i),nodelist);			
		return nodelist;
	}
	
	public ArrayList<Node<T>> allDescendants(Node<T> node) {
		ArrayList<Node<T>> list = new ArrayList<Node<T>>();

		getDescendants(node,list);

		return list;
	}
	
	public ArrayList<Node<T>> getDescendants(Node<T> node, ArrayList<Node<T>> list) {

		for (Node<T> child : node.getChildren()){	
			list.add(child);
			getDescendants(child,list);
		}		
	return list;
	}
		
	public ArrayList<Node<T>> getParents(Node<T> node, ArrayList<Node<T>> list){		
		
		if(!node.getData().toString().contains(root.getData().toString()) ){
			list.add(node);
			getParents(node.getParent(),list);
			
		}
		
	return list;
	}	
	
	public boolean find(Node<T> node, T keyNode) {
		boolean res = false;
		if (node.getData().equals(keyNode))
			return true;

		else {
			for (Node<T> child : node.getChildren())
				if (find(child, keyNode))
					res = true;
		}

		return res;
	}
		
	public boolean checker(String Data1, String Data2){
		boolean flag =true;
		char[] array1,array2;
		array1 = Data1.toCharArray();
		array2 = Data2.toCharArray();
		
		if(Data1.length() != Data2.length())
			return false;
		
		for(int i=0; i<Data1.length(); i++){
			if(array1[i] != array2[i])
				flag=false;
		}
				
		return flag;
	}
	
	public Node<T> findNodeinTree(String Data)
	{
		ArrayList<Node<T>> nodeList = new ArrayList<Node<T>>();
		Node<T> node = this.root;
		
		if(Data == null)
    		return null;
		if(node.getData().toString() == Data) 
			return node;

		nodeList = this.getPreOrderTraversal();
		for(Node<T> oneNode: nodeList)
		{
			
			if(checker(Data,oneNode.getData().toString()) ){
				return oneNode;
			}
				
		}
		
	return null;
	}
		
    private Node<T> findNode(Node<T> node, T keyNode)
    {
    	if(node == null)
    		return null;
    	if(node.getData().equals(keyNode))
    		return node;
    	else
    	{
    		Node<T> cnode = null;
    		for (Node<T> child : node.getChildren())
    			if ((cnode = findNode(child, keyNode)) != null)
    				return cnode;
    	}
    return null;         
    } 

	public ArrayList<Node<T>> getPreOrderTraversal() {
		ArrayList<Node<T>> preOrder = new ArrayList<Node<T>>();
		buildPreOrder(root, preOrder);
		return preOrder;
	}

	public ArrayList<Node<T>> getPostOrderTraversal() {
		ArrayList<Node<T>> postOrder = new ArrayList<Node<T>>();
		buildPostOrder(root, postOrder);
		return postOrder;
	}

	private void buildPreOrder(Node<T> node, ArrayList<Node<T>> preOrder) {
		preOrder.add(node);
		for (Node<T> child : node.getChildren()) {
			buildPreOrder(child, preOrder);
		}
	}

	private void buildPostOrder(Node<T> node, ArrayList<Node<T>> postOrder) {
		for (Node<T> child : node.getChildren()) {
			buildPostOrder(child, postOrder);
		}
		postOrder.add(node);
	}

	public ArrayList<Node<T>> getLongestPathFromRootToAnyLeaf() {
		ArrayList<Node<T>> longestPath = null;
		int max = 0;
		for (ArrayList<Node<T>> path : getPathsFromRootToAnyLeaf()) {
			if (path.size() > max) {
				max = path.size();
				longestPath = path;
			}
		}
		return longestPath;
	}

	public int getMaxDepth()
	{
		return getLongestPathFromRootToAnyLeaf().size();
	}
	
	public ArrayList<ArrayList<Node<T>>> getPathsFromRootToAnyLeaf() {
		ArrayList<ArrayList<Node<T>>> paths = new ArrayList<ArrayList<Node<T>>>();
		ArrayList<Node<T>> currentPath = new ArrayList<Node<T>>();
		getPath(root, currentPath, paths);

		return paths;
	}
	
	public ArrayList<ArrayList<Node<T>>> getPathsFromNodeToAnyLeaf(Node<T> node) {
		ArrayList<ArrayList<Node<T>>> paths = new ArrayList<ArrayList<Node<T>>>();
		ArrayList<Node<T>> currentPath = new ArrayList<Node<T>>();
		getPath(node, currentPath, paths);

		return paths;
	}
	
	public Tree<T> markAs(String color, ArrayList<Node<T>> Nodes){
				
		for(Node<T> current: Nodes  ){
			colorTree(current,color);			
		}
		
		return this;
	}
	
	public  void colorTree(Node<T> node, String color){
		Node<T> current = this.findNode(node, node.getData());
		current.setColor(color);
		
		if(current.getChildren().size() == 0 )	{
			return;
		}	
		for(Node<T> child : node.getChildren())
			colorTree(child,color);
				
	}
	
	public void markTree(ArrayList<Node<T>> list, String color){
		for(Node<T> child: list)
			colorTree(child,color);
		
	}

	private void getPath(Node<T> node, ArrayList<Node<T>> currentPath,
			ArrayList<ArrayList<Node<T>>> paths) {
		if (currentPath == null)
			return;

		currentPath.add(node);

		if (node.getChildren().size() == 0) {
			
			paths.add(cloneList(currentPath));
		}
		for (Node<T> child : node.getChildren())
			getPath(child, currentPath, paths);

		int index = currentPath.indexOf(node);
		for (int i = index; i < currentPath.size(); i++)
			currentPath.remove(index);
	}
	
	public ArrayList<Node<T>> getAllLeaves(){
		ArrayList<Node<T>> list = new ArrayList<Node<T>>();
		ArrayList<ArrayList<Node<T>>> lists = getPathsFromRootToAnyLeaf();
		int size;
		for(int i=0; i<lists.size(); i++){
			size = lists.get(i).size();
			list.add(lists.get(i).get(size-1));
		}		
		return list;
	}
	

	private ArrayList<Node<T>> cloneList(ArrayList<Node<T>> list) {
		ArrayList<Node<T>> newList = new ArrayList<Node<T>>();
		for (Node<T> node : list)
			newList.add(new Node<T>(node));

		return newList;
	}
}