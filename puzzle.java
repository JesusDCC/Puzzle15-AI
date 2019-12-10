import java.util.*;
import java.lang.*;


public class puzzle {

	static class Node implements Comparable<Node> {
		String caminho;
		int[] vec;
		String pai;
		int peso;

		public Node(String path, int[] vector){
			caminho=path;
			vec = vector.clone();
			peso = 0;
		}


		// Getters e Setters
		public int[] getvec(){ return vec; }
		public String getpath(){ return caminho; }
		public int getPeso(){ return peso; }
		public void setPeso(int novoPeso){ peso = novoPeso; }

		@Override
		public int compareTo(Node n) {
			return Integer.valueOf(this.peso).compareTo(Integer.valueOf(n.peso));
		}
	}

	static int numNodes;


	public static int blank(int[] vec){
		for(int i=0;i<vec.length;i++){
			if(vec[i]==0){ 
				return i;
			}
		}
		return 0;
	}

	// IDFS
	public static String IDFS(int[] vec, int[] vecfinal){
		int i = 0;
		while(true){
			String path = DFS(vec,vecfinal,"",i);
			if(!path.equals(""))
				return path;
			i++;
			visited = new HashSet<>();
		}
	}

	// DFS
	private static HashSet<String> visited = new HashSet<>();
	public static String DFS(int[] vec,int[] vecfinal,String caminho,int maxdepth){

		if(visited.contains(Arrays.toString(vec)))
			return "";

		if(caminho.length()>maxdepth)
			return "";
		if(Arrays.equals(vec,vecfinal))
			return caminho;

		visited.add(Arrays.toString(vec));

		String s1="", s2="", s3="", s4="";


		if(slide(vec,'R').length!=0){
			int[] nvec = slide(vec,'R');
			s2 = DFS(nvec,vecfinal,caminho+'R',maxdepth);
			if(!s2.equals(""))
				return s2;
		}
		if(slide(vec,'D').length!=0){
			int[] nvec = slide(vec,'D');
			s4 = DFS(nvec,vecfinal,caminho+'D',maxdepth);
			if(!s4.equals(""))
				return s4;
		}
		if(slide(vec,'L').length!=0){
			int[] nvec = slide(vec,'L');
			s1 = DFS(nvec,vecfinal,caminho+'L',maxdepth);
			if(!s1.equals(""))
				return s1;
		}	
		if(slide(vec,'U').length!=0){
			int[] nvec = slide(vec,'U');
			s3 = DFS(nvec,vecfinal,caminho+'U',maxdepth);
			if(!s3.equals(""))
				return s3;
		}
	    
	    visited.remove(Arrays.toString(vec));
		return "";	
	} 

	// BFS
	public static String BFS(int[] vec,int[] vecfinal){
		if(Arrays.equals(vec,vecfinal))
			return "Puzzle já resolvido";
		String caminhob = "";
		Queue<Node> q = new LinkedList<>();
		HashSet<String> visited = new HashSet<>();
		visited.add(Arrays.toString(vec));
		Node first = new Node("", vec);
		numNodes++;
		q.add(first);
		while(!q.isEmpty()){
			Node prev = q.remove();
			String[] moves = possiblemoves(prev.getvec());
			for(String mv : moves){
				if(mv!=null){
					int[] cvec = slide(prev.getvec(),mv.charAt(0));
					String add = prev.getpath() + mv.charAt(0);
					if(Arrays.equals(cvec,vecfinal))
						return add;
					if(!visited.contains(Arrays.toString(cvec))){
						Node curr = new Node(add,cvec);
						numNodes++;
						q.add(curr);
						visited.add(Arrays.toString(curr.getvec()));
					}
				}
			}
		}
		return "Solução não encontrada";
	}

	public static String[] possiblemoves(int[] vec){
		int branco = blank(vec);
		String[] moves = new String[4];
		Arrays.fill(moves,null);

		if(branco%4!=0)
			moves[0] = "L";
		
		if(branco%4!=3)
			moves[1] = "R";

		if(branco/4!=0)
			moves[2] = "U";

		if(branco/4!=3)
			moves[3] = "D";
		
		return moves;	
	}

	// Greedy
	public static String greedy(int[] vec, int[] vec_final){
		if(Arrays.equals(vec,vec_final))
			return "Puzzle já resolvido";
		String gaminhog = "";
		Node first = new Node("",vec);
		numNodes++;
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(first);
		while(q.peek()!=null){
			Node prev = q.remove();
			String[] moves = possiblemoves(prev.getvec());
			for(String mv: moves){
				if(mv!=null){
					int[] avec = slide(prev.getvec(),mv.charAt(0));
					String add = prev.getpath() + mv.charAt(0);
					if(Arrays.equals(avec,vec_final)) return add;
					if(!ast.contains(Arrays.toString(avec))){
						Node curr = new Node(add,avec);
						numNodes++;
						curr.setPeso(heuristic(curr.getvec(),vec_final));
						q.add(curr);
						ast.add(Arrays.toString(curr.getvec()));
					}
				}
			}
		}
		return "";
	}

	public static int[] slide(int[] vec, char direction){
		int branco = blank(vec);
		int[] nvec = vec.clone();

		if(direction=='L'){
			if(branco%4==0)
				return new int[0];
			nvec[branco]=nvec[branco-1];
			nvec[branco-1]=0;
			return nvec;
		}
		if(direction=='R'){
			if(branco%4==3)
				return new int[0];
			nvec[branco]=nvec[branco+1];
			nvec[branco+1]=0;
			return nvec;
		}
		if(direction=='U'){
			if(branco/4==0)
				return new int[0];
			nvec[branco]=nvec[branco-4];
			nvec[branco-4]=0;
			return nvec;
		}
		if(direction=='D'){
			if(branco/4==3)
				return new int[0];
			nvec[branco]=nvec[branco+4];
			nvec[branco+4]=0;
			return nvec;
		}
		return new int[0];
	}

	public static void main(String[] args){
		int[] configi = new int[16];
		int[] configf = new int[16];
		Scanner stdin = new Scanner(System.in);
		System.out.println("Insira o estado inicial do  quadro:");
		System.out.println();
		for(int i=0;i<16;i++){ configi[i] = stdin.nextInt(); }
		System.out.println("Insira o estado final do  quadro:");
		System.out.println();
		for(int i=0;i<16;i++){ configf[i] = stdin.nextInt(); }
		printBoard(configi);
		long start = System.currentTimeMillis();
		long beforeUsedMem;
		long afterUsedMem;

		if(!CheckSolvable(configi, configf)){
			System.out.println("Impossivel");
			return;
		} 
		printBoard(configf);
		System.out.println("Resolver com:\n\n1)DFS\n2)BFS\n3)IDFS\n4)A*\n5)Greedy\n\nOpção:");
		int op1 = stdin.nextInt();
		switch(op1){
			case 1:
				beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				String caminho = DFS(configi,configf,"",80);
				long end = System.currentTimeMillis();
				long duration = end-start;
				afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long mem = afterUsedMem - beforeUsedMem;
				if(caminho.length()!=0){
					System.out.println("Resolvido em "+ caminho.length() + " passos");
					System.out.println("Caminho para a solução: " + caminho);
					System.out.println("Tempo de execução: " + duration + " millisegundos");
					System.out.println("Memória utilizada: " + mem + " bytes");
				}
				else 
					System.out.println("Profundidade máxima ultrapassada");
				break;
			case 3:
				beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				String caminhoi = IDFS(configi, configf);  
				long endi = System.currentTimeMillis();
				long durationi = endi-start;
				afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long memi = afterUsedMem - beforeUsedMem;
				if(caminhoi.length()!=0){ 
					System.out.println("caminho para solução: "+ caminhoi);
					System.out.println("Resolvido em: " + caminhoi.length() + "passos");
					System.out.println("Tempo de execução: " + durationi + " millisegundos");
					System.out.println("Memória utilizada: " + memi + " bytes");
				}
				break;
			case 2:
				beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				String caminhob = BFS(configi,configf);;
				long endb = System.currentTimeMillis();
				long durationb = endb-start;
				afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long memb = afterUsedMem - beforeUsedMem;
				if(caminhob.length()!=0){
					System.out.println("Número de Nós criados: " + numNodes);
					System.out.println("Resolvido em "+ caminhob.length() + " passos");
					System.out.println("Caminho para a solução: "+ caminhob);
					System.out.println("Tempo de execução: " + durationb + " millisegundos");
					System.out.println("Memória utilizada: " + memb + " bytes");
				}
				break;
			case 4:
				beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				String caminhoa = astar(configi,configf);
				long enda = System.currentTimeMillis();
				long durationa = enda-start;
				afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long mema = afterUsedMem - beforeUsedMem;
				if(caminhoa.length()!=0){
					System.out.println("Número de Nós criados: " + numNodes);
					System.out.println("Resolvido em "+ caminhoa.length() + " passos");
					System.out.println("Caminho para a solução: "+ caminhoa);
					System.out.println("Tempo de execução: " + durationa + " millisegundos");
					System.out.println("Memória utilizada: " + mema + " bytes");
				}
				break;
			case 5:
				beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				String caminhog = greedy(configi,configf);
				long endg = System.currentTimeMillis();
				long durationg = endg-start;
				afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long memg = afterUsedMem - beforeUsedMem;
				if(caminhog.length()!=0){
					System.out.println("Número de Nós criados: " + numNodes);
					System.out.println("Resolvido em "+ caminhog.length() + " passos");
					System.out.println("Caminho para a solução: " + caminhog);
					System.out.println("Tempo de execução: " + durationg + " millisegundos");
					System.out.println("Memória utilizada: " + memg + " bytes");
				}
				break;
			default: break;
		}
		return;
	}
		
    public static void printBoard(int[] t){
		System.out.println();
		System.out.printf("---------------------\n");
		System.out.printf("| %d | %d | %d | %d |\n", t[0], t[1], t[2], t[3]);
		System.out.printf("| %d | %d | %d | %d |\n", t[4], t[5], t[6], t[7]);
		System.out.printf("| %d | %d | %d | %d |\n", t[8], t[9], t[10], t[11]);
		System.out.printf("| %d | %d | %d | %d |\n", t[12], t[13], t[14], t[15]);
		System.out.printf("---------------------\n");
		System.out.println();
		return;
    }

    public static int BlankRow(int[] vec){
		double n = Math.sqrt(vec.length);
		int k=0;
		for(int i=0;i<n;i++){
		    for(int j=0;j<n;j++){
				if(vec[k]==0) return ((int)n-i);
				k++;
		    }
		}
		return -1;
    }

    public static int inversions(int[] vec){
		int inversions=0;
		for(int i=0;i<vec.length-1;i++){
		    for(int j=i+1;j<vec.length;j++)
				if(vec[i]>vec[j] && vec[j]!=0)
			    	inversions++;
		}
		return inversions;
    }

    public static Boolean CheckSolvable(int[] init, int[] fin){
		if (init.length!=fin.length) return false;

		int blankrowi = BlankRow(init);
		int blankrowf = BlankRow(fin);
		int invi = inversions(init);
		int invf = inversions(fin);
		Boolean condi = ((invi%2==0) == (blankrowi%2==1));
		Boolean condf = ((invf%2==0) == (blankrowf%2==1));	
		return (condf==condi);
    }

    public static Boolean oposto(char prev,char next){
		if(prev=='L' && next=='R')
		    return false;
		else if(prev=='R' && next == 'L')
		    return false;
		else if(prev=='D' && next == 'U')
		    return false;
		else if(prev=='U' && next == 'D')
		    return false;
		else
		    return true;
    }

    // A*
    static HashSet<String> ast = new HashSet<>();
    public static String astar(int[] vec, int[] vec_final){
		if(Arrays.equals(vec,vec_final)) return "Puzzle já resolvido";
		String gaminhoa = "";
		Node first = new Node("",vec);
		numNodes++;
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(first);
		while(q.peek()!=null){
			Node prev = q.remove();
			String[] moves = possiblemoves(prev.getvec());
			for(String mv: moves){
				if(mv!=null){
					int[] avec = slide(prev.getvec(),mv.charAt(0));
					String add = prev.getpath() + mv.charAt(0);
					if(Arrays.equals(avec,vec_final)){ return add; }
					if(!ast.contains(Arrays.toString(avec))){
						Node curr = new Node(add,avec);
						numNodes++;
						curr.setPeso(heuristic(curr.getvec(),vec_final) + curr.getpath().length());
						q.add(curr);
						ast.add(Arrays.toString(curr.getvec()));
					}
				}
			}
		}
		return "";
    }

    public static int heuristic(int[] v, int[] f){
		if(v.length==0)
			return Integer.MAX_VALUE;
		
		int k=0, i, y=0, py=0, x=0, px=0;
		int hy, hx, heuristicSum=0;
		while(k<16){
		    if(v[k]==0){
				if(k==15)
				    break;
				else{
				    k++;
				    continue;
				}
		    }
		    for(i=0;i<4;i++){
				if(f[i*4+0]==v[k] || f[i*4+1]==v[k] || f[i*4+2]==v[k] || f[i*4+3]==v[k]){
				    y=i;
				    break;
				}
		    }
		    for(i=0;i<4;i++){
				if(i*4<=k && k<(i+1)*4){
				    py=i;
				    break;
				}
		    }
		    hy=Math.abs(py-y);
		    for(i=0;i<4;i++){
				if(f[4*y+i]==v[k]){
				    x=i;
				    break;
				}
		    }
		    for(i=0;i<4;i++){
				if((4*py+i)==k){
				    px=i;
				    break;
				}
		    }
		    hx=Math.abs(px-x);
		    heuristicSum = heuristicSum+hx+hy;
		    k++;
		}
		return heuristicSum;
    }
}