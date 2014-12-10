package jp.terasoluna.batch.functionsample.b006;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

@FileFormat(encloseChar = '"')
public class SampleFileLineObject {

    @InputFileColumn(columnIndex = 0)
    private String code = null;

    @InputFileColumn(columnIndex = 1)
    private String name = null;

    @InputFileColumn(columnIndex = 2)
    private String name_kana = null;

    @InputFileColumn(columnIndex = 3)
    private String sex = null;

    @InputFileColumn(columnIndex = 4)
    private String telnum = null;

    @InputFileColumn(columnIndex = 5)
    private String mail = null;

    @InputFileColumn(columnIndex = 6)
    private String birth = null;

    @InputFileColumn(columnIndex = 7)
    private String age = null;

    @InputFileColumn(columnIndex = 8)
    private String hometown = null;

    @InputFileColumn(columnIndex = 9)
    private String bloodtype = null;

    /**
     * codeを取得する。
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * codeを設定する。
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * nameを取得する。
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定する。
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * name_kanaを取得する。
     * @return name_kana
     */
    public String getName_kana() {
        return name_kana;
    }

    /**
     * name_kanaを設定する。
     * @param name_kana
     */
    public void setName_kana(String name_kana) {
        this.name_kana = name_kana;
    }

    /**
     * sexを取得する。
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * sexを設定する。
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * telnumを取得する。
     * @return telnum
     */
    public String getTelnum() {
        return telnum;
    }

    /**
     * telnumを設定する。
     * @param telnum
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * mailを取得する。
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * mailを設定する。
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * birthを取得する。
     * @return birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * birthを設定する。
     * @param birth
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * ageを取得する。
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * ageを設定する。
     * @param age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * hometownを取得する。
     * @return hometown
     */
    public String getHometown() {
        return hometown;
    }

    /**
     * hometownを設定する。
     * @param hometown
     */
    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    /**
     * bloodtypeを取得する。
     * @return bloodtype
     */
    public String getBloodtype() {
        return bloodtype;
    }

    /**
     * bloodtypeを設定する。
     * @param bloodtype
     */
    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public SampleFileLineObject(String code, String name, String name_kana,
            String sex, String telnum, String mail, String birth, String age,
            String hometown, String bloodtype) {
        this.code = code;
        this.name = name;
        this.name_kana = name_kana;
        this.sex = sex;
        this.telnum = telnum;
        this.mail = mail;
        this.birth = birth;
        this.age = age;
        this.hometown = hometown;
        this.bloodtype = bloodtype;
    }

    public SampleFileLineObject() {

    }

    public String toString() {
        return this.code;
    }

}
