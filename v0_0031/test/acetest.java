import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
public class acetest
{
	public static void main(String[] args) {
		List<Integer> test = new ArrayList<>();
		int[] a = {4, 7, 13, 22, 45, 60};
		
		
		for (int n : a) {
    			test.add(n);
		}
		List<Integer> lengthlist = new ArrayList<>();
		int[] c = {3, 4, 4, 3, 2, 5};
		for (int n : c) {
    			lengthlist.add(n);
		}
		Stack <Integer> test2 = new Stack<>();
		int[] b = { 45,13,22};
		for (int n : b) {
    			test2.push(n);
		}
		int count = 0;
		int num = 100;
		List<Integer> save = new ArrayList<>();
		List<Integer> savelength = new ArrayList<>();
		int length = 0;
		for(int k :test)
		{
			length = 0;
			for(int i = 0; i < count ; i++)
			{
				length += lengthlist.get(i)-1;
			}
			for(int i = 0 ; i < num; i++)
			{
				if(i+length==k)
				{
					num-= lengthlist.get(count);
					save.add(i);
					savelength.add(lengthlist.get(count));
				}	
			}
			count++;
		}
		System.out.println(save);
		System.out.println(savelength);
		count = 0;
		int stackput = test2.pop();
		System.out.println(stackput);
		for(int i = 0;i <num;i++)
		{
			length = 0;
			for(int j = 0; j<count;count++)
			{
				if(length+i < stackput)
				{
					length += savelength.get(i)-1;
				}
			}
			if(i+length==stackput)
			{
				System.out.println((i+length));
				
				test.remove(Integer.valueOf(i+length));

			}
			count++;	
		}
		
		System.out.println(test);


	}
}