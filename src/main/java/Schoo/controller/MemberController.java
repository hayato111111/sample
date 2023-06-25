package com.example.demo.Controller;



import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Inn;
import com.example.demo.entity.Member;
import com.example.demo.entity.Room;
import com.example.demo.model.MemberModel;
import com.example.demo.model.MemberModelUpdate;
import com.example.demo.model.RoomModelUpdate;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.InnRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RoomRepository;

import jakarta.servlet.http.HttpSession;



@Controller
public class MemberController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	RoomModelUpdate roomModelUpdate;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	InnRepository innRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	MemberModelUpdate memberModelUpdate;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberModel memberModel;
	
	@GetMapping("/toppage")
	public String index() {
		
		return "/member/membertop";
	}
	
	//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	
	@GetMapping("/member/logout")
	public String logout() {
		
		session.invalidate();
		return "/member/membertop";
	}
	
	@GetMapping("/member/login")
	public String login() {
		return "/member/login";
	}
	
	//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	
	//ログイン処理
	@PostMapping("/member/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {    

		System.out.println(email);
		System.out.println(password);
		List<Member> memberList = memberRepository.findByEmailAndPassword(email, password);
		if (memberList == null || memberList.size() == 0) {
			model.addAttribute("error", "・メールアドレスもしくはパスワードが一致しません");
			return "member/login";
		}
		
		
		//memberModelはログイン後ユーザの情報をセッションで管理する
		//新規登録では、ダミ-モデルを作って、そっちをEntitiyに移してデータベースに登録
		//更新ではダミーモデルを作ってそっちをEntitiyに移してデータベースに登録。そして完了画面を返すメソッドでダミーモデルの内容をmemberModelにコピーする
		Member member = memberList.get(0);
		memberModel.setMemberId(member.getMemberId());
		memberModel.setName(member.getName());
		memberModel.setEmail(member.getEmail());
		memberModel.setAge(member.getAge());
		memberModel.setAddress(member.getAddress());
		

 
		return "redirect:/toppage";
	}
	
	//新規登録処理　登録画面を返す
	@GetMapping("/member/add")
	public String add() {
		return "member/addtop";
	}
	
	//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	
	//新規登録処理　確認画面を返す
	@PostMapping("/member/add")
	public String add(Model model,
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="email",defaultValue="") String email,
			@RequestParam(name="age",required = false) Integer age,
			@RequestParam(name="adress",defaultValue="") String adress,
			@RequestParam(name="password",defaultValue="") String password
			) {
		
		//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		
		
		//変更されてもいいダミーモデルに追加する
		//そうすることで戻るボタンで戻られても、ダミーに登録されてあるので、ログイン後に登録したユーザ情報のモデルには関係ない
		memberModelUpdate.setName(name);
		memberModelUpdate.setEmail(email);
		memberModelUpdate.setAge(age);
		memberModelUpdate.setAddress(adress);
		memberModelUpdate.setPassword(password);
		
		
		
		return "member/memberaddcheck";
		
	}
	
	
	//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	
	//新規登録処理　完了画面を返す
	@GetMapping("/member/addok")
	public String addOk() {
		
		//コンストラクタを使用してダミーモデルからとってきてMemberエンティティに値を代入する
		Member member = new Member(
		memberModelUpdate.getName(),
		memberModelUpdate.getEmail(),
		memberModelUpdate.getAge(),
		memberModelUpdate.getAddress(),
		memberModelUpdate.getPassword()
		);
		
		
		
		//データベースに値を登録する
		memberRepository.save(member);
		
		
		
		//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		List<Member> member2  = memberRepository.findByEmail(memberModelUpdate.getEmail());
		
		memberModel.setMemberId(member2.get(0).getMemberId());
		memberModel.setName(member2.get(0).getName());
		memberModel.setEmail(member2.get(0).getEmail());
		memberModel.setAge(member2.get(0).getAge());
		memberModel.setAddress(member2.get(0).getAddress());
		memberModel.setPassword(member2.get(0).getPassword());
		
		return "member/memberaddok";
	}
	
	//都道府県で宿検索
	@GetMapping("/member/area")
	public String area(
			@RequestParam(name="areaId",required = false) Integer areaId,
			Model model
			) {
		
	
		
			 List<Inn>innList = innRepository.findByAreaId(areaId);
			 
			 model.addAttribute("innList",innList);
					 
			 System.out.println(innList.get(0).getInnPicture());
			 
			 return "/member/innsearch";
	
		
	}
	
	@GetMapping("/member/room")
	public String room(
			@RequestParam(name="innId",required=false) Integer innId,
			@RequestParam(name="price1",required = false) Integer price1,
			@RequestParam(name="price2",required = false) Integer price2,
			@RequestParam(name="roomtype1Id",required = false) Integer roomtype1Id,
			@RequestParam(name="roomtype2Id",required = false) Integer roomtype2Id,
			Model model
			) {
		
		System.out.println(price1);
		System.out.println(price2);
		System.out.println(roomtype1Id);
		System.out.println(roomtype2Id);
		List<Room> roomList;
		if(price1==null && price2==null && roomtype1Id!=null) {
			roomList = roomRepository.findByRoomType1IdAndRoomType2IdAndInnId
					(roomtype1Id,roomtype2Id,innId);
		}else if(price1==null && price2!=null) {
			roomList = roomRepository.findByRoomType1IdAndRoomType2IdAndFeeLessThanEqualAndInnId
					(roomtype1Id,roomtype2Id,price2,innId);
		}else if(price1!=null && price2==null) {
			roomList = roomRepository.findByRoomType1IdAndRoomType2IdAndFeeGreaterThanEqualAndInnId
					(roomtype1Id,roomtype2Id,price1,innId);
		}else if(price1!=null && price2!=null) {
			roomList = roomRepository.findByRoomType1IdAndRoomType2IdAndFeeBetweenAndInnId
					(roomtype1Id,roomtype2Id,price1,price2,innId);
		}else {
			roomList = roomRepository.findByInnId(innId);
		}
		
		
		model.addAttribute("roomList",roomList);
		 model.addAttribute("innId",innId);	
		
		
		return "member/roomlist";
		
		
		
		
	}
	
	@GetMapping("/member/emptye")
	public String emptye(
			@RequestParam(name="roomId",required=false) Integer roomId,
			Model model
			) {
		
		//セッションに登録。下のカレンダーで使うから
		Room roomSub = roomRepository.findByRoomId(roomId);
		
		roomModelUpdate.setRoomId(roomSub.getRoomId());
		roomModelUpdate.setRoomNumber(roomSub.getRoomNumber());
		roomModelUpdate.setRoomType1Id(roomSub.getRoomType1Id());
		roomModelUpdate.setRoomType2Id(roomSub.getRoomType2Id());
		roomModelUpdate.setFee(roomSub.getFee());
		roomModelUpdate.setInnId(roomSub.getInnId());
		roomModelUpdate.setRoomId(roomSub.getRoomId());
		roomModelUpdate.setRoomPicture(roomSub.getRoomPicture());

		model.addAttribute("emptye",getScheduleCalendar());
		return "member/roomemptye";
	}
	
		
		public String getScheduleCalendar() {
		StringBuilder html = new StringBuilder();

	        //今月のスケジュールを取得する場合
	        LocalDate today = LocalDate.now();  //本日
	        int year = today.getYear();         //今年
	        Month month = today.getMonth();     //今月

	        //HTMLテーブルのヘッダー
	        html.append("<table>");
	        html.append("<caption>" + year + "年" + (month.ordinal() + 1) + "月</caption>");
	        html.append("<tr><th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th></tr>");

	        LocalDate currentDate = LocalDate.of(year, month, 1);   //今月の1日
	        DayOfWeek firstWeek = currentDate.getDayOfWeek();       //今月1日の曜日

	        //カレンダーの最初の余白
	        html.append("<tr>");
	        for (int i = 0; i < firstWeek.getValue(); i++) {        //今月1日の曜日まで埋める
	            html.append("<td></td>");
	        }

	        int daysInMonth = month.length(this.isLeapYear(year));  //今月の日数
	        for (int day = 1; day <= daysInMonth; day++) {

	            html.append("<td>" + day);

	            String ret = this.getSchedule(currentDate);     //この日のスケジュールを取得
	            if ("".equals(ret)) {
	            	html.append("<br><a href=\"/member/booking?day=" + currentDate+ "\">" +"〇"+ "</a>");
	            }
	            else {
	                html.append(this.getSchedule(currentDate));
	            }

	            html.append("</td>");

	            //土曜日で改行
	            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
	                html.append("</tr><tr>");
	            }

	            currentDate = currentDate.plusDays(1);              //+1日する
	        }

	        //カレンダーの最後の余白
	        while (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
	            html.append("<td></td>");
	            currentDate = currentDate.plusDays(1);
	        }

	        html.append("</tr>");
	        html.append("</table>");

	        return html.toString();
	    }

	    //うるう年の判定
	    private boolean isLeapYear(int year) {
	        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	    }

	    //指定した日のスケジュール内容を取得する
	    private String getSchedule(LocalDate currentDate) {
	        StringBuilder html = new StringBuilder();
	        
	    	List<Booking> booking = bookingRepository.findByRoomId(roomModelUpdate.getRoomId());
	    	System.out.println(roomModelUpdate.getRoomId());
	        for (Booking bookingModel : booking) {
	            if (bookingModel.getDay().equals(currentDate)) {
	                //html.append("<br><a href=\"tekito?id="  + "\">" +"×"+ "</a>");
	            	html.append("<br>x");
	            }
	        }

	        return html.toString();

	}
	    
	   
	    @GetMapping("/member/booking")
	    public String booking(
	    		@RequestParam(name="day",required=false) LocalDate day
	    		) {
	    	
	    	System.out.println(day);
	    	return "member/addtop";//個々を直そう！
	    	
	    }
	    
	
	
	
	
	

}
