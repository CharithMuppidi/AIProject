package morrisGame;

import java.io.*;
import java.util.*;


public class MiniMaxOpening {
	
	
	private int pos_eval = 0;
	private int stat_est = 0;
	char[] newBoard = new char[21];
	Map<Integer, char[]> position = new HashMap<>();
		
	public MiniMaxOpening(String currentBoard, int depth) 
	{
		char[] board = currentBoard.toCharArray();
		boolean playerWhite = true;
		stat_est = miniMax(board, depth, playerWhite);
		newBoard = position.get(stat_est);
			
	}
	
	/**
	 *  generates moves in the opening 
	 *  @param a board position
	 *  @return a list of board positions
	 */
	public List<char[]> generateMovesOpening(char[] b)
	{
		return generateAdd(b);
	}
	
	/**
	 *  generates moves created by adding a white piece 
	 *  @param board a board position
	 *  @return a list of board positions
	 */
	private List<char[]> generateAdd(char[] board){
		//L = empty list
		List<char[]> l = new ArrayList<>(); 
		//for each location in board:
		for(int location = 0; location < board.length; location++) 
		{
			//if board[location] == empty{
			if(board[location] == 'x') 
			{
				//b = copy of board; b[location] = W
				char [] b = board.clone(); 
				b[location] = 'W';
				//if closeMill(location, b) generateRemove(b, L)
				if(closeMill(location, b))
					generateRemove(b, l);
				//else add b to L
				else
					l.add(b);
			}
		}
		//return L
		return l;
	}
	
	/**
	 * generates moves create by removing a black piece
	 * @param board a board position
	 * @param l list of board positions
	 */
	private void generateRemove(char[] board, List<char[]> l) {
		int notBlackMills = 0;
		//for each location in board:
		for(int location = 0; location < board.length; location++)
		{
			//if board[location]==B {
			if(board[location] == 'B') 
			{
				//if not closeMill(location, board) {
				if(!closeMill(location, board)) 
				{
					//b = copy of board; b[location] = empty
					char[] b = board.clone();
					b[location] = 'x';
					//add b to L
					l.add(b);
					notBlackMills++;
				}
			}	
		}
		//If no positions were added (all black pieces are in mills) add b to L.
		if(notBlackMills > 0)
			l.add(board);
	}
	
	/**
	 * checks to see if the location closes a mill
	 * @param j a location in the array representing the boar
	 * @param b the board 
	 * @return true if the move to j closes a mill
	 */
	private boolean closeMill(int j, char[] b){
		//C = b[j]; C must be either W or B. Cannot be x.
		char c = b[j];
		if(c != 'x')
		{
			switch(j)
			{
			case 0: 
				if((b[2] == c && b[4] == c) || (b[6] == c && b[18] == c))
					return true;
				else
					return false;
			case 1:
				if((b[3]==c && b[5]==c) || (b[11]==c && b[20]==c))
					return true;
				else
					return false;
			case 2:
				if((b[0]==c && b[4]==c) || (b[7]==c && b[15]==c))
					return true;
				else 
					return false;
			case 3:
				if((b[1]==c && b[5]==c) || (b[10]==c && b[17]==c))
					return true;
				else
					return false;
			case 4:
				if((b[0]==c && b[2]==c) || (b[8]==c && b[12]==c))
					return true;
				else
					return false;
			case 5: 
				if((b[1]==c && b[3]==c) || (b[9]==c && b[14]==c))
					return true;
				else
					return false;
			case 6:
				if((b[0]==c && b[18]==c) || (b[7]==c && b[8]==c))
					return true;
				else
					return false;
			case 7:
				if((b[2]==c && b[15]==c) || (b[6]==c && b[8]==c))
					return true;
				else
					return false;
			case 8:
				if((b[6]==c && b[7]==c) || (b[4]==c && b[12]==c))
					return true;
				else
					return false;
			case 9:
				if ((b[10]==c && b[11]==c) || (b[5]==c && b[14]==c))
					return true;
				else
					return false;
			case 10: 
				if((b[9]==c && b[11]==c) || (b[3]==c && b[17]==c))
					return true;
				else
					return false;
			case 11:
				if((b[1]==c && b[20]==c) || (b[9]==c && b[10]==c))
					return true;
				else
					return false;	
			case 12:
				if((b[4]==c && b[8]==c) || (b[13]==c && b[14]==c) || (b[15]==c && b[18]==c))
					return true;
				else
					return false;
			case 13:
				if((b[12]==c && b[14]==c) || (b[15]==c && b[18]==c))
					return true;
				else
					return false;
			case 14:
				if((b[12]==c && b[13]==c) || (b[5]==c && b[9]==c) || (b[17]==c && b[20]==c))
					return true;
				else
					return false;
			case 15:
				if((b[2]==c && b[7]==c) || (b[16]==c && b[17]==c) || (b[12]==c && b[18]==c))
					return true;
				else
					return false;
			case 16:
				if((b[15]==c && b[17]==c) || (b[13]==c && b[19]==c))
					return true;
				else
					return false;
			case 17:
				if((b[3]==c && b[10]==c) || (b[15]==c && b[16]==c) || (b[14]==c && b[20]==c))
					return true;
				else
					return false;
			case 18:
				if((b[0]==c && b[6]==c) || (b[12]==c && b[15]==c) || (b[19]==c && b[20]==c))
					return true;
				else
					return false;
			case 19:
				if((b[13]==c && b[16]==c) || (b[18]==c && b[20]==c))
					return true;
				else
					return false;
			case 20:
				if((b[1]==c && b[11]==c) || (b[14]==c && b[17]==c) || (b[18]==c && b[19]==c))
					return true;
				else
					return false;
			default: return false;
			}
		}
		else
			return false;
	}
	
	/**
	 * generates moves for black pieces
	 * @param b a board position
	 * @return a list of all positions reachable by a black move
	 */
	private List<char[]> generateBlackMoves(char[] b){
		List<char[]> l = new ArrayList<>(); 
		char[] tempb = new char[21];
		for(int i = 0; i < b.length; i++) {
			if(b[i] == 'W')
				tempb[i] = 'B';
			else if(b[i] == 'B')
				tempb[i] = 'W';
			else 
				tempb[i] = 'x';
		}
		l = generateMovesOpening(tempb);
		for(int list = 0; list < l.size(); list++) 
		{
			for(int i = 0; i < l.get(list).length; i++) 
			{
				if(l.get(list)[i] == 'W')
					l.get(list)[i] = 'B';
				else if(l.get(list)[i] == 'B')
					l.get(list)[i] = 'W';
				else 
					l.get(list)[i] = 'x';
			}
		}
		return l;
	}
	
	/**
	 * Uses miniMax algorithm to generate the best move
	 * @param x the position of the board
	 * @param depth the depth that we are current searching in the tree
	 * @param whiteFlag whether we are max or min
	 * @return the static estimate of the best move
	 */
	private int miniMax(char[] x, int depth, boolean whiteFlag) {
		pos_eval++;
		//if x is a leaf return static(x)
		if(depth == 1) {
			position.put(staticEstimate(x), x);
			return staticEstimate(x);
		}
		//maxMin
		else if(whiteFlag) 
		{
			int newDepth = depth - 1;
			//set v = -infinity
			int v = Integer.MIN_VALUE;
			boolean blackFlag = !whiteFlag;
			List<char[]> children = generateMovesOpening(x);
			//for each child y of x:
			for(char[] y: children)
				//v = max(v, MinMax(y))
				v = Math.max(v, miniMax(y, newDepth, blackFlag));
			//return v
			return v;
		}
		//minMax
		else {
			int newDepth = depth - 1;
			//set v = +infinity
			int v = Integer.MAX_VALUE;
			boolean blackFlag = !whiteFlag;
			//for each child y of x:
			List<char[]> children = generateBlackMoves(x);
			for(char[] y: children) 
				//v = min(v, MaxMin(y))
				v = Math.min(v, miniMax(y, newDepth, blackFlag));
			//return v
			return v;		
		}
			
	}
	
	
	/**
	 * give a static evaluation of the position
	 * @param b the board position
	 * @return static evaluation
	 */
	private int staticEstimate(char[] b) {
		int numWhitePieces = 0;
		int numBlackPieces = 0;
		for(char elem : b) {
			if(elem == 'W')
				numWhitePieces++;
			if(elem == 'B')
				numBlackPieces++;
		}
		return numWhitePieces - numBlackPieces;
	}
	
	public static void main(String[] args) {
		File fin = new File(args[0]);
		File fout = new File(args[1]);
		int depth = Integer.parseInt(args[2]);
		try {
			FileInputStream fish = new FileInputStream(fin);
			PrintWriter otter = new PrintWriter(new FileWriter(fout));
			try (Scanner scan = new Scanner(fish)) {
				String board = scan.next(); 
				MiniMaxOpening start = new MiniMaxOpening(board, depth);
				String bob = new String(start.newBoard);
				System.out.println("Board Position: " + bob);
				System.out.println("Positions evaluated by static estimation: " + start.pos_eval);
				System.out.println("MINIMAX estimate: " + start.stat_est);
				otter.write(bob);
			}
			fish.close();
			otter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
