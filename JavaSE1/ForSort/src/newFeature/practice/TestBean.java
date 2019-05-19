/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestBean
 * Author:   郭新晔
 * Date:     2019/3/16 0016 11:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature.practice;

/**
 * 〈〉
 * @create 2019/3/16 0016
 */
public class TestBean {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String mail;
    private double weight;
    // 0:女性;1:男性;2:未知
    private int gender;
    private boolean isChina;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isChina() {
        return isChina;
    }

    public void setChina(boolean china) {
        isChina = china;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", weight=" + weight +
                ", gender=" + gender +
                ", isChina=" + isChina +
                '}';
    }
}