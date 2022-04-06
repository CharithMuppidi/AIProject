package morrisGame;

import java.io.*;
import java.util.*;

public class MiniMaxGameBlack {
	
	private int pos_eval = 0;
	private int stat_est = 0;
	char[] newBoard = new char[21];
	Map<Integer, char[]> position = new HashMap<>();

	public MiniMaxGameBlack(String currentBoard, int depth) 
	{
		char[] board = currentBoard.toCharArray();
		boolean playerBlack = true;
		stat_est = miniMax(board, depth, playerBlack);
		newBoard = position.get(stat_est);
	}
	
	/**
	 *  generates moves in the midgame and endgame 
	 *  @param a board position
	 *  @return a list of board positions
	 */
	public List<char[]> generateMovesMidgameEndgame(char[] b){
		//if the board has 3 black pieces Return the list produced by GenerateHopping
		int numBlacks = 0;
		for (char elem : b) 
			if (elem == 'B')
				numBlacks++;
		//applied to the board. Otherwise return the list produced by GenerateMove
		if(numBlacks == 3)
			return generateHopping(b);
		//applied to the board.
		else
			return generateMove(b);
	}
	
	/**
	 *  generate moves created by a black piece to an adjacent location 
	 *  @param pos a board position
	 *  @return a list of board positions
	 */
	private List<char[]> generateMove(char[] board){
		//L = empty list
		List<char[]> l = new ArrayList<>(); 
		//for each location in board
		for(int location = 0; location < board.length; location++) 
		{
			//if board[location]==B
			if(board[location] == 'B') 
			{
				//n = list of neighbors of location
				List<Integer> n = neighbors(location);
				//for each j in n
				for(Integer j: n)
				{
					//if board[j] == empty
					if(board[j] == 'x') 
					{
						//b = copy of board; b[location] = empty; b[j]=W
						char[] b = board.clone();
						b[location] = 'x';
						b[j] = 'B';
						//if closeMill(j, b) GenerateRemove(b, L)
						if(closeMill(j, b))
							generateRemove(b, l);
						//else add b to L
						else
							l.add(b);
					}
				}
			}
		}
		//return L
		return l;
	}
	
	/**
	 *  generate moves created by black pieces hopping 
	 *  @param pos a board position
	 *  @return a list of board positions
	 */
	private List<char[]> generateHopping(char[] board){
		//L = empty list
		List<char[]> l = new ArrayList<>(); 
		//for each location alpha in board
		for(int alpha = 0; alpha < board.length; alpha++)
		{
			//if board[alpha] == B 
			if(board[alpha] == 'B') 
			{
				//for each location beta in board
				for(int beta = 0; beta < board.length; beta++) 
				{
					//if board[beta] == empty {
					if(board[beta] == 'x') 
					{
						//b = copy of board; b[alpha] = empty; b[beta] = B
						char[] b = board.clone(); 
						b[alpha] = 'x';
						b[beta] = 'B';
						//if closeMill(beta, b) generateRemove(b, L)
						if(closeMill(beta, b))
							generateRemove(b,l);
						//else add b to L}}
						else
							l.add(b);
					}
				}
			}	
		}	
		//return L
		return l;
	}
	
	/**
	 * generates moves create by removing a white piece
	 * @param pos a board position
	 * @param board list of board positions
	 */
	private void generateRemove(char[] board, List<char[]> l) {
		int notWhiteMills = 0;
		//for each location in board:
		for(int location = 0; location < board.length; location++)
		{
			//if board[location]==W {
			if(board[location] == 'W') 
			{
				//if not closeMill(location, board) {
				if(!closeMill(location, board)) 
				{
					//b = copy of board; b[location] = empty
					char[] b = board.clone();
					b[location] = 'x';
					//add b to L
					l.add(b);
					notWhiteMills++;
				}
			}	
		}
		//If no positions were added (all white pieces are in mills) add b to L.
		if(notWhiteMills > 0)
			l.add(board);
	}
	
	/**
	 * finds the neighbors of the location
	 * @param j a location in the array representing the board
	 * @return a list of locations in the array corresponding to j’s neighbors
	 */
	private List<Integer> neighbors(int j){
		switch(j) {
			case 0: return Arrays.asList(1, 2, 6);
			case 1: return Arrays.asList(0, 3, 11);
			case 2: return Arrays.asList(0, 3, 4, 7);
			case 3: return Arrays.asList(1, 2, 5, 10);
			case 4: return Arrays.asList(2, 5, 8);
			case 5: return Arrays.asList(3, 4, 9);
			case 6: return Arrays.asList(0, 7, 18);
			case 7: return Arrays.asList(2, 6, 8, 15);
			case 8: return Arrays.asList(4, 7, 12);
			case 9: return Arrays.asList(5, 10, 14);
			case 10: return Arrays.asList(3, 9, 11, 17);
			case 11: return Arrays.asList(1, 10, 20);
			case 12: return Arrays.asList(8, 13, 15);
			case 13: return Arrays.asList(12, 14, 16);
			case 14: return Arrays.asList(9, 13, 17);
			case 15: return Arrays.asList(7, 12, 16, 18);
			case 16: return Arrays.asList(13, 15, 17, 19);
			case 17: return Arrays.asList(10, 12, 16, 20);
			case 18: return Arrays.asList(0, 15, 19);
			case 19: return Arrays.asList(16, 18, 20);
			case 20: return Arrays.asList(11, 17, 19);
			default: return null;
		}
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
	 * generates moves for white pieces
	 * @param b a board position
	 * @return a list of all positions reachable by a white move
	 */
	private List<char[]> generateWhiteMoves(char[] b){
		List<char[]> l = new ArrayList<>(); 
		char[] tempb = new char[21];
		for(int i = 0; i < b.length; i++) {
			if(b[i] == 'B')
				tempb[i] = 'W';
			else if(b[i] == 'W')
				tempb[i] = 'B';
			else 
				tempb[i] = 'x';
		}
		l = generateMovesMidgameEndgame(tempb);
		for(int list = 0; list < l.size(); list++) 
		{
			for(int i = 0; i < l.get(list).length; i++) 
			{
				if(l.get(list)[i] == 'B')
					l.get(list)[i] = 'W';
				else if(l.get(list)[i] == 'W')
					l.get(list)[i] = 'B';
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
	private int miniMax(char[] x, int depth, boolean blackFlag) {
		pos_eval++;
		//if x is a leaf return static(x)
		if(depth == 1) {
			position.put(staticEstimate(x), x);
			return staticEstimate(x);
		}
		//maxMin
		else if(blackFlag) 
		{
			int newDepth = depth - 1;
			//set v = -infinity
			int v = Integer.MIN_VALUE;
			boolean whiteFlag = !blackFlag;
			List<char[]> children = generateMovesMidgameEndgame(x);
			//for each child y of x:
			for(char[] y: children)
				//v = max(v, MinMax(y))
				v = Math.max(v, miniMax(y, newDepth, whiteFlag));
			//return v
			return v;
		}
		//minMax
		else {
			int newDepth = depth - 1;
			//set v = +infinity
			int v = Integer.MAX_VALUE;
			boolean whiteFlag = !blackFlag;
			//for each child y of x:
			List<char[]> children = generateWhiteMoves(x);
			for(char[] y: children) 
				//v = min(v, MaxMin(y))
				v = Math.min(v, miniMax(y, newDepth, whiteFlag));
			//return v
			return v;		
		}
			
	}
	
	/**
	 * give a static evaluation of the position
	 * @param b the board position
	 * @return static evaluation
	 */
	private int staticEstimate(char[] b) 
	{
		int numWhitePieces = 0;
		int numBlackPieces = 0;
		int numWhiteMoves = 0;
		for(char elem : b) {
			if(elem == 'B')
				numBlackPieces++;
			if(elem == 'W')
				numWhitePieces++;
		}
		numWhiteMoves = generateWhiteMoves(b).size();

		if(numWhitePieces <= 2)
			return 10000;
		else if(numBlackPieces <= 2)
			return -10000;
		else if (numWhiteMoves == 0)
			return 10000;
		else
			return (1000 * (numBlackPieces - numWhitePieces) - numWhiteMoves);
			
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
				MiniMaxGameBlack bend = new MiniMaxGameBlack(board, depth);
				String bob = new String(bend.newBoard);
				System.out.println("Board Position: " + bob);
				System.out.println("Positions evaluated by static estimation: " + bend.pos_eval);
				System.out.println("MINIMAX estimate: " + bend.stat_est);
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
