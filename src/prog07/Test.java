package prog07;
public class Test {
    public static void main (String args[]) {
	SkipList<String, Integer> sl = new SkipList<String, Integer>();

	String[] names = { "Irina", "Victor", "Negin", "Nick", "Oliver" };
	int[] nums = { 60, 54, 21, 19, 18 };

	Integer n = null;
	for (int i = 0; i < names.length; i++) {
	    sl.put(names[i], 1000);
	    n = sl.get(names[i]);
	    if (n == null || n != 1000)
		System.out.println(names[i] + " bad get 1000? " + n);
	    n = sl.put(names[i], nums[i]);
	    if (n == null || n != 1000)
		System.out.println(names[i] + " bad put 1000? " + n);
	    n = sl.get(names[i]);
	    if (n == null || n != nums[i])
		System.out.println(names[i] + " bad get " + nums[i] + "? " + n);
	    n = sl.remove(names[i]);
	    if (n == null || n != nums[i])
		System.out.println(names[i] + " bad remove " + nums[i] + "? " + n);
	    n = sl.get(names[i]);
	    if (n != null)
		System.out.println(names[i] + " still there null? " + n);
	}
    }
}


	    
	    