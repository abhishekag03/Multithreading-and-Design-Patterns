package Lab8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.*;
import java.util.concurrent.*;

public class Pascal extends RecursiveTask<BigInteger>{
	int n, k;
	BigInteger result;
	private volatile static Map<String, Pascal> instances = new HashMap<String, Pascal>();
	
	public synchronized static Pascal getInstance(int n1, int n2) {
		String key = n1 + " " + n2;
		if (!instances.containsKey(key)) {
			instances.put(key, new Pascal(n1, n2));
		}
		return instances.get(key);
	}
	
	
//	long result;
	public Pascal(int l, int h){
		n = l;
		k = h;
		result = BigInteger.ZERO;
	}
	
	public BigInteger compute() {
		
		if (result.compareTo(BigInteger.ZERO)!=0){
			return result;
		}
		if (n==0 || k==0 || n==k) {
//			this.result = 1;
			return  BigInteger.ONE;
		}
//		System.out.println("compute");
		Pascal left = getInstance(this.n-1, this.k-1);
		Pascal right = getInstance(this.n-1, this.k);
		left.fork();
		// right.compute();
//		left.join();
		left.result = left.join();
		right.result = right.compute();
		result = left.result.add(right.result);
		return ( result);
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		// TODO Auto-generated method stub
		Reader.init(System.in);
		int n,k;
		n=1000;k=513;
//		int n = Reader.nextInt();
//		int k = Reader.nextInt();
//		System.out.println("hello");
		ForkJoinPool pool = new ForkJoinPool(1);
		Pascal task = new Pascal(n, k);
//		System.out.println("hello2");
		long startTime = System.currentTimeMillis();
		BigInteger result = pool.invoke(task);	
//		System.out.println("hello3");
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		System.out.println(result);
	}

	
	

}
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }
 
    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine() );
        }
        return tokenizer.nextToken();
    }
 
    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    static float nextFloat() throws IOException {
        return Float.parseFloat( next() );
    }
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}


/* For thread pool size 1, the value obtained for time = 1257 ms
For thread pool size 1, the value obtained for time = 1142 ms
For thread pool size 1, the value obtained for time = 1071 ms 
Speedup 1:2 = 1.10
Speedup 1:3 = 1.17
Speedup 2:3 = 1.06  */


