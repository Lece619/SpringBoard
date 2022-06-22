package com.korea.bbs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.BoardDAO;
import util.Common;
import util.Paging;
import vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	
	BoardDAO board_dao;
	public BoardController() {
		// TODO Auto-generated constructor stub
	}
	public void setBoard_dao(BoardDAO board_dao) {
		this.board_dao = board_dao;
	}
	
	@RequestMapping(value= {"/","/board_list.do"})
	public String list(Model model) {
		int nowPage = 1; //현재 페이지 설정
		String page = request.getParameter("page");//처음엔 null
		
		if(page != null && !page.isEmpty()) {
			nowPage = Integer.parseInt(page);
		}
		
		//한 페이지에 표시될 게시물의 시작과 끝 번호를 계산 해야한다.
		//page가 1이면 1~10 까지 계산이 되야되고
		//page가 2 명 11~20 까지 계산이 되야한다.
		int start = (nowPage-1) * Common.Board.BLOCKLIST + 1;
		int end = start + Common.Board.BLOCKLIST - 1;
		
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		//전체목록 가져오기 
		//List<BoardVO> list = BoardDAO.getInstance().selectList();
		
		//전체 게시글 조회 -> 페이지 번호에 따른 게시글 조회
		List<BoardVO> list = board_dao.selectList(map);
		
		//전체 게시글 수 조회
		int rowTotal =  board_dao.getRowTotal();
		
		String pageMenu = Paging.getPaging("board_list.do", nowPage, rowTotal, Common.Board.BLOCKLIST, Common.Board.BLOCKPAGE);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		return Common.VIEW_PATH + "board_list.jsp?page=" + nowPage;
	}
	
	
	@RequestMapping("insert_form.do")
	public String insert_form() {
		return Common.VIEW_PATH + "insert_form.jsp";
	}
	
	//글 추가하기
	@RequestMapping("insert.do")
	public String insert(BoardVO vo) throws UnknownHostException {
		String ip = request.getRemoteAddr();//ip 구해오기
		
		String myIp = InetAddress.getLocalHost().getHostAddress();
		if(ip.equals("0:0:0:0:0:0:0:1")) {
			ip=myIp;
		}
		
		vo.setIp(ip);
		
		int res =  board_dao.insert(vo);
		
		//성공시 등록완료후 게시판 첫 페이지로 복귀

//		return Common.VIEW_PATH + "board_list.jsp";
		return "redirect:board_list.do";
	}
	
	@RequestMapping("view.do")
	public String view(int idx, int page, Model model) {

		//조회수증가
		HttpSession session = request.getSession();
		String show = (String)(session.getAttribute("show"));
		if(show == null) {			
			int res = board_dao.update_readhit(idx);
			session.setAttribute("show", "0");
		}
		//메인화면으로 갈때는 세션을 해제해줘야 잘 작동한다.
		BoardVO vo = board_dao.selectOne(idx);
		
		
		//상세보기 페이지로 전환하기 위해 바인딩 및 포워딩을 해준다.
		model.addAttribute("vo", vo);
		model.addAttribute("page", page );
		
		return  Common.VIEW_PATH + "board_view.jsp";
	}
	
	@RequestMapping("reply_form.do")
	public String reply_form(int idx, int page, Model model) {
		model.addAttribute("idx", idx);
		model.addAttribute("page", page);
		
		return Common.VIEW_PATH+"reply_form.jsp";
	}
	
	@RequestMapping("reply.do")
	public String doReply(BoardVO vo, int page) throws UnknownHostException {
		String ip = request.getRemoteAddr();
		String myIp = InetAddress.getLocalHost().getHostAddress();
		if(ip.equals("0:0:0:0:0:0:0:1")) {
			ip=myIp;
		}
		BoardVO base_vo = board_dao.selectOne(vo.getIdx());
		board_dao.update_step(base_vo);
		vo.setRef(base_vo.getRef());
		vo.setStep(base_vo.getStep()+1);
		vo.setDepth(base_vo.getDepth()+1);
		vo.setIp(ip);
		int res = board_dao.reply(vo);
		
		return "redirect:board_list.do?page="+page;
	}
}
