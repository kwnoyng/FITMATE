package com.ssafy.finalPjt.model.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.finalPjt.model.dao.UserDao;
import com.ssafy.finalPjt.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean registUser(User user) {
		System.out.println(user.toString());
		return userDao.createUser(user) == 1;
	}

	@Override
	public String loginUser(String id, String pw) {

		String raw = pw;

		// sha512 암호화

		// -------
		System.out.println(id);
		User found_user = userDao.findUser(id);
		System.out.println(found_user);

		if (found_user == null)
			return "fail";
		else {
			String DB_pw = found_user.getPw();
			System.out.println(DB_pw.equals(pw));
			if (DB_pw.equals(pw))
				return "success";
			else
				return "wrong";
		}
	}

	@Override
	public User datailUser(String id) {
		return userDao.findUser(id);
	}

	@Override
	public void dropUser(String id) {
		System.out.println(id);
		userDao.deleteUser(id);
	}

	@Override
	public boolean modifyUser(User user) {
		User orginUser = userDao.findUser(user.getU_id());
		orginUser.setPw(user.getPw());
		orginUser.setEmail(user.getEmail());
		orginUser.setName(user.getName());
		orginUser.setPhone_no(user.getPhone_no());
		orginUser.setNickname(user.getNickname());
		orginUser.setGender(user.getGender());
		orginUser.setState_message(user.getState_message());

		return userDao.updateUser(orginUser) == 1;
	}

	@Override
	public boolean checkId(String id) {
		return (0 == userDao.chkUserID(id));
	}

	@Override
	public String checkJoin(User user) {
		if (0 != userDao.chkEmail(user.getEmail())) {
			System.out.println("중복 메일");
			return "dup_email";
		}

		if (0 != userDao.chkNickname(user.getNickname())) {
			System.out.println("중복 닉네임");
			return "dup_nickname";
		}

		if (0 != userDao.chkPhoneNo(user.getPhone_no())) {
			System.out.println("중복 번호");
			return "dup_phone";
		}

		return "성공";
	}

	@Override
	public HashMap<User, Double> findAllUser(String id, int dist) {
		HashMap<User, Double> map = new HashMap<>();
		User myUser = userDao.findUser(id);
		// 위도 : y, 경도 : x
		double myX = Double.parseDouble(myUser.getX());
		double myY = Double.parseDouble(myUser.getY());

		List<User> userList = (ArrayList<User>) userDao.findAllUser();

		for (int i = 0; i < userList.size(); i++) {
			// 거리계산 후 맵에 집어넣어버리기
			User user = userList.get(i);

			double userX = Double.parseDouble(user.getX());
			double userY = Double.parseDouble(user.getY());

			double theta = myX - userX;

			// k : 너와 나의 거리
			double k = Math.sin(myY * Math.PI / 180.0) * Math.sin(userY * Math.PI / 180.0)
					+ Math.cos(myY * Math.PI / 180.0) * Math.cos(userY * Math.PI / 180.0)
							* Math.cos(theta * Math.PI / 180.0);
//			System.out.println(k);

			k = Math.acos(k);
//			System.out.println(k);
			k = k * 180 / Math.PI;
//			System.out.println(k);
			k = k * 60 * 1.1515 * 1.609344;
//			System.out.println(k);

			if (k <= dist) {
				map.put(user, k);
			}

		}
		return map;
	}

	@Override
	public HashMap<String, String> getNicknames() {
		HashMap<String, String> res = new HashMap<String, String>();
		List<User> sqlResult = (List<User>) userDao.getNicknames();
		for (User user : sqlResult) {
			res.put(user.getNickname(), user.getU_id());
		}
		System.out.println(res);
		return res;
	}

}
