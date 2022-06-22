package util;

public class Common {
	
	public final static String VIEW_PATH="/WEB-INF/views/board/";
	
	//일반 게시판용
	public static class Board{
		//한페이지에 보여줄 수 있는 게시물 수
		public final static int BLOCKLIST = 10;
		
		//Common.Board.BLOCKLIST 이렇게 호출해서 사용가능
		
		//페이지 메뉴 수
		// <- 1 2 3 ->
		
		public final static int BLOCKPAGE = 3; 
	}
	
	//공지사항용
	public static class Notice{
		public final static int BLOCKLIST = 10;
		public final static int BLOCKPAGE = 3; 
	}
}
