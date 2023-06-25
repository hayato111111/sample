package Schoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Schoo.form.UserForm;

/**
 * Springで作成する簡単な情報入力のサンプルクラスです.
 */
@Controller
public class SchooController {
	/**
	 * トップページへのリクエスト
	 * @return user.htmlのパス
	 */
	@GetMapping("/")
	public String index() {
		// user.htmlのパスを返却する
		return "user";
	}
	/**
	 * 入力処理リクエスト
	 * @param 	ユーザ情報
	 * @return 結果画面のパス
	 */
	@PostMapping("input")
	public String input(@ModelAttribute UserForm userForm) {
		return "user-input";
	}
}