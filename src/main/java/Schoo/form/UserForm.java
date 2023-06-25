package Schoo.form;

/**
 * ユーザ情報のFormクラス
 */
public class UserForm {
	private String name;
	private String age;
	private String mailAddress;

	/**
	 * コンストラクタ
	 * @param name
	 * @param age
	 * @param mailAddress
	 */
	public UserForm(String name, String age, String mailAddress) {
		this.name = name;
		this.age = age;
		this.mailAddress = mailAddress;
	}
	/**
	 * コンストラクタ
	 */
	public UserForm() {
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age セットする age
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * @param mailAddress セットする mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
}