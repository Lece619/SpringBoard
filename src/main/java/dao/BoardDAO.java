package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import vo.BoardVO;

@Component
public class BoardDAO {
	
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public BoardDAO() {
	}
	
	//게시판 목록 전체 조회
	public List<BoardVO> selectList(){
		List<BoardVO> list = sqlSession.selectList("b.board_list2");
		
		return list;
	}
	
	//전체 게시물 수 조회
	public int getRowTotal() {
		int count = sqlSession.selectOne("b.board_count");
		
		return count;
	}
	
	public List<BoardVO> selectList(HashMap<String, Integer> map){
		List<BoardVO> list = sqlSession.selectList("b.board_list", map);
		
		return list;
	}


	public int insert(BoardVO vo) {
		int res = sqlSession.insert("b.board_insert",vo);

		return res;
	}
	
	//하나의 인덱스 가져오기
	public BoardVO selectOne(int idx) {
		BoardVO vo = sqlSession.selectOne("b.board_one",idx);
		return vo;
	}


	//조회수 하나 올리기
	public int update_readhit(int idx) {
		int res = sqlSession.update("b.update_readhit",idx);
		return res;
	}
	
	//댓글 추가를 위한 step + 1
	public int update_step(BoardVO base_vo){
		int res = sqlSession.update("b.board_update_step",base_vo);
		return res;
	}


	public int reply(BoardVO vo) { 
		int res = sqlSession.insert("b.board_reply",vo);
		return res;
	}


	public int del_update(BoardVO baseVO) {
		int res = sqlSession.update("b.del_update",baseVO);
		return res;
	}
	
	
	
}
