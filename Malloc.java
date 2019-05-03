/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package malloc;
import java.util.Scanner;
/**
 *
 * @author piyumika
 */
class LinkedList
{
	//Class variables for the Linked List
	private static Node head;
	private static int numNodes;
        private static char has;
        private static int index;
        public static int number;
       
	
	public static void main(String [] args)
	{
            //allocation memory using user input
            System.out.println("1. To allocate memory : malloc\n2. To free memory : free\n3. To end the process : end\n3. To print the memory");
		
		LinkedList ll = new LinkedList();
		
                Scanner scan= new Scanner(System.in);
                String allocate_free;
                do{
                  allocate_free=scan.nextLine(); 
                  //to allocate memory
                if("malloc".equals(allocate_free)){
                    int asize=scan.nextInt();
                    ll.malloc(asize);
                   //to free up space
                }else if("free".equals(allocate_free)){
                    int fnumber=scan.nextInt();
                    ll.free(fnumber);
                   // to print the memory
                }else if("p".equals(allocate_free)){
                    LinkedList.printList(); 
                }
                //warn when space is finished
             }while(!"end".equals(allocate_free));
                if("end".equals(allocate_free)){
                    System.out.println("****************Memory process is finish**************");
                }
                
                
	}
       
	
	public LinkedList()
	{
            //linked list constructor with 25000 memory units
                number=0;
		head = new Node(25000);
                head.space="hole";
                head.start=0;
                head.end=25000;
                numNodes=1;
	}
	
	public void addAtHead(int size)
	{
            //add node at the head of linkedlist
		Node temp = head;
                if(temp.size>size){
                    head = new Node(size);
                    head.next = temp;
                    head.space="allocated";
                    head.start=0;
                    head.end=size;
                    head.n=++number;
                    temp.start=head.end;
                    temp.size=temp.size-size;
                    numNodes++;
                }else if(temp.size==size){
                    temp.space="allocated";
                    temp.n=++number;
                    head=temp;
                    
                }
		
		
	}
	
	
	public void addAtIndex(int index, int size)
                //add node at the given index
	{
		Node temp = head;
		Node holder;
		for(int i=0; i < (index-1); i++)
		{
			temp = temp.next;
		}
		holder = temp.next;
                
                if(temp.next.size>size){
                    
                    Node node=new Node(size);
                    temp.next=node;
                    node.next=holder;
                    node.start=temp.end;
                    node.end=node.start+size;
                    node.n=++number;//number of the new node
                    
                    holder.space="hole";
                    holder.start=node.end;
                    holder.size=holder.size-size;
                    numNodes++;
                    
                }else if(temp.next.size==size){
                    temp.next.space="allocated";
                    temp.next.n=++number;
                    
                }
		
		;
	}
        
        public int malloc(int size){
            //best fit algorithm
            
            bestFit(size);
        /*check whether there is a best fit*/
            if(has=='t'){
                //System.out.println(index);
                if(index==0){
                    addAtHead(size);
                   
                    
                }else if(index>0){
                    addAtIndex(index,size);
                }
            }
            return this.number;
                
        }
	
        public void free(int n){
         
            Node temp1=head;
            int j;
            int index = 0;
            /*to search the index of the correct node*/
            for(j=0;j<numNodes;j++){
                if(temp1.n==n){
                    index=j;
                    
                    //System.out.println("////////////"+index);
                    break;
                }else{
                    temp1=temp1.next;
                }
            } 
            int i=0;
            Node temp=head;
            /*if number cannot be found*/
            if(temp1==null && index==0){
                System.out.println("incorrect index");
                index= -1;
                
            }
            
           
            /*to set the pointer to free*/
            else if(index<numNodes && index>0){
                while(i!=index-1){      
                temp=temp.next;
                i++;
                }
            }else if(index==0){
                temp=head;
            }
            
            if(index==0 && temp.next==null){
                temp.space="hole";
            }else if(index==0 && temp.next!=null){
                   if(temp.next.space=="hole" && temp.space=="allocated"){
                    temp.end=temp.next.end;
                    temp.space="hole";
                    temp.size=temp.size+temp.next.size;
                    temp.next=null;
                    numNodes--;
                    
                }else if(temp.next.space=="allocated" && temp.space=="allocated"){
                    temp.space="hole";
                }
            }else if(index>0){/*when  doing free node is the last and previous is allocated*/
                if(temp.next.next==null&&temp.space=="allocated"&&temp.next.space=="allocated"){
                    temp.next.space="hole";
                }else if(temp.space=="hole" &&temp.next.next==null && temp.next.space=="allocated"){/*when the left  is hole and doing free node is the last */
                    temp.end=temp.next.end;
                    temp.size=temp.size+temp.next.size;
                    temp.next=null;
                    numNodes--;
                }else if(temp.next.next!=null){
                    if(temp.space=="allocated"&&temp.next.next.space=="allocated" && temp.next.space=="allocated"){/*when nodes in other two sides are allocated*/
                        temp.next.space="hole";
                    }else if(temp.space=="hole" &&temp.next.next.space=="allocated" && temp.next.space=="allocated"){/*left is hole right is allocated*/
                        temp.end=temp.next.end;
                        temp.size=temp.size+temp.next.size;
                        temp.next=temp.next.next;
                        temp.space="hole";
                        numNodes--;
                    }else if(temp.space=="allocated"&&temp.next.next.space=="hole" &&temp.next.next.next==null && temp.next.space=="allocated"){/*left node allocated , right node is hole and right node is the last*/
                        temp.next.end=temp.next.next.end;
                        temp.next.space="hole";
                        temp.next.size=temp.next.size+temp.next.next.size;
                        temp.next.next=null;
                        numNodes--;
                    }else if(temp.space=="allocated"&&temp.next.next.space=="hole" &&temp.next.next.next!=null && temp.next.space=="allocated"){/*left node allocated , right node is a hole and right node is not the last*/
                        temp.next.end=temp.next.next.end;
                        temp.next.space="hole";
                        temp.next.size=temp.next.size+temp.next.next.size;
                        temp.next.next=temp.next.next.next;
                        numNodes--;
                    }else if(temp.space=="hole"&& temp.next.next.space=="hole" &&temp.next.next.next==null && temp.next.space=="allocated"){/*left is hole right is hole right is the last*/
                        temp.end=temp.next.next.end;
                        temp.space="hole";
                        temp.size=temp.size+temp.next.size+temp.next.next.size;
                        temp.next=null;
                        numNodes=numNodes-2;
                    }else if(temp.space=="hole"&& temp.next.next.space=="hole" &&temp.next.next.next!=null && temp.next.space=="allocated"){/*left is hole right is hole right is not the last*/
                        temp.end=temp.next.next.end;
                        temp.space="hole";
                        temp.size=temp.size+temp.next.size+temp.next.next.size;
                        temp.next=temp.next.next.next;
                        numNodes=numNodes-2;
                    }
                }
            
            
            }else{
                System.out.println("no node");
            }
            
        }
		
	public static void printList()
	{
            System.out.println("============ TESTING ============");
		Node temp = head;
                //int i=0;
		while(temp != null)
		{
			System.out.println("\nnumber of nodes"+numNodes+"\nnumber: "+temp.n+"\nsize: "+temp.size+"\nspace:"+temp.space+"\nstart:"+temp.start+"\tend: "+temp.end);
                        System.out.println("____________________________________________________");
                        temp = temp.next;
                        //i++;
		}
                
	}
        
        public static void bestFit(int size){
            Node temp=head;
           
            int i=25000,j=0;
            index = 0;
            has='f';
            while(temp!=null){
                if(temp.space=="hole" &&temp.size>=size){
                    if(temp.size<=i){
                        i=temp.size;
                        index=j;
                        has='t';
                    }
                }
                temp=temp.next; 
                j++;
            }
            if(has=='f'){
                System.out.println("***********no space in the memory for "+size+" ************");
            }
        }
	
	
	
	class Node
	{
		//Declare class variables
		private Node next;
		private int size;
                int start;
                int end;
                String space;
                int n;
		
		public Node(int size )
		{
                    //noed constructor
			this.size=size;
                        this.space="allocated";
                        this.next=null;
		}
				
	}
        
}